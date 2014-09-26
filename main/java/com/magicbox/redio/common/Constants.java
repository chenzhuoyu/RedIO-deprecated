package com.magicbox.redio.common;

public class Constants
{
	public static final String	MOD_ID			= "redio";
	public static final String	MOD_VERSION		= "v0.1a";

	public static final String	CLIENT_PROXY	= "com.magicbox.redio.proxy.ClientProxy";
	public static final String	SERVER_PROXY	= "com.magicbox.redio.proxy.CommonProxy";

	public interface ScriptStorage
	{
		public static final String	SCRIPT_EXT		= ".py";
		public static final String	SCRIPT_FOLDER	= "scripts";

		public static final String	BLOCK_NAME		= "scriptStorage";
		public static final String	TEXTURE_NAME	= MOD_ID + ":" + BLOCK_NAME;
	}
	
	public interface BUS
	{
		public static final String	BLOCK_NAME		= "bus";
		public static final String	TEXTURE_NAME	= MOD_ID + ":" + BLOCK_NAME;
	}
}
