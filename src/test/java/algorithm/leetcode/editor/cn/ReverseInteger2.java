//给你一个 32 位的有符号整数 x ，返回将 x 中的数字部分反转后的结果。 
//
// 如果反转后整数超过 32 位的有符号整数的范围 [−2³¹, 231 − 1] ，就返回 0。 
//假设环境不允许存储 64 位整数（有符号或无符号）。
//
// 
//
// 示例 1： 
//
// 
//输入：x = 123
//输出：321
// 
//
// 示例 2： 
//
// 
//输入：x = -123
//输出：-321
// 
//
// 示例 3： 
//
// 
//输入：x = 120
//输出：21
// 
//
// 示例 4： 
//
// 
//输入：x = 0
//输出：0
// 
//
// 
//
// 提示： 
//
// 
// -2³¹ <= x <= 2³¹ - 1 
// 
// Related Topics 数学 👍 3248 👎 0

package algorithm.leetcode.editor.cn;

public class ReverseInteger2 {
     public static void main(String[] args){
         Solution solution = new ReverseInteger2().new Solution();
 //        System.out.println(Integer.MAX_VALUE);
//         System.out.println(Integer.valueOf(12));
         System.out.println(solution.reverse(2));
         System.out.println(solution.reverse(12 / 10));
         System.out.println(solution.reverse(2 / 10));
     }
        //leetcode submit region begin(Prohibit modification and deletion)
class Solution {
    public int reverse(int x) {
        int reverse = 0;

        while (x != 0) {
            int temp = x % 10;

        }


        return reverse;
    }
}
//leetcode submit region end(Prohibit modification and deletion)

 }