package com.magicbox.redio.entities;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

import com.magicbox.redio.network.packets.PacketEntityUpdateBase;
import com.magicbox.redio.utils.InventoryUtils;

public class EntityScriptStorage extends EntityBase implements IInventory
{
	public ItemStack inventory;
	public boolean inventoryChanged;

	@Override
	public void updateClientEntity()
	{
		;
	}

	@Override
	public void updateServerEntity()
	{
		;
	}

	@Override
	public void handleUpdatePacket(PacketEntityUpdateBase packet)
	{
		;
	}

	@Override
	public int getSizeInventory()
	{
		return 1;
	}

	@Override
	public ItemStack getStackInSlot(int var1)
	{
		return inventory;
	}

	@Override
	public ItemStack decrStackSize(int var1, int var2)
	{
		if (var1 == 0)
		{
			if (inventory != null)
			{
				ItemStack stack;
				if (inventory.stackSize <= var2)
				{
					stack = inventory;
					inventory = null;
					return stack;
				}
				else
				{
					stack = inventory.splitStack(var2);

					if (inventory.stackSize == 0)
					{
						inventory = null;
					}
					return stack;
				}
			}
			else
			{
				return null;
			}
		}
		else
		{
			return null;
		}
	}

	@Override
	public ItemStack getStackInSlotOnClosing(int var1)
	{
		return null;
	}

	@Override
	public void setInventorySlotContents(int var1, ItemStack var2)
	{
		inventory = var2;
	}

	@Override
	public String getInventoryName()
	{
		return "scriptStorageInventory";
	}

	@Override
	public boolean hasCustomInventoryName()
	{
		return false;
	}

	@Override
	public int getInventoryStackLimit()
	{
		// TODO Auto-generated method stub
		return 1;
	}

	@Override
	public void markDirty()
	{
		inventoryChanged = true;
	}

	@Override
	public boolean isUseableByPlayer(EntityPlayer var1)
	{
		return true;
	}

	@Override
	public void openInventory()
	{
	}

	@Override
	public void closeInventory()
	{
	}

	@Override
	public boolean isItemValidForSlot(int var1, ItemStack var2)
	{
		return true;
	}

	@Override
	public void writeToNBT(NBTTagCompound nbt)
	{
		super.writeToNBT(nbt);
		InventoryUtils.writeStackToNBT(nbt, "storage", inventory);
	}

	@Override
	public void readFromNBT(NBTTagCompound nbt)
	{
		super.readFromNBT(nbt);
		inventory = InventoryUtils.readStackFromNBT(nbt, "storage");
	}
}
