package example;

import java.util.HashSet;
import java.util.Set;

/**
 * @author puppylpg on 2024/04/30
 */
public class LeetCode {

    public static void main(String[] args) {
        LeetCode leetcode = new LeetCode();
        System.out.println(leetcode.checkSubarraySum(new int[] {1, 0}, 2));
    }

    public boolean checkSubarraySum(int[] nums, int k) {
        int n = nums.length;
        int[] f = new int[n + 1];
        f[0] = 0;

        for (int i = 1; i < n + 1; i++) {
            f[i] = f[i - 1] + nums[i - 1];
        }

        // f(j) - f(i) = nk, 假设k为3，则fj - fi = 3n，所以fj和fi模3同余
        Set<Integer> set = new HashSet<>();
        for (int j = 0; j < n + 1; j++) {
            int former = f[j] % k;
            if (set.contains(former) && j >= 2) {
                return true;
            } else {
                set.add(former);
            }
        }

        return false;
    }
}
