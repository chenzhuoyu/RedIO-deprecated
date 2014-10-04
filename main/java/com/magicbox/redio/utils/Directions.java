package com.magicbox.redio.utils;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public enum Directions
{
	XN(0),

	XP(1),

	YN(2),

	YP(3),

	ZN(4),

	ZP(5);

	private int dir;
	public static final Directions[] directions = values();

	private Directions(int dir1)
	{
		dir = dir1;
	}

	public TileEntity applyToTileEntity(TileEntity te)
	{
		return applyTo(te.getWorldObj(), te.xCoord, te.yCoord, te.zCoord);
	}

	public TileEntity applyTo(World world, int x, int y, int z)
	{
		int[] coords = { x, y, z };

		coords[(dir / 2)] += getSign();

		if ((world != null) && (world.blockExists(coords[0], coords[1], coords[2])))
		{
			try
			{
				return world.getTileEntity(coords[0], coords[1], coords[2]);
			}
			catch (Exception e)
			{
				throw new RuntimeException("error getting TileEntity at dim " + world.provider.dimensionId + " "
						+ coords[0] + "/" + coords[1] + "/" + coords[2]);
			}
		}

		return null;
	}

	public Directions getInverse()
	{
		int inverseDir = dir - getSign();

		for (Directions direction : directions)
		{
			if (direction.dir == inverseDir) return direction;
		}

		return this;
	}

	public int toSideValue()
	{
		return (dir + 4) % 6;
	}

	private int getSign()
	{
		return dir % 2 * 2 - 1;
	}

	public ForgeDirection toForgeDirection()
	{
		return ForgeDirection.getOrientation(toSideValue());
	}
}