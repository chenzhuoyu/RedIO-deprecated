package com.magicbox.redio.network.packets;

import io.netty.buffer.ByteBuf;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

import com.magicbox.redio.entities.EntityBase;

public abstract class PacketEntityUpdateBase implements IPacketProtocol
{
	protected int id = 0;
	protected int xCoord = 0;
	protected int yCoord = 0;
	protected int zCoord = 0;

	public PacketEntityUpdateBase(int id)
	{
		this.id = id;
	}

	public PacketEntityUpdateBase(int id, EntityBase entity)
	{
		this(id);
		xCoord = entity.xCoord;
		yCoord = entity.yCoord;
		zCoord = entity.zCoord;
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

	public boolean isExists(World world)
	{
		return !world.isAirBlock(xCoord, yCoord, zCoord);
	}

	public TileEntity getTarget(World world)
	{
		return world.getTileEntity(xCoord, yCoord, zCoord);
	}
}
