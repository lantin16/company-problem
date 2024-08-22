package devOpsTask;

import java.util.Deque;
import java.util.LinkedList;
import java.util.Scanner;

/**
 * DevOps任务调度
 * https://mp.weixin.qq.com/s/RoUyVEQKepsJbtZd9FZTRw
 * @Author lantin
 * @Date 2024/8/22
 */
public class Main {

    /**
     * 模拟 + 队列
     * @param args
     */
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();   // 任务数量和执行机的数量
        int[] tasks = new int[n];   // 任务数组
        int[] machines = new int[n];   // 空置执行机数组
        for (int i = 0; i < n; i++) {
            tasks[i] = sc.nextInt();
        }
        for (int i = 0; i < n; i++) {
            machines[i] = sc.nextInt();
        }
        sc.close();

        Deque<Integer> dq0 = new LinkedList<>(); // 双端队列用于存放空置的执行机（通用执行机全部执行0任务）
        Deque<Integer> dq1 = new LinkedList<>(); // 双端队列用于存放空置的执行机（通用执行机全部执行1任务）

        // 初始化两个双端队列
        for (int m : machines) {
            dq0.offer(m == 2 ? 0 : m);
            dq1.offer(m == 2 ? 1 : m);
        }

        // 无非两种情况：通用执行机全用于处理0任务或通用执行机全用于处理1任务，结果就是两种情况的最小值
        System.out.println(Math.min(solve(tasks, dq0), solve(tasks, dq1)));
    }

    /**
     * 模拟任务调度过程
     * @param tasks
     * @param dq
     * @return 返回剩余的空置执行机个数
     */
    private static int solve(int[] tasks, Deque<Integer> dq) {
        for (int task : tasks) {
            int cnt = 0;    // 记录进行了多少次将执行机移到队尾的操作
            while (task != dq.getFirst() && cnt < dq.size()) {  // 如果队首任务与队首的执行机不匹配则将执行机移到队尾
                dq.offer(dq.poll());
                cnt++;
            }

            if (task == dq.getFirst()) {    // 如果匹配上了
                dq.poll();  // 将队首匹配上的执行机移出队列
            } else {    // 所有执行机都不匹配，那么这个任务就无法执行了，退出循环
                break;
            }
        }
        return dq.size();
    }
}
