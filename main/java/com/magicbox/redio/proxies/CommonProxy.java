package com.magicbox.redio.proxies;

import java.lang.reflect.Field;

import net.minecraft.block.Block;

import com.magicbox.redio.common.Constants;
import com.magicbox.redio.common.Instances;
import com.magicbox.redio.common.Utils;

import cpw.mods.fml.common.registry.GameRegistry;

public class CommonProxy
{
	public void registerBlocks()
	{
		for (Field field : Instances.Blocks.class.getFields())
		{
			try
			{
				Block block = (Block) field.get(field.getClass());
				GameRegistry.registerBlock(block, block.getUnlocalizedName());
			}
			catch (IllegalAccessException | IllegalArgumentException e)
			{
				e.printStackTrace();
			}
		}
	}

	public void registerEntities()
	{
		for (Field field : Instances.Entities.class.getFields())
		{
			try
			{
				Class entity = (Class) field.get(field.getClass());
				GameRegistry.registerTileEntity(entity, entity.getName());
			}
			catch (IllegalAccessException | IllegalArgumentException e)
			{
				e.printStackTrace();
			}
		}
	}

	public void registerRenderers()
	{
		// Nothing to register
	}

	public void registerGuiHandler()
	{
		;
	}

	public void registerCraftingRecipes()
	{
		for (Field field : Constants.Recipes.class.getFields())
		{
			try
			{
				Object[] recipe = (Object[]) field.get(field.getClass());
				Utils.addCraftingRecipe((Block) recipe[0], (Integer) recipe[1], (Object[]) recipe[2]);
			}
			catch (IllegalAccessException | IllegalArgumentException e)
			{
				e.printStackTrace();
			}
		}
	}
}
