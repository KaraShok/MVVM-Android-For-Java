package com.karashok.library.module_util.utilcode.utils.base_util;

import android.bluetooth.BluetoothGattCharacteristic;
import android.text.format.Time;

import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.Charset;

/**
 * @author Ralf(wanglixin)
 * DESCRIPTION
 * @name ByteUtils
 * @date 2018/06/26 下午4:00
 **/
public class ByteUtils {

    /**
     * char 转成 byte
     * @param chars 带转换的 char
     * @return 字节
     */
    public static byte[] charsToBytes(char[] chars) {
        Charset cs = Charset.forName("US-ASCII");
        CharBuffer cb = CharBuffer.allocate(chars.length);
        cb.put(chars);
        cb.flip();
        ByteBuffer bb = cs.encode(cb);

        return bb.array();

    }

    /**
     * 字节转成 char
     * @param bytes 字节
     * @return
     */
    public static char[] bytesToChars(byte[] bytes) {
        Charset cs = Charset.forName("US-ASCII");
        ByteBuffer bb = ByteBuffer.allocate(bytes.length);
        bb.put(bytes);
        bb.flip();
        CharBuffer cb = cs.decode(bb);

        return cb.array();
    }

    /**
     * 获取当前系统时间--年月日时分秒
     *
     * @return
     */
    public static String getFullTime() {

        Time t = new Time();
        t.setToNow(); // 取得系统时间。

        int year = t.year;
        int month = t.month + 1;
        int day = t.monthDay;
        int hour = t.hour;
        int minute = t.minute;
        int second = t.second;

        String hourStr = getTimeHexString(hour);
        String minuteStr = getTimeHexString(minute);
        String secondStr = getTimeHexString(second);
        String monthStr = getTimeHexString(month);
        String dayStr = getTimeHexString(day);

        // 年
        String yearStr = String.valueOf(year).substring(2);
        String yearOfLastTwo = Integer.toHexString(Integer.valueOf(yearStr));
        return yearOfLastTwo + monthStr + dayStr + hourStr + minuteStr + secondStr;
    }

    /**
     * 将日期转为16进制，少于16的补充0
     * @param value
     * @return
     */
    private static String getTimeHexString(int value){

        String hexStr;
        if (value < 16){
            hexStr  = "0" + Integer.toHexString(value);
        }
        else {
            hexStr  = Integer.toHexString(value);

        }
        return  hexStr;
    }
    /**
     * 获取当前系统时间--分钟和秒
     *
     * @return
     */
    public static String getTimeNow() {

        Time t = new Time(); // or Time t=new Time("GMT+8"); 加上Time Zone资料
        t.setToNow(); // 取得系统时间。

        int minute = t.minute;
        int second = t.second;

        return getTimeHexString(minute) + getTimeHexString(second);
    }

    /**
     * Returns the manufacture name from the given characteristic
     *
     * @param characteristic
     * @return manfacture_name_string
     */
    public static String getManufacturerNameString(
            BluetoothGattCharacteristic characteristic) {
        String manfacture_name_string = characteristic.getStringValue(0);
        return manfacture_name_string;
    }

    /**
     * Returns the model number from the given characteristic
     *
     * @param characteristic
     * @return model_name_string
     */

    public static String getModelNumberString(
            BluetoothGattCharacteristic characteristic) {
        String model_name_string = characteristic.getStringValue(0);

        return model_name_string;
    }

    /**
     * Returns the serial number from the given characteristic
     *
     * @param characteristic
     * @return serial_number_string
     */
    public static String getSerialNumberString(
            BluetoothGattCharacteristic characteristic) {
        String serial_number_string = characteristic.getStringValue(0);

        return serial_number_string;
    }

    /**
     * Returns the hardware number from the given characteristic
     *
     * @param characteristic
     * @return hardware_revision_name_string
     */
    public static String getHardwareRevisionString(
            BluetoothGattCharacteristic characteristic) {
        String hardware_revision_name_string = characteristic.getStringValue(0);

        return hardware_revision_name_string;
    }

    /**
     * Returns the Firmware number from the given characteristic
     *
     * @param characteristic
     * @return hardware_revision_name_string
     */
    public static String getFirmwareRevisionString(
            BluetoothGattCharacteristic characteristic) {
        String firmware_revision_name_string = characteristic.getStringValue(0);

        return firmware_revision_name_string;
    }

