package tryPassword;

import java.util.*;

/**
 * 小美的密码
 * https://mp.weixin.qq.com/s/5BaXwpLG9d--Qoto9YZybA
 * @Author lantin
 * @Date 2024/8/10
 */
public class Main {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Map<Integer, Set<String>> len2pw = new HashMap<>(); // 记录各个长度的密码尝试
        int n = sc.nextInt();
        String tp = sc.next(); // 正确的密码
        for(int i = 0; i < n; i++) {
            String pw = sc.next();
            int len = pw.length();
            len2pw.putIfAbsent(len, new HashSet<>());
            len2pw.get(len).add(pw);
        }
        int l1Cnt = 0, lCnt = 0;    // 小于tl的密码的个数，小于等于tl的密码的个数
        int tl = tp.length();   // 真实密码的长度
        for (Map.Entry<Integer, Set<String>> entry : len2pw.entrySet()) {
            if (entry.getKey() < tl) {
                l1Cnt += entry.getValue().size();
                lCnt += entry.getValue().size();
            }
            if (entry.getKey() == tl) {
                lCnt += entry.getValue().size();
            }
        }
        System.out.println((l1Cnt + 1) + " " + lCnt);
        sc.close();
    }
}
