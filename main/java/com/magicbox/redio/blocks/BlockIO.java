package com.magicbox.redio.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import com.magicbox.redio.common.Constants;
import com.magicbox.redio.entities.EntityIO;

public class BlockIO extends BlockBase
{
	public BlockIO(Material material)
	{
		super(material);

		setHardness(3.0f);
		setResistance(5.0f);
		setHarvestLevel("pickaxe", 1);

		setBlockName(Constants.IO.BLOCK_NAME);
		setBlockTextureName(Constants.getTextureName(Constants.IO.BLOCK_NAME));
	}

	@Override
	public int getTextureCount()
	{
		return 6;
	}

	@Override
	public boolean canProvidePower()
	{
		return true;
	}

	@Override
	public TileEntity createNewTileEntity(World world, int metadata)
	{
		return new EntityIO();
	}

	@Override
	public void breakBlock(World world, int x, int y, int z, Block block, int metadata)
	{
		TileEntity entity = world.getTileEntity(x, y, z);

		if (entity instanceof EntityIO)
			((EntityIO)entity).unregister();

		super.breakBlock(world, x, y, z, block, metadata);
	}

	@Override
	public int isProvidingWeakPower(IBlockAccess world, int x, int y, int z, int metadata)
	{
		TileEntity entity = world.getTileEntity(x, y, z);
		return entity instanceof EntityIO && ((EntityIO)entity).getOutputing() ? 15 : 0;
	}

	@Override
	public int isProvidingStrongPower(IBlockAccess world, int x, int y, int z, int metadata)
	{
		TileEntity entity = world.getTileEntity(x, y, z);
		return entity instanceof EntityIO && ((EntityIO)entity).getOutputing() ? 15 : 0;
	}
}
