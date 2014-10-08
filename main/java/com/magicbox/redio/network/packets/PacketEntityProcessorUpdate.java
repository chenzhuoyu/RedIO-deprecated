package com.magicbox.redio.network.packets;

import io.netty.buffer.ByteBuf;

import com.magicbox.redio.entities.EntityProcessor;

public class PacketEntityProcessorUpdate extends PacketEntityUpdate
{
	private double heatValue = 0.0d;
	private boolean isDamaged = false;
	private boolean isPowered = false;

	public PacketEntityProcessorUpdate()
	{
		super(0);
	}

	public PacketEntityProcessorUpdate(EntityProcessor entity)
	{
		super(0, entity);
		isDamaged = entity.getDamaged();
		isPowered = entity.getPowered();
		heatValue = entity.getHeatValue();
	}

	public double getHeatValue()
	{
		return heatValue;
	}

	public boolean getDamaged()
	{
		return isDamaged;
	}

	public boolean getPowered()
	{
		return isPowered;
	}

	@Override
	public void readData(ByteBuf buffer)
	{
		super.readData(buffer);
		heatValue = buffer.readDouble();
		isDamaged = buffer.readBoolean();
		isPowered = buffer.readBoolean();
	}

	@Override
	public void writeData(ByteBuf buffer)
	{
		super.writeData(buffer);
		buffer.writeDouble(heatValue);
		buffer.writeBoolean(isDamaged);
		buffer.writeBoolean(isPowered);
	}
}
