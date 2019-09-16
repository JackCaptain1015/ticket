import com.google.common.collect.Maps;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by wurz on 2019/4/29.
 */
public class LeetCode0 {
    /**
     * 给定一个整数数组 nums 和一个目标值 target，请你在该数组中找出和为目标值的那 两个 整数，并返回他们的数组下标。

     你可以假设每种输入只会对应一个答案。但是，你不能重复利用这个数组中同样的元素。

     示例:

     给定 nums = [2, 7, 11, 15], target = 9

     因为 nums[0] + nums[1] = 2 + 7 = 9
     所以返回 [0, 1]
     */
    public static int[] twoSum(int[] nums, int target) {
        //key:value value:index
        Map<Integer,Integer> numsMap = new HashMap<>();
        for (int i = 0; i < nums.length; i++) {
            if (nums[i] == target){
                return new int[]{i};
            }
            numsMap.put(nums[i],i);
        }
        for (Map.Entry<Integer,Integer> entry : numsMap.entrySet()){
            Integer isExistIndex = numsMap.get(target - entry.getKey());
            if (isExistIndex != null){
                return new int[]{entry.getValue(),isExistIndex};
            }
        }
        return new int[0];
    }


    /**
     *
     * 给出两个 非空 的链表用来表示两个非负的整数。其中，它们各自的位数是按照 逆序 的方式存储的，并且它们的每个节点只能存储 一位 数字。

     如果，我们将这两个数相加起来，则会返回一个新的链表来表示它们的和。

     您可以假设除了数字 0 之外，这两个数都不会以 0 开头。

     示例：

     输入：(2 -> 4 -> 3) + (5 -> 6 -> 4)
     输出：7 -> 0 -> 8
     原因：342 + 465 = 807
     */

    public class ListNode {
        int val;
        ListNode next;
        ListNode(int x) { val = x; }
    }


    public ListNode addTwoNumbers(ListNode l1, ListNode l2) {
        boolean isTen = false;
        ListNode headNode = null;
        while (l1 != null || l2 != null){
            int andVal = (l1 == null ? 0 : l1.val) + (l2 == null ? 0 : l2.val);
            l1 = l1 == null ? null : l1.next;
            l2 = l2 == null ? null : l2.next;
            if (isTen){
                andVal++;
            }
            if (andVal >= 10){
                isTen = true;
                andVal -= 10;
            }else{
                isTen = false;
            }
            ListNode newNode = new ListNode(andVal);
            if (headNode == null){
                headNode = newNode;
            }else{
                ListNode temp = headNode;
                while (temp.next != null){
                    temp = temp.next;
                }
                temp.next = newNode;
            }
        }
        if (isTen){
            ListNode temp = headNode;
            while (temp.next != null){
                temp = temp.next;
            }
            temp.next = new ListNode(1);
        }
        return headNode;
    }
}
