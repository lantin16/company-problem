package longestChain;

import java.util.Arrays;
import java.util.Scanner;

/**
 * 树中找最长链节点数
 * https://mp.weixin.qq.com/s/MlReKdZrItOsdYklXBJvpQ
 * @Author lantin
 * @Date 2024/8/7
 */
public class Main {

    static final int N = 100007;
    // h[i]存储节点i的第一个邻接边在e和ne数组中的下标
    // e[i]表示第i条边指向的节点
    // ne[i]表示与第i条边同起点的下一条边在e数组中的下标。
    static int[] h = new int[N], e = new int[N * 2], ne = new int[N * 2];   // h：邻接表的头节点数组  e：邻接表的便数组  ne：邻接表的下一条边数组
    static int[] w = new int[N];    // 节点权值数组
    static int[] ffmin = new int[N], ffmax = new int[N];    // ffmin[i]：以i为根节点，满足父节点小于等于子节点的深度  ffmax[i]：以i为根节点，满足父节点大于等于子节点的深度
    static int idx = 0, res = 0;    // idx记录当前边的下标，用于在添加边时递增。

    /**
     * 构建树的邻接表，添加一条从节点a到b的边（看作有向边）
     * 比如说，当前要添加的边是 a -> b，属于所有边中编号idx的边
     * @param a
     * @param b
     */
    static void add(int a, int b) {
        e[idx] = b; // 记录当前边指向的节点
        ne[idx] = h[a]; // 当前边的下一条边就是记录在 h[a] 中的边编号，即从a指向其他节点的另一条边
        h[a] = idx++;   // 将当前边的编号存入 h[a]，并递增idx，如果下次a又有指向其他节点的边则现在的边就作为下一次边的ne
    }

    /**
     * 找到以节点u为根节点，权值非递减的最长路径（单边）
     * @param u
     * @param fa u的父节点
     * @return 从节点u开始向下的非递减路径的最大长度
     */
    static int dfs1(int u, int fa) {
        if (ffmin[u] != 0) return ffmin[u]; // 如果已计算过，直接返回
        int ma = 0, cma = 0;    // ma记录最长路径，cma记录次长路径
        for (int i = h[u]; i != -1; i = ne[i]) {    // 从节点u沿着边往下找，当i为-1代表没有下一条边了
            int j = e[i];   // 找到编号为i的边所指向的节点
            if (j == fa) {
                continue;  // 跳过父节点，因为是以u为根向下搜索
            }
            int tmax = dfs1(j, u);  // 递归计算子节点的最长路径
            if (w[u] <= w[j]) { // 权值非递减
                if (tmax >= ma) {
                    cma = ma;
                    ma = tmax;
                } else if (tmax > cma) {
                    cma = tmax;
                }
            }
        }
        if (ma > 0 && cma > 0) {
            res = Math.max(res, ma + cma + 1);  // 以节点u为中心，找到节点i为根的两条长子链（拼接）
        } else {
            res = Math.max(res, ma + 1);    // 只有以u为根的一条长子链
        }
        return ffmin[u] = ma + 1;   // 记录并返回当前节点u为根的权值非递减的最长路径
    }

    /**
     * 找到以节点u为根节点，权值非递增的最长路径（单边）
     * @param u
     * @param fa
     * @return 节点u开始向下的非递增路径的最大长度
     */
    static int dfs2(int u, int fa) {
        if (ffmax[u] != 0) return ffmax[u];
        int ma = 0, cma = 0;
        for (int i = h[u]; i != -1; i = ne[i]) {
            int j = e[i];
            if (j == fa) continue;
            int tmax = dfs2(j, u);
            if (w[u] >= w[j]) { // 权值非递增
                if (tmax >= ma) {   // 找到比ma还长的路径，则ma和cma都得更新
                    cma = ma;
                    ma = tmax;
                } else if (tmax > cma) {    // 只比cma长，则只更新cma
                    cma = tmax;
                }
            }
        }
        if (ma > 0 && cma > 0) {
            res = Math.max(res, ma + cma + 1);  // 以节点u为中心，找到节点i为根的两条长子链（拼接）
        } else {
            res = Math.max(res, ma + 1);    // 只有以u为根的一条长子链
        }
        return ffmax[u] = ma + 1;
    }

    /**
     * 树形dp，dfs记忆化搜索
     * （掌握）根据给定节点和边的编号构建树的邻接表
     * @param args
     */
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        Arrays.fill(h, -1);

        // 读取各节点权值
        for (int i = 1; i <= n; i++) {
            w[i] = sc.nextInt();
        }

        // 读取树的 n-1 条边，构建邻接表
        for (int i = 1; i < n; i++) {
            int a = sc.nextInt();
            int b = sc.nextInt();
            add(a, b);  // a -> b
            add(b, a);  // b -> a
        }

        dfs1(1, -1);
        dfs2(1, -1);
        System.out.println(res);
        sc.close();
    }
}
