##### 字节类工具

###### 字节转十六进制
```Java
String toHexString(byte b);
String toHexString(byte[] bytes);
```
###### 二进制转字符串
```Java
String toStringFromBcd(byte[] bytes);
```
###### 异或
```Java
byte[] xor(byte[] message, byte[] key);
```

##### 数据类工具

###### 字节数组与int,long相互转换
```Java
int bytes2Int(byte[] bytes);
byte[] int2Bytes(int i);

byte[] long2Bytes(long num);
long bytes2Long(byte[] byteNum);

```
###### 各进制数相互转换
```
String binary2Oct(String source);
String binary2Dec(String source);
String binary2Hex(String source)

String oct2Binary(String source);
String oct2Dec(String source)
String oct2Hex(String source);

String dec2Binary(int source);
String dec2Oct(int source);
String dec2Hex(int source);

String hex2Binary(String source)；
String hex2Oct(String source);
String hex2Dec(String source)；

```
##### Gson 
[查看支持的方法](https://github.com/teclan/teclan-utils/blob/master/src/main/java/teclan/utils/GsonUtils.java)

##### 字符串工具
###### 计算摘要(algorithm 可指定[MD5|SHA1])
```Java
String toHexDigest(String str, String algorithm);
```
###### 转十六进制字符串
```Java
toHexString(String str);
```
###### 其他
[查看源码](https://github.com/teclan/teclan-utils/blob/master/src/main/java/teclan/utils/Strings.java)

##### 文件工具
###### 获取文本文件编码
```Java
String getCoding(String filePath);
```
###### 获取文本文件内容(暂时只区分GBK和UTF-8两种编码，其他编码方式可能会乱码)
```Java
String getContent(File file);
```
###### 删除文件(如果指定文件是文件夹，那么将文件夹以及文件夹的内容删掉)
```Java
void deleteFiles(File file);
void deleteFiles(String filePath);
```
###### 获取文件后缀名
```Java
String getExtension(File file);
```
###### 获取支持探测的文件类型(Tika)
```Java
ArrayList<String> getSupportMediaType();
```
##### 获取支持识别的文件后缀
```Java
ArrayList<String> getSupportExtension();
```
###### 获取指定文件类型匹配的Tika探测类型
```Java
ArrayList<String> getMediaTypeMatch(String type);
```
###### 获取指定后缀的文件类型(如指定类型”word“，则"doc", "docx", "docm","dot","dotx","dotm"均支持，因其均属于“word”)
```Java
ArrayList<String> getExtensionMatch(String extension);
```
###### 获取指定文件的文件类型(Tika探测)
```Java
String getMediaType(File file);
```
###### 获取指定文件的文件类型(通过文件后缀名判断)
```Java
String getMediaTypeWithExtension(File file);
```
###### 判断文件是否是给定类型的(Tika探测)
```Java
boolean isSpecialType(File file, String type);
```
##### 获取文件摘要 (MD5,SHA-1,SHA-256)
```Java
public static String getFileSummary(File file, String algorithm);
```

