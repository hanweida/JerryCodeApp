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
        //System.out.println(fn(4));
        //System.out.println(fn2(3));
        //System.out.println(fn2(3));
        System.out.println(fun(7));
        System.out.println(fn4(1));
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

    /**
     * description:求1+2+3+..n 
     * @author:Jerry 
     * @method:fn 
     * @date: 2018/11/27
     * @param:[n]
     * @Returns:int
     */
    int fn(int n){
        if(n == 1) return 1;
        return fn(n-1) + n;
    }

    //求1*2*3*……*n的值
    int fn2(int n){
        if(n == 1){ return 1;}
        return fn2(n - 1) * n;
    }

    //小猴子第一天摘下若干桃子,当即吃掉一半,又多吃一个.第二天早上又将剩下的桃子吃
    //一半,又多吃一个.以后每天早上吃前一天剩下的一半另一个.到第10天早上猴子想再吃时发
    //现,只剩下一个桃子了.问第一天猴子共摘多少个桃子？

    int fn3(int day){
        if(day == 10){
            return 1;
        }
        return 2*(fn(day+1)+1);
    }

    //.一个人赶着鸭子去每个村庄卖，每经过一个村子卖去所赶鸭子的一半又一只。这样他
    //经过了七个村子后还剩两只鸭子，问他出发时共赶多少只鸭子？经过每个村子卖出多少只
    //鸭子？
    int fn4(int n){
        if(n == 7){
            return 2;
        }
        return 2*(fn(n+1)+1);
    }
    //.一个人赶着鸭子去每个村庄卖，每经过一个村子卖去所赶鸭子的一半又一只。这样他
    //经过了七个村子后还剩两只鸭子，问他出发时共赶多少只鸭子？经过每个村子卖出多少只
    //鸭子？
    int fun(int village) {
        if (village == 1){
            //System.out.println("2");
            return 2;
        }
        int temp=fun(village - 1);

        //System.out.println( (temp+1)+" ");
        return (2 * temp + 1);
    }
}
