package bracelets;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * 手串
 * https://www.nowcoder.com/questionTerminal/429c2c5a984540d5ab7b6fa6f0aaa8b5
 */

public class Main {

    /**
     * 判断环上任意两个染色元素之间的距离
     * @param args
     */
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int n = in.nextInt();   // 串珠数
        int m = in.nextInt();   // 连续串珠数
        int c = in.nextInt();   // 颜色种数（不包含无色）
        int res = 0;

        // l[i]存放颜色i出现的珠子的列表
        List<List<Integer>> l = new ArrayList<>();
        for(int i = 0; i <= c; i++) {
            l.add(new ArrayList<>());
        }
        for(int i = 0; i< n; i++) {
            int num = in.nextInt(); // 该珠子的颜色个数
            for(int j = 0; j < num; j++) {
                int color = in.nextInt();
                l.get(color).add(i);    // 在对应颜色的列表中加入当前串珠编号（从0开始）
            }
        }

        // 遍历每一种颜色，看是否满足题意
        for(int k = 1; k <= c; k++) {
            List<Integer> tmp = l.get(k);
            int size = tmp.size();
            boolean flag = false;
            // 两两相邻串珠之间的距离都要被满足（珠子数 <= m）
            // 不需要涉及环的
            for(int cur = 0; cur < size - 1; cur++) {
                if(tmp.get(cur) - tmp.get(cur + 1) < m) {
                    flag = true;
                    break;
                }
            }
            // 首尾串珠的距离也需判断（按照环）
            if(!flag && n - tmp.get(size - 1) + tmp.get(0) < m) {
                flag = true;
            }
            if(flag) {
                res++;
            }
        }
        System.out.println(res);
    }
}
