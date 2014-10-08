package com.magicbox.redio.network;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

import com.magicbox.redio.common.Constants;
import com.magicbox.redio.entities.EntityBase;
import com.magicbox.redio.network.packets.IPacketProtocol;
import com.magicbox.redio.network.packets.PacketEntityUpdateBase;

@ChannelHandler.Sharable
public class PacketHandler extends SimpleChannelInboundHandler<IPacketProtocol>
{
	@Override
	protected void channelRead0(ChannelHandlerContext context, IPacketProtocol packetProtocol) throws Exception
	{
		switch (packetProtocol.getPacketId())
		{
			case Constants.Packets.packetBusCable:
			case Constants.Packets.packetProcessor:
			{
				World world = Minecraft.getMinecraft().thePlayer.worldObj;
				PacketEntityUpdateBase update = (PacketEntityUpdateBase)packetProtocol;

				if (!update.isExists(world))
					return;

				TileEntity entity = update.getTarget(world);

				if (!(entity instanceof EntityBase))
					return;

				((EntityBase)entity).handleUpdatePacket(update);
				break;
			}
		}
	}
}
