package deleteInterval;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * 小美的区间删除
 * https://www.nowcoder.com/questionTerminal/d7f4f96f29b14feb973e7ad8ca74a8b7
 *
 * 注意：本题遍历删除区间时不要用两个for循环去遍历并计算每一种区间的可能，这样部分测试用例会超时，因为存在许多重复计算
 */

public class Main {

    /**
     * 0只可能来源于因子2和5相乘，一个2和一个5相乘得一个0
     * 因此可以计算出因子2和5的个数的前缀和，这样就可以快速得到某个区间内的数因式分解后的2和5的个数
     * @param args
     * @throws IOException
     */
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String line = br.readLine();
        String[] strs = line.trim().split(" ");
        int n = Integer.parseInt(strs[0]);
        int k = Integer.parseInt(strs[1]);
        int[] nums = new int[n + 1];

        int[] cnt2 = new int[n + 1];    // cnt2[i]：计算nums[1:i]中因子2的个数
        int[] cnt5 = new int[n + 1];    // cnt5[i]：计算nums[1:i]中因子5的个数

        line = br.readLine();
        strs = line.trim().split(" ");
        for(int i = 1; i <= n; i++) {
            nums[i] = Integer.parseInt(strs[i - 1]);
            // 计算因子2和5个数的前缀和
            cnt2[i] = cnt2[i-1] + count2(nums[i]);
            cnt5[i] = cnt5[i-1] + count5(nums[i]);
        }

        long res = 0;   // 注意结果要用long，否则如果满足的区间过多，用int可能越界

        // 删除区间[l,r]
        // 下面这样写部分测试用例会超时，因为有的大区间全删了都满足，再在里面挑删哪些小的肯定也满足
        // for(int l = 1; l <= n; l++) {
        //     for(int r = l; r <= n; r++) {
        //         // 求删除区间[l,r]后剩余的数中因子2和5的个数
        //         int num2 = cnt2[l-1] + cnt2[n] - cnt2[r];
        //         int num5 = cnt5[l-1] + cnt5[n] - cnt5[r];
        //         if(Math.min(num2, num5) >= k) {
        //             res++;
        //         }
        //     }
        // }

        // 每次固定左边界，右边界从右到左（删除区间从大到小），这样就能找到删除后仍满足的最大删除区间，那么右边界继续左移肯定也满足
        for (int l = 1; l <= n; l++) {
            int r = n;
            while(r >= l) {
                // 求删除区间[l,r]后剩余的数中因子2和5的个数
                int num2 = cnt2[l-1] + cnt2[n] - cnt2[r];
                int num5 = cnt5[l-1] + cnt5[n] - cnt5[r];
                if(Math.min(num2, num5) >= k) { // 找到以l为左边界且满足题意的最大删除区间
                    break;
                }
                r--;
            }
            res += r - l + 1;   // 以l为删除区间左边界的情况一次性加上
        }

        System.out.println(res);

    }

    /**
     * 求x因子2的个数
     * @param x
     * @return
     */
    private static int count2(int x) {
        int cnt = 0;
        while(x % 2 == 0) {
            cnt++;
            x /= 2;
        }
        return cnt;
    }

    /**
     * 求x因子5的个数
     * @param x
     * @return
     */
    private static int count5(int x) {
        int cnt = 0;
        while(x % 5 == 0) {
            cnt++;
            x /= 5;
        }
        return cnt;
    }
}
