package com.magicbox.redio.network.packets;

import io.netty.buffer.ByteBuf;

import com.magicbox.redio.common.Constants;
import com.magicbox.redio.entities.EntityBusCable;

public class PacketEntityBusCableUpdate extends PacketEntityUpdateBase
{
	private int connectivity = 0x00;

	public PacketEntityBusCableUpdate()
	{
		super(Constants.Packets.packetBusCable);
	}

	public PacketEntityBusCableUpdate(EntityBusCable entity)
	{
		super(Constants.Packets.packetBusCable, entity);
		connectivity = entity.getConnectivity();
	}

	public int getConnectivity()
	{
		return connectivity;
	}

	@Override
	public void readData(ByteBuf buffer)
	{
		super.readData(buffer);
		connectivity = buffer.readInt();
	}

	@Override
	public void writeData(ByteBuf buffer)
	{
		super.writeData(buffer);
		buffer.writeInt(connectivity);
	}
}
