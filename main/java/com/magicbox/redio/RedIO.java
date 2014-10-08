package com.magicbox.redio;

import com.magicbox.redio.common.Constants;
import com.magicbox.redio.proxies.CommonProxy;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;

@Mod(modid = Constants.MOD_ID, version = Constants.MOD_VERSION)
public class RedIO
{
	@Instance(value = Constants.MOD_ID)
	public static RedIO instance;

	@SidedProxy(clientSide = Constants.CLIENT_PROXY, serverSide = Constants.SERVER_PROXY)
	public static CommonProxy proxy;

	@EventHandler
	public void load(FMLInitializationEvent event)
	{
		proxy.registerBlocks();
		proxy.registerEntities();
		proxy.registerRenderers();
		proxy.registerGuiHandler();
	}
}
