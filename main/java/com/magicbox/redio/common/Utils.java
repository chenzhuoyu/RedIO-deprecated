package com.magicbox.redio.common;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.MathHelper;

public class Utils
{
	public static int getPlayerFacing(EntityLivingBase player)
	{
		return MathHelper.floor_double(player.rotationYaw * 4.0F / 360.0F + 0.5d) & 0x03;
	}

	public static boolean isEntityPowered(TileEntity entity)
	{
		return entity.getWorldObj().isBlockIndirectlyGettingPowered(entity.xCoord, entity.yCoord, entity.zCoord);
	}
}
