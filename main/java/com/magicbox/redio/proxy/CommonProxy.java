package com.magicbox.redio.proxy;

import java.lang.reflect.Field;

import net.minecraft.block.Block;

import com.magicbox.redio.common.Instances;

import cpw.mods.fml.common.registry.GameRegistry;

public class CommonProxy
{
	public void registerRenderers()
	{
		for (Field field : Instances.class.getFields())
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
}
