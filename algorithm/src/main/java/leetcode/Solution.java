package leetcode;

/**
 * @author puppylpg on 2023/07/23
 */
public class Solution {
        public int trap(int[] height) {
            int i = 0, j = height.length - 1;
            // 左右已经出现过的最高值
            int leftMax = 0, rightMax = 0;
            int total = 0;
            while (i < j) {
                int curLeft = height[i], curRight = height[j];
                leftMax = Math.max(leftMax, curLeft);
                rightMax = Math.max(rightMax, curRight);

                // 只要有leftMax和rightMax在，中间一定能接到水。所以左边小了就更新右边，以求得到个更大的rightMax。右边同理
                if (curLeft < curRight) {
                    total += leftMax - curLeft;
                    i++;
                } else {
                    total += rightMax - curRight;
                    j--;
                }
            }
            return total;
        }
    }
