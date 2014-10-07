package com.magicbox.redio.entities;

import net.minecraft.network.Packet;
import net.minecraft.tileentity.TileEntity;

import com.magicbox.redio.common.Utils;
import com.magicbox.redio.network.PacketEntityUpdate;

public abstract class EntityBase extends TileEntity
{
	public int getFacing()
	{
		return worldObj.getBlockMetadata(xCoord, yCoord, zCoord);
	}

	public void setFacing(int facing)
	{
		worldObj.setBlockMetadataWithNotify(xCoord, yCoord, zCoord, facing, 3);
	}

	public int getTextureIndex(int side, int meta)
	{
		return -1;
	}

	@Override
	public Packet getDescriptionPacket()
	{
		return Utils.toPacket(new PacketEntityUpdate(this), 0);
	}

	public void handleUpdatePacket(PacketEntityUpdate packet)
	{
		// Empty stub
	}
}
