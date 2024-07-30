package binaryGraph;

import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

/**
 * 二分图
 * https://mp.weixin.qq.com/s/s6k0iDABSFbpg92wHdypnA
 */

public class Main {

    static List<Integer>[] adj; // 存储边（类似邻接矩阵）
    static int[] color; // 记录节点染色情况，避免重复染色
    static int cnt1 = 0, cnt2 = 0;  // 两个集合的节点数

    /**
     * dfs + 染色法
     * 二分图：设 G=(V,E) 是一无向图，若顶点 V 可分割为两个互不相交的子集 (A,B)，且图中的每条边（i,j）所关联的两个顶点 i 和 j 分属这两个不同的顶点集 (i ∈ A,j ∈ B)，则称图 G 为一个二分图。
     * 树是一个无环连通图，任何两个节点之间有且只有一条路径。树本身天然是一个二分图。
     * 我们可以通过染色法将树的节点分为两个集合 U 和 V。从树的某一个节点开始，交替地将相邻节点染成不同的颜色（集合），即可实现将树划分为两个集合。
     * 只要是在两个集合的节点之间连边，则一定还是二分图。
     * 在一个二分图中，最大边数为 U x V，但是在我们已经有的树结构中，我们已经有 n−1 条边，因此我们最多还能添加的边数为 U x V - (n - 1)
     *
     * @param args
     */
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int n = in.nextInt();   // 节点数
        adj = new List[n + 1];
        for (int i = 0; i < n - 1; i++) {
            int u = in.nextInt(), v = in.nextInt();
            adj[u].add(v);
            adj[v].add(u);
        }

        // n个节点编号 1~n
        color = new int[n + 1];
        Arrays.fill(color, -1); // -1代表未染色

        // dfs遍历节点并染色
        dfs(1, 1);  // 从1开始染色

        // 计算可以添加的最大边数（二分图能够有的最大边数 - 树已经有的边数）
        System.out.println(cnt1 * cnt2 - (n - 1));
    }

    public static void dfs(int node, int c) {
        color[node] = c;
        if (c == 1) {
            cnt1++;
        } else {
            cnt2++;
        }

        // 将与node相邻的未染色节点染成另一种颜色
        for (int neighbor : adj[node]) {
            if (color[neighbor] == -1) {
                dfs(neighbor, 3 - c);   // node为颜色1则neighbor染成颜色2，node为颜色2则neighbor染成颜色1
            }
        }
    }

}
