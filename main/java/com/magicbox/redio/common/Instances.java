package com.magicbox.redio.common;

import net.minecraft.block.material.Material;

import com.magicbox.redio.CreativeTab;
import com.magicbox.redio.blocks.ScriptStorage;

public class Instances
{
	public static final CreativeTab	creativeTab	= new CreativeTab();

	public interface Blocks
	{
		public static final ScriptStorage	scriptStorage	= new ScriptStorage(Material.rock);
		public static final BUS	bus	= new BUS(Material.rock);
	}
}
