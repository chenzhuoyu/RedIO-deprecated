package com.magicbox.redio.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.util.IIcon;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BaseBlock extends Block
{
	protected BaseBlock(Material material)
	{
		super(material);
	}

	protected void setTextureName(String texture)
	{
		;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIcon(int side, int meta)
	{
		return blockIcon;
	}
}
