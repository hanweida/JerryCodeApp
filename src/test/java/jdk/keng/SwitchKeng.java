package jdk.keng;

import org.junit.Test;

public class SwitchKeng {
    /**
     * 3 没有break, 会执行default
     */
    @Test
    public void switch1(){
        System.out.println("switch1------------------");
        int id = 3;
        switch (id){
            case 2:
                System.out.println("2");
                break;
            case 3:
                System.out.println("3");
            default:
                System.out.println("default");
        }
    }

    /**
     * 穿透效果 坑：case1 没有break，会继续向下执行，直到遇到break
     */
    @Test
    public void switch2(){
        System.out.println("switch2------------------");
        int id = 1;
        switch (id){
            case 1:
                System.out.println("1");
            case 2:
                System.out.println("2");
                break;
            case 3:
                System.out.println("3");
        }
    }

    /**
     * default坑, 如果没有匹配上，则执行default，然后向下执行 直到没有遇到break
     */
    @Test
    public void switch3(){
        System.out.println("switch3------------------");
        int id = 4;
        switch (id){
            default:
                System.out.println("default");
            case 1:
                System.out.println("1");
            case 2:
                System.out.println("2");
                break;
            case 3:
                System.out.println("3");
        }
    }
}