    /**
     * Returns the software revision number from the given characteristic
     *
     * @param characteristic
     * @return hardware_revision_name_string
     */
    public static String getSoftwareRevisionString(
            BluetoothGattCharacteristic characteristic) {
        String hardware_revision_name_string = characteristic.getStringValue(0);

        return hardware_revision_name_string;
    }

    /**
     * Returns the PNP ID from the given characteristic
     *
     * @param characteristic
     * @return {@link String}
     */
    public static String getPNPID(BluetoothGattCharacteristic characteristic) {
        final byte[] data = characteristic.getValue();
        final StringBuilder stringBuilder = new StringBuilder(data.length);
        if (data != null && data.length > 0) {
            for (byte byteChar : data)
                stringBuilder.append(String.format("%02X ", byteChar));
        }

        return String.valueOf(stringBuilder);
    }

    /**
     * Returns the SystemID from the given characteristic
     *
     * @param characteristic
     * @return {@link String}
     */
    public static String getSYSID(BluetoothGattCharacteristic characteristic) {
        final byte[] data = characteristic.getValue();
        final StringBuilder stringBuilder = new StringBuilder(data.length);
        if (data != null && data.length > 0) {
            for (byte byteChar : data)
                stringBuilder.append(String.format("%02X ", byteChar));
        }

        return String.valueOf(stringBuilder);
    }

    /**
     * bytes to hex string
     *
     * @param
     * @return
     */
    public static String byteArrayTohexString(byte[] bytes) {
        final String HEX = "0123456789abcdef";
        StringBuilder sb = new StringBuilder(bytes.length * 2);
        for (byte b : bytes) {
            // 取出这个字节的高4位，然后与0x0f与运算，得到一个0-15之间的数据，通过HEX.charAt(0-15)即为16进制数
            sb.append(HEX.charAt((b >> 4) & 0x0f));
            // 取出这个字节的低位，与0x0f与运算，得到一个0-15之间的数据，通过HEX.charAt(0-15)即为16进制数
            sb.append(HEX.charAt(b & 0x0f));
        }

        return sb.toString().toUpperCase();
    }

    /**
     * 字节转成 int 字符串
     * @param bytes
     * @return
     */
    public static String byteArrToIntStr(byte[] bytes) {
        StringBuilder sb = new StringBuilder();
        for (byte b : bytes) {
            sb.append(String.valueOf(b & 0xff) + " ");
        }
        return sb.toString();
    }

    /**
     * 16进制字符串转成字节数组
     * @param s
     * @return
     */
    public static byte[] hexStringToByteArray(String s) {
        if (s.length() % 2 != 0) {
            StringBuilder stringBuilder = new StringBuilder(s);
            stringBuilder.insert(s.length() - 1, "0");
            s = stringBuilder.toString();
        }


        int len = s.length();
        byte[] data = new byte[len / 2];
        for (int i = 0; i < len; i += 2) {
            data[i / 2] = (byte) ((Character.digit(s.charAt(i), 16) << 4)
                    + Character.digit(s.charAt(i + 1), 16));
        }
        return data;
    }


    /**
     * 判断是否是16进制字符
     * @param str
     * @return
     */
    public static boolean isRightHexStr(String str) {
        String reg = "^[0-9a-fA-F]+$";
        return str.matches(reg);
    }



    /**
     * Converting the Byte to binary
     *
     * @param bytes
     * @return {@link String}
     */
    public static String bytetoBinary(byte[] bytes) {
        StringBuilder sb = new StringBuilder(bytes.length * Byte.SIZE);
        for (int i = 0; i < Byte.SIZE * bytes.length; i++)
            sb.append((bytes[i / Byte.SIZE] << i % Byte.SIZE & 0x80) == 0 ? '0'
                    : '1');
        return sb.toString();
    }

    /**
     * 字节转成 ASCII 码
     * @param array 字节
     * @return
     */
    public static String byteToASCII(byte[] array) {

        StringBuffer sb = new StringBuffer();
        for (byte byteChar : array) {
            if (byteChar >= 32 && byteChar < 127) {
                sb.append(String.format("%c", byteChar));
            } else {
                sb.append(String.format("%d ", byteChar & 0xFF)); // to convert
                // >127 to
                // positive
                // value
            }
        }
        return sb.toString();
    }

}
