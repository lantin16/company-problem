package travel;

import java.util.Scanner;


/**
 * 毕业旅行问题
 * https://www.nowcoder.com/questionTerminal/3d1adf0f16474c90b27a9954b71d125d
 *
 * 尝试dfs暴力做，时间复杂度O(n!)，超时，仅能通过一半用例
 */

public class Main {


    /**
     * 动态规划 + 状态压缩（掌握）
     * 完全图（各个顶点都有一条边两两互相连接），并且各个边没有方向。
     * 参考解法：https://blog.csdn.net/abc123lzf/article/details/102667120
     * @param args
     */
    // 时间复杂度：O(n^2 2^n)。分析：然看上去时间复杂度还是很大，但好在基数 n 并不大，在 n 接近20时比起回溯法效率提高了很多倍
    // 空间复杂度：O(n 2^n)
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int n = in.nextInt();   // 城市个数
        int[][] dis = new int[n][n];
        for(int i = 0; i < n; i++) {
            for(int j = 0; j < n; j++) {
                dis[i][j] = in.nextInt();
            }
        }
        // 从哪出发都一样，假设从城市0出发
        // dp[i][j]：从城市i出发途径j表示的若干城市然后返回城市0的最短路径
        // 如何用一个int数字来表示若干城市的途径状态呢？
        // ——状态压缩，int数字第 m 位为1代表要途径城市m，这样int数字可以表示32个城市的途径情况（多于20个）
        int[][] dp = new int[n][1 << (n - 1)];  // 由于起点是城市0，因此后续途径城市一定不包含城市0，所以只需要(n-1)位来表示剩下(n-1)个城市途径状态，因此dp数组的宽度为2^(n-1)，可以取0~111..11

        // 初始化，dp[i][0]代表从i出发返回起点城市0的最短路径，就是dis[i][0]
        for(int i = 1; i < n; i++) {
            dp[i][0] = dis[i][0];
        }
        
        // 遍历所有集合（对应int型数字p）
        // dp[i][p]
        for (int p = 1; p < 1 << (n - 1); p++) {    // 得倒着推，只有根据小集合才能推导出大集合，所以p从小到大（p=0已经初始化了）
            // 选一个城市作为这一次的出发城市
            for (int i = 0; i < n; i++) {
                dp[i][p] = Integer.MAX_VALUE >> 1;  // 除以2防止计算时越界，对于不可行的路径就让它为较大值，如果可行后面也会被覆盖
                // 出发城市不能在集合p中，因为集合p的含义是从城市i出发途径p中的城市然后返回城市0
                if (contain(i, p)) {
                    continue;
                }

                // 遍历所有子问题（p中所有途径的城市分一个出来尝试先走）
                for (int j = 1; j < n; j++) {
                    if (contain(j, p)) {
                        dp[i][p] = Math.min(dp[i][p], dis[i][j] + dp[j][remove(p, j)]); // 先从城市i到城市j，然后从j出发途径p去除j后的城市
                    }
                }
            }
        }

        System.out.println(dp[0][(1 << (n - 1)) - 1]);    // 结果就是从城市0出发途径剩下n-1个城市最后返回城市0的最短路径(p = 111..11 共n-1个1)
    }


    /**
     * 判断集合p中是否包含城市i
     * 对城市0直接返回false，因为p的表示范围为 0 ~ 2^(n-1) - 1，共 n-1 位，也就是能表示除城市0以外的剩下n-1个城市的途径情况，因此城市0肯定不在p的集合中
     * 对其他城市，判断p的对应位是否为1
     * ！！！用位运算判断某位是否为1！！！
     * @param i
     * @param p
     * @return
     */
    public static boolean contain(int i, int p) {
        return i != 0 && (p & (1 << (i - 1))) != 0; // 注意这里不能用==1来判断，因为判断第i位是否为1，按位与之后得到的是1000000这种而不是单纯的1
    }

    /**
     * 从集合p中除去城市j，返回新的集合对应的int数
     * ！！！用位运算将某位1改为0！！！
     * @param p
     * @param j
     * @return
     */
    public static int remove(int p, int j) {
        // return p & ((1 << (j - 1)) - 1); // 这么写是错的！比如p=11101，那么1 << (j - 1)) - 1 = 011，这样做按位与其实高位被丢掉了，但实际上我们想做的是只修改某位1为0
        return p & (~(1 << (j - 1)));   // 正确的操作
    }

}
