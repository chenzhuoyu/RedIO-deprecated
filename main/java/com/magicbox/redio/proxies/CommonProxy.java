package com.magicbox.redio.proxies;

import java.lang.reflect.Field;

import net.minecraft.block.Block;

import com.magicbox.redio.common.Instances;

import cpw.mods.fml.common.registry.GameRegistry;

public class CommonProxy
{
	public void registerBlocks()
	{
		for (Field field : Instances.Blocks.class.getFields())
		{
			try
			{
				Block block = (Block)field.get(field.getClass());
				GameRegistry.registerBlock(block, block.getUnlocalizedName());
			} catch (IllegalAccessException e)
			{
				e.printStackTrace();
			} catch (IllegalArgumentException e)
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
				Class entity = (Class)field.get(field.getClass());
				GameRegistry.registerTileEntity(entity, entity.getName());
			} catch (IllegalAccessException e)
			{
				e.printStackTrace();
			} catch (IllegalArgumentException e)
			{
				e.printStackTrace();
			}
		}
	}
}
