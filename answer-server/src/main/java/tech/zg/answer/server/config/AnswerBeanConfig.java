package tech.zg.answer.server.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.context.annotation.Configuration;

@Configuration
@AutoConfigureAfter(AnswerConfig.class)
public class AnswerBeanConfig {

    @Autowired
    private AnswerConfig answerConfig;

    /*<bean id="serviceRegistry" class="cn.itcast.rpc.registry.ServiceRegistry">
        <constructor-arg name="registryAddress" value="${registry.address}"/>
    </bean>

    <bean id="rpcServer" class="cn.itcast.rpc.server.RpcServer">
        <constructor-arg name="serverAddress" value="${server.address}"/>
        <constructor-arg name="serviceRegistry" ref="serviceRegistry"/>
    </bean>*/

    // public getAnswerServer


}