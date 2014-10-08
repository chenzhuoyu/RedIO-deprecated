package com.magicbox.redio.network.packets;

import com.magicbox.redio.common.Constants;
import com.magicbox.redio.entities.EntityBase;

public class PacketEntityScriptStorageUpdate extends PacketEntityUpdateBase
{
	public PacketEntityScriptStorageUpdate()
	{
		super(Constants.Packets.packetScriptStorage);
	}

	public PacketEntityScriptStorageUpdate(EntityBase entity)
	{
		super(Constants.Packets.packetScriptStorage, entity);
	}
}
