package tech.zg.answer.server.netty;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import tech.zg.answer.common.codec.AnswerDecoder;
import tech.zg.answer.common.codec.AnswerEncoder;
import tech.zg.answer.common.bean.AnswerRequest;
import tech.zg.answer.common.bean.AnswerResponse;

public class NettyServer {

    private static final Logger log = LoggerFactory.getLogger(NettyServer.class);
    private int port;

    public NettyServer(int port) {
        this.port = port;
    }

    public void run() throws InterruptedException {
        EventLoopGroup bossGroup = new NioEventLoopGroup(1);
        EventLoopGroup workGroup = new NioEventLoopGroup();

        try {
            // 配置服务器
            ServerBootstrap serverBootstrap = new ServerBootstrap();
            serverBootstrap.group(bossGroup, workGroup)
                    .channel(NioServerSocketChannel.class)
                    .option(ChannelOption.SO_BACKLOG, 128)
                    .childOption(ChannelOption.SO_KEEPALIVE, true)
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        // 这里每次连接都能获取到客户端的 channel， 这里就可以给用户一个标记和 channel 做一个标记，可以用来给用户推送消息
                        @Override
                        protected void initChannel(SocketChannel socketChannel) throws Exception {

                            socketChannel.pipeline().addLast(new AnswerDecoder(AnswerRequest.class));
                            socketChannel.pipeline().addLast(new AnswerEncoder(AnswerResponse.class));
                            socketChannel.pipeline().addLast(null);
                        }
                    });

            log.info("服务端初始化完成");
            // 启动服务器
            ChannelFuture channelFuture = serverBootstrap.bind(port).sync();

            channelFuture.addListener(new ChannelFutureListener() {
                @Override
                public void operationComplete(ChannelFuture channelFuture) throws Exception {
                    if (channelFuture.isSuccess()) {
                        log.info("服务器启动成功");
                    } else {
                        log.info("服务器启动失败");
                    }
                }
            });

            // 监听关闭通道
            channelFuture.channel().closeFuture().sync();
        } finally {
            bossGroup.shutdownGracefully();
            workGroup.shutdownGracefully();
        }
    }

}
