/**
 *
 */
package com.project.callbreak.websocket;

import com.project.callbreak.nio.NIOConstants;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.DefaultFullHttpResponse;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.FullHttpResponse;
import static io.netty.handler.codec.http.HttpHeaders.Names.HOST;
import static io.netty.handler.codec.http.HttpHeaders.isKeepAlive;
import static io.netty.handler.codec.http.HttpHeaders.setContentLength;
import static io.netty.handler.codec.http.HttpMethod.GET;
import static io.netty.handler.codec.http.HttpResponseStatus.BAD_REQUEST;
import static io.netty.handler.codec.http.HttpVersion.HTTP_1_1;
import io.netty.handler.codec.http.websocketx.CloseWebSocketFrame;
import io.netty.handler.codec.http.websocketx.PingWebSocketFrame;
import io.netty.handler.codec.http.websocketx.PongWebSocketFrame;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.handler.codec.http.websocketx.WebSocketFrame;
import io.netty.handler.codec.http.websocketx.WebSocketServerHandshaker;
import io.netty.handler.codec.http.websocketx.WebSocketServerHandshakerFactory;
import io.netty.util.Attribute;
import io.netty.util.CharsetUtil;

/**
 * @author Yellaiah Devasani It is base handler to handle websockets request,
 * All websocket request will come to this handler. It reads message and
 * delegates to implementor. Who ever extends this class need to implement all
 * abstract methods to achieve their own behaviour.
 *
 */
abstract public class AbstractWebSocketHandler extends SimpleChannelInboundHandler<Object> {

    private static final String WEBSOCKET_PATH = "/html";
    private static final String WEBSOCKET_LOGICAL_PROTOCOL = "wss://";

    protected WebSocketServerHandshaker handshaker;

    String msg = "";

    @Override
    public void channelRead0(ChannelHandlerContext ctx, Object msg) throws Exception {

        if (msg instanceof FullHttpRequest) {
            handleHttpRequest(ctx, (FullHttpRequest) msg);
        } else if (msg instanceof WebSocketFrame) {
            handleWebSocketFrame(ctx, (WebSocketFrame) msg);
        }
    }

    /**
     * It validate the message type and pass to the implementor to takecare
     * specific behaviour
     *
     * @param ctx - ChannelHandlerContext.
     * @param msg - WebSocketFrame. it is frame contains client message and
     * request details.
     */
    public void handleWebSocketFrame(ChannelHandlerContext ctx, WebSocketFrame msg) {
        // Check for closing frame- close socket if close request
        if (msg instanceof CloseWebSocketFrame) {
            handshaker.close(ctx.channel(), (CloseWebSocketFrame) msg.retain());
            return;

        } else if (msg instanceof PingWebSocketFrame) {
            // ping protocol for keeping socket live
            ctx.channel().write(new PongWebSocketFrame(msg.content().retain()));
            return;
        } else if (msg instanceof PongWebSocketFrame) {
            // handling binary data- we will be handling only text based data, so empty implementation
            return;
        }
        if (!(msg instanceof TextWebSocketFrame)) {
            // throwing exception if any data type
            throw new UnsupportedOperationException(String.format("%s frame types not supported", msg.getClass().getName()));
        }

        unmergeProtocols(((TextWebSocketFrame) msg).text(), ctx);

    }

    /**
     * It validates whether request coming from supported version client or not.
     * if supported version, then it created handshaking so that two directional
     * communication channel established.
     *
     * @param ctx -ChannelHandlerContext
     * @param req -FullHttpRequest it contains http request information
     * @throws Exception - Throws if any un handled exception. even for
     * unsupported version.
     */
    private void handleHttpRequest(ChannelHandlerContext ctx, FullHttpRequest req) throws Exception {
        // not required to handle
        //System.out.println("Decoder res "+req.getDecoderResult().isSuccess());
        if (!req.getDecoderResult().isSuccess() || req.getMethod() != GET || "/".equals(req.getUri())) {
            sendHttpResponse(ctx, req, new DefaultFullHttpResponse(HTTP_1_1, BAD_REQUEST));
            System.out.println("return at Bad request " + req);
            return;
        }

        // Handshake- this must be happen in order to send and receive message
        WebSocketServerHandshakerFactory wsFactory = new WebSocketServerHandshakerFactory(getWebSocketLocation(req), null, false);
        handshaker = wsFactory.newHandshaker(req);
        if (handshaker == null) {
            WebSocketServerHandshakerFactory.sendUnsupportedVersionResponse(ctx.channel());
        } else {
            handshaker.handshake(ctx.channel(), req);
            Attribute<String> protocolAtt = ctx.attr(NIOConstants.PROTOCOL);
            protocolAtt.set(NIOConstants.WEB_SOCKET_PROTOCOL);
        }
    }

    /**
     * It creates websocket location or URL with context
     *
     * @param req -FullHttpRequest it contains http request information
     * @return String - Websocket location
     */
    private String getWebSocketLocation(FullHttpRequest req) {
        return WEBSOCKET_LOGICAL_PROTOCOL + req.headers().get(HOST) + WEBSOCKET_PATH;
    }

    /**
     * Simply sends response to client and closes the channle if keepAlive is
     * not true or status code is not found
     *
     * @param ctx -ChannelHandlerContext
     * @param req -FullHttpRequest
     * @param res -FullHttpResponse
     */
    protected void sendHttpResponse(ChannelHandlerContext ctx, FullHttpRequest req, FullHttpResponse res) {

        // Generate an error page if response getStatus code is not OK (200).
//        LOGGER.info("Res "+res.getStatus());
        if (res.getStatus().code() != 200) {
            ByteBuf buf = Unpooled.copiedBuffer(res.getStatus().toString(), CharsetUtil.UTF_8);
            res.content().writeBytes(buf);
            buf.release();
            setContentLength(res, res.content().readableBytes());
        }

        // Send the response and close the connection if necessary.
        ChannelFuture f = ctx.channel().writeAndFlush(res);
        if (!isKeepAlive(req) || res.getStatus().code() != 200) {
            f.addListener(ChannelFutureListener.CLOSE);
        }
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        super.channelInactive(ctx);
        //logger.info("channelInActive");
        // session tracking - start
        Attribute<Object> state = ctx.attr(NIOConstants.ATTACHMENT);
        // session tracking -end
        Object session = state.get();
        channelDisconnected(ctx, session);
        System.out.println("ChannelDisconnected-Inactive: "+ ctx.channel().toString());
        
        ctx.close();

    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        super.channelActive(ctx);
        channelConnected(ctx);

    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {

        Attribute<Object> state = ctx.attr(NIOConstants.ATTACHMENT);
        // session tracking -end
        Object session = state.get();
        channelDisconnected(ctx, session);
        System.out.println("ChannelDisconnected-exceptionCaught: "+ ctx.channel());
        System.out.println("ChannelDisconnected-exceptionCaught: "+ ctx.channel().toString());
        ctx.close();
    }

    abstract protected void unmergeProtocols(String msg, ChannelHandlerContext ctx);

    abstract protected void channelDisconnected(ChannelHandlerContext ctx, Object atatchment) throws Exception;

    abstract protected void channelConnected(ChannelHandlerContext ctx) throws Exception;
}