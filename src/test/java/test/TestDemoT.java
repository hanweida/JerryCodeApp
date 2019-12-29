package test;

import com.jerry.javase.fanxing.DemoT;
import org.junit.Test;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;


/**
 * Created by ES-BF-IT-126 on 2018/1/8.
 */
public class TestDemoT {
    private static int i =0;

    public static void main(String[] args) {
        Random random = new Random();

        System.out.println(random.nextInt(900));
    }


    @Test
    public void test(){
        if( 1 == ++i || 2 == ++i){
            System.out.println(i);
        }
    }

    @Test
    public void test2(){
        StringBuilder a= null;
        //System.out.println(a.append("d"));
        Cell b = null;
        System.out.println(b.cas(1, 1));
    }

    static final class Cell {
        volatile long value;
        Cell(long x) { value = x; }
        final boolean cas(long cmp, long val) {
            return true;
        }
    }


    @Test
    public void test3(){
        String s = "abcdbefg";
        System.out.println(lengthOfLongestSubstring(s));
        //System.out.println(lengthOfLongestSubstring2(s));
    }

    public int lengthOfLongestSubstring(String s) {
        int length = s.length(), ans =0;
        HashMap<Character, Integer> hashMap = new HashMap<>();

        for (int k = 0, i=0;k<length;k++){
            
            if(hashMap.containsKey(s.charAt(k))){
                i = Math.max(hashMap.get(s.charAt(k)), i);
            }
            ans = Math.max(ans, k - i + 1);
            hashMap.put(s.charAt(k), k + 1);

        }
        return ans;
    }

//    public int lengthOfLongestSubstring2(String s) {
//        int n = s.length();
//        int max = 0;
//        Map<Character, Integer> map = new HashMap<>();
//
//        for (int i = 0, j =0; j<n ; j++){
//
//        }
//    }

    @Test
    public void test4(){
        String s = "abcba";
        System.out.println(longestPalindrome(s));
        //System.out.println(lengthOfLongestSubstring2(s));
    }

    public String longestPalindrome(String s) {
        int length = s.length();
        boolean[][] P = new boolean[length][length];
        int maxLen = 0;
        String maxPal = "";
        for (int len = 1; len <= length; len++) //遍历所有的长度
            for (int start = 0; start < length; start++) {
                int end = start + len - 1;
                if (end >= length) //下标已经越界，结束本次循环
                    break;
                P[start][end] = (len == 1 || len == 2 || P[start + 1][end - 1]) && s.charAt(start) == s.charAt(end); //长度为 1 和 2 的单独判断下
                if (P[start][end] && len > maxLen) {
                    maxPal = s.substring(start, end + 1);
                }
            }
        return maxPal;
    }

    public boolean isRepeat(String s, int i, int j){
        HashSet hashSet = new HashSet();

        for(int start = i ;start<j;start++){
            char a = s.charAt(start);
            if(!hashSet.contains(a)){
                hashSet.add(a);
            } else{
                return true;
            }
        }
        return false;
    }
}
