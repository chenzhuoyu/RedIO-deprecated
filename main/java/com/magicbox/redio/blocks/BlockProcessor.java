package com.magicbox.redio.blocks;

import java.util.Random;

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
	public int quantityDropped(Random random)
	{
		return 0;
	}

	@Override
	public boolean canConnectRedstone(IBlockAccess world, int x, int y, int z, int side)
	{
		TileEntity entity = world.getTileEntity(x, y, z);
		return entity == null ? false : entity.getWorldObj().isBlockIndirectlyGettingPowered(x, y, z);
	}

	@Override
	public EntityBase createNewTileEntity(World world, int meta)
	{
		return new EntityProcessor();
	}
}
