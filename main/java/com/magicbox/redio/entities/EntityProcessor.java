package com.magicbox.redio.entities;

import java.util.Random;

public class EntityProcessor extends EntityBase
{
	private int facing = 0;
	private static final Random random = new Random();

	public EntityProcessor()
	{
		super();
	}

	@Override
	public int getFacing()
	{
		return facing;
	}

	@Override
	public void updateEntity()
	{
		super.updateEntity();
		// facing = random.nextInt(6);
		// getWorldObj().markBlockForUpdate(xCoord, yCoord, zCoord);
	}
}
