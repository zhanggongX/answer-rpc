package tech.zg.answer.client;

import tech.zg.answer.client.thread.SingleThreadPool;
import tech.zg.answer.common.bean.AnswerRequest;
import tech.zg.answer.common.bean.AnswerResponse;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.UUID;

public class AnswerClientProxy {

    public AnswerClientProxy(String serverAddress, Integer serverPort) throws InterruptedException {
        // 启动netty客户端线程
        SingleThreadPool.SINGLE_THREAD_POOL_EXECUTOR.execute(new AnswerNettyThread(serverAddress, serverPort));
    }

    @SuppressWarnings("unchecked")
    public <T> T create(Class<T> interfaceClass) {

        return (T) Proxy.newProxyInstance(interfaceClass.getClassLoader(), new Class[]{interfaceClass}, new InvocationHandler() {
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                AnswerRequest request = new AnswerRequest();
                request.setRequestId(UUID.randomUUID().toString());
                request.setClassName(method.getDeclaringClass().getName());
                request.setMethodName(method.getName());
                request.setParameterTypes(method.getParameterTypes());
                request.setParameters(args);

                AnswerResponse response = AnswerClient.send(request);
                if (response.isError()) {
                    throw response.getError();
                } else {
                    return response.getResult();
                }
            }
        });
    }
}
