package leetcode.tree;

/**
 * @author puppylpg on 2023/07/23
 */
public class TreeMain {

    public static void main(String... args) {
        String tree = "[1,-2,-3,1,3,-2,null,-1]";
        TreeNode treeNode = StrToTreeNode.createTree(tree.substring(1, tree.length() - 1));

        Solution solution = new Solution();
        solution.maxPathSum(treeNode);
    }
}
