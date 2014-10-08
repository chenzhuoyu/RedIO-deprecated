package com.magicbox.redio.entities;

import net.minecraft.tileentity.TileEntity;

import com.magicbox.redio.common.Constants;
import com.magicbox.redio.network.Network;
import com.magicbox.redio.network.packets.PacketEntityBusCableUpdate;
import com.magicbox.redio.network.packets.PacketEntityUpdateBase;

public class EntityBusCable extends EntityBase
{
	private int connectivity = 0x00;

	public int getConnectivity()
	{
		return connectivity;
	}

	private void connectCable(Constants.BusCable.Direction side)
	{
		connectivity |= 1 << side.ordinal();
	}

	private void disconnectCable(Constants.BusCable.Direction side)
	{
		connectivity &= ~(1 << side.ordinal());
	}

	private boolean isEntityConnactable(TileEntity entity)
	{
		return entity instanceof EntityBusCable || entity instanceof EntityProcessor;
	}

	public boolean isCableConnected()
	{
		return connectivity != 0;
	}

	public boolean isCableConnectedAt(Constants.BusCable.Direction side)
	{
		int value = 1 << side.ordinal();
		return (connectivity & value) == value;
	}

	@Override
	public void updateClientEntity()
	{
		// Nothing to do
	}

	@Override
	public void updateServerEntity()
	{
		TileEntity xNeg = worldObj.getTileEntity(xCoord - 1, yCoord, zCoord);
		TileEntity xPos = worldObj.getTileEntity(xCoord + 1, yCoord, zCoord);
		TileEntity zNeg = worldObj.getTileEntity(xCoord, yCoord, zCoord - 1);
		TileEntity zPos = worldObj.getTileEntity(xCoord, yCoord, zCoord + 1);

		if (isEntityConnactable(xNeg))
			connectCable(Constants.BusCable.Direction.XNegative);
		else
			disconnectCable(Constants.BusCable.Direction.XNegative);

		if (isEntityConnactable(xPos))
			connectCable(Constants.BusCable.Direction.XPositive);
		else
			disconnectCable(Constants.BusCable.Direction.XPositive);

		if (isEntityConnactable(zNeg))
			connectCable(Constants.BusCable.Direction.ZNegative);
		else
			disconnectCable(Constants.BusCable.Direction.ZNegative);

		if (isEntityConnactable(zPos))
			connectCable(Constants.BusCable.Direction.ZPositive);
		else
			disconnectCable(Constants.BusCable.Direction.ZPositive);

		Network.broadcastToClients(new PacketEntityBusCableUpdate(this));
	}

	@Override
	public void handleUpdatePacket(PacketEntityUpdateBase packet)
	{
		PacketEntityBusCableUpdate updates = (PacketEntityBusCableUpdate)packet;

		connectivity = updates.getConnectivity();
		worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
	}
}
