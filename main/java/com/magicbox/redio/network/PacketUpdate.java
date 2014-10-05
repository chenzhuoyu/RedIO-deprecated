package com.magicbox.redio.network;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

public class PacketUpdate extends PacketBase
{
	protected int id = 0;
	protected int xCoord = 0;
	protected int yCoord = 0;
	protected int zCoord = 0;
	protected ByteBuf payload = Unpooled.buffer();

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

		int length = buffer.readInt();

		payload = Unpooled.buffer(length);
		payload.writeBytes(buffer);
	}

	@Override
	public void writeData(ByteBuf buffer)
	{
		buffer.writeInt(id);
		buffer.writeInt(xCoord);
		buffer.writeInt(yCoord);
		buffer.writeInt(zCoord);
		buffer.writeInt(payload.readableBytes());
		buffer.writeBytes(payload);
	}
}
