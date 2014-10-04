package com.magicbox.redio.entities;

import net.minecraft.block.Block;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public abstract class EntityBase extends TileEntity
{
	private int facing = 0;
	private IIcon [] lastRenderIcons;

	@SideOnly(Side.CLIENT)
	private int tesrMask;

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

	@SideOnly(Side.CLIENT)
	public void onRender()
	{
		Block block = getBlockType();

		if (lastRenderIcons == null)
			lastRenderIcons = new IIcon [6];

		for (int side = 0; side < 6; side++)
			lastRenderIcons[side] = block.getIcon(worldObj, xCoord, yCoord, zCoord, side);

		tesrMask = 0;
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
