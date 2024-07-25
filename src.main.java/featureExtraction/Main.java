package featureExtraction;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/**
 * 特征提取
 * https://www.nowcoder.com/questionTerminal/5afcf93c419a4aa793e9b325d01957e2
 */

public class Main {

    /**
     * 用两个hash表记录上一帧和这一帧的特征最大连续出现次数
     * 如何将一对数据(x,y)作为一个整体存储？
     * ——这里采用的拼成字符串 x-y，因为特征取值均为非负整数
     * @param args
     */
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        // 注意 hasNext 和 hasNextLine 的区别
        int n = in.nextInt();

        while (in.hasNextInt()) { // 注意 while 处理多个 case
            int res = 0;
            int m = in.nextInt();   // 帧数
            Map<String, Integer> preFeaTimes = new HashMap<>();  // 记录上一帧特征连续出现的次数
            Map<String, Integer> feaTimes = new HashMap<>();  // 记录当前帧特征连续出现的次数
            for(int i = 0; i < m; i++) {
                int fn = in.nextInt();  // 该帧的特征个数
                for(int j = 0; j < fn; j++) {
                    int x = in.nextInt(), y = in.nextInt();
                    String fea = x + "-" + y;
                    int lastTimes = preFeaTimes.getOrDefault(fea, 0);   // 该特征在上一帧为止连续出现的最多次数
                    res = Math.max(res, lastTimes + 1); // 看到这一帧为止是否有特征超过了当前的最长连续次数
                    feaTimes.put(fea, lastTimes + 1);   // 维护当前帧的特征次数
                }
                // 在下一帧前将上一帧帧替换成当前帧，当前帧map清空为下一帧做准备
                preFeaTimes = feaTimes;
                feaTimes = new HashMap<>();
            }
            System.out.println(res);    // 此测试用例的结果
        }
    }


}
