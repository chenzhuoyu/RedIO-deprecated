package com.magicbox.redio.entities;

import java.util.ArrayList;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

import com.magicbox.redio.common.Constants;
import com.magicbox.redio.items.ItemScriptCard;
import com.magicbox.redio.network.packets.PacketEntityUpdateBase;
import com.magicbox.redio.utils.InventoryUtils;

public class EntityScriptStorage extends EntityBase implements IInventory
{
	public ItemStack inventory;
	public boolean inventoryChanged;

	public ArrayList<Script> scripts = new ArrayList(Constants.ScriptStorage.MAX_STORAGE);

	public void addScript(String name, String code)
	{
		scripts.add(new Script(name, code));
		System.out.print(name + "|" + code);
	}

	public void overwriteScript(int index, String name, String code)
	{
		scripts.set(index, new Script(name, code));
	}

	public void insertScript(int index, String name, String code)
	{
		scripts.add(index, new Script(name, code));
	}

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
		if (var2.getItem() instanceof ItemScriptCard)
			return true;
		else
			return false;
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
