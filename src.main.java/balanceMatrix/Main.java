package balanceMatrix;

import java.util.Scanner;

/**
 * 小美的平衡矩阵
 * https://www.nowcoder.com/questionTerminal/d3a6268bbbf743f6b7441eb7ab30633e
 */

public class Main {

    /**
     * 二维数组前缀和
     * @param args
     */
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        // 读取n
        int n = in.nextInt();   // nextInt不会将行末尾的换行符读掉
        in.nextLine();  // 因此需要执行一次nextLine还读掉末尾的换行符
        // 读取接下来的n行矩阵并存入二维数组中
        int[][] matrix = new int[n][n];
        for (int i = 0; i < n; i++) {
            // 由于每行是以01串的形式输入，因此一行一行读
            String line = in.nextLine();
            for (int j = 0; j < n; j++) {
                matrix[i][j] = line.charAt(j) - '0';    // 字符转数字
            }
        }

        // 计算二维数组前缀和
        int[][] preSum = new int[n + 1][n + 1]; // 使用n+1是为了统一操作，否则计算第一行和第一列的前缀和时需要单独计算（小技巧）
        // preSum[i][j]代表matrix[0:i-1][0:j-1]的前缀和，当i=0或j=0时，前缀和为0
        for (int i = 1; i <= n; i++) {
            for (int j = 1; j <= n; j++) {
                // 左和上方的前缀和是计算出来了的
                preSum[i][j] = preSum[i][j-1] + preSum[i-1][j] - preSum[i-1][j-1] + matrix[i-1][j-1];
            }
        }

        // 计算各个大小的矩形区域的平衡矩阵个数
        for (int k = 1; k <= n; k++) {  // k为矩形宽度
            // 结果第奇数行（宽度为奇数）打印0，因为0，1数量不可能相等
            if (k % 2 != 0) {
                System.out.println(0);
                continue;
            }

            // 宽度为偶数
            int res = 0;
            int target = k * k / 2; // 边长为k的正方形区域要想平衡，1的个数
            for (int i = k; i <= n; i++) {
                for (int j = k; j <= n; j++) {
                    // 计算以 matrix[i-1][j-1]为右下角的宽度为k的矩形区域的元素和
                    int sum = preSum[i][j] - preSum[i-k][j] - preSum[i][j-k] + preSum[i-k][j-k];
                    if (sum == target) {
                        res++;
                    }
                }
            }
            System.out.println(res);
        }
    }
}
