package mahjong;

import java.util.Scanner;

/**
 * 雀魂启动
 * https://www.nowcoder.com/questionTerminal/448127caa21e462f9c9755589a8f2416
 */

public class Main {


    static int[] cnt;   // cnt[i]：记录数字为i的牌的数量
    /**
     * 递归尝试并回溯
     * 由于数据的规模非常小(n<=9)，所以递归不会很深。
     * @param args
     */
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        // 注意 hasNext 和 hasNextLine 的区别
        cnt = new int[10];
        for(int i = 0; i < 13; i++) {
            int num = in.nextInt();
            cnt[num]++;
        }
        StringBuilder sb = new StringBuilder();
        // 尝试各种数字
        for(int k = 1; k <= 9; k++) {
            // 如果已有的该数字牌不足4个，说明还可能抽到
            if (cnt[k] < 4) {
                cnt[k]++;
                if (canHu()) {
                    sb.append(k).append(" ");
                }
                cnt[k]--;   // 撤销操作
            }
        }
        if(sb.length() == 0) {
            System.out.println(0);
        } else {
            System.out.println(sb.toString());
        }

    }

    public static boolean canHu() {
        // 先选一个至少有2张的牌作雀头
        for (int i = 1; i <= 9; i++) {
            if (cnt[i] >= 2) {
                cnt[i] -= 2;
                // 还剩12张牌，看是否能分成4组顺子或刻子
                if (hasTriples(4)) {
                    cnt[i] += 2;
                    return true;
                }
                cnt[i] += 2;
            }
        }
        return false;
    }


    /**
     * 剩下的牌中是否能凑出n组刻子或顺子
     * @param n
     * @return
     */
    public static boolean hasTriples(int n) {
        if (n == 0) {
            return true;
        }

        for (int i = 1; i <= 9; i++) {
            // 尝试凑刻子：i,i,i
            if (cnt[i] >= 3) {
                cnt[i] -= 3;
                boolean success = hasTriples(n - 1);
                cnt[i] += 3;
                if (success) {
                    return true;
                }
            }

            // 尝试凑顺子：i,i+1,i+2
            if (i <= 7 && cnt[i] >= 1 && cnt[i + 1] >= 1 && cnt[i + 2] >= 1) {
                cnt[i]--;
                cnt[i + 1]--;
                cnt[i + 2]--;
                boolean success = hasTriples(n - 1);
                cnt[i]++;
                cnt[i + 1]++;
                cnt[i + 2]++;
                if (success) {
                    return true;
                }
            }
        }
        return false;
    }
}
