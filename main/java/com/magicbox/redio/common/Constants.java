package com.magicbox.redio.common;

import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.util.ResourceLocation;

/* @formatter:off */
public class Constants
{
	public static final String MOD_ID = "redio";
	public static final String MOD_VERSION = "v0.1a";
	public static final String CHANNEL_NAME = "RedIO";

	public static final String CLIENT_PROXY = "com.magicbox.redio.proxies.ClientProxy";
	public static final String SERVER_PROXY = "com.magicbox.redio.proxies.CommonProxy";

	public static final int [][] FACING_SIDE = new int [][]
	{
		{ 0, 1, 2, 3, 4, 5 },
		{ 0, 1, 4, 5, 3, 2 },
		{ 0, 1, 3, 2, 5, 4 },
		{ 0, 1, 5, 4, 2, 3 },
	};

	public interface Packets
	{
		public static final int packetBusCable = 0;
		public static final int packetProcessor = 1;
		public static final int packetScriptStorage = 2;
	}

	public interface Recipes
	{
		public static final Object[] recipeBusCableVertical = new Object[]
		{
			Instances.Blocks.blockBusCable, 1, new Object[]
			{
				Blocks.wool,		Items.redstone,		Blocks.wool,
				Blocks.iron_bars,	Items.redstone,		Blocks.iron_bars,
				Blocks.wool,		Items.redstone,		Blocks.wool,
			}
		};

		public static final Object[] recipeBusCableHorizontal = new Object[]
		{
			Instances.Blocks.blockBusCable, 1, new Object[]
			{
				Blocks.wool,		Blocks.iron_bars,	Blocks.wool,
				Items.redstone,		Items.redstone,		Items.redstone,
				Blocks.wool,		Blocks.iron_bars,	Blocks.wool,
			}
		};
	}

	public interface IO
	{
		public static final String BLOCK_NAME = "IO";
	}

	public interface BusCable
	{
		public static final String BLOCK_NAME = "busCable";

		public static enum Direction
		{
			XNegative,
			XPositive,
			ZNegative,
			ZPositive,
		}
	}

	public interface Processor
	{
		public static final String BLOCK_NAME = "processor";
	}

	public interface ScriptStorage
	{
		public static final String BLOCK_NAME = "scriptStorage";

		public static final String SCRIPT_EXT = ".py";
		public static final String SCRIPT_FOLDER = "scripts";

		public static final int GUI_ID = 0;
		public static final int GUI_X_SIZE = 176;
		public static final int GUI_Y_SIZE = 166;
	}

	public static String getTextureName(String blockName)
	{
		return MOD_ID + ":" + blockName;
	}

	public static ResourceLocation getGuiTextureLocation(String blockName)
	{
		return new ResourceLocation(MOD_ID, "textures/gui/" + blockName + "_gui.png");
	}
}
