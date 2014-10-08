package com.magicbox.redio.network.packets;

import io.netty.buffer.ByteBuf;

/* @formatter:off */
public interface IPacketProtocol
{
	public int getPacketId();
	public void readData(ByteBuf buffer);
	public void writeData(ByteBuf buffer);
}
