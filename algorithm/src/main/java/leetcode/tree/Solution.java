package leetcode.tree;

class Solution {

    int max = -1;

    public int diameterOfBinaryTree(TreeNode root) {
        if (root == null) {
            return 0;
        }
        height(root);
        return max;
    }

    private int height(TreeNode root) {
        if (root == null) {
            return 0;
        }

        int l = height(root.left);
        int r = height(root.right);
        // 求告诉的同时，记录下最大值。这次height函数不能返回两个值了，只能用全局变量了……
        // 但是思想是一致的，自底向上——后序遍历
        max = Math.max(max, l + r);
        return 1 + Math.max(l, r);
    }
}