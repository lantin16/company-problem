package amiableOpsTask;

import java.util.*;

/**
 * 亲和调度任务组
 * https://mp.weixin.qq.com/s/RoUyVEQKepsJbtZd9FZTRw
 *
 * @Author lantin
 * @Date 2024/8/22
 */
public class Main {

    static List<Integer> choose;    // 用于存放当前选择了的任务
    static int maxNum = 0;  // 亲和调度组的最多数量任务
    static int minTime = Integer.MIN_VALUE;  // 亲和调度组的最小执行时间之和
    static int[] tasks;
    static Map<Integer, Set<Integer>> mutex;   // 不亲和任务关系

    /**
     * 回溯法尝试 + 剪枝
     * 数据量比较小，直接暴力法解决
     * @param args
     */
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int jobNum = sc.nextInt();
        tasks = new int[jobNum + 1];
        for (int i = 1; i <= jobNum; i++) {
            tasks[i] = sc.nextInt();
        }
        int mutexNum = sc.nextInt();
        mutex = new HashMap<>(); // 不亲和任务关系
        for (int i = 0; i < mutexNum; i++) {
            int t1 = sc.nextInt(), t2 = sc.nextInt();
            // 使用computeIfAbsent方法，它会自动处理键不存在的情况，即如果映射中没有给定的键，它会使用提供的函数来计算一个值并关联到给定键然后插入到映射中
            mutex.computeIfAbsent(t1, k -> new HashSet<>()).add(t2);
            mutex.computeIfAbsent(t2, k -> new HashSet<>()).add(t1);
        }
        sc.close();
        choose = new ArrayList<>();
        dfs(1, jobNum, 0);
        System.out.println(minTime);
    }

    /**
     * 从idx开始考虑选择
     * @param idx 考虑的起始编号
     * @param last 最后一个任务编号
     * @param sum 前面已选任务的时间和
     */
    private static void dfs(int idx, int last, int sum) {
        if (choose.size() + last - idx + 1 < maxNum) { // 剪枝，如果把从idx之后的任务全选上都不足以达到maxNum则不用考虑之后的情况了
            return;
        }

        // 从[idx,last]中选择一个任务
        for (int i = idx; i <= last; i++) {
            boolean conflict = false;
            if (mutex.containsKey(i)) {   // 如果i任务存在不亲和的任务
                Set<Integer> mutexSet = mutex.get(i);
                for (int ct : choose) {
                    if (mutexSet.contains(ct)) {    // i任务与已经选择的某个任务存在冲突
                        conflict = true;
                        break;
                    }
                }
            }
            // 如果i任务与前面已选的任务都没有冲突则本次可以尝试选
            if (!conflict) {
                choose.add(i);
                int curNum = choose.size(); // 当前亲和调度组的任务数量
                int curSum = sum + tasks[i];
                if (curNum > maxNum) {  // 有任务数更多的调度组
                    maxNum = curNum;
                    minTime = curSum;
                } else if (curNum == maxNum) {  // 任务数与之前最多的数量相等，则选择时间和更小的
                    minTime = Math.min(minTime, curSum);
                }
                dfs(i + 1, last, curSum);
                choose.remove(choose.size() - 1);   // 撤销操作，准备尝试其他情况
            }
        }
    }


}
