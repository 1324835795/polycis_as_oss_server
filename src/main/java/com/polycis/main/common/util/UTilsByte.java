package com.polycis.main.common.util;
import java.util.Calendar;
import java.util.GregorianCalendar;

public abstract class UTilsByte {
    public static void main(String args[]) throws Exception {
        //a000a0a1
        //a1a000a0

        byte[] key = new byte[]{(byte) 0xF1, (byte) 0x01, (byte) 0x10, (byte) 0x00,(byte) 0x00};
        byte[]date = new byte[2];
        date[0]= (byte) 1;
        date[1]=24;
        System.arraycopy(date, 0, key, 3, date.length);

        String s3 = UTilsByte.Bytes2HexString(key);
        System.out.println("发送数据"+s3);
      //  http://{{url}}:8084/downdata/datas?ecode=1&key=1234567890&devEUI=32343647024b0027&payload=AQIDBA==

        byte[] keys = new byte[]{(byte) 0xA1, (byte) 0xA0, (byte) 0x00, (byte) 0xA0};
        String s = bytes2hex03(keys);
        System.out.println(s);


        // 十进制转化为十六进制，结果为C8。
        Integer.toHexString(200);

        // 十六进制转化为十进制，结果140。
        Integer.parseInt("1C",16);
        System.out.println("16进制的数据"+ Integer.toHexString(123));

        String aa="f101110c930258812000021231231230";
        String id = aa.substring(8, 16);
        String substring = aa.substring(16, 22);
        String subs = aa.substring(22);
        System.out.println(id+"-------------"+substring+"-------"+subs+"数据长度"+subs.length());

        StringBuilder ss=new StringBuilder();
        int top= Integer.parseInt(substring);
            ss.append("第1包数据包内容："+top);
        int z=0;
        for(int i=0;i<subs.length()/3;i++){
            String a = subs.substring(z,z+3);
            int tos= Integer.parseInt(a);
            top=top+tos;
            ss.append("第"+(2+i)+"数据包内容："+top);
            z = z+3;
        }
        System.out.println("最终数据"+ss.toString());



//        for (int i = 0; i < ; i++) {
//
//        }

    }

    /**
     * 方式三
     *
     * @param bytes
     * @return
     */
    public static String bytes2hex03(byte[] bytes) {
        final String HEX = "0123456789abcdef";
        StringBuilder sb = new StringBuilder(bytes.length * 2);
        for (byte b : bytes) {

            // 取出这个字节的高4位，然后与0x0f与运算，得到一个0-15之间的数据，通过HEX.charAt(0-15)即为16进制数
            sb.append(HEX.charAt((b >> 4) & 0x0f));
            // 取出这个字节的低位，与0x0f与运算，得到一个0-15之间的数据，通过HEX.charAt(0-15)即为16进制数
            sb.append(HEX.charAt(b & 0x0f));
        }
        return sb.toString();
    }

    //获取当天的开始时间
    public static java.util.Date getDayBegin() {
        Calendar cal = new GregorianCalendar();
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return cal.getTime();
    }

    //获取当天的结束时间
    public static java.util.Date getDayEnd() {
        Calendar cal = new GregorianCalendar();
        cal.set(Calendar.HOUR_OF_DAY, 23);
        cal.set(Calendar.MINUTE, 59);
        cal.set(Calendar.SECOND, 59);
        return cal.getTime();
    }

    //数组转换成16进制的string
    public static String toHexString1(byte[] b) {
        StringBuffer buffer = new StringBuffer();
        for (int i = 0; i < b.length; ++i) {
            buffer.append(toHexString1(b[i]));
        }
        return buffer.toString();
    }

    public static String toHexString1(byte b) {
        String s = Integer.toHexString(b & 0xFF);
        if (s.length() == 1) {
            return "0" + s;
        } else {
            return s;
        }
    }

    /**
     * 16进制的字符串表示转成字节数组
     *
     * @param hexString 16进制格式的字符串
     * @return 转换后的字节数组
     **/
    public static byte[] toByteArray(String hexString) {
        hexString = hexString.toLowerCase();
        final byte[] byteArray = new byte[hexString.length() / 2];
        int k = 0;
        for (int i = 0; i < byteArray.length; i++) {//因为是16进制，最多只会占用4位，转换成字节需要两个16进制的字符，高位在先
            byte high = (byte) (Character.digit(hexString.charAt(k), 16) & 0xff);
            byte low = (byte) (Character.digit(hexString.charAt(k + 1), 16) & 0xff);
            byteArray[i] = (byte) (high << 4 | low);
            k += 2;
        }
        return byteArray;
    }
    //该方法等同于Integer.toBinaryString(b)

    public static String byte2bits(byte b) {
        int z = b;
        z |= 256;
        String str = Integer.toBinaryString(z);
        int len = str.length();
        return str.substring(len - 8, len);

    }

    //将二进制字符串转换回字节
    public static byte bit2byte(String bString) {
        byte result = 0;
        for (int i = bString.length() - 1, j = 0; i >= 0; i--, j++) {
            result += (Byte.parseByte(bString.charAt(i) + "") * Math.pow(2, j));
        }
        return result;
    }

    /**
     * int到byte[]
     *
     * @param i
     * @return
     */
    public static byte[] intToByteArray(int i) {
        byte[] result = new byte[4];
        //由高位到低位
        result[0] = (byte) ((i >> 24) & 0xFF);
        result[1] = (byte) ((i >> 16) & 0xFF);
        result[2] = (byte) ((i >> 8) & 0xFF);
        result[3] = (byte) (i & 0xFF);
        return result;
    }

    public static byte[] intToByteArraya(int a) {
        return new byte[]{(byte) (a & 0xFF), (byte) ((a >> 8) & 0xFF), (byte) ((a >> 16) & 0xFF), (byte) ((a >> 24) & 0xFF)};
    }

    public static byte[] int2Bytes(int value, int len) {
        byte[] b = new byte[len];
        for (int i = 0; i < len; i++) {
            b[len - i - 1] = (byte) ((value >> 8 * i) & 0xff);
        }
        return b;
    }

    //高位在前，低位在后
    public static byte[] int2Byte(int num) {
        byte[] result = new byte[4];
        result[0] = (byte) ((num >>> 24) & 0xff);//说明一
        result[1] = (byte) ((num >>> 16) & 0xff);
        result[2] = (byte) ((num >>> 8) & 0xff);
        result[3] = (byte) ((num >>> 0) & 0xff);
        return result;
    }

    //高位在前，低位在后
    public static int bytes2int(byte[] bytes) {
        int result = 0;
        if (bytes.length == 4) {
            int a = (bytes[0] & 0xff) << 24;//说明二
            int b = (bytes[1] & 0xff) << 16;
            int c = (bytes[2] & 0xff) << 8;
            int d = (bytes[3] & 0xff);
            result = a | b | c | d;
        }
        return result;
    }
    /**
     * 从一个byte[]数组中截取一部分
     *
     * @param src
     * @param begin
     * @param count
     * @return
     */
    public static byte[] subBytes(byte[] src, int begin, int count) {
        byte[] bs = new byte[count];
        for (int i = begin; i < begin + count; i++) {
            bs[i - begin] = src[i];
        }
        return bs;
    }
    /**
     * byte[] 转为16进制String
     */
    public static String Bytes2HexString(byte[] b) {
        String ret = "";
        for (int i = 0; i < b.length; i++) {
            String hex = Integer.toHexString(b[i] & 0xFF);
            if (hex.length() == 1) {
                hex = '0' + hex;
            }
            ret += hex.toUpperCase();
        }
        return ret;
    }
}
