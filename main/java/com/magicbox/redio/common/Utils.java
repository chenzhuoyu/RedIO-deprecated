package com.magicbox.redio.common;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.network.Packet;
import net.minecraft.util.MathHelper;

import com.magicbox.redio.network.PacketBase;

import cpw.mods.fml.common.network.internal.FMLProxyPacket;

public class Utils
{
	public static Packet toPacket(PacketBase packet, int id)
	{
		ByteBuf buffer = Unpooled.buffer();

		buffer.writeInt(id);
		packet.writeData(buffer);
		return new FMLProxyPacket(buffer, Constants.CHANNEL_NAME);
	}

	public static int getPlayerFacing(EntityLivingBase player)
	{
		return MathHelper.floor_double(player.rotationYaw * 4.0F / 360.0F + 0.5d) & 0x03;
	}
}
