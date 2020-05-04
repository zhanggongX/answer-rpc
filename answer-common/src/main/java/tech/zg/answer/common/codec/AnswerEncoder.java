package tech.zg.answer.common.codec;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    private static final Logger log = LoggerFactory.getLogger(AnswerEncoder.class);

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
        log.info("请求序列化");
        //序列化
        if (genericClass.isInstance(inObject)) {
            byte[] data = AnswerSerializationUtil.serialize(inObject);
            out.writeInt(data.length);
            out.writeBytes(data);
        }
    }
}