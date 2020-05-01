package tech.zg.answer.common.codec;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import tech.zg.answer.common.util.AnswerSerializationUtil;

import java.util.List;

/**
 * RPC 解码器
 * <p>
 *
 * @author ：zhanggong
 * @version : 1.0.0
 * @date ：2018/5/1
 */
public class AnswerDecoder extends ByteToMessageDecoder {

    private Class<?> genericClass;

    public AnswerDecoder(Class<?> genericClass) {
        this.genericClass = genericClass;
    }

    /**
     * 解码
     * <p>
     *
     * @param ctx
     * @param in
     * @param out
     * @return
     * @throws
     * @author : zhanggong
     * @version : 1.0.0
     * @date : 2018/5/1
     */
    @Override
    public final void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        if (in.readableBytes() < 4) {
            return;
        }
        in.markReaderIndex();
        int dataLength = in.readInt();
        if (dataLength < 0) {
            ctx.close();
        }
        if (in.readableBytes() < dataLength) {
            in.resetReaderIndex();
        }
        //将ByteBuf转换为byte[]
        byte[] data = new byte[dataLength];
        in.readBytes(data);
        //将data转换成object
        Object obj = AnswerSerializationUtil.deserialize(data, genericClass);
        out.add(obj);
    }
}
