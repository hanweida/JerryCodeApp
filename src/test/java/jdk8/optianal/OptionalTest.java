package jdk8.optianal;

import common.pojo.User;
import org.junit.Test;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.Optional;

public class OptionalTest {

    @Test
    public void testSimple(){
    //API
        //构造
            //当null值传入，of() 会抛出NullPointerException 、ofNullable()
            User user = null;
            Optional<User> optional = Optional.of(user);
            //throw NullPointerException

            Optional<User> optionalAble = Optional.ofNullable(user);
            //不会抛出异常
        //判断有值
            if(!optional.isPresent()){
                //doing somting
            }
            //只有 user 用户不为 null 的时候才会执行断言
            optional.ifPresent(user1 -> assertThat(user1.getId()).isNull());
        //返回默认值
            user = Optional.ofNullable(user).orElse(new User());
            //如果没有值，它会执行作为参数传入的 Supplier(供应者) 函数式接口，并将返回其执行结果
            user = Optional.ofNullable(user).orElseGet(()->new User());
            //这个示例中，两个 Optional  对象都包含非空值，两个方法都会返回对应的非空值。不过，orElse() 方法仍然创建了 User 对象。与之相反，orElseGet() 方法不创建 User 对象。
        //返回异常
            user = Optional.ofNullable(user).orElseThrow( () -> new IllegalArgumentException());
        //转换值
            String email = Optional.ofNullable(user)
                .map(u -> u.getName()).orElse("default@gmail.com");

    }
}
