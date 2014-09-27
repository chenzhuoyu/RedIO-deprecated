package com.magicbox.redio.entities;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;

public abstract class EntityBase extends TileEntity
{
	private int facing = 0;

	public int getFacing()
	{
		return facing;
	}

	public void setFacing(int facing)
	{
		this.facing = facing;
	}

	public void forceUpdate()
	{
		getWorldObj().markBlockForUpdate(xCoord, yCoord, zCoord);
	}

	public int getTextureIndex(int side)
	{
		return side;
	}

	@Override
	public void writeToNBT(NBTTagCompound nbt)
	{
		super.writeToNBT(nbt);
		nbt.setInteger("facing", facing);
	}

	@Override
	public void readFromNBT(NBTTagCompound nbt)
	{
		super.readFromNBT(nbt);
		facing = nbt.getInteger("facing");
	}
}
