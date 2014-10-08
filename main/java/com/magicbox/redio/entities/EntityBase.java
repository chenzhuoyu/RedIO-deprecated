package com.magicbox.redio.entities;

import net.minecraft.network.Packet;
import net.minecraft.tileentity.TileEntity;

import com.magicbox.redio.common.Utils;
import com.magicbox.redio.network.packets.PacketEntityUpdate;

public abstract class EntityBase extends TileEntity
{
	// @formatter:off
	protected abstract void updateClientEntity();
	protected abstract void updateServerEntity();
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
