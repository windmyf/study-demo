package com.myf.wind.base.rpcdemo.rpc.transport;

import com.myf.wind.base.rpcdemo.rpc.ResponseMappingCallback;
import com.myf.wind.base.rpcdemo.rpc.model.PackageMsg;
import io.netty.channel.*;

/**
 * εεΊε€η
 * @author wind_myf
 */
public class ClientResponse extends ChannelInboundHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {

        PackageMsg responseMsg = (PackageMsg)msg;
        ResponseMappingCallback.runCallBack(responseMsg);
    }
}




