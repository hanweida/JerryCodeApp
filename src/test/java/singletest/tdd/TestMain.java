package singletest.tdd;

import org.junit.Assert;
import org.junit.Test;

public class TestMain {
    @Test
    public void test(){
        FizzBuzz fizzBuzz = new FizzBuzz();

        String number = fizzBuzz.of(1);

        Assert.assertTrue(number.equals("1"));
    }

    @Test
    public void testMars(){
        // info x y

        //init x y

        //init nesw

        //move f

        //move b

        //turn l

        //turn r

        //List map(x y)
        //List map(forward)
    }
}
