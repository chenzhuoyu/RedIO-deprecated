package com.magicbox.redio.blocks;

import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

import com.magicbox.redio.common.Constants;
import com.magicbox.redio.common.Instances;
import com.magicbox.redio.entities.EntityBusCable;

public class BlockBusCable extends BlockBase
{
	public BlockBusCable(Material material)
	{
		super(material);
		setHardness(0.5f);
		setBlockName(Constants.BusCable.BLOCK_NAME);
		setCreativeTab(Instances.creativeTab);
		setBlockTextureName(Constants.getTextureName(Constants.BusCable.BLOCK_NAME));
		setBlockBounds(0.0f, 0.0f, 0.0f, 1, 0.2f, 1);
		setLightOpacity(0);
	}

	@Override
	public boolean isOpaqueCube()
	{
		return true;
	}

	@Override
	public int getTextureCount()
	{
		return 6;
	}

	@Override
	public TileEntity createNewTileEntity(World world, int meta)
	{
		return new EntityBusCable();
	}
}
