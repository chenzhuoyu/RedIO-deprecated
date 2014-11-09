package com.magicbox.redio.common;

import net.minecraft.block.material.Material;

import com.magicbox.redio.CreativeTab;
import com.magicbox.redio.blocks.BlockBusCable;
import com.magicbox.redio.blocks.BlockIO;
import com.magicbox.redio.blocks.BlockProcessor;
import com.magicbox.redio.blocks.BlockScriptStorage;
import com.magicbox.redio.entities.EntityBusCable;
import com.magicbox.redio.entities.EntityIO;
import com.magicbox.redio.entities.EntityProcessor;
import com.magicbox.redio.entities.EntityScriptStorage;
import com.magicbox.redio.items.ItemScriptCard;
import com.magicbox.redio.renderers.RendererBusCable;
import com.magicbox.redio.renderers.RendererGlobal;
import com.magicbox.redio.renderers.RendererProcessor;

public class Instances
{
	public static final CreativeTab creativeTab = new CreativeTab();

	public interface Items
	{
		public static final ItemScriptCard itemScriptCard = new ItemScriptCard();
	}

	public interface Blocks
	{
		public static final BlockIO blockIO = new BlockIO(Material.rock);
		public static final BlockBusCable blockBusCable = new BlockBusCable(Material.cloth);
		public static final BlockProcessor blockProcessor = new BlockProcessor(Material.rock);
		public static final BlockScriptStorage blockScriptStorage = new BlockScriptStorage(Material.rock);
	}

	public interface Entities
	{
		public static final Class entityIO = EntityIO.class;
		public static final Class entityBusCable = EntityBusCable.class;
		public static final Class entityProcessor = EntityProcessor.class;
		public static final Class entityScriptStorage = EntityScriptStorage.class;
	}

	public interface Renderers
	{
		public static final RendererGlobal rendererGlobal = new RendererGlobal();
		public static final RendererBusCable rendererBusCable = new RendererBusCable();
		public static final RendererProcessor rendererProcessor = new RendererProcessor();
	}
}
