package com.magicbox.redio;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;

import com.magicbox.redio.common.Instances;

public class CreativeTab extends CreativeTabs
{
	public CreativeTab()
	{
		super("RedIO");
	}

	@Override
	public Item getTabIconItem()
	{
		return Item.getItemFromBlock(Instances.Blocks.scriptStorage);
	}
}
