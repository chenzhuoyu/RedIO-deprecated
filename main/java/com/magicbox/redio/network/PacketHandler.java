package com.magicbox.redio.network;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

import com.magicbox.redio.entities.EntityBase;

@ChannelHandler.Sharable
public class PacketHandler extends SimpleChannelInboundHandler<PacketBase>
{
	@Override
	protected void channelRead0(ChannelHandlerContext context, PacketBase packet) throws Exception
	{
		switch (packet.getPacketId())
		{
			case 0:
			{
				World world = Minecraft.getMinecraft().thePlayer.worldObj;
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
