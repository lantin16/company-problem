package goodTree;

/**
 * 小红的二叉树计数
 * https://www.nowcoder.com/questionTerminal/7e675d9a3e1545f9bc4a0290e09be36f
 */

public class Solution {

    int mod = 1000000007;
    /**
     * 动态规划
     * 本题难点在于大数越界的处理，思路就是先用大的long存，最后转int，当然期间还是要取余
     *
     * 代码中的类名、方法名、参数名已经指定，请勿修改，直接返回方法规定的值即可
     *
     * @param n int整型
     * @return int整型
     */
    public int cntOfTrees (int n) {
        // 偶数个节点不可能为好二叉树
        if(n % 2 ==0) {
            return 0;
        }

        if(n <= 3) {
            return 1;
        }

        // dp[i]：i个节点组成的好二叉树的不同形态数
        long[] dp = new long[n + 1];    // 这里用long类型而不是int类型，因为虽然会取余防止越界，但dp[j] * dp[i-1-j]仍可能int越界，这是取余无法解决的
        dp[1] = 1;
        dp[3] = 1;

        // 只有奇数个节点才可能是好二叉树
        for(int i = 5; i <= n; i += 2) {
            for(int j = 1; j <= i - 2; j += 2) {    // 子树节点数为奇数才可能是好二叉树
                dp[i] += dp[j] * dp[i - 1 - j] % mod;   // 左右子树的每种情况都考虑到
                dp[i] %= mod;   // dp[i]每次累加也可能int越界因此这里还要取余
            }
        }

        return (int)dp[n];
    }
}
