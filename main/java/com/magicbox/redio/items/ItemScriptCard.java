package com.magicbox.redio.items;

import net.minecraft.item.Item;

import com.magicbox.redio.common.Constants;
import com.magicbox.redio.common.Instances;
import com.magicbox.redio.entities.Script;

public class ItemScriptCard extends Item
{
	private Script script;

	public ItemScriptCard()
	{
		setMaxStackSize(8);
		setCreativeTab(Instances.creativeTab);
		setUnlocalizedName(Constants.ScriptCard.ITEM_NAME);
	}
}
