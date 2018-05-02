package tech.zg.answer.common;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

/**
 * RPC 编码器
 * <p>
 *
 * @author ：zhanggong
 * @version : 1.0.0
 * @date ：2018/5/1
 */
public class AnswerEncoder extends MessageToByteEncoder {

    private Class<?> genericClass;

    // 构造函数传入向反序列化的class
    public AnswerEncoder(Class<?> genericClass) {
        this.genericClass = genericClass;
    }

    /**
     * 编码
     * <p>
     *
     * @param ctx
     * @param inob
     * @param out
     * @return
     * @throws
     * @author : zhanggong
     * @version : 1.0.0
     * @date : 2018/5/1
     */
    @Override
    public void encode(ChannelHandlerContext ctx, Object inob, ByteBuf out)
            throws Exception {
        //序列化
        if (genericClass.isInstance(inob)) {
            byte[] data = AnswerSerializationUtil.serialize(inob);
            out.writeInt(data.length);
            out.writeBytes(data);
        }
    }
}