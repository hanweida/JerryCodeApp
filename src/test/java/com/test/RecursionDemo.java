package com.test;

import org.junit.Test;

/**
 * description:递归
 * @author:Jerry 
 * @method: 
 * @date: 2018/11/26
 * @param:
 * @Returns:
 */
public class RecursionDemo {
    @Test
    public void testRecursion(){
        //System.out.println(f(11));
        System.out.println(f2(10));
    }

    int f(int i){
        if(i == 1){
            return 1;
        }
        return f(i - 1) + 1;
    }

    int f2(int n) {
        if (n == 1) return 1;
        if (n == 2) return 2;
        return f2(n-1) + f(n-2);
    }
}
