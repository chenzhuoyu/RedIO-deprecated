package com.magicbox.redio.network;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.INetHandler;
import net.minecraft.network.NetHandlerPlayServer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

import com.magicbox.redio.entities.EntityBase;

import cpw.mods.fml.common.network.NetworkRegistry;

@ChannelHandler.Sharable
public class PacketHandler extends SimpleChannelInboundHandler<PacketBase>
{
	private EntityPlayer getPlayer(ChannelHandlerContext context)
	{
		INetHandler handler = context.channel().attr(NetworkRegistry.NET_HANDLER).get();
		return handler instanceof NetHandlerPlayServer ? ((NetHandlerPlayServer)handler).playerEntity : null;
	}

	@Override
	protected void channelRead0(ChannelHandlerContext context, PacketBase packet) throws Exception
	{
		EntityPlayer player = getPlayer(context);

		switch (packet.getPacketId())
		{
			case 0:
			{
				World world = player.worldObj;
				PacketEntityUpdate update = (PacketEntityUpdate)packet;

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
