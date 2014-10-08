package com.magicbox.redio.proxies;

import java.lang.reflect.Field;

import com.magicbox.redio.common.Instances;
import com.magicbox.redio.renderers.RendererGlobal;

import cpw.mods.fml.client.registry.RenderingRegistry;

public class ClientProxy extends CommonProxy
{
	@Override
	public void registerRenderers()
	{
		for (Field field : Instances.Renderers.class.getFields())
		{
			try
			{
				RenderingRegistry.registerBlockHandler((RendererGlobal)field.get(field.getClass()));
			} catch (IllegalAccessException | IllegalArgumentException e)
			{
				e.printStackTrace();
			}
		}
	}
}
