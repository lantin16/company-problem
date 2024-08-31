package plantTrees;

import java.util.Arrays;
import java.util.Scanner;

/**
 * 小美种树（二）
 * https://mp.weixin.qq.com/s/eB0ralhhAxk9hFsT9ExVVA
 * @Author lantin
 * @Date 2024/8/31
 */
public class Main {

    static int[] pos;   // 工人站的位置
    static int k;   // 最少要种的树的总数

    /**
     * 二分法枚举每个人种树的棵树，找到满足的最小区间长度
     * @param args
     */
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();   // 工人数
        k = sc.nextInt();   // 最少要种的树
        pos = new int[n]; // 工人站的位置
        for (int i = 0; i < n; i++) {
            pos[i] = sc.nextInt();
        }

        // 注意：题目给的工人位置序列并不一定是有序的！所以要先对位置数组排序！
        Arrays.sort(pos);

        int l = 1, r = 300000;  // 二分查找的左右边界
        // 注意是越大越满足，所以要找到满足的最优解（最小）
        while (l <= r) {
            int mid = (l + r) / 2;  // 验证每个工人向右种mid棵树，总数能否满足k
            if (check(mid)) {   // 如果每人种mid棵，总数能满足k，则向左区间继续搜索最优解
                r = mid - 1;
            } else {    // 如果每人种mid棵，总数不能满足k，则向右区间搜索可行解
                l = mid + 1;
            }
        }
        System.out.println(l);  // 循环跳出条件是 l <= r，最后这里结果就是取l
    }

    /**
     * 检查每个工人向右种x棵树是否能满足k
     * @param x
     * @return
     */
    private static boolean check(int x) {
        int cnt = 0;
        int bd = 0; // 前面工人种植到的位置
        for (int p : pos) { // 从左到右遍历每一个工人并让他们种树
            if (bd < p) {   // 前面工人种的树还没种到p位置
                cnt += x;   // p位置的工人可以向右种x棵
                bd = p + x - 1; // p往右种x棵树能到的位置
            } else {    // 前面工人种的树已经种到了p位置，有重叠
                cnt += (p + x - 1 - bd);    // 新增重叠之后的树
                bd = p + x - 1;
            }
        }
        return cnt >= k;
    }
}
