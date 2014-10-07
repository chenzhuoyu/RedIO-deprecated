package com.magicbox.redio.network;

import io.netty.buffer.ByteBuf;

public class PacketUpdate extends PacketBase
{
	protected int id = 0;
	protected int xCoord = 0;
	protected int yCoord = 0;
	protected int zCoord = 0;

	public PacketUpdate(int id)
	{
		this.id = id;
		isChunkDataPacket = true;
	}

	@Override
	public int getPacketId()
	{
		return id;
	}

	@Override
	public void readData(ByteBuf buffer)
	{
		id = buffer.readInt();
		xCoord = buffer.readInt();
		yCoord = buffer.readInt();
		zCoord = buffer.readInt();
	}

	@Override
	public void writeData(ByteBuf buffer)
	{
		buffer.writeInt(id);
		buffer.writeInt(xCoord);
		buffer.writeInt(yCoord);
		buffer.writeInt(zCoord);
	}
}
