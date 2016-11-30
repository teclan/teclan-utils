package teclan.utils;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map.Entry;

import org.apache.tika.config.TikaConfig;
import org.apache.tika.io.TikaInputStream;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.mime.MediaType;
import org.apache.tools.zip.ZipEntry;
import org.apache.tools.zip.ZipOutputStream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;
import com.typesafe.config.ConfigValue;

public class FileUtils {
    private static final Logger LOGGER         = LoggerFactory
            .getLogger(FileUtils.class);

    private static final Config mediaTypes     = ConfigFactory
            .load("media-types.conf").getConfig("teclan");

    private static final Config mediaExtension = ConfigFactory
            .load("file-extensions.conf").getConfig("teclan");

    private static TikaConfig   tika;

    static {
        try {
            tika = new TikaConfig();
        } catch (Exception e) {
            LOGGER.error(e.getLocalizedMessage(), e);
        }
    }

    /**
     * @author Teclan
     * 
     *         获取文件编码
     * @param filePath
     * @return
     * @throws Exception
     */
    @SuppressWarnings("resource")
    public static String getCoding(String filePath) throws Exception {
        File file = new File(filePath);
        if (!file.isFile() || !file.exists()) {
            return "unkonw";
        }
        BufferedInputStream bis = new BufferedInputStream(
                new FileInputStream(filePath));
        int p = (bis.read() << 8) + bis.read();
        String code = null;
        switch (p) {
            case 0xefbb:
                code = "UTF-8";
                break;
            case 0xfffe:
                code = "Unicode";
                break;
            case 0xfeff:
                code = "UTF-16BE";
                break;
            default:
                code = "GBK";
        }
        return code;
    }

    /**
     * @author Teclan
     * 
     *         读取文本文件内容
     * @param file
     * @return 文本内容(字符串形式)
     */
    public static String getContent(File file) {
        StringBuilder content = new StringBuilder();
        try {
            String encoding = FileUtils.getCoding(file.getAbsolutePath())
                    .toUpperCase();

            if (encoding.contains("GBK")) {
                encoding = "GBK";
            } else {
                encoding = "UTF-8";
            }

            if (file.isFile() && file.exists()) { // 判断文件是否存在
                InputStreamReader read = new InputStreamReader(
                        new FileInputStream(file), encoding);// 考虑到编码格式
                BufferedReader bufferedReader = new BufferedReader(read);
                String line = null;
                while ((line = bufferedReader.readLine()) != null) {
                    content.append(line);
                }
                read.close();
            } else {
                LOGGER.error("找不到指定的文件:{}", file.getAbsolutePath());
            }
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            return null;
        }
        return content.toString();
    }

    /**
     * @author Teclan
     *         删除文件，如果是目录，则删除整个目录
     * @param file
     */
    public static void deleteFiles(File file) {
        if (!file.exists()) {
            LOGGER.warn("\nthe file {} is not exists!", file.getAbsolutePath());
        }
        if (file.isDirectory()) {
            File[] files = file.listFiles();
            for (int i = 0; i < files.length; i++) {
                deleteFiles(files[i]);
            }
        }
        file.delete();
    }

    /**
     * @author Teclan
     *         删除文件，如果是目录，则删除整个目录
     * @param filePath
     */
    public static void deleteFiles(String filePath) {
        File file = new File(filePath);
        deleteFiles(file);
    }

    /**
     * @author Teclan
     *         获取文件后缀
     * @param file
     */
    public static String getExtension(File file) {
        String fileName = file.getName();
        int dotIndex = fileName.lastIndexOf(".");
        if (dotIndex == -1) {
            LOGGER.warn("this name of file({}) doesn't with a extension",
                    file.getAbsolutePath());
            return "unknown file";
        }
        return fileName.substring(dotIndex + 1);
    }

    /**
     * @author Teclan
     *         获取本工具支持检测的文件类型
     * @param file
     */
    public static ArrayList<String> getSupportMediaType() {
        ArrayList<String> supportMedias = new ArrayList<String>();
        for (Entry<String, ConfigValue> contents : mediaTypes.entrySet()) {
            supportMedias.add(contents.getKey());
        }
        return supportMedias;
    }

    /**
     * @author Teclan
     *         获取本工具支持检测的文件后缀
     * @param file
     */
    public static ArrayList<String> getSupportExtension() {
        ArrayList<String> supportExtension = new ArrayList<String>();
        for (Entry<String, ConfigValue> contents : mediaExtension.entrySet()) {
            supportExtension.add(contents.getKey());
            LOGGER.info("{}", contents.getKey());
        }
        return supportExtension;
    }

    /**
     * @author Teclan
     *         获取指定类型文件匹配的类型
     * @param file
     */
    public static ArrayList<String> getMediaTypeMatch(String type) {
        List<Object> validTypes = new LinkedList<Object>();
        validTypes.addAll(mediaTypes.getList(type.toLowerCase()).unwrapped());
        ArrayList<String> typeMatchs = new ArrayList<String>();
        for (Object o : validTypes) {
            typeMatchs.add(o.toString());
        }
        return typeMatchs;
    }

    /**
     * @author Teclan
     *         获取指定后缀匹配的文件类型，如word，
     * @param file
     */
    public static ArrayList<String> getExtensionMatch(String extension) {
        List<Object> validTypes = new LinkedList<Object>();
        validTypes.addAll(
                mediaExtension.getList(extension.toLowerCase()).unwrapped());

        ArrayList<String> typeMatchs = new ArrayList<String>();
        for (Object o : validTypes) {
            typeMatchs.add(o.toString());
            LOGGER.info("{}", o.toString());
        }
        return typeMatchs;
    }

