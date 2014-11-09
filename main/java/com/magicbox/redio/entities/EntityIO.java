package com.magicbox.redio.entities;

import net.minecraft.nbt.NBTTagCompound;

import com.magicbox.redio.common.Utils;
import com.magicbox.redio.emulator.IPacketRouterNode;
import com.magicbox.redio.network.Network;
import com.magicbox.redio.network.packets.PacketEntityIOUpdate;
import com.magicbox.redio.network.packets.PacketEntityUpdateBase;
import com.magicbox.redio.script.objects.RedBoolObject;
import com.magicbox.redio.script.objects.RedNullObject;
import com.magicbox.redio.script.objects.RedObject;

public class EntityIO extends EntityBase implements IPacketRouterNode
{
	private String name = Utils.getRouterName("IO-");
	private boolean isActivated = false;
	private boolean isInterrupt = false;
	private boolean isOutputing = false;

	public String getName()
	{
		return name;
	}

	public boolean getActivated()
	{
		return isActivated;
	}

	public boolean getInterrupt()
	{
		return isInterrupt;
	}

	public boolean getOutputing()
	{
		return isOutputing;
	}

	public void setName(String name)
	{
		if (!this.name.equals(name))
		{
			unregister();
			this.name = name;
			Utils.registerRouter(name, this);
		}
	}

	public void setActivated(boolean isActivated)
	{
		this.isActivated = isActivated;
	}

	public void setInterrupt(boolean isInterrupt)
	{
		this.isInterrupt = isInterrupt;
	}

	public void setOutputing(boolean isOutputing)
	{
		this.isOutputing = isOutputing;
	}

	public void unregister()
	{
		Utils.unregisterRouter(name, this);
	}

	@Override
	public void writeToNBT(NBTTagCompound nbt)
	{
		super.writeToNBT(nbt);
		nbt.setString("name", name);
		nbt.setBoolean("isActivated", isActivated);
		nbt.setBoolean("isInterrupt", isInterrupt);
		nbt.setBoolean("isOutputing", isOutputing);
	}

	@Override
	public void readFromNBT(NBTTagCompound nbt)
	{
		super.readFromNBT(nbt);
		setName(nbt.getString("name"));
		setActivated(nbt.getBoolean("isActivated"));
		setInterrupt(nbt.getBoolean("isInterrupt"));
		setOutputing(nbt.getBoolean("isOutputing"));
	}

	@Override
	public void updateClientEntity()
	{
		// Nothing to update
	}

	@Override
	public void updateServerEntity()
	{
		Network.broadcastToClients(new PacketEntityIOUpdate(this));
	}

	@Override
	public void handleUpdatePacket(PacketEntityUpdateBase packet)
	{
		PacketEntityIOUpdate updates = (PacketEntityIOUpdate)packet;

		setName(updates.getName());
		setActivated(updates.getActivated());
		setInterrupt(updates.getInterrupt());
		setOutputing(updates.getOutputing());
		worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
	}

	@Override
	public String getNodeName()
	{
		return name;
	}

	@Override
	public RedObject dispatchPacket(IPacketRouterNode source, IPacketRouterNode previous, String destination, RedObject packet)
	{
		if (!destination.equals(name))
			return RedNullObject.nullObject;

		boolean currentState = Utils.isEntityPowered(this);

		if (packet != null && !packet.isNull())
		{
			setOutputing(packet.isTrue());
			worldObj.notifyBlockChange(xCoord, yCoord, zCoord, getBlockType());
		}

		return RedBoolObject.fromBoolean(currentState);
	}
}
