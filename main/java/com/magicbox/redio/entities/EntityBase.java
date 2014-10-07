package com.magicbox.redio.entities;

import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;

import com.magicbox.redio.common.Utils;
import com.magicbox.redio.network.PacketEntityUpdate;
import com.magicbox.redio.network.PacketUpdate;

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

	@Override
	public Packet getDescriptionPacket()
	{
		return Utils.toPacket(new PacketEntityUpdate(this), 0);
	}

	@Override
	public void onDataPacket(NetworkManager net, S35PacketUpdateTileEntity packet)
	{
		System.out.println(packet);
	}

	@Override
	public boolean receiveClientEvent(int arg1, int arg2)
	{
		System.out.println(arg1 + " " + arg2);
		return false;
	}

	public void handleUpdatePacket(PacketUpdate packet)
	{
		;
	}
}
