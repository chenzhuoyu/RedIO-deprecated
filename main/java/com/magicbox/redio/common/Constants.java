package com.magicbox.redio.common;

/* @formatter:off */
public class Constants
{
	public static final String MOD_ID = "redio";
	public static final String MOD_VERSION = "v0.1a";
	public static final String CHANNEL_NAME = "RedIO";

	public static final String CLIENT_PROXY = "com.magicbox.redio.proxies.ClientProxy";
	public static final String SERVER_PROXY = "com.magicbox.redio.proxies.CommonProxy";

	public static final int [][] FACING_SIDE = new int [] []
	{
		{ 0, 1, 2, 3, 4, 5 },
		{ 0, 1, 4, 5, 3, 2 },
		{ 0, 1, 3, 2, 5, 4 },
		{ 0, 1, 5, 4, 2, 3 },
	};

	public interface BusCable
	{
		public static final String BLOCK_NAME = "busCable";
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
	}

	public static String getTextureName(String blockName)
	{
		return MOD_ID + ":" + blockName;
	}
}
