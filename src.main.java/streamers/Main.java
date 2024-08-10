package streamers;

import java.util.*;

/**
 * 小美的彩带
 * https://mp.weixin.qq.com/s/5BaXwpLG9d--Qoto9YZybA
 * @Author lantin
 * @Date 2024/8/10
 */
public class Main {
    private static final int MAXN = 2000007;

    private static long[] a = new long[MAXN];   // 存储输入的初始数组元素
    private static long[] ans = new long[MAXN];
    private static long[] hsh = new long[MAXN]; // 用于对数组 a[] 的值进行离散化处理。原数组中的值被转化为离散化后的顺序值。
    private static long[] vis = new long[MAXN]; // 用于记录每个元素上次出现的位置，用来处理重复元素的覆盖问题。
    private static long[] c = new long[MAXN];

    // 存储每个查询的左端点l、右端点r以及查询的编号id，以便后续按照查询的右端点排序处理。
    private static class Query {
        int l, r, id;
        Query(int l, int r, int id) {
            this.l = l;
            this.r = r;
            this.id = id;
        }
    }

    /**
     * 用于获取 x 的最低位 1 的值，例如 x = 6 (110)，则 lowbit(6) 为 2 (10)
     * 树状数组中，lowbit 用于确定当前下标的影响范围
     * @param x
     * @return
     */
    private static int lowbit(int x) {
        return x & -x;
    }

    /**
     * 用于更新树状数组 c[]，使下标 x 及其影响范围的值增加 k
     * 想要将a[x]增加k，则对应的树状数组上的父节点（区间和）也要跟着更新
     * @param x
     * @param k
     */
    private static void update(int x, int k) {
        for (int i = x; i < MAXN; i += lowbit(i)) {
            c[i] += k;
        }
    }

    /**
     * 用于获取从下标 1 到 x 的前缀和，即在树状数组 c[] 上累计影响范围的总和
     * 可以通过不断的-lowbit操作来实现求和
     * 本题的和指的是某段区间内不同颜色的个数和
     * @param x
     * @return
     */
    private static long getsum(int x) {
        long sum = 0;
        for (int i = x; i > 0; i -= lowbit(i)) {
            sum += c[i];
        }
        return sum;
    }

    /**
     * 离散化 + 离线处理（莫队）+ 树状数组
     * 树状数组介绍：https://blog.csdn.net/TheWayForDream/article/details/118436732
     * @param args
     */
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();  // 彩带长度
        int q = scanner.nextInt();  // 剪彩带次数

        List<Query> queries = new ArrayList<>();

        for (int i = 1; i <= n; i++) {
            a[i] = scanner.nextLong();
            hsh[i] = a[i];
        }

        // 对a[]进行离散化
        // 离散化是一种常用的算法技巧，通常用于处理数据范围过大且数据本身不太重要的场景。
        // 其核心思想是将原数据映射到一个连续且较小的整数范围内，以便在后续操作中能够更高效地进行处理。
        Arrays.sort(hsh, 1, n + 1); // 排序，使得相同的元素排列在一起，不同的元素按照从小到大的顺序排列。
        int len = 1;
        // 去除重复的元素，只保留每个元素的第一个出现位置，同时记录每个元素在排序后序列中的位置。
        for (int i = 2; i <= n; i++) {
            if (hsh[i] != hsh[len]) {   // 如果当前元素与上一个存储的唯一元素 hsh[len] 不同，则将当前元素作为新的唯一元素存储到 hsh 数组中，并更新 len。
                hsh[++len] = hsh[i];
            }
        }
        // 处理之后，数组 hsh 的前 len 个元素就是原始数组中去重后的排序结果

        // 使用 Arrays.binarySearch() 函数来查找原数组 a[] 中每个元素在 hsh 中的位置，并用其位置（即离散化后的值）来替代原始值。
        for (int i = 1; i <= n; i++) {
            a[i] = Arrays.binarySearch(hsh, 1, len + 1, a[i]) + 1;  // Arrays.binarySearch() 返回的索引是从 0 开始计数的，而在许多情况下，离散化的值我们希望从 1 开始。
            a[n + i] = a[i];
        }

        int nl = 1, nr = n * 2; // 当前可操作范围的左边界和右边界，因为数组 a 在之前的代码中被扩展到了 2n 的长度（复制了原始数组）
        // 处理q次查询
        for (int i = 1; i <= q; i++) {
            char c = scanner.next().charAt(0);
            int x = scanner.nextInt();
            x = Math.min(x, n); // x 被限制为不超过 n，因为移动的步数最多为数组的长度 n。
            if (c == 'L') {
                // 如果当前左边界 nl 已经超过了 n，则将其减去 n 使其回到数组的前半部分。这是因为数组的后半部分实际上是前半部分的复制。
                if (nl > n) {
                    nl -= n;
                }
                queries.add(new Query(nl, nl + x - 1, i));
                nl += x;    // 更新 nl 为 nl + x，表示左边界向右移动 x 个位置（裁剪）
            } else {
                //  如果当前右边界 nr 小于或等于 n，则将其加上 n，使其回到数组的后半部分。
                if (nr <= n) {
                    nr += n;
                }
                queries.add(new Query(nr - x + 1, nr, i));
                nr -= x;    // 更新 nr 为 nr - x，表示右边界向左移动 x 个位置（裁剪）
            }
        }

        // 按照查询的右端点 r 对 queries 进行排序，确保每个查询能在合适的区间范围内进行计算。
        queries.sort(Comparator.comparingInt(a -> a.r));

        int cur = 1;

        // 计算每个查询区间内不同元素的数量
        for (Query query : queries) {
            // 处理当前查询的右边界 r，并更新树状数组 c
            for (int i = cur; i <= query.r; i++) {  // 确保所有位于 [cur, query.r] 范围内的元素都被处理。
                if (vis[(int) a[i]] > 0) {
                    update((int) vis[(int) a[i]], -1);  // 如果a[i]出现过，需要在树状数组中把该元素原位置的贡献移除
                }
                update(i, 1);   // 将当前元素的位置 i 更新到树状数组中
                vis[(int) a[i]] = i;
            }
            cur = query.r + 1;
            ans[query.id] = getsum(query.r) - getsum(query.l - 1);  // 区间和等于两个前缀和之差 [l, r] = [1, r] - [1, l-1]
        }


        for (int i = 1; i <= q; i++) {
            System.out.println(ans[i]);
        }

        scanner.close();
    }
}
