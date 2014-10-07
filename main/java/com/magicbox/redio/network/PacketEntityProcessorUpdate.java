package com.magicbox.redio.network;

import io.netty.buffer.ByteBuf;

import com.magicbox.redio.entities.EntityProcessor;

public class PacketEntityProcessorUpdate extends PacketEntityUpdate
{
	private boolean isPowered = false;

	public PacketEntityProcessorUpdate()
	{
		super();
	}

	public PacketEntityProcessorUpdate(EntityProcessor entity)
	{
		super(entity);
		isPowered = entity.getPowered();
	}

	public boolean getPowered()
	{
		return isPowered;
	}

	@Override
	public void readData(ByteBuf buffer)
	{
		super.readData(buffer);
		isPowered = buffer.readBoolean();
	}

	@Override
	public void writeData(ByteBuf buffer)
	{
		super.writeData(buffer);
		buffer.writeBoolean(isPowered);
	}
}
