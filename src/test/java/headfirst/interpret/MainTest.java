package headfirst.interpret;

/**
 * 解释器模式
 * 领域：比较小众，在一些特定领域被用到，如 编译器、规则引擎、正则表达式
 * 原理和实现：对于复杂语法规则解析，逻辑复杂，代码量多，解析逻辑都耦合在一个函数中，是不合适的；需要将逻辑分到独立的小类中
 * 核心思想：将语法解析的工作拆分到各个小类中，以此来避免大而全的解析类，将语法规则拆分成一些小的独立单元，对每个单元进行解析，最终合并整个语法规则解析
 */
public class MainTest {
    public static void main(String[] args) {
        NumberExpression numberExpression = new NumberExpression(1);
        NumberExpression numberExpression2 = new NumberExpression(2);

        String operator = "+";

        Expression additionExpression = null;

        //如果是加法的话，相当于把 解析的规则 根据类型 区分每个子类，在子类中进行 运算，返回结果
        if(operator.equals("+")){
            additionExpression = new AdditionExpression(numberExpression, numberExpression2);
        }
        long result = additionExpression.interpret();

        System.out.println(result);
    }
}
