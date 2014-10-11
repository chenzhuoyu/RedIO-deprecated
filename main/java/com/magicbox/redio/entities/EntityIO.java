package com.magicbox.redio.entities;

import com.magicbox.redio.common.Utils;
import com.magicbox.redio.emulator.IPacketRouterNode;
import com.magicbox.redio.network.packets.PacketEntityUpdateBase;
import com.magicbox.redio.script.objects.RedNullObject;
import com.magicbox.redio.script.objects.RedObject;

public class EntityIO extends EntityBase implements IPacketRouterNode
{
	private String name = "";

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		unregister();
		this.name = name;
		Utils.registerRouter(name, this);
	}

	public void unregister()
	{
		Utils.unregisterRouter(name, this);
	}

	@Override
	public void updateClientEntity()
	{
		;
	}

	@Override
	public void updateServerEntity()
	{
		;
	}

	@Override
	public void handleUpdatePacket(PacketEntityUpdateBase packet)
	{
		;
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

		return RedNullObject.nullObject;
	}
}
