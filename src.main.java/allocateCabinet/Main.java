package allocateCabinet;

import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Scanner;

/**
 * 最后分配的储物柜
 * https://mp.weixin.qq.com/s/xJkY-JoEsCAqyY57RXd3oA
 * @Author lantin
 * @Date 2024/8/2
 */
public class Main {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        String[] clients = in.nextLine().trim().split(" ");
        in.close();

        PriorityQueue<Integer> pq = new PriorityQueue<>();  // 优先队列存储空闲的柜子编号（升序）
        Map<String, Integer> alloc = new HashMap<>();   // 给客户分配的柜子编号
        int n = clients.length; // 最多需要的柜子数，假设全部都是来存的
        // 初始化空闲的柜子编号，入队n个即可
        for (int i = 1; i <= n; i++) {
            pq.offer(i);
        }

        int last = -1;  // 最后分配的柜子编号

        for (String c : clients) {
            if (!alloc.containsKey(c)) {    // 还没给客户分配
                int cabinet = pq.poll();    // 取出当前最小编号空闲的柜子分给该用户
                alloc.put(c, cabinet);
                last = cabinet;
            } else {    // 这次是取出物品释放柜子
                int own = alloc.get(c);
                pq.offer(own);  // 释放的柜子重新加入队列
                alloc.remove(c);
            }
        }
        System.out.println(last);
    }
}
