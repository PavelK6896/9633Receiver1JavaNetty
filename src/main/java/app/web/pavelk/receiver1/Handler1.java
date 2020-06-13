package app.web.pavelk.receiver1;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

public class Handler1 extends ChannelInboundHandlerAdapter {

    String name = "file/q11.html";
    byte[] readByte = new byte[10];

    ByteBuf buf;

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        buf = ((ByteBuf) msg);
        System.out.println(buf.readableBytes() + " =readableBytes");


        while (buf.readableBytes() > 0) {
            if (buf.readableBytes() >= 10) {
                buf.readBytes(readByte);
                Files.write(Paths.get(name), readByte, StandardOpenOption.APPEND);
            } else {
                Files.write(Paths.get(name), new byte[]{buf.readByte()}, StandardOpenOption.APPEND);
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
