package minASCSum;

import java.util.Scanner;

/**
 * 两个字符串的最小 ASCII 删除总和
 * https://kamacoder.com/problempage.php?pid=1220
 */

public class Main {

    /**
     * 二维dp
     * 两个字符串的ASCII删除总和 = s1的ASCII总和 + s2的ASCII总和 - 公共子序列的ASCII总和 * 2
     * 要想删除总和越小，则公共子序列ASCII总和得越大，类似于求最长公共子序列的长度
     * @param args
     */
    public static void main (String[] args) {
        Scanner sc = new Scanner(System.in);
        String s1 = sc.nextLine();
        String s2 = sc.nextLine();
        int asc1 = 0, asc2 = 0; // s1，s2的ASCII和
        // 找最长公共子序列
        // dp[i][j]：s1前i个的子串和s2前j个的子串的最大公共子序列的ASCII和
        int[][] dp = new int[s1.length() + 1][s2.length() + 1];

        for (int i = 1; i <= s1.length(); i++) {
            char c1 = s1.charAt(i - 1);
            asc1 += c1;
            for(int j = 1; j <= s2.length(); j++) {
                char c2 = s2.charAt(j - 1);
                // 考虑s1的第i个和s2的第j个字符
                if (c1 == c2) { // 如果相等则肯定选上这两个公共子序列更长/ASCII和更大
                    dp[i][j] = dp[i-1][j-1] + c1;
                } else {    // 如果不相等则不能都选，在可能选一个、另一个不选里面挑和最大的
                    dp[i][j] = Math.max(dp[i-1][j], dp[i][j-1]);
                }
            }
        }

        // s1的ASCII值在上面循环中已经顺便计算了，这里只要计算s2的
        for (char c : s2.toCharArray()) {
            asc2 += c;
        }

        System.out.println(asc1 + asc2 - dp[s1.length()][s2.length()] * 2);

    }
}
