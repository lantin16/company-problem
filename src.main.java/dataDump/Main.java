package dataDump;

import java.util.*;

/**
 * 数据重删
 * https://mp.weixin.qq.com/s/RoUyVEQKepsJbtZd9FZTRw
 * @Author lantin
 * @Date 2024/8/22
 */
public class Main {


    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();   // 数据个数
        int k = sc.nextInt();   // 数据块大小
        int[] arr = new int[n];
        for(int i = 0; i < n; i++) {
            arr[i] = sc.nextInt();
        }
        sc.close();

        Map<String, Integer> cntMp = new HashMap<>();   // 记录每个数据块的出现次数
        List<String> list = new ArrayList<>();  // 记录不重复数据块的出现次数
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < n; i++) {
            sb.append(arr[i]).append(" ");
            if (i % k == k - 1) {   // 切出了一个数据块
                String block = sb.toString();
                if (!cntMp.containsKey(block)) {    // 第一次出现
                    cntMp.put(block, 1);
                    list.add(block);
                } else {    // 重复出现则只加次数
                    cntMp.put(block, cntMp.get(block) + 1);
                }
                sb.setLength(0);    // 清空StringBuilder以便存放下一个数据块
            }
        }

        // 最后如果还有一个不足k长度的数据块，那么必不可能与前面重复，也要添加进去
        if (sb.length() > 0) {
            list.add(sb.toString());
            cntMp.put(sb.toString(), 1);
        }

        // 输出结果
        for (int j = 0; j < list.size(); j++) {
            System.out.print(list.get(j) + cntMp.get(list.get(j)));
            if (j != list.size() - 1) { // 注意最后一个后面不要有空格和换行符
                System.out.print(" ");
            }
        }
        System.out.println();
    }
}
