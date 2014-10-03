package com.magicbox.redio.entities;

public class EntityBusCable extends EntityBase
{
	public EntityBusCable(int facing)
	{
		super();
		setFacing(facing);
	}

	@Override
	public void updateEntity()
	{
		super.updateEntity();
		forceUpdate();
	}
}
