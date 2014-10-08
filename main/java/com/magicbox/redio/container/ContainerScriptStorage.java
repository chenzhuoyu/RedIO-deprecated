package com.magicbox.redio.container;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;

import com.magicbox.redio.entities.EntityScriptStorage;

public class ContainerScriptStorage extends Container
{
	public ContainerScriptStorage(InventoryPlayer inventory, EntityScriptStorage entity)
	{

	}

	@Override
	public boolean canInteractWith(EntityPlayer var1)
	{
		return true;
	}

}
