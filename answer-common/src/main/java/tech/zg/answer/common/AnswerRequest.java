package tech.zg.answer.common;

/**
 * 封装 RPC 请求
 * 封装发送的object的反射属性
 * <p>
 *
 * @author ：zhanggong
 * @version : 1.0.0
 * @date ：2018/5/1
 */
public class AnswerRequest {

    /**
     * 一次rpc请求的id，用来标识
     */
    private String requestId;
    /**
     * 请求的类名称
     */
    private String className;
    /**
     * 请求的方法名
     */
    private String methodName;
    /**
     * 一次请求的入参类型
     */
    private Class<?>[] parameterTypes;
    /**
     * 入参值
     */
    private Object[] parameters;

    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    public Class<?>[] getParameterTypes() {
        return parameterTypes;
    }

    public void setParameterTypes(Class<?>[] parameterTypes) {
        this.parameterTypes = parameterTypes;
    }

    public Object[] getParameters() {
        return parameters;
    }

    public void setParameters(Object[] parameters) {
        this.parameters = parameters;
    }
}
