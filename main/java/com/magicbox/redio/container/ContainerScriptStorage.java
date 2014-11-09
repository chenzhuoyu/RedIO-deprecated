package com.magicbox.redio.container;

import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Slot;

import com.magicbox.redio.entities.EntityScriptStorage;

public class ContainerScriptStorage extends ContainerBase
{
	public ContainerScriptStorage(InventoryPlayer inventory, EntityScriptStorage entity)
	{
		super(inventory);
		this.entity = entity;

		Slot slot = new SlotScriptStorage(entity, 0, 13, 42);
		addSlotToContainer(slot);
	}
}
