package com.cc.ccspace.facade.domain.common.test.aio.server;

import com.cc.ccspace.facade.domain.bizobject.HostInfo;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.AsynchronousServerSocketChannel;
import java.util.concurrent.CountDownLatch;

/**
 * @AUTHOR CF
 * @DATE Created on 2019/4/9/009 11:39.
 */


@Slf4j
public class AioEchoServer {

}
@Slf4j
class AIOServerThread  implements  Runnable{
    //服务器通道
    private AsynchronousServerSocketChannel serverChannel=null;
    private CountDownLatch latch=null;
    public AIOServerThread() throws IOException {
        //等待线程数量为1
        this.latch=new  CountDownLatch(1);
        //打开异步的服务端通道
        this.serverChannel = AsynchronousServerSocketChannel.open();
        //绑定端口
        this.serverChannel.bind(new InetSocketAddress(HostInfo.HOST_NAME,HostInfo.PORT));
    }

    @Override
    public void run() {

    }
}
