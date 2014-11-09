package com.magicbox.redio.entities;

import java.util.ArrayList;

import net.minecraft.tileentity.TileEntity;

import com.magicbox.redio.common.Constants;
import com.magicbox.redio.emulator.IPacketRouterNode;
import com.magicbox.redio.network.Network;
import com.magicbox.redio.network.packets.PacketEntityBusCableUpdate;
import com.magicbox.redio.network.packets.PacketEntityUpdateBase;
import com.magicbox.redio.script.objects.RedNullObject;
import com.magicbox.redio.script.objects.RedObject;

public class EntityBusCable extends EntityBase implements IPacketRouterNode
{
	private int connectivity = 0x00;
	private boolean isDispatching = false;

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
		// Nothing to update
	}

	@Override
	public void updateServerEntity()
	{
		TileEntity xNeg = worldObj.getTileEntity(xCoord - 1, yCoord, zCoord);
		TileEntity xPos = worldObj.getTileEntity(xCoord + 1, yCoord, zCoord);
		TileEntity zNeg = worldObj.getTileEntity(xCoord, yCoord, zCoord - 1);
		TileEntity zPos = worldObj.getTileEntity(xCoord, yCoord, zCoord + 1);

		if (xNeg instanceof IPacketRouterNode)
			connectCable(Constants.BusCable.Direction.XNegative);
		else
			disconnectCable(Constants.BusCable.Direction.XNegative);

		if (xPos instanceof IPacketRouterNode)
			connectCable(Constants.BusCable.Direction.XPositive);
		else
			disconnectCable(Constants.BusCable.Direction.XPositive);

		if (zNeg instanceof IPacketRouterNode)
			connectCable(Constants.BusCable.Direction.ZNegative);
		else
			disconnectCable(Constants.BusCable.Direction.ZNegative);

		if (zPos instanceof IPacketRouterNode)
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

	@Override
	public String getNodeName()
	{
		return null;
	}

	@Override
	public RedObject dispatchPacket(IPacketRouterNode source, IPacketRouterNode previous, String destination, RedObject packet)
	{
		if (isDispatching)
			return RedNullObject.nullObject;

		TileEntity xNeg = worldObj.getTileEntity(xCoord - 1, yCoord, zCoord);
		TileEntity xPos = worldObj.getTileEntity(xCoord + 1, yCoord, zCoord);
		TileEntity zNeg = worldObj.getTileEntity(xCoord, yCoord, zCoord - 1);
		TileEntity zPos = worldObj.getTileEntity(xCoord, yCoord, zCoord + 1);
		ArrayList<IPacketRouterNode> nodes = new ArrayList<IPacketRouterNode>();

		if (xNeg != previous && xNeg instanceof IPacketRouterNode)
			nodes.add((IPacketRouterNode)xNeg);

		if (xPos != previous && xPos instanceof IPacketRouterNode)
			nodes.add((IPacketRouterNode)xPos);

		if (zNeg != previous && zNeg instanceof IPacketRouterNode)
			nodes.add((IPacketRouterNode)zNeg);

		if (zPos != previous && zPos instanceof IPacketRouterNode)
			nodes.add((IPacketRouterNode)zPos);

		isDispatching = true;

		for (IPacketRouterNode node : nodes)
		{
			RedObject result = node.dispatchPacket(source, this, destination, packet);

			if (result != null && !result.isNull())
			{
				isDispatching = false;
				return result;
			}
		}

		isDispatching = false;
		return RedNullObject.nullObject;
	}
}
