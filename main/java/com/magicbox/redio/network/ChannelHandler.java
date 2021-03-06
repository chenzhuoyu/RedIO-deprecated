package com.magicbox.redio.network;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;

import com.magicbox.redio.common.Constants;
import com.magicbox.redio.network.packets.IPacketProtocol;
import com.magicbox.redio.network.packets.PacketEntityBusCableUpdate;
import com.magicbox.redio.network.packets.PacketEntityIOUpdate;
import com.magicbox.redio.network.packets.PacketEntityProcessorUpdate;
import com.magicbox.redio.network.packets.PacketEntityScriptStorageUpdate;

import cpw.mods.fml.common.network.FMLIndexedMessageToMessageCodec;

public class ChannelHandler extends FMLIndexedMessageToMessageCodec<IPacketProtocol>
{
	public ChannelHandler()
	{
		addDiscriminator(Constants.Packets.packetIO, PacketEntityIOUpdate.class);
		addDiscriminator(Constants.Packets.packetBusCable, PacketEntityBusCableUpdate.class);
		addDiscriminator(Constants.Packets.packetProcessor, PacketEntityProcessorUpdate.class);
		addDiscriminator(Constants.Packets.packetScriptStorage, PacketEntityScriptStorageUpdate.class);
	}

	@Override
	public void decodeInto(ChannelHandlerContext context, ByteBuf source, IPacketProtocol packetProtocol)
	{
		packetProtocol.readData(source);
	}

	@Override
	public void encodeInto(ChannelHandlerContext context, IPacketProtocol packetProtocol, ByteBuf target) throws Exception
	{
		packetProtocol.writeData(target);
	}
}
