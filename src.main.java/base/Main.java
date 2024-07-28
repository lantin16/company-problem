package base;

import java.util.Scanner;

/**
 * base
 * https://mp.weixin.qq.com/s/UDJ-lx9hjjZJsQECfKoHSw
 */

public class Main {
    // static char[] num;   // 记录数字/字母代表的数值

    /**
     * 这题只用计算转换后的数中1的个数，并不需要得到转换后的数，因此只需要在转换的同时记录1的个数即可
     * @param args
     */
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int n = in.nextInt();   // 十进制数
        int maxOne = 0;

        // // 初始化num数组
        // num = new char[36];
        // // 0~9
        // for(int i = 0; i < 10; i++) {
        //     num[i] = (char) ('0' + i);
        // }
        // // a~z
        // for(int i = 10; i < 36; i++) {
        //     num[i] = (char) ('a' + i - 10);
        // }

        for (int base = 2; base <= 36; base++) {    // 进制
            int tmp = n;
            int count = 0;
            // 进制转换并计算1的个数
            // 其实余数最后倒序才是转换后的数，不过这不重要了，因为需要的只是1的个数
            while(tmp != 0) {
                int remain = tmp % base;    // 余数就是转换后的数的一位数字/字母
                if (remain == 1) {
                    count++;
                }
                tmp /= base;
            }

            // 维护maxOne
            maxOne = Math.max(maxOne, count);
        }
        System.out.println(maxOne);
    }


}
