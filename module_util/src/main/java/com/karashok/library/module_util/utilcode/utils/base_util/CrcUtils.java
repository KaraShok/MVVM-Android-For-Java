package com.karashok.library.module_util.utilcode.utils.base_util;

/**
 * @author Ralf(wanglixin)
 *         DESCRIPTION
 *         CRC16校验
 * @name CRC16Utils
 * @date 2018/04/17 下午3:30
 **/
public class CrcUtils {

    public static int Crc16(byte[] Buf, int len) {
        int CRC;
        int i, j;
        CRC = 0xffff;
        for (i = 0; i < len; i++) {
            CRC = CRC ^ (Buf[i] & 0xff);
            for (j = 0; j < 8; j++) {
                if ((CRC & 0x01) == 1)
                    CRC = (CRC >> 1) ^ 0xA001;
                else
                    CRC = CRC >> 1;
            }
        }
        return CRC;
    }
}
