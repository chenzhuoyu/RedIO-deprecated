package com.magicbox.redio.utils;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;

public class InventoryUtils
{
	public static void writeStacksToNBT(NBTTagCompound nbt, String name, ItemStack[] stacks)
	{
		NBTTagList nbttaglist = new NBTTagList();

		for (int i = 0; i < stacks.length; i++)
		{
			NBTTagCompound cpt = new NBTTagCompound();
			nbttaglist.appendTag(cpt);
			if (stacks[i] != null)
			{
				stacks[i].writeToNBT(cpt);
			}

		}
		nbt.setTag(name, nbttaglist);
	}

	public static void writeStackToNBT(NBTTagCompound nbt, String name, ItemStack stack)
	{
		NBTTagList nbttaglist = new NBTTagList();
		NBTTagCompound cpt = new NBTTagCompound();
		nbttaglist.appendTag(cpt);
		if (stack != null)
			stack.writeToNBT(cpt);
		nbt.setTag(name, nbttaglist);
		System.out.print("\nwrite to nbt\n");
	}

	public static ItemStack readStackFromNBT(NBTTagCompound nbt, String name)
	{
		NBTTagList nbttaglist = nbt.getTagList(name, InventoryUtils.NBTTag_Types.NBTTagCompound.ordinal());
		NBTTagCompound nbttagcompound2 = nbttaglist.getCompoundTagAt(0);
		ItemStack stack = ItemStack.loadItemStackFromNBT(nbttagcompound2);
		System.out.print("\nread from nbt\n");
		return stack;
	}

	public static enum NBTTag_Types
	{
		NBTTagEnd, NBTTagByte, NBTTagShort, NBTTagInt, NBTTagLong, NBTTagFloat, NBTTagDouble, NBTTagByteArray, NBTTagString, NBTTagList, NBTTagCompound, NBTTagIntArray;
	}
}
