package com.magicbox.redio.blocks;

import com.magicbox.redio.common.Constants;
import com.magicbox.redio.common.Instances;

import net.minecraft.block.material.Material;

public class BUS extends BaseBlock
{
	public BUS(Material material)
	{
		super(material);
		setHardness(1.0f);
		setBlockName(Constants.BUS.BLOCK_NAME);
		setCreativeTab(Instances.creativeTab);
		setHarvestLevel("pickaxe", 1);
		setBlockTextureName(Constants.BUS.TEXTURE_NAME);
	}
}
