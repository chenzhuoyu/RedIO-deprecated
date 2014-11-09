package com.magicbox.redio.network.packets;

import io.netty.buffer.ByteBuf;

import com.magicbox.redio.common.Constants;
import com.magicbox.redio.entities.EntityIO;

public class PacketEntityIOUpdate extends PacketEntityUpdateBase
{
	private String name = "";
	private boolean isActivated = false;
	private boolean isInterrupt = false;
	private boolean isOutputing = false;

	public PacketEntityIOUpdate()
	{
		super(Constants.Packets.packetIO);
	}

	public PacketEntityIOUpdate(EntityIO entity)
	{
		super(Constants.Packets.packetIO, entity);
		name = entity.getName();
		isActivated = entity.getActivated();
		isInterrupt = entity.getInterrupt();
		isOutputing = entity.getOutputing();
	}

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

	@Override
	public void readData(ByteBuf buffer)
	{
		super.readData(buffer);

		int length = buffer.readInt();
		byte [] data = new byte [length];

		buffer.readBytes(data);

		name = new String(data);
		isActivated = buffer.readBoolean();
		isInterrupt = buffer.readBoolean();
		isOutputing = buffer.readBoolean();
	}

	@Override
	public void writeData(ByteBuf buffer)
	{
		byte [] data = name.getBytes();

		super.writeData(buffer);
		buffer.writeInt(data.length);
		buffer.writeBytes(data);
		buffer.writeBoolean(isActivated);
		buffer.writeBoolean(isInterrupt);
		buffer.writeBoolean(isOutputing);
	}
}
