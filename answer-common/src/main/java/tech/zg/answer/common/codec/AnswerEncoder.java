package tech.zg.answer.common.codec;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import tech.zg.answer.common.util.AnswerSerializationUtil;

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

    public AnswerEncoder(Class<?> genericClass) {
        this.genericClass = genericClass;
    }

    /**
     * 编码
     * <p>
     *
     * @param ctx
     * @param inObject
     * @param out
     * @return
     * @throws
     * @author : zhanggong
     * @version : 1.0.0
     * @date : 2018/5/1
     */
    @Override
    public void encode(ChannelHandlerContext ctx, Object inObject, ByteBuf out) throws Exception {
        //序列化
        if (genericClass.isInstance(inObject)) {
            byte[] data = AnswerSerializationUtil.serialize(inObject);
            out.writeInt(data.length);
            out.writeBytes(data);
        }
    }
}