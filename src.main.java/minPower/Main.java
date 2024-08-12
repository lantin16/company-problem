package minPower;

import java.util.Scanner;

/**
 * 无人机最低初始电量
 * https://mp.weixin.qq.com/s/TgPX7fd_vroUulgs2eBdGQ
 * @Author lantin
 * @Date 2024/8/12
 */
public class Main {

    /**
     * 动态规划 + 网格迷宫问题
     * @param args
     */
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int m = sc.nextInt(), n = sc.nextInt();
        int[][] matrix = new int[m][n];
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                matrix[i][j] = sc.nextInt();
            }
        }
        sc.close();

        // dp[i][j]：从matrix[i][j]到终点所需的最低初始电量
        int[][] dp = new int[m][n];
        // 初始化
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                dp[i][j] = Integer.MAX_VALUE;
            }
        }
        dp[m - 1][n - 1] = Math.max(1 - matrix[m - 1][n - 1], 1);   //  1 是因为最低电量不能低于 1（否则坠毁）

        // 从右下往左上倒推
        for (int i = m - 1; i >= 0; i--) {
            for (int j = n - 1; j >= 0; j--) {
                // 注意边界越界问题
                if (j + 1 < n) {    // 向右走到maxtrix[i][j+1]
                    dp[i][j] = Math.min(dp[i][j], Math.max(1, dp[i][j + 1] - matrix[i][j]));    // 注意最低电量至少为1
                }
                if (i + 1 < m) {    // 向右走到maxtrix[i+1][j]
                    dp[i][j] = Math.min(dp[i][j], Math.max(1, dp[i + 1][j] - matrix[i][j]));
                }
            }
        }
        System.out.println(dp[0][0]);
    }
}
