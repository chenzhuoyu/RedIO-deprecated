package com.magicbox.redio.container;

import net.minecraft.entity.player.InventoryPlayer;

import com.magicbox.redio.entities.EntityProcessor;

public class ContainerProcessor extends ContainerBase
{

	public ContainerProcessor(InventoryPlayer inventory, EntityProcessor te)
	{
		super(inventory);
		entity = te;
	}

}
