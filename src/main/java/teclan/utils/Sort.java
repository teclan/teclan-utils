package teclan.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Sort {
    private static final Logger LOGGER = LoggerFactory.getLogger(Sort.class);

    /**
     * 冒泡升序排列
     * 
     * @param source
     * @return
     */
    public static int[] bubbleSortASC(int[] source) {
        int length = source.length;
        int tmp;
        for (int i = 0; i < length; i++) {
            for (int j = i + 1; j < length; j++) {

                if (source[i] > source[j]) {
                    tmp = source[i];
                    source[i] = source[j];
                    source[j] = tmp;
                }
                display(source);
            }
        }
        return source;
    }

    /**
     * 冒泡降序排列
     * 
     * @param source
     * @return
     */
    public static int[] bubbleSortDESC(int[] source) {
        int length = source.length;
        int tmp;
        for (int i = 0; i < length; i++) {
            for (int j = i + 1; j < length; j++) {

                if (source[i] < source[j]) {
                    tmp = source[i];
                    source[i] = source[j];
                    source[j] = tmp;
                }
                display(source);
            }
        }
        return source;
    }

    /**
     * 选择升序排序
     * 
     * @param source
     * @return
     */
    public static int[] SelectSortASC(int[] source) {

        int length = source.length;
        int tmp;
        for (int i = 0; i < length; i++) {
            for (int j = i + 1; j < length; j++) {
                if (source[i] > source[j]) {
                    tmp = source[i];
                    source[i] = source[j];
                    source[j] = tmp;
                }
            }
            display(source);
        }
        return source;
    }

    /**
     * 选择降序排序
     * 
     * @param source
     * @return
     */
    public static int[] SelectSortDESC(int[] source) {

        int length = source.length;
        int tmp;
        for (int i = 0; i < length; i++) {
            for (int j = i + 1; j < length; j++) {
                if (source[i] < source[j]) {
                    tmp = source[i];
                    source[i] = source[j];
                    source[j] = tmp;
                }
            }
            display(source);
        }
        return source;
    }

    /**
     * 插入升序排序
     * 
     * @param source
     * @return
     */
    public static int[] InsertSortASC(int[] source) {
        int length = source.length;
        int temp;

        for (int i = 1; i < length; i++) {
            temp = source[i];
            int j = i - 1;
            while (j >= 0 && source[j] > temp) {
                source[j + 1] = source[j];
                j--;
            }
            source[j + 1] = temp;
            display(source);
        }
        return source;
    }

    /**
     * 插入降序排序
     * 
     * @param source
     * @return
     */
    public static int[] InsertSortDESC(int[] source) {
        int length = source.length;
        int temp;

        for (int i = 1; i < length; i++) {
            temp = source[i];
            int j = i - 1;
            while (j >= 0 && source[j] < temp) {
                source[j + 1] = source[j];
                j--;
            }
            source[j + 1] = temp;
            display(source);
        }
        return source;
    }

    /**
     * 希尔升序排列，默认初始增量是数组长度的一半取整
     * 
     * @param source
     * @return
     */
    public static int[] ShellSortASC(int[] source) {
        return ShellSortASC(source, source.length / 2);
    }

    /**
     * 希尔升序排列
     * 
     * @param source
     * @param inc
     *            增量
     * @return
     */
    public static int[] ShellSortASC(int[] source, int inc) {
        int length = source.length;
        if (inc > length) {
            LOGGER.error("给定增量有误，增量应该小于数组长度 增量:{},数组长度:{}", inc, length);
            return null;
        }
        int temp;
        do {
            for (int i = 0; i < inc; i++) {
                for (int j = i; j + inc < length; j += inc) {
                    if (source[j] > source[j + inc]) {
                        temp = source[j];
                        source[j] = source[j + inc];
                        source[j + inc] = temp;
                    }
                }
                display(source);
            }
        } while (inc-- > 1);
        return source;
    }

    /**
     * 希尔降序排列，默认初始增量是数组长度的一半取整
     * 
     * @param source
     * @return
     */
    public static int[] ShellSortDESC(int[] source) {
        return ShellSortDESC(source, source.length / 2);
    }

    /**
     * 希尔降序排列
     * 
     * @param source
     * @param inc
     *            增量
     * @return
     */
    public static int[] ShellSortDESC(int[] source, int inc) {
        int length = source.length;
        if (inc > length) {
            LOGGER.error("给定增量有误，增量应该小于数组长度 增量:{},数组长度:{}", inc, length);
            return null;
        }
        int temp;
        do {

            for (int i = 0; i < inc; i++) {
                for (int j = i; j + inc < length; j += inc) {
                    if (source[j] > source[j + inc]) {
                        temp = source[j];
                        source[j] = source[j + inc];
                        source[j + inc] = temp;
                    }
                }
                display(source);
            }
        } while (inc-- > 1);
        return source;
    }

    private static void display(int[] source) {
        StringBuilder result = new StringBuilder();
        for (int i : source) {
            result.append(String.format("%d\t", i));
        }
        LOGGER.info("{}", result.toString());
    }

}
