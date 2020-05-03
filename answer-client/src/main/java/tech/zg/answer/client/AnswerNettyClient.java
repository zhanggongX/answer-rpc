package tech.zg.answer.client;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import tech.zg.answer.client.handler.ClientHandler;
import tech.zg.answer.common.bean.AnswerRequest;
import tech.zg.answer.common.bean.AnswerResponse;
import tech.zg.answer.common.codec.AnswerDecoder;
import tech.zg.answer.common.codec.AnswerEncoder;

import java.util.concurrent.TimeoutException;

public class AnswerNettyClient {

    private static final Logger log = LoggerFactory.getLogger(AnswerNettyClient.class);

    private String host;
    private Integer port;
    private Channel channel;

    public AnswerNettyClient(String host, Integer port) {
        this.host = host;
        this.port = port;
    }

    public AnswerResponse send(AnswerRequest request) throws TimeoutException {
        if (channel == null) {
            throw new RuntimeException("服务未连接");
        }
        channel.writeAndFlush(request);
        return AnswerClientResult.getResponse(request.getRequestId(), 3000);
    }

    public void connect() throws InterruptedException {
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
                                    .addLast(new ClientHandler());
                        }
                    });
            log.info("客户端初始化完成");
            channel = bootstrap.connect(host, port).sync().channel();
            log.info("客户端启动成功");
        } finally {
            eventLoopGroup.shutdownGracefully();
        }
    }
}
