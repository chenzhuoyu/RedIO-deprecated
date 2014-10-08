package com.magicbox.redio.common;

import net.minecraft.block.material.Material;

import com.magicbox.redio.CreativeTab;
import com.magicbox.redio.blocks.BlockBusCable;
import com.magicbox.redio.blocks.BlockProcessor;
import com.magicbox.redio.entities.EntityBusCable;
import com.magicbox.redio.entities.EntityProcessor;
import com.magicbox.redio.renderers.RendererBusCable;
import com.magicbox.redio.renderers.RendererGlobal;

public class Instances
{
	public static final CreativeTab creativeTab = new CreativeTab();

	public interface Blocks
	{
		public static final BlockBusCable blockBusCable = new BlockBusCable(Material.cloth);
		public static final BlockProcessor blockProcessor = new BlockProcessor(Material.rock);
	}

	public interface Entities
	{
		public static final Class entityBusCable = EntityBusCable.class;
		public static final Class entityProcessor = EntityProcessor.class;
	}

	public interface Renderers
	{
		public static final RendererGlobal rendererGlobal = new RendererGlobal();
		public static final RendererBusCable rendererBusCable = new RendererBusCable();
	}
}
