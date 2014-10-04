package com.magicbox.redio.proxies;

import com.magicbox.redio.common.Instances;

import cpw.mods.fml.client.registry.RenderingRegistry;

public class ClientProxy extends CommonProxy
{
	@Override
	public void registerRenderers()
	{
		super.registerRenderers();
		RenderingRegistry.registerBlockHandler(Instances.Renderers.rendererBusCable);
	}
}
