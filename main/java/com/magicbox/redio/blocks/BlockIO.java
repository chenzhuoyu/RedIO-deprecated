package com.magicbox.redio.blocks;

import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.Explosion;
import net.minecraft.world.World;

import com.magicbox.redio.common.Constants;
import com.magicbox.redio.common.Utils;
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
	public TileEntity createNewTileEntity(World world, int metadata)
	{
		EntityIO entity = new EntityIO();

		entity.setName(Utils.getRouterName("IO-"));
		return entity;
	}

	@Override
	public void onBlockExploded(World world, int x, int y, int z, Explosion explosion)
	{
		TileEntity processor = world.getTileEntity(x, y, z);

		if (processor instanceof EntityIO)
			((EntityIO)processor).unregister();

		super.onBlockExploded(world, x, y, z, explosion);
	}

	@Override
	public void onBlockDestroyedByPlayer(World world, int x, int y, int z, int metadata)
	{
		TileEntity processor = world.getTileEntity(x, y, z);

		if (processor instanceof EntityIO)
			((EntityIO)processor).unregister();

		super.onBlockDestroyedByPlayer(world, x, y, z, metadata);
	}
}
