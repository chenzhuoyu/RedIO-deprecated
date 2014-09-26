package com.magicbox.redio.blocks;

import java.io.File;
import java.io.FilenameFilter;
import java.util.ArrayList;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;

import com.magicbox.redio.common.Constants;

public class ScriptStorage extends BaseBlock
{
	private File				folder	= new File(Constants.ScriptStorage.SCRIPT_FOLDER);
	private ArrayList<String>	scripts	= new ArrayList<String>();

	private class ScriptFilter implements FilenameFilter
	{
		@Override
		public boolean accept(File dir, String name)
		{
			return name.endsWith(Constants.ScriptStorage.SCRIPT_EXT);
		}
	}

	public ScriptStorage(Material material)
	{
		super(material);
		setHardness(3.0f);
		setStepSound(Block.soundTypeStone);
		setBlockName(Constants.ScriptStorage.BLOCK_NAME);
		setCreativeTab(CreativeTabs.tabMisc);
		setHarvestLevel("pickaxe", 1);
		setBlockTextureName(Constants.ScriptStorage.TEXTURE_NAME);

		if (!folder.exists())
			folder.mkdirs();

		for (File file : folder.listFiles(new ScriptFilter()))
			System.out.println(file.getName());
	}
}
