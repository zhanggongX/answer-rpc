package tech.zg.answer.common.bean;

/**
 * 封装 RPC 响应
 * 封装相应object
 * <p>
 *
 * @author ：zhanggong
 * @version : 1.0.0
 * @date ：2018/5/1
 */
public class AnswerResponse {

    /**
     * 请求的id
     */
    private String requestId;
    /**
     * 是否抛出错误
     */
    private Throwable error;
    /**
     * 结果
     */
    private Object result;

    public boolean isError() {
        return error != null;
    }

    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

    public Throwable getError() {
        return error;
    }

    public void setError(Throwable error) {
        this.error = error;
    }

    public Object getResult() {
        return result;
    }

    public void setResult(Object result) {
        this.result = result;
    }
}
