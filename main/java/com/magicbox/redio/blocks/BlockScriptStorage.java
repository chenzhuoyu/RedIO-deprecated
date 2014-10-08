package com.magicbox.redio.blocks;

import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

import com.magicbox.redio.common.Constants;
import com.magicbox.redio.entities.EntityScriptStorage;

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
	public TileEntity createNewTileEntity(World world, int meta)
	{
		return new EntityScriptStorage();
	}
}
