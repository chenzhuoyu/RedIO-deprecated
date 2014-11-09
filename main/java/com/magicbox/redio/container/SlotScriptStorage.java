package com.magicbox.redio.container;

import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

import com.magicbox.redio.items.ItemScriptCard;

public class SlotScriptStorage extends Slot
{
	public SlotScriptStorage(IInventory inventory, int index, int x, int y)
	{
		super(inventory, index, x, y);
	}

	@Override
	public boolean isItemValid(ItemStack itemStack)
	{
		if (itemStack.getItem() instanceof ItemScriptCard)
		{
			return true;
		}
		else
			return false;
	}
}
