package friendship;

import java.io.*;
import java.util.*;

/**
 * 小美的朋友关系
 * https://www.nowcoder.com/questionTerminal/e03f133cbabb41c8abfc3c7d4137bc6f
 *
 * tips：
 * 1. 超时的话首先考虑一下把 Scanner 和 println 换成 BufferedReader 和 PrinterWriter（记得手动刷新流，确保所有数据被输出）
 * （本题用BufferedReader和PrinterWriter测试用例能过得多些，但仍然不能AC）
 * 2. n 的规模超过 10^6 ，需要离散化。如果用总人数n来初始化并查集那么会报OOM堆溢出，因为n太大需要创建的并查集节点就很多。
 *  因此需要离散化处理，只将涉及到朋友关系的双方的编号重新映射到 1 ~ idx 新的编号，对于和其他人没有关系的人则后续判断可达性不用管它们，
 *  因此只需要用重新映射后的人的数量（涉及朋友关系的人的数量）来初始化并查集
 * 3. 注意逆序添加边/关系的时候要先验证初始是否存在关系再添加，否则可能会由于测试用例中存在删除无效的边导致逆序添加了错误的边
 * 4. 保存关系对(u,v)的时候，将较小的编号放前面，较大的放后面，这样在Set比较关系对的时候方便判断是否为相同的关系对。
 * 5. 由于ArrayList实现了equals方法，当集合中元素相同时就认为是相等的集合，因此用它来保存关系对。如果用 Integer[2] 来保存，Set就
 *  无法根据内容判断相同的关系对
 */

public class Solution {


    static Set<List<Integer>> initRela, rela;    // 初始的朋友关系，当前的朋友关系（由于操作可能删除）
    static int[][] ops;    // 操作记录
    static int idx;    // 离散化后的索引计数器
    static Map<Integer, Integer> mp;   // 编号离散化映射表
    static Deque<String> resStk;   // 存储输出的结果
    /**
     * 反向并查集
     * 并查集不方便删除，故采用反向并查集。对于每个淡忘操作(u,v)，若初始存在关系(u,v)，则此操作前均存在关系(u,v)，此操作后均不存在关系(u,v)。
     * 因此，可以记录下初始的关系，然后记录所有操作后仍保留的关系。利用最终保留的关系先构建并查集，再逆序进行操作，对于删除关系的操作变成逆序添加关系（union），
     * 同时用栈记录查询操作结果，最后正向输出即为答案。
     * @param args
     */
    public static void main(String[] args) throws IOException {
        // Scanner in = new Scanner(System.in);
        // int n = in.nextInt();   // 总人数
        // int m = in.nextInt();   // 初始的朋友关系数量
        // int q = in.nextInt();   // 发生的事件数量

        // 用 BufferedReader读
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String line = br.readLine();    // 读一行
        String[] strs = line.trim().split(" "); // 按照空格切分
        // 将其中的每个数值读出
        int n = Integer.parseInt(strs[0]);  // 总人数
        int m = Integer.parseInt(strs[1]);   // 初始的朋友关系数量
        int q = Integer.parseInt(strs[2]);   // 发生的事件数量

        // 初始化
        initRela = new HashSet<>(m);
        rela = new HashSet<>();
        ops = new int[q][3];
        idx = 0;
        mp = new HashMap<>();
        resStk = new LinkedList<>();

        // 读取并记录初始的朋友关系
        for(int i = 0; i < m; i++) {
            // int u = dsz(in.nextInt());
            // int v = dsz(in.nextInt());

            line = br.readLine();
            strs = line.trim().split(" ");
            int u = dsz(Integer.parseInt(strs[0]));
            int v = dsz(Integer.parseInt(strs[1]));
            List<Integer> pair = new ArrayList<>(List.of(Math.min(u, v), Math.max(u, v)));  // 关系对中，小的放前面，大的放后面
            initRela.add(pair);
            rela.add(pair);
        }

        // 读取发生的事件
        for(int i = 0; i < q; i++) {
            // int op = in.nextInt();
            // int u = dsz(in.nextInt());
            // int v = dsz(in.nextInt());

            line = br.readLine();
            strs = line.trim().split(" ");
            int op = Integer.parseInt(strs[0]);
            int u = dsz(Integer.parseInt(strs[1]));
            int v = dsz(Integer.parseInt(strs[2]));
            List<Integer> pair = new ArrayList<>(List.of(Math.min(u, v), Math.max(u, v)));
            if (op == 1) {
                rela.remove(pair);  // 移除删除的关系
            }
            ops[i] = new int[] {op, pair.get(0), pair.get(1)};
        }

        // 注意这里传的是idx（涉及到朋友关系的人数）而不要传总人数 n，否则可能发生OOM
        UnionFind uf = new UnionFind(idx);

        // 先对最后仍保留下来的关系进行合并
        for (List<Integer> pair : rela) {
            uf.union(pair.get(0), pair.get(1));
        }

        // 逆序操作，反向构建并查集
        for (int j = q - 1; j >= 0; j--) {
            if (ops[j][0] == 1) {   // 添加关系操作（逆序删除变添加）
                List<Integer> pair = new ArrayList<>(List.of(ops[j][1], ops[j][2]));
                if (initRela.contains(pair)) {  // 防止删除的是不存在的边，这里如果不检查的话会添加错误的边，因此只有在初始时存在的边这里才逆序添加回去
                    uf.union(ops[j][1], ops[j][2]);
                }
            } else {    // 查询关系操作
                if (uf.find(ops[j][1]) == uf.find(ops[j][2])) {
                    resStk.push("Yes");
                } else {
                    resStk.push("No");
                }
            }
        }

        // 用打印流来输出
        PrintWriter pw = new PrintWriter(new OutputStreamWriter(System.out));
        // 正序打印结果
        while(!resStk.isEmpty()) {
            // System.out.println(resStk.pop());
            pw.println(resStk.pop());
        }
        pw.flush(); // 记得手动刷新流，确保所有数据被输出
    }

    /**
     * 将编号x转换为离散化后的值（只有处于关系中的人的编号才离散化）
     * @param x
     * @return
     */
    public static int dsz(int x) {
        if (!mp.containsKey(x)) {   // 如果x还没有离散化过
            mp.put(x, ++idx);
        }
        return mp.get(x);   // 返回离散化后的值
    }


    /**
     * 并查集
     */
    static class UnionFind {
        int[] parent;   // 快速找到父节点
        int[] rank;

        public UnionFind(int n) {
            parent = new int[n + 1];
            rank = new int[n + 1];
            for (int i = 1; i <= n; i++) {  // 与1开始的编号对应，因此下标从1开始
                parent[i] = i;   // 初始时每个节点的上级都是自己
                rank[i] = 1;
            }
        }

        /**
         * 查找x的根节点
         * @param x
         * @return
         */
        public int find(int x) {
            if (parent[x] == x) {
                return x;
            }
            return parent[x] = find(parent[x]);
        }

        /**
         * 合并x，y所在的集合
         * @param x
         * @param y
         */
        public void union(int x, int y) {
            int xRoot = find(x), yRoot = find(y);
            if (xRoot == yRoot) {   // x，y已经在同一个集合中了
                return;
            }
            if (rank[xRoot] > rank[yRoot]) {
                parent[yRoot] = xRoot;
            } else {
                if (rank[xRoot] == rank[yRoot]) {
                    rank[yRoot]++;
                }
                parent[xRoot] = yRoot;
            }
        }
    }
}
