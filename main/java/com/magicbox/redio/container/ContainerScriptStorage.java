package com.magicbox.redio.container;

import net.minecraft.entity.player.InventoryPlayer;

import com.magicbox.redio.entities.EntityScriptStorage;

public class ContainerScriptStorage extends ContainerBase
{
	public ContainerScriptStorage(InventoryPlayer inventory, EntityScriptStorage entity)
	{
		super(inventory);
		this.entity = entity;
	}
}
