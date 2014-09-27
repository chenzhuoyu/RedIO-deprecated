package com.magicbox.redio.common;

public class Constants
{
	public static final String MOD_ID = "redio";
	public static final String MOD_VERSION = "v0.1a";

	public static final String CLIENT_PROXY = "com.magicbox.redio.proxies.ClientProxy";
	public static final String SERVER_PROXY = "com.magicbox.redio.proxies.CommonProxy";

	public static final int [][] TEXTURE_INDEX =
	{
		{
			0, 1, 2, 3, 4, 5
		},
		{
			0, 1, 3, 4, 5, 2
		},
		{
			0, 1, 4, 5, 2, 3
		},
		{
			0, 1, 5, 2, 3, 4
		},
		{
			4, 5, 0, 1, 2, 3
		},
		{
			5, 4, 3, 2, 1, 0
		}
	};

	public interface Processor
	{
		public static final String BLOCK_NAME = "processor";
	}

	public interface ScriptStorage
	{
		public static final String SCRIPT_EXT = ".py";
		public static final String SCRIPT_FOLDER = "scripts";

		public static final String BLOCK_NAME = "scriptStorage";
	}

	public static String getTextureName(String blockName)
	{
		return MOD_ID + ":" + blockName;
	}
}
