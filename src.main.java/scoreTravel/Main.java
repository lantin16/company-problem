package scoreTravel;

import java.util.*;

/**
 * 大众评分最高的一次旅程
 * https://mp.weixin.qq.com/s/nOL9zQXqB3qWSpP_GX0kfg
 * @Author lantin
 * @Date 2024/8/2
 */
public class Main {

    private static int n, m, r;
    private static int[] scores, heights, rests;
    private static int maxScore = 0;    // 最高大众评分和
    private static List<Integer> bestPath = new ArrayList<>();   // 结果最优旅游路径
    private static List<Integer> bestRest = new ArrayList<>();   // 结果最优休息方案

    /**
     * 景点数比较少，可以暴力回溯
     * 递归地尝试选择每个景点，更新当前的路径和评分。
     * 检查是否需要休息亭，并根据需要进行休息，更新最优解和路径。注意，需要 比较两个列表的字典序，确定哪一个是字典序较小的。
     * @param args
     */
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        n = in.nextInt();
        m = in.nextInt();
        r = in.nextInt();

        scores = new int[n];  // 大众评分
        for (int i = 0; i < n; i++) {
            scores[i] = in.nextInt();
        }
        heights = new int[n]; // 景点海拔
        for (int i = 0; i < n; i++) {
            heights[i] = in.nextInt();
        }
        rests = new int[n];    // 是否有休息亭
        for (int i = 0; i < n; i++) {
            rests[i] = in.nextInt();
        }

        in.close();

        List<Integer> path = new ArrayList<>(); // 记录临时的路径
        List<Integer> restPath = new ArrayList<>(); // 记录临时的休息方案
        Set<Integer> visited = new HashSet<>();    // 已经玩过的景点

        // 尝试各种路径、方案
        backtrace(0, 0, 0, visited, path, restPath);

        // 打印结果
        System.out.println(maxScore);
        for (int place : bestPath) {
            System.out.print((place + 1) + " "); // 注意数组中景点索引从0开始，但实际编号从1开始
        }
        System.out.println();
        for (int r : bestRest) {
            System.out.print(r + " ");
        }
        System.out.println();
    }

    /**
     * 回溯
     * @param con 已经连续玩的景点数
     * @param cnt 已经玩过的景点数
     * @param score 已经玩过的景点评分
     * @param visited 玩过的景点编号
     * @param path 玩过的路径
     * @param restPath 已有的休息方案
     */
    private static void backtrace(int con, int cnt, int score, Set<Integer> visited, List<Integer> path, List<Integer> restPath) {
        // 先看评分，再看旅游路径，最后看休息方案（休息方案随旅游路径改变而改变）
        if (score > maxScore) { // 如果已经玩过的评分超过了当前最高评分，则更新
            bestPath = new ArrayList<>(path);
            bestRest = new ArrayList<>(restPath);
        } else if (score == maxScore) { // 如果评分等于当前最高分，则判断是否字典顺序更小来决定是否更新
            int cr = compareLists(path, bestPath);
            if (cr < 0) {   // 如果当前的旅游路径更好则全部换成当前的方案
                bestPath = new ArrayList<>(path);
                bestRest = new ArrayList<>(restPath);
            } else if (cr == 0 && compareLists(restPath, bestRest) < 0) {   // 如果评分和旅游路径相同但当前的休息方案更好则更换休息方案
                bestRest = new ArrayList<>(restPath);
            }
        }

        // 判断是否玩到了m个景点
        if (cnt == m) { // 达到游玩上限，结束
            return;
        }

        // 判断是否必须在当前景点休息
        boolean hasToRest = (con == r); // 连续玩了r个景点需要休息

        // 遍历所有景点，决定当前准备玩哪个景点
        for (int i = 0; i < n; i++) {
            if (visited.contains(i)) {
                continue;   // 玩过的景点直接跳过
            }

            if (!path.isEmpty() && heights[i] <= path.get(path.size() - 1)) {
                continue;   // 如果当前景点海拔不如上一个的高则不考虑
            }

            // 可以尝试游玩该景点
            path.add(i);
            visited.add(i);

            if (!hasToRest) {   // 在这里不是必须休息，则有两个选择：不休息继续玩后面的、休息重置精力
                if (rests[i] == 1) {    // 如果该景点可以休息
                    // 选择在这里休息
                    restPath.add(1);
                    backtrace(0, cnt + 1, score + scores[i], visited, path, restPath);
                    restPath.remove(restPath.size() - 1); // 撤销操作
                }
                // 如果该景点不允许休息或者允许休息但不打算在这里休息
                restPath.add(0);
                backtrace(con + 1, cnt + 1, score + scores[i], visited, path, restPath);
                restPath.remove(restPath.size() - 1);   // 撤销操作
            } else {    // 如果在该景点必须休息
                if (rests[i] == 1) {    // 该景点允许休息，那就刚好在这里休息
                    restPath.add(1);
                    backtrace(0, cnt + 1, score + scores[i], visited, path, restPath);
                    restPath.remove(restPath.size() - 1);
                }
                // 如果该景点不允许休息，则只能结束旅程，不继续向后递归了
            }

            // 撤销操作，准备尝试这次选其他景点玩
            visited.remove(i);
            path.remove(path.size() - 1);
        }

    }

    /**
     * 比较两个List的字典顺序，如果l1字典顺序小于l2则返回负数，相等返回0，大于返回正数
     * 优先逐位比较公共长度的部分（索引都不越界的部分），如果公共长度部分都相同则长度短的字典顺序小
     * @param l1
     * @param l2
     * @return
     */
    private static int compareLists(List<Integer> l1, List<Integer> l2) {
        int size1 = l1.size(), size2 = l2.size();
        int ms = Math.min(size1, size2);
        for (int i = 0; i < ms; i++) {
            if (!l1.get(i).equals(l2.get(i))) {
                return l1.get(i) - l2.get(i);
            }
        }
        // 如果ms内都是相同的则比较长度
        return size1 - size2;
    }
}
