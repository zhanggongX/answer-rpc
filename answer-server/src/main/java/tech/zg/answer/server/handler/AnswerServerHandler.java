package tech.zg.answer.server.handler;

import io.netty.channel.Channel;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import tech.zg.answer.common.bean.AnswerRequest;
import tech.zg.answer.common.bean.AnswerResponse;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;

public class AnswerServerHandler extends SimpleChannelInboundHandler<AnswerRequest> {

    private static final Logger log = LoggerFactory.getLogger(AnswerServerHandler.class);

    private Map<String, Object> serviceMap;

    public AnswerServerHandler(Map<String, Object> serviceMap) {
        this.serviceMap = serviceMap;
    }

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, AnswerRequest answerRequest) throws Exception {
        AnswerResponse answerResponse = new AnswerResponse();
        answerResponse.setRequestId(answerRequest.getRequestId());
        try {
            Object result = handle(answerRequest);
            answerResponse.setResult(result);
        } catch (Exception e) {
            answerResponse.setError(e);
        }
        channelHandlerContext.writeAndFlush(answerResponse).addListener(ChannelFutureListener.CLOSE);
    }

    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        Channel channel = ctx.channel();
        log.info("{} 已连接", channel.remoteAddress());
    }

    private Object handle(AnswerRequest answerRequest) throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        String className = answerRequest.getClassName();
        String methodName = answerRequest.getMethodName();

        Object service = serviceMap.get(className);
        Class<?>[] parameterTypes = answerRequest.getParameterTypes();
        Object[] parameters = answerRequest.getParameters();

        Class<?> aClass = Class.forName(className);
        Method method = aClass.getMethod(methodName, parameterTypes);
        return method.invoke(service, parameters);
    }
}
