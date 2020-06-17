package app.web.pavelk.receiver1;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

public class Handler2 extends ChannelInboundHandlerAdapter {

    String name = "file/test1.txt";
    byte[] readByte = new byte[10];

    ByteBuf buf;
    byte b1;
    int n = 0;

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        buf = ((ByteBuf) msg);
        System.out.println(buf.readableBytes() + " =readableBytes");

        while (buf.readableBytes() > 0) {
            if (buf.readByte() == 13) {
                if (buf.readByte() == 10) {
                    if (buf.readByte() == 13) {
                        if (buf.readByte() == 10) {
                            int s = 0;
                            while (buf.readableBytes() > 0) {
                                b1 = buf.readByte();
                                if (b1 == 10) {
                                    if (s == 0 ){
                                        Files.write(Paths.get(name), "\n".getBytes(), StandardOpenOption.APPEND);
                                    }
                                    s++;
                                    continue;
                                }
                                s = 0;
                                Files.write(Paths.get(name), new byte[]{b1}, StandardOpenOption.APPEND);
                            }
                        }
                    }
                }
            }

        }

        if (buf.readableBytes() == 0) {
            buf.release();
        }
    }


    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        super.channelReadComplete(ctx);
        System.out.println("package");
        Files.write(Paths.get(name), ("\n--------\n").getBytes(), StandardOpenOption.APPEND);
        ctx.close();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        super.channelActive(ctx);
        System.out.println("Client is connected ");
        Files.write(Paths.get(name), new byte[]{}, StandardOpenOption.CREATE);
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        super.channelInactive(ctx);
        System.out.println("Client disconnected ");
    }

}
