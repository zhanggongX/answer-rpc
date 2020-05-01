package tech.zg.answer.server;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
* RPC 请求注解（标注在服务实现类上）
* <p>
* @author ：zhanggong
* @version : 1.0.0
* @date ：2018/5/1
*/
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface Answer {

    Class<?> value();
}