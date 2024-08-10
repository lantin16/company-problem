package deleteArray;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

/**
 * 小美的数组删除
 * https://mp.weixin.qq.com/s/5BaXwpLG9d--Qoto9YZybA
 * @Author lantin
 * @Date 2024/8/10
 */
public class Main {

    /**
     * 动态规划 + 维护动态最小未出现的非负整数
     * @param args
     */
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int T = sc.nextInt();   // 测试数据组数
        while(T-- > 0) {
            long n = sc.nextLong();
            long k = sc.nextLong();
            long x = sc.nextLong();

            long[] arr = new long[(int) n];
            for (int i = 0; i < n; i++) {
                arr[i] = sc.nextLong();
            }

            // dp[i]：按照规则删除arr[i]及之后的元素的消耗
            long[] dp = new long[(int) (n + 1)];
            Arrays.fill(dp, Long.MAX_VALUE);
            dp[(int) n] = 0; // arr[n]不存在，所以相当于没消费
            Set<Long> vst = new HashSet<>();    // 记录从后往前出现的元素
            long suffix_MEX = 0;    // 未出现的最小非负整数

            // 遍历顺序，从后往前
            for (int i = (int) (n - 1); i >= 0; i--) {
                vst.add(arr[i]);
                // 找出arr[i:n-1]中未出现的最小非负整数
                while(vst.contains(suffix_MEX)) {
                    suffix_MEX++;
                }

                // arr[i:n-1]这一段有两种删法：arr[i]单独删，后面的再看；或者arr[i:n-1]整体删
                dp[i] = Math.min(x + dp[i + 1], k * suffix_MEX);
            }
            System.out.println(dp[0]);  // dp[0]就是删除整段arr[0:n-1]的最小消耗
        }
        sc.close();
    }
}
