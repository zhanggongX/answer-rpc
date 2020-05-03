package tech.zg.answer.client.handler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import tech.zg.answer.client.AnswerClientResult;
import tech.zg.answer.common.bean.AnswerResponse;

public class ClientHandler extends SimpleChannelInboundHandler<AnswerResponse> {

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, AnswerResponse answerResponse) throws Exception {
        AnswerClientResult.cacheResponse(answerResponse.getRequestId(), answerResponse);
    }
}
