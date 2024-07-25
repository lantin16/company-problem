package robotJump;

import java.util.Scanner;

/**
 * 机器人跳跃问题
 * https://www.nowcoder.com/questionTerminal/7037a3d57bbd4336856b8e16a9cafd71
 */

public class Main {

    /**
     * 推导出递推公式：无论站在第k个楼时的能量E(k)与第k+1个楼的高度H(k+1)的大小关系如何，均有：E(k+1) = 2E(k) - H(k+1)
     * 假设初始能量为E，E(k)表示站在第k个楼时的能量，H(k)表示第k个楼高，那么根据递推公式有：
     * E(k) = 2^k * E - 2^(k-1) * H(1) - 2^(k-2) * H(2) - ... -2 * H(k-1) - H(k)
     * 由 E(k) >= 0 这一限制条件可得：
     * E >= H(1) / 2 + H(2) / 4 + H(3) / 8 + ... + H(k) / 2^k
     * 又因为在每个楼处都得满足 E(k) >= 0，因此E的取值需要使这些不等式同时成立，不等式右边k每多1则项数多1，因此E只需要大于等于 k=n 时的右式即可，最后向上取整即为所求
     *
     * @param args
     */
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        // 注意 hasNext 和 hasNextLine 的区别
        int n = in.nextInt();
        int[] h = new int[n + 1];
        for(int i = 1; i <= n; i++) {
            h[i] = in.nextInt();
        }

        // 注意：耗能还是充能比较的是E和h[k+1]而不是h[k]和h[k+1]！！！
        double e = 0;
        for(int i = 1; i <= n; i++) {
            e += h[i] * Math.pow(0.5, i);
        }

        System.out.println((int)Math.ceil(e));
    }
}
