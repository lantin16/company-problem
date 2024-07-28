package buyHouse;

import java.util.*;

/**
 * 购房之旅
 * https://mp.weixin.qq.com/s/UDJ-lx9hjjZJsQECfKoHSw
 */

public class Main {

    static class House {
        int comfort;
        int price;

        public House(){};
        public House(int comfort, int price) {
            this.comfort = comfort;
            this.price = price;
        }
    }

    /**
     * 贪心 + TreeMap
     * TreeMap中的Entry自动按照key排序好了，并且有方法方便地找到大于/小于某个值的第一个key
     * 对于每一个人，购买舒适度尽可能大的，并且价格尽可能接近的 。（钱尽其用，即在买得起的情况下有钱的朋友尽可能买贵的，钱少的朋友来买便宜的）
     * @param args
     */
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int n = in.nextInt(), m = in.nextInt();
        // 读取朋友的金币数并存到TreeMap
        TreeMap<Integer, Integer> friends = new TreeMap<>();    // key是朋友的金币数，value是拥有该金币数的人数
        for(int i = 0; i < n; i++) {
            int money = in.nextInt();
            friends.put(money, friends.getOrDefault(money, 0) + 1);
        }

        // 读取房子的舒适度和价格并存到List
        List<House> houses = new ArrayList<>();    // 存储房子的信息
        for (int i = 0; i < m; i++) {
            int comfort = in.nextInt(), price = in.nextInt();
            houses.add(new House(comfort, price));
        }

        // 按照价格从高到低排序房子
        houses.sort((h1, h2) -> h2.price - h1.price);

        long res = 0;
        // 依次考虑每个房子，看是否有朋友的金币购买且金额最接近（钱尽其用）
        for(House h : houses) {
            Map.Entry<Integer, Integer> entry = friends.ceilingEntry(h.price);  // 找到第一个不小于房子价格的金币数量（能够买下房子且钱最少的）
            if(entry != null) {
                res += h.comfort;
                int count = entry.getValue();   // 该金币数量的人数
                if (count == 1) {   // 如果该金币数量只有一人，则买下这个房子后将键值对删除
                    friends.remove(entry.getKey());
                } else {    // 如果多于一人则只数量减一
                    friends.put(entry.getKey(), count - 1);
                }
            }
        }
        System.out.println(res);
    }
}
