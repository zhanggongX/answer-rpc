package tech.zg.answer.client;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import tech.zg.answer.common.bean.AnswerRequest;
import tech.zg.answer.common.bean.AnswerResponse;
import tech.zg.answer.common.codec.AnswerDecoder;
import tech.zg.answer.common.codec.AnswerEncoder;

public class AnswerNettyClient extends SimpleChannelInboundHandler<AnswerResponse> {

    private static final Logger log = LoggerFactory.getLogger(AnswerNettyClient.class);

    private String host;
    private Integer port;
    private Object lock = new Object();
    private AnswerResponse answerResponse;

    public AnswerNettyClient(String host, Integer port) {
        this.host = host;
        this.port = port;
    }

    public AnswerResponse send(AnswerRequest request) throws InterruptedException {
        EventLoopGroup eventLoopGroup = new NioEventLoopGroup();

        try {
            Bootstrap bootstrap = new Bootstrap();
            // 配置线程组
            bootstrap.group(eventLoopGroup)
                    // 配置客户端通道channel 实现类
                    .channel(NioSocketChannel.class)
                    //.option(ChannelOption.SO_KEEPALIVE, true)
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel socketChannel) throws Exception {
                            // 配置处理器
                            socketChannel.pipeline()
                                    .addLast(new AnswerEncoder(AnswerRequest.class))
                                    .addLast(new AnswerDecoder(AnswerResponse.class))
                                    .addLast(AnswerNettyClient.this);
                        }
                    });
            log.info("客户端初始化完成");
            ChannelFuture channelFuture = bootstrap.connect(host, port).sync();
            Channel channel = channelFuture.channel();
            channel.writeAndFlush(request).sync();

            synchronized (lock) {
                lock.wait();
            }
            if (answerResponse != null) {
                channelFuture.channel().closeFuture().sync();
                return answerResponse;
            }

            log.info("客户端启动成功");
        } finally {
            eventLoopGroup.shutdownGracefully();
        }

        return null;
    }

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, AnswerResponse answerResponse) throws Exception {
        this.answerResponse = answerResponse;
        synchronized (lock) {
            lock.notifyAll();
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        log.info("异常-{}", cause.getMessage());
        ctx.close();
    }
}
