package com.magicbox.redio.blocks;

import java.io.File;
import java.io.FilenameFilter;
import java.util.ArrayList;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

import com.magicbox.redio.common.Constants;
import com.magicbox.redio.common.Instances;

public class BlockScriptStorage extends BlockBase
{
	private final File folder = new File(Constants.ScriptStorage.SCRIPT_FOLDER);
	private final ArrayList<String> scripts = new ArrayList<String>();

	private class ScriptFilter implements FilenameFilter
	{
		@Override
		public boolean accept(File dir, String name)
		{
			return name.endsWith(Constants.ScriptStorage.SCRIPT_EXT);
		}
	}

	public BlockScriptStorage(Material material)
	{
		super(material);
		setHardness(3.0f);
		setStepSound(Block.soundTypeStone);
		setBlockName(Constants.ScriptStorage.BLOCK_NAME);
		setCreativeTab(Instances.creativeTab);
		setHarvestLevel("pickaxe", 1);
		setBlockTextureName(Constants.getTextureName(Constants.ScriptStorage.BLOCK_NAME));

		if (!folder.exists())
			folder.mkdirs();

		for (File file : folder.listFiles(new ScriptFilter()))
			System.out.println(file.getName());
	}

	@Override
	public int getTextureCount()
	{
		return 6;
	}

	@Override
	public TileEntity createNewTileEntity(World world, int meta)
	{
		return null;
	}
}
