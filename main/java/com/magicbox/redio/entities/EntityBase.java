package com.magicbox.redio.entities;

import net.minecraft.tileentity.TileEntity;

import com.magicbox.redio.network.packets.PacketEntityUpdate;

public abstract class EntityBase extends TileEntity
{
	// @formatter:off
	public abstract void updateClientEntity();
	public abstract void updateServerEntity();
	public abstract void handleUpdatePacket(PacketEntityUpdate packet);
	// @formatter:on

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
	public void updateEntity()
	{
		super.updateEntity();

		if (worldObj.isRemote)
			updateClientEntity();
		else
			updateServerEntity();
	}
}
