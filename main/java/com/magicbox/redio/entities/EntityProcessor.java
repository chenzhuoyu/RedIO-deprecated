package com.magicbox.redio.entities;

public class EntityProcessor extends EntityBase
{
	@Override
	public void updateEntity()
	{
		super.updateEntity();
		forceUpdate();
	}

	@Override
	public int getTextureIndex(int side)
	{
		return getFacing();
	}
}
