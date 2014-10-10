package com.magicbox.redio.network.packets;

import io.netty.buffer.ByteBuf;

import com.magicbox.redio.common.Constants;
import com.magicbox.redio.entities.EntityProcessor;

public class PacketEntityProcessorUpdate extends PacketEntityUpdateBase
{
	private String name = "";
	private double heatValue = 0.0d;
	private boolean isDamaged = false;
	private boolean isPowered = false;

	public PacketEntityProcessorUpdate()
	{
		super(Constants.Packets.packetProcessor);
	}

	public PacketEntityProcessorUpdate(EntityProcessor entity)
	{
		super(Constants.Packets.packetProcessor, entity);
		isDamaged = entity.getDamaged();
		isPowered = entity.getPowered();
		heatValue = entity.getHeatValue();
	}

	public String getName()
	{
		return name;
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

		int length = buffer.readInt();
		byte [] data = new byte [length];

		buffer.readBytes(data);

		name = new String(data);
		heatValue = buffer.readDouble();
		isDamaged = buffer.readBoolean();
		isPowered = buffer.readBoolean();
	}

	@Override
	public void writeData(ByteBuf buffer)
	{
		byte [] data = name.getBytes();

		super.writeData(buffer);
		buffer.writeInt(data.length);
		buffer.writeBytes(data);
		buffer.writeDouble(heatValue);
		buffer.writeBoolean(isDamaged);
		buffer.writeBoolean(isPowered);
	}
}
