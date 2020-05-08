package tech.zg.answer.client;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import tech.zg.answer.client.cache.AnswerClientRequestCache;
import tech.zg.answer.client.handler.ServerClientHandler;
import tech.zg.answer.common.bean.AnswerRequest;
import tech.zg.answer.common.bean.AnswerResponse;
import tech.zg.answer.common.codec.AnswerDecoder;
import tech.zg.answer.common.codec.AnswerEncoder;

public class AnswerNettyThread implements Runnable {

    private static final Logger log = LoggerFactory.getLogger(AnswerNettyThread.class);
    private String host;
    private int port;

    public AnswerNettyThread(String host, int port) {
        this.host = host;
        this.port = port;
    }

    @Override
    public void run() {
        EventLoopGroup eventLoopGroup = new NioEventLoopGroup();
        try {
            Bootstrap bootstrap = new Bootstrap();
            // 配置线程组
            bootstrap.group(eventLoopGroup)
                    // 配置客户端通道channel 实现类
                    .channel(NioSocketChannel.class)
                    .option(ChannelOption.SO_KEEPALIVE, true)
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel socketChannel) throws Exception {
                            // 配置处理器
                            socketChannel.pipeline()
                                    .addLast(new AnswerEncoder(AnswerRequest.class))
                                    .addLast(new AnswerDecoder(AnswerResponse.class))
                                    .addLast(new ServerClientHandler());
                        }
                    });
            log.info("客户端初始化完成");

            ChannelFuture channelFuture = bootstrap.connect(host, port).sync();
            log.info("客户端启动成功");

            channelFuture.channel().closeFuture().addListener(new ChannelFutureListener() {
                @Override
                public void operationComplete(ChannelFuture channelFuture) throws Exception {
                    log.info("客户端关闭");
                }
            });

            for (; ; ) {
                Thread.sleep(100);
                AnswerRequest request = null;
                try {
                    request = AnswerClientRequestCache.pollRequest();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                if (request != null) {
                    channelFuture.channel().writeAndFlush(request);
                }
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
            eventLoopGroup.shutdownGracefully();
        } finally {
            eventLoopGroup.shutdownGracefully();
        }
    }
}
