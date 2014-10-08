package com.magicbox.redio.blocks;

import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

import com.magicbox.redio.common.Constants;
import com.magicbox.redio.entities.EntityProcessor;
import com.magicbox.redio.entities.EntityScriptStorage;

import cpw.mods.fml.common.network.internal.FMLNetworkHandler;

public class BlockScriptStorage extends BlockBase
{
	public BlockScriptStorage(Material material)
	{
		super(material);

		setHardness(3.0f);
		setHarvestLevel("pickaxe", 1);

		setBlockName(Constants.ScriptStorage.BLOCK_NAME);
		setBlockTextureName(Constants.getTextureName(Constants.ScriptStorage.BLOCK_NAME));
	}

	@Override
	public int getTextureCount()
	{
		return 6;
	}

	@Override
	public boolean canPlaceBlockAt(World world, int x, int y, int z)
	{
		if (!super.canPlaceBlockAt(world, x, y, z))
			return false;

		// @formatter:off
		TileEntity [] entities = new TileEntity[]
		{
			world.getTileEntity(x - 1, y, z),
			world.getTileEntity(x + 1, y, z),
			world.getTileEntity(x, y, z - 1),
			world.getTileEntity(x, y, z + 1),
			world.getTileEntity(x, y + 1, z),
		};

		// @formatter:on
		for (TileEntity entity : entities)
			if (entity instanceof EntityProcessor)
				return true;

		return false;
	}

	@Override
	public TileEntity createNewTileEntity(World world, int meta)
	{
		return new EntityScriptStorage();
	}

	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float hitx, float hity, float hitz)
	{
		if (world.isRemote || player.isSneaking())
			return false;

		FMLNetworkHandler.openGui(player, Constants.MOD_ID, 0, world, x, y, z);
		return true;
	}
}
