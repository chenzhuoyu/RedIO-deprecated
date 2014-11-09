package com.magicbox.redio.network;

import java.util.EnumMap;

import com.magicbox.redio.common.Constants;
import com.magicbox.redio.network.packets.IPacketProtocol;

import cpw.mods.fml.common.network.FMLEmbeddedChannel;
import cpw.mods.fml.common.network.FMLOutboundHandler;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.relauncher.Side;

public class Network
{
	private static final EnumMap<Side, FMLEmbeddedChannel> channels = NetworkRegistry.INSTANCE.newChannel(
		Constants.CHANNEL_NAME,
		new ChannelHandler(),
		new PacketHandler());

	public static void broadcastToClients(IPacketProtocol packetProtocol)
	{
		try
		{
			channels.get(Side.SERVER).attr(FMLOutboundHandler.FML_MESSAGETARGET).set(FMLOutboundHandler.OutboundTarget.ALL);
			channels.get(Side.SERVER).writeOutbound(packetProtocol);
		} catch (Exception e)
		{
			e.printStackTrace();
		}
	}
}
