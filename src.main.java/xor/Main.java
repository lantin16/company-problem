package xor;

import java.util.Scanner;

/**
 * 异或和
 * https://mp.weixin.qq.com/s/UDJ-lx9hjjZJsQECfKoHSw
 */
public class Main {

    static int mod = 1000000007;

    /**
     * 动态规划 + 前缀和优化
     * 序列的题定义dp时要加上以xxx结尾才好递推
     * @param args
     */
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int n = in.nextInt(), m = in.nextInt();
        // dp[i][j][k]：考虑序列的前i个数，且最后一个数是j，异或和为k的方案数
        int[][][] dp = new int[n + 1][m + 1][m + 1];
        // 递推公式：dp[i][j][k] = sum{dp[i-1][l][k^j]}，其中 l <= j

        // TODO

    }
}
