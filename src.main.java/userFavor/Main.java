package userFavor;

import java.util.*;

/**
 * 用户喜好
 * https://www.nowcoder.com/questionTerminal/66b68750cf63406ca1db25d4ad6febbf
 */

public class Main {

    /**
     * 哈希表的key为喜爱度，value为该喜爱度的用户列表，对于某个喜爱度，遍历其用户列表并计数在区间内的即可
     * 险险通过
     * @param args
     */
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int n = in.nextInt();
        in.nextLine();
        int[] arr = new int[n + 1];
        for(int i = 1; i<= n; i++) {
            arr[i] = in.nextInt();
        }
        // key为喜爱度，value为该喜爱度的用户列表
        Map<Integer, List<Integer>> map = new HashMap<>();
        for(int i = 1; i <= n; i++) {
            List<Integer> list = map.getOrDefault(arr[i], new ArrayList<>());
            list.add(i);
            map.put(arr[i], list);
        }

        int q = in.nextInt();   // 查询的组数
        for(int i = 0; i < q; i++) {
            int l = in.nextInt(), r = in.nextInt(), k = in.nextInt();
            int res = 0;
            if (map.containsKey(k)) {  // 确保确实这个喜爱度有用户
                for(int u : map.get(k)) {   // 遍历该喜爱度的用户列表，看哪些用户在[l,r]范围内
                    if (u >= l && u <= r) {
                        res++;
                    }
                }
            }
            System.out.println(res);
        }
    }


    /**
     * 前缀和思想，只不过一次要存多个k的前缀和，因此数组元素用哈希表来存
     * 但提示 内存超限
     * @param args
     */
    // public static void main(String[] args) {
    //     Scanner in = new Scanner(System.in);
    //     int n = in.nextInt();
    //     in.nextLine();
    //     int[] arr = new int[n + 1];
    //     for(int i = 1; i<= n; i++) {
    //         arr[i] = in.nextInt();
    //     }
    //     // preMaps[i]：从第1个到第i个的喜好度map
    //     List<Map<Integer, Integer>> preMaps = new ArrayList<>();
    //     preMaps.add(new HashMap<>());   // preMaps[0]
    //     for(int i = 1; i <= n; i++) {
    //         // 先拷贝preMaps[i-1]到tmp（掌握拷贝map的方法，用构造方法直接传进去）
    //         HashMap<Integer, Integer> tmp = new HashMap<>(preMaps.get(preMaps.size() - 1));
    //         // 再将当前k的对应次数加一
    //         tmp.put(arr[i], preMaps.get(preMaps.size() - 1).getOrDefault(arr[i], 0) + 1);
    //         preMaps.add(tmp);
    //     }
    //
    //     int q = in.nextInt();   // 查询的组数
    //     for(int i = 0; i < q; i++) {
    //         int l = in.nextInt(), r = in.nextInt(), k = in.nextInt();
    //         System.out.println(preMaps.get(r).getOrDefault(k, 0) - preMaps.get(l-1).getOrDefault(k, 0));
    //     }
    // }

}
