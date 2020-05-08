package tech.zg.answer.client.handler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import tech.zg.answer.client.cache.AnswerClientResponseCache;
import tech.zg.answer.common.bean.AnswerResponse;

public class ServerClientHandler extends SimpleChannelInboundHandler<AnswerResponse> {

    private static final Logger log = LoggerFactory.getLogger(ServerClientHandler.class);

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, AnswerResponse answerResponse) throws Exception {
        AnswerClientResponseCache.cacheResponse(answerResponse.getRequestId(), answerResponse);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        log.info("异常-{}", cause.getMessage());
        ctx.close();
    }
}
