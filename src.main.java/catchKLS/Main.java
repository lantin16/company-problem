package catchKLS;

import java.util.Scanner;

/**
 * 万万没想到之抓捕孔连顺
 * https://www.nowcoder.com/questionTerminal/c0803540c94848baac03096745b55b9b
 */
public class Main {

    static int mod = 99997867;

    /**
     * 动态规划 + 组合数
     * @param args
     */
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int n = in.nextInt(), d = in.nextInt();
        in.nextLine();
        int[] pos = new int[n];
        for(int i = 0; i < n; i++) {
            pos[i] = in.nextInt();
        }

        // 超时
        // long res = 0;
        // for(int l = 0; l < n - 2; l++) {
        //     for(int r = l + 2; r < n && pos[r] - pos[l] <= d; r++) {
        //         res += r - l - 1;
        //         res %= mod;
        //     }
        // }

        // dp[i]：在pos[0:i]中选位置的选择数
        long[] dp = new long[n];
        dp[0] = 0;
        dp[1] = 0;
        int l = 0;  // 满足条件的左边界
        for(int r = 2; r < n; r++) {
            // 对于每个右边界，从左往右找到第一个满足距离条件的左边界
            while(pos[r] - pos[l] > d) {
                l++;
            }
            if(r - l >= 2) {    // 左右边界之间至少有三栋楼
                dp[r] = dp[r - 1] + (long)(r - l) * (r - l - 1) / 2;  // 相较于dp[r-1]增加的种类数：以r为其中一个埋伏点，剩下的两个埋伏点在[l, r-1]中挑两个，组合数Cn2 = n(n-1)/2
                dp[r] %= mod;
            } else {    // 如果左边界与右边界之间不足三栋楼，则该右边界无法安排人，维持dp[r-1]
                dp[r] = dp[r - 1];
            }
        }

        System.out.println(dp[n - 1]);
    }
}
