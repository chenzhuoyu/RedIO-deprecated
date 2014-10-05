package com.magicbox.redio.network;

import io.netty.buffer.ByteBuf;

public abstract class PacketBase
{
	protected boolean isChunkDataPacket = false;
	
	public abstract int getPacketId();
	public abstract void readData(ByteBuf buffer);
	public abstract void writeData(ByteBuf buffer);
}
