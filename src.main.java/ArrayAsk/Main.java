package ArrayAsk;

import java.util.Scanner;

/**
 * 小美的数组询问
 * https://www.nowcoder.com/questionTerminal/6b9a8fe1c40e41b3a654975b332e4dc7
 */

public class Main {

    /**
     * 注意考虑大数越界问题
     * int 最大可以表示 10^9，但不能表示 10^10。
     * long 最大可以表示 10^18，但不能表示 10^19。
     * 再大的数可能就要用到 BigInteger 来运算了
     * @param args
     */
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int n = in.nextInt();
        int q = in.nextInt();
        long knownSum = 0;   // 已知的正整数的和
        int unknownNum = 0; // 未知元素的个数（0的个数）
        for(int i = 0; i < n; i++) {
            int num = in.nextInt();
            if(num == 0) {
                unknownNum++;
            } else {
                knownSum += num;
            }
        }

        // long 类型可以安全地存储 10 的 18 次幂以内的整数
        for(int j = 0; j < q; j++) {
            long l = in.nextLong();
            long r = in.nextLong();
            long min = knownSum + unknownNum * l, max = knownSum + unknownNum * r;
            System.out.println(min + " " + max);
        }
    }
}
