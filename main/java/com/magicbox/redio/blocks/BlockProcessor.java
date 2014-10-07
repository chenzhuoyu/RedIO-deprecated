package com.magicbox.redio.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import com.magicbox.redio.common.Constants;
import com.magicbox.redio.entities.EntityBase;
import com.magicbox.redio.entities.EntityProcessor;

public class BlockProcessor extends BlockBase
{
	public BlockProcessor(Material material)
	{
		super(material);
		setHardness(3.0f);
		setHarvestLevel("pickaxe", 1);
		setBlockName(Constants.Processor.BLOCK_NAME);
		setBlockTextureName(Constants.getTextureName(Constants.Processor.BLOCK_NAME));
	}

	@Override
	public int getTextureCount()
	{
		return 12;
	}

	@Override
	public boolean canConnectRedstone(IBlockAccess world, int x, int y, int z, int side)
	{
		return true;
	}

	@Override
	public EntityBase createNewTileEntity(World world, int meta)
	{
		return new EntityProcessor();
	}

	@Override
	public void onNeighborBlockChange(World world, int x, int y, int z, Block block)
	{
		TileEntity entity = world.getTileEntity(x, y, z);
		if (entity instanceof EntityProcessor)
			((EntityProcessor)entity).onNeighborBlockChanged(block);
	}
}
