package com.polycis.main.common.util.script;

/**
 * @auther cheng
 * @date 2019-06-20
 */
public class FileSizeUtil {

    public static boolean fileSizeIsOK(int size){
        return size / 1024.0 < 48 ? true : false;
    }

    private static String getFileSizeWithUnit(long size) {
        /** 如果字节数少于1024，则直接以B为单位，否则先除于1024，后3位因太少无意义 */
        if (size < 1024) {
            return String.valueOf(size) + "B";
        } else {
            size = size / 1024;
        }
        /**如果原字节数除于1024之后，少于1024，则可以直接以KB作为单位
        因为还没有到达要使用另一个单位的时候
        接下去以此类推
        */
        if (size < 1024) {
            return String.valueOf(size) + "KB";
        } else {
            size = size / 1024;
        }
        if (size < 1024) {
            /**因为如果以MB为单位的话，要保留最后1位小数，
            因此，把此数乘以100之后再取余
             */
            size = size * 100;
            return String.valueOf((size / 100)) + "."+ String.valueOf((size % 100)) + "MB";
        } else {
            /** 否则如果要以GB为单位的，先除于1024再作同样的处理 */
            size = size * 100 / 1024;
            return String.valueOf((size / 100)) + "."+ String.valueOf((size % 100)) + "GB";
        }
    }

    public static void main(String[] args) {
//        System.out.println(getFileSizeWithUnit(51200));
//        System.out.println(getFileSizeWithUnit(200));
//        System.out.println(getFileSizeWithUnit(200000));
//        System.out.println(getFileSizeWithUnit(200000000));
//        System.out.println(getFileSizeWithUnit(2000000000));
        double d = 2830000 / 1024.0;
        System.out.println(d);
        System.out.println(48.0000001 < 48);
    }
}
