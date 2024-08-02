package workTask;

import java.util.*;

/**
 * 最佳工作任务安排
 * https://mp.weixin.qq.com/s/nOL9zQXqB3qWSpP_GX0kfg
 * @Author lantin
 * @Date 2024/8/2
 */
public class Main {

    /**
     * 动态规划 + 用数组记录到达每个任务最短耗时的前驱任务以便回溯路径
     * @param args
     */
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int n = in.nextInt();   // 任务数
        int[] tasks = new int[n];
        for (int i = 0; i < n; i++) {
            tasks[i] = in.nextInt();
        }
        int maxStep = in.nextInt(); // 最大跳跃长度
        List<Integer> tl = new ArrayList<>();   // 记录任务执行方案
        // dp[i]：到达任务i的最短耗时
        int[] dp = new int[n];
        // from[i]：到达任务i的最短耗时是从哪个任务跳过来的（为了后续回溯找到最短耗时的路径）
        int[] from = new int[n];

        // 初始化
        Arrays.fill(dp, Integer.MAX_VALUE);
        dp[0] = 0;
        Arrays.fill(from, -1);  // -1代表没有上一个任务

        // 从前往后计算每个dp[i]并更新对应的from数组
        for (int i = 1; i < n; i++) {
            // -1代表该任务无法进行，不要跳到这
            if (tasks[i] == -1) {
                continue;
            }
            for (int k = 1; k <= maxStep; k++) {    // 考虑上一个任务
                if (i - k < 0) {
                    break;  // 如果 i - k 都小于0了，那么更前面的更加小于0
                }

                if (tasks[i - k] != -1 && dp[i - k] + tasks[i] < dp[i]) { // 找到更短的耗时方案
                    dp[i] = dp[i - k] + tasks[i];
                    from[i] = i - k;
                }
            }
        }

        // 回溯从from数组中找到最短耗时的路径
        if (from[n - 1] != -1) {   // 如果可以达到最终任务
            int idx = n - 1;
            while(idx != -1) {
                tl.add(idx + 1);    // 注意索引和任务编号相差1
                idx = from[idx];
            }
        }

        // 输出结果
        Collections.reverse(tl);    // 反转链表从起点到终点
        for (int t : tl) {
            System.out.print(t + " ");
        }
        in.close();
    }
}
