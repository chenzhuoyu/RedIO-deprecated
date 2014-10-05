package com.magicbox.redio.entities;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.Packet;
import net.minecraft.tileentity.TileEntity;

import com.magicbox.redio.common.Utils;
import com.magicbox.redio.network.PacketEntityUpdate;
import com.magicbox.redio.network.PacketUpdate;

public abstract class EntityBase extends TileEntity
{
	@Override
	public void writeToNBT(NBTTagCompound nbt)
	{
		super.writeToNBT(nbt);
	}

	@Override
	public void readFromNBT(NBTTagCompound nbt)
	{
		super.readFromNBT(nbt);
	}

	@Override
	public Packet getDescriptionPacket()
	{
		return Utils.toPacket(new PacketEntityUpdate(this), 0);
	}

	public void handleUpdatePacket(PacketUpdate packet)
	{
		;
	}
}
