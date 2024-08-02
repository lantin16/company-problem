package damageUpper;

import java.util.Scanner;

/**
 * 小友策划游戏人物（砍竹子，剑指131）
 * https://mp.weixin.qq.com/s/nOL9zQXqB3qWSpP_GX0kfg
 * @Author lantin
 * @Date 2024/8/2
 */
public class Main {

    /**
     * dp
     * @param args
     */
    // public static void main(String[] args) {
    //     Scanner in = new Scanner(System.in);
    //     int upper = in.nextInt(), n = in.nextInt();
    //     // dp[i]：和为i的附加攻击力的最大乘积（至少拆两份）
    //     int[] dp = new int[n + 1];
    //     dp[0] = 0;
    //     dp[1] = 1;
    //
    //     for (int i = 2; i <= n; i++) {
    //         for(int k = 1; k <= i / 2; k++) {   // 后面一半其实不用考虑了，因为长的最好是切分跟短的长度相近乘积比较大
    //             dp[i] = Math.max(dp[i], Math.max(k * (i - k), k * dp[i - k]));  // 由于dp代表的就是把一个长度至少分两段的最大乘积，因此dp[i-j]代表i-j一定会再分
    //         }
    //     }
    //
    //     System.out.println(dp[n] > upper);
    // }


    /**
     * 贪心，尽可能拆成长度相等的段，最优段是3
     * @param args
     */
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int upper = in.nextInt(), n = in.nextInt();
        System.out.println(solve(n) > upper);
    }

    static int solve(int n) {
        if (n <= 3) {   // 长度小于3的由于要求必须切分故乘积最大只能为 len - 1
           return n - 1;
        }
        int num3 = n / 3;
        int remainder = n % 3;  // 最后的余数
        int tmp = (int)Math.pow(3, num3 - 1);   // 3^(k-1) 避免重复计算
        if (remainder == 0) {   // 余数为0，即总长度为3的整数倍，最优
            return tmp * 3;
        } else if (remainder == 1){ // 余数为1则从前面拿出一个3让凑一块拆成 2*2 乘积更大
            return tmp * 2 * 2;
        } else {    // 余数为2，不继续拆
            return tmp * 3 * 2;
        }
    }
}
