package longSameValPath;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

/**
 * 最长同值路径
 * https://kamacoder.com/problempage.php?pid=1221
 */

public class Solution {

    /**
     * ！！！这样写是错误的！！！
     * l = 2i+1, r = 2i+2 只适用于完全二叉树存储在数组中的情况
     * 本题存在null节点，这种方法可能会导致索引计算错误。因为null节点只会在节点有一个孩子时另一边为空时出现，其他地方相较于完全二叉树欠缺的都不会用null填补。
     * @param args
     */
    // public static void main (String[] args) {
    //     Scanner sc = new Scanner(System.in);
    //     int n = sc.nextInt();
    //     sc.nextLine();
    //     String[] vals = sc.nextLine().trim().split(" ");
    //     int res = 0;
    //     // dp[i]：以vals[i]为根向下的最大路径长度
    //     int[] dp = new int[n];
    //
    //     // 从叶子往上，层序遍历的倒序
    //     for(int i = n - 1; i >= 0; i--) {
    //         if(vals[i].equals("null")) {
    //             continue;
    //         }
    //         int l = 2 * i + 1, r = 2 * i + 2;   // 左右孩子的索引（错误！因为本题不一定是完全二叉树）
    //         int lp = 0, rp = 0; // vals[i]为根向左右子树的同值最长路径长度
    //         if (l < n && vals[i].equals(vals[l])) {
    //             lp = dp[l] + 1;
    //         }
    //         if (r < n && vals[i].equals(vals[r])) {
    //             rp = dp[r] + 1;
    //         }
    //         dp[i] = Math.max(lp, rp);
    //         res = Math.max(res, lp + rp);
    //     }
    //     System.out.println(res);
    // }



    static int res = 0;

    /**
     * 先根据层序遍历构建二叉树，再dfs得到结果
     * @param args
     */
    public static void main (String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        if (n == 0) {
            System.out.println(0);
            return;
        }
        sc.nextLine();
        String[] vals = sc.nextLine().trim().split(" ");
        dfs(buildTree(vals));
        System.out.println(res);
    }

    /**
     * 计算root向下的最大同值路径长度（向左下或右下）
     * @param root
     * @return
     */
    private static int dfs(TreeNode root) {
        if (root == null) {
            return 0;
        }

        // 必须确保在计算当前root的左右子树的同值路径长度之前，先调用递归函数来更新左右子树为root时的同值路径
        // 即无论左右孩子的val是否和root的val相等都要尝试先计算左右子树的情况，如果只在值相等的时候向下递归就有可能有某个子树的情况没有递归到
        int l = dfs(root.left);
        int r = dfs(root.right);

        int lp = root.left != null && root.left.val == root.val ? l + 1 : 0;
        int rp = root.right != null && root.right.val == root.val ? r + 1 : 0;

        res = Math.max(res, lp + rp);
        return Math.max(lp, rp);
    }

    /**
     * 根据层序遍历结果建树
     * @param vals
     * @return
     */
    private static TreeNode buildTree(String[] vals) {
        int n = vals.length;
        Queue<TreeNode> q = new LinkedList<>();
        TreeNode root = new TreeNode(Integer.parseInt(vals[0]));
        q.offer(root);
        int idx = 1;
        while(!q.isEmpty()) {
            TreeNode cur = q.poll();
            if (idx < n && !"null".equals(vals[idx])) {
                cur.left = new TreeNode(Integer.parseInt(vals[idx]));
                q.offer(cur.left);
            }
            idx++;  // 无论上面是否为null索引都要+1（相当于遇到null就跳过）
            if (idx == n) { // 如果下标移动到了n则可以直接返回了
                return root;
            }
            if (idx < n && !"null".equals(vals[idx])) {
                cur.right = new TreeNode(Integer.parseInt(vals[idx]));
                q.offer(cur.right);
            }
            idx++;
            if (idx == n) {
                return root;
            }
        }
        return root;
    }
}

class TreeNode {
    int val;
    TreeNode left;
    TreeNode right;

    public TreeNode(){}

    public TreeNode(int val) {
        this.val = val;
    }
}
