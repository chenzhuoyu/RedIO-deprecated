package com.magicbox.redio.container;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;

import com.magicbox.redio.entities.EntityBase;

public abstract class ContainerBase extends Container
{
	public EntityBase entity;
	public IInventory inv;

	protected ContainerBase(InventoryPlayer inventory)
	{
		for (int i = 0; i < 9; i++)
		{
			addSlotToContainer(new Slot(inventory, i, 8 + i * 18, 142));
		}

		for (int i = 0; i < 3; i++)
		{
			for (int j = 0; j < 9; j++)
			{
				addSlotToContainer(new Slot(inventory, i * 9 + j + 9, 8 + j * 18, 84 + i * 18));
			}
		}
	}

	@Override
	protected void retrySlotClick(int par1, int par2, boolean par3, EntityPlayer par4EntityPlayer)
	{
	}

	@Override
	public boolean canInteractWith(EntityPlayer var1)
	{
		return true;
	}

}
