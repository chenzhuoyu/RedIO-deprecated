package com.magicbox.redio.network.packets;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

import com.magicbox.redio.entities.EntityBase;

public class PacketEntityUpdate extends PacketUpdate
{
	public PacketEntityUpdate()
	{
		super(0);
	}

	public PacketEntityUpdate(EntityBase entity)
	{
		super(0);
		xCoord = entity.xCoord;
		yCoord = entity.yCoord;
		zCoord = entity.zCoord;
	}

	public boolean isExists(World world)
	{
		return !world.isAirBlock(xCoord, yCoord, zCoord);
	}

	public TileEntity getTarget(World world)
	{
		return world.getTileEntity(xCoord, yCoord, zCoord);
	}
}
