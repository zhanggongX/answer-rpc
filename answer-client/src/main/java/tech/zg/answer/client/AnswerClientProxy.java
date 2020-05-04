package tech.zg.answer.client;

import tech.zg.answer.common.bean.AnswerRequest;
import tech.zg.answer.common.bean.AnswerResponse;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.UUID;

public class AnswerClientProxy {

    private String serverAddress;
    private Integer serverPort;
    private AnswerNettyClient answerNettyClient;

    public AnswerClientProxy(String serverAddress) {
        this.serverAddress = serverAddress;
        this.serverPort = 8888;
    }

    public AnswerClientProxy(String serverAddress, Integer serverPort) {
        this.serverAddress = serverAddress;
        this.serverPort = serverPort;
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

                answerNettyClient = new AnswerNettyClient(serverAddress, serverPort);
                AnswerResponse response = answerNettyClient.send(request);
                if (response.isError()) {
                    throw response.getError();
                } else {
                    return response.getResult();
                }
            }
        });
    }
}