    /**
     * @author Teclan
     *         获取指定文件的真实文件类型
     * @param file
     */
    @SuppressWarnings("deprecation")
    public static String getMediaType(File file) {
        Metadata metadata = new Metadata();
        metadata.set(Metadata.RESOURCE_NAME_KEY, file.toString());
        List<Object> correspondTypes = new LinkedList<Object>();
        TikaInputStream in = null;
        try {
            in = TikaInputStream.get(file);
            MediaType mimetype = tika.getDetector().detect(in, metadata);
            for (String type : getSupportMediaType()) {
                correspondTypes.addAll(
                        mediaTypes.getList(type.toLowerCase()).unwrapped());
                if (correspondTypes
                        .contains(mimetype.getBaseType().toString())) {
                    return type;
                }
            }
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
        }
        return "unknown media type";
    }

    /**
     * @author Teclan
     *         通过文件后缀名获取指定文件的文件类型
     * @param file
     */
    public static String getMediaTypeWithExtension(File file) {
        List<Object> correspondTypes = new LinkedList<Object>();

        for (String type : getSupportExtension()) {
            correspondTypes.addAll(
                    mediaExtension.getList(type.toLowerCase()).unwrapped());
            if (correspondTypes.contains(getExtension(file))) {
                return type;
            }
        }
        return "unknown media type";
    }

    /**
     * @author Teclan
     *         判断指定文件是否是给定的文件类型
     * @param file
     * @param type
     */
    @SuppressWarnings("deprecation")
    public static boolean isSpecialType(File file, String type) {
        Metadata metadata = new Metadata();
        metadata.set(Metadata.RESOURCE_NAME_KEY, file.toString());
        List<Object> correspondTypes = new LinkedList<Object>();
        TikaInputStream in = null;
        try {
            in = TikaInputStream.get(file);
            MediaType mimetype = tika.getDetector().detect(in, metadata);
            correspondTypes
                    .addAll(mediaTypes.getList(type.toLowerCase()).unwrapped());
            if (correspondTypes.contains(mimetype.getBaseType().toString())) {
                return true;
            }
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
        }
        return false;
    }

    /**
     * 获取文件摘要
     * 
     * @param file
     * @param algorithm
     *            MD5,SHA-1,SHA-256
     * @return
     */
    public static String getFileSummary(File file, String algorithm) {
        if (!file.isFile()) {
            return null;
        }
        MessageDigest digest = null;
        FileInputStream in = null;
        byte buffer[] = new byte[1024];
        int len;
        try {
            digest = MessageDigest.getInstance(algorithm);
            in = new FileInputStream(file);
            while ((len = in.read(buffer, 0, 1024)) != -1) {
                digest.update(buffer, 0, len);
            }
            in.close();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        BigInteger bigInt = new BigInteger(1, digest.digest());
        return bigInt.toString(16);
    }

    /**
     * 压缩目录 {@code root} 至文件 {@code target}.zip
     * 
     * @param root
     *            压缩目录
     * @param target
     *            压缩后的文件名
     * @throws FileNotFoundException
     */
    public static void zip(String root, String target)
            throws FileNotFoundException {
        File zipDir;
        ZipOutputStream zipOut;
        zipDir = new File(root);
        String zipFileName = target + ".zip";
        try {
            zipOut = new ZipOutputStream(new BufferedOutputStream(
                    new FileOutputStream(zipFileName)));
            zipOut.setEncoding("GBK");
            zipDir(root, zipDir, zipOut);
            zipOut.close();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }

    /**
     * 压缩目录 {@code root} 至文件 {@code root}.zip
     * 
     * @param root
     *            压缩目录
     * @throws FileNotFoundException
     */
    public static void zip(String root) throws FileNotFoundException {
        File zipDir;
        ZipOutputStream zipOut;
        zipDir = new File(root);
        String zipFileName = zipDir.getName() + ".zip";
        try {
            zipOut = new ZipOutputStream(new BufferedOutputStream(
                    new FileOutputStream(zipFileName)));
            zipOut.setEncoding("GBK");
            zipDir(root, zipDir, zipOut);
            zipOut.close();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }

    private static void zipDir(String root, File dir, ZipOutputStream zipOut)
            throws IOException {
        FileInputStream fileIn;
        File[] files;
        byte[] buf = new byte[1024];
        int readedBytes;
        files = dir.listFiles();

        if (files.length == 0) {// 如果目录为空,则单独创建之.
            zipOut.putNextEntry(new ZipEntry(dir.toString() + "/"));
            zipOut.closeEntry();
        } else {
            for (File fileName : files) {
                if (fileName.isDirectory()) {
                    zipDir(root, fileName, zipOut);
                } else {
                    fileIn = new FileInputStream(fileName);
                    ZipEntry zipEntry = new ZipEntry(new String(fileName.toURI()
                            .getPath().replace(root, "").getBytes(), "GBK"));
                    zipEntry.setUnixMode(755);
                    zipOut.putNextEntry(zipEntry);

                    while ((readedBytes = fileIn.read(buf)) > 0) {
                        zipOut.write(buf, 0, readedBytes);
                    }
                    zipOut.closeEntry();
                }
            }
        }
    }
}
