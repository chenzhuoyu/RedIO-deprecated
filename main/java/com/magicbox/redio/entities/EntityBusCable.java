package com.magicbox.redio.entities;

import java.util.Random;

public class EntityBusCable extends EntityBase
{
	private static final Random random = new Random();

	public EntityBusCable()
	{
		super();
	}

	@Override
	public void updateEntity()
	{
		super.updateEntity();
		forceUpdate();
	}
}
