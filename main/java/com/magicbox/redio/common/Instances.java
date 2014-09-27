package com.magicbox.redio.common;

import net.minecraft.block.material.Material;

import com.magicbox.redio.CreativeTab;
import com.magicbox.redio.blocks.BlockProcessor;
import com.magicbox.redio.entities.EntityProcessor;

public class Instances
{
	public static final CreativeTab creativeTab = new CreativeTab();

	public interface Blocks
	{
		public static final BlockProcessor blockProcessor = new BlockProcessor(Material.rock);
		// public static final BlockScriptStorage blockScriptStorage = new
		// BlockScriptStorage(Material.rock);
	}

	public interface Entities
	{
		public static final Class entityProcessor = EntityProcessor.class;
	}
}
