package cleverEditors;

import java.util.Scanner;

/**
 * 万万没想到之聪明的编辑
 * https://www.nowcoder.com/questionTerminal/42852fd7045c442192fa89404ab42e92
 */

public class Main {

    /**
     * 滑动窗口
     *
     * @param args
     */
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int n = in.nextInt();
        in.nextLine();
        StringBuilder sb = new StringBuilder();
        while(in.hasNext()) {
            String s = in.nextLine();
            sb.append(s.charAt(0));
            int l = 0, r;   // [l, r]之间均为相同的字符
            for(r = 1; r < s.length(); r++) {
                if(s.charAt(r) != s.charAt(l)) {    // 遇到不同的字符
                    sb.append(s.charAt(r)); // 将当前字符直接添加到sb
                    l = r;  // 左边界移到当前字符
                } else if(r == l + 1 && (sb.length() < 3 || sb.charAt(sb.length() - 2) != sb.charAt(sb.length() - 3))) {
                    // 若l、r相同且相邻，并且l的前两个不是相同的字符则也可以添加r，如ABCC，由于AB不同所以CC合规，此时可以将后一个C加入sb
                    // 但这种情况就不移l了，因为目前仍是与l的重复元素
                    sb.append(s.charAt(r));
                }
                // 其他情况，如AABB的第二个B不会添加，直到r遇到下一个不等于B的会直接添加下一个字符
                // ABCCC的情况，第三个C开始的后面的C都会被跳过，保证不会出现三个以上相连的
            }

            System.out.println(sb.toString());
            sb.setLength(0);    // 清空sb
        }
    }


    /**
     * 正则替换
     * 捕获组与反向引用：https://blog.csdn.net/Lirx_Tech/article/details/51721491
     * @param args
     */
    // public static void main(String[] args) {
    //     Scanner sc = new Scanner(System.in);
    //     int n = sc.nextInt();
    //     sc.nextLine();
    //     for (int i = 0; i < n; i++) {
    //         System.out.println(sc.nextLine()
    //                 .replaceAll("(.)\\1+", "$1$1")  // 第一个正则把3个以上的字符换成2个，替换完之后最多连续两个相同字符
    //                 .replaceAll("(.)\\1(.)\\2", "$1$1$2")); // 第二个正则把AABB型换成AAB型
    //     }
    // }

}
