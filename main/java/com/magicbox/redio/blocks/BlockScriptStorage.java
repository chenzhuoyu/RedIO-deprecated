package com.magicbox.redio.blocks;

import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

import com.magicbox.redio.common.Constants;
import com.magicbox.redio.common.Utils;
import com.magicbox.redio.entities.EntityScriptStorage;

import cpw.mods.fml.common.network.internal.FMLNetworkHandler;

public class BlockScriptStorage extends BlockBase
{
	public BlockScriptStorage(Material material)
	{
		super(material);

		setHardness(3.0f);
		setResistance(5.0f);
		setHarvestLevel("pickaxe", 1);

		setBlockName(Constants.ScriptStorage.BLOCK_NAME);
		setBlockTextureName(Constants.getTextureName(Constants.ScriptStorage.BLOCK_NAME));
	}

	@Override
	public int getTextureCount()
	{
		return 12;
	}

	@Override
	public boolean canPlaceBlockAt(World world, int x, int y, int z)
	{
		return super.canPlaceBlockAt(world, x, y, z) && Utils.hasProcessorAround(world, x, y, z);
	}

	@Override
	public TileEntity createNewTileEntity(World world, int metadata)
	{
		return new EntityScriptStorage();
	}

	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float hitx, float hity, float hitz)
	{
		if (player.isSneaking())
			return false;

		if (!world.isRemote)
			FMLNetworkHandler.openGui(player, Constants.MOD_ID, Constants.ScriptStorage.GUI_ID, world, x, y, z);

		return true;
	}
}
