package com.magicbox.redio.blocks;

import java.util.Random;

import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.Explosion;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import com.magicbox.redio.common.Constants;
import com.magicbox.redio.entities.EntityBase;
import com.magicbox.redio.entities.EntityProcessor;

public class BlockProcessor extends BlockBase
{
	private int dropCount = 0;

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
		return 24;
	}

	@Override
	public int quantityDropped(Random random)
	{
		return dropCount;
	}

	@Override
	public boolean canConnectRedstone(IBlockAccess world, int x, int y, int z, int side)
	{
		TileEntity entity = world.getTileEntity(x, y, z);
		return entity instanceof EntityProcessor ? ((EntityProcessor)entity).getPowered() : false;
	}

	@Override
	public EntityBase createNewTileEntity(World world, int meta)
	{
		return new EntityProcessor();
	}

	@Override
	public void onBlockExploded(World world, int x, int y, int z, Explosion explosion)
	{
		dropCount = 0;
		super.onBlockExploded(world, x, y, z, explosion);
	}

	@Override
	public void onBlockDestroyedByPlayer(World world, int x, int y, int z, int meta)
	{
		dropCount = 1;
	}
}
