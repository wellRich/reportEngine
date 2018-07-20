package cn.guoyka.simplermybatis.util;

import java.util.UUID;

/**
 * 生成uuid的工具类
 */
public class KeyGreaterUtil {
    public static String greater(char flag){
        int ran=(int)(Math.random()*900)+100;
        return flag+String.valueOf(ran)+generateShortUuid();
    }

    private static String[] chars = new String[] { "0", "1", "2", "3", "4", "5",
            "6", "7", "8", "9", "A", "B", "C", "D", "E", "F", "G", "H", "I",
            "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V",
            "W", "X", "Y", "Z" };


    private static String generateShortUuid() {
        StringBuffer shortBuffer = new StringBuffer();
        String uuid = UUID.randomUUID().toString().replace("-", "");
        for (int i = 0; i < 16; i++) {
            String str = uuid.substring(i * 2, i * 2 + 2);
            int x = Integer.parseInt(str, 16);
            shortBuffer.append(chars[x % 36]);
        }
        return shortBuffer.toString();

    }

    private KeyGreaterUtil(){}
}
