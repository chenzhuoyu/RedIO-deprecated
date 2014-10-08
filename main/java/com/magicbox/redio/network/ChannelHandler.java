package com.magicbox.redio.network;

import com.magicbox.redio.network.packets.PacketBase;
import com.magicbox.redio.network.packets.PacketEntityProcessorUpdate;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import cpw.mods.fml.common.network.FMLIndexedMessageToMessageCodec;

public class ChannelHandler extends FMLIndexedMessageToMessageCodec<PacketBase>
{
	public ChannelHandler()
	{
		addDiscriminator(0, PacketEntityProcessorUpdate.class);
	}

	@Override
	public void decodeInto(ChannelHandlerContext context, ByteBuf source, PacketBase packet)
	{
		packet.readData(source);
	}

	@Override
	public void encodeInto(ChannelHandlerContext context, PacketBase packet, ByteBuf target) throws Exception
	{
		packet.writeData(target);
	}
}
