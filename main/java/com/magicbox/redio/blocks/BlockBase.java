package com.magicbox.redio.blocks;

import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;

import com.magicbox.redio.common.Constants;
import com.magicbox.redio.entities.EntityBase;
import com.magicbox.redio.utils.TextureLoader;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public abstract class BlockBase extends BlockContainer
{
	private IIcon [] icons = new IIcon [6];

	protected BlockBase(Material material)
	{
		super(material);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIcon(int side, int meta)
	{
		return icons[Constants.TEXTURE_INDEX[0][side]];
	}

	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIcon(IBlockAccess access, int x, int y, int z, int side)
	{
		TileEntity entity = access.getTileEntity(x, y, z);

		if (!(entity instanceof EntityBase))
			return icons[Constants.TEXTURE_INDEX[0][side]];

		return icons[Constants.TEXTURE_INDEX[((EntityBase)entity).getFacing()][side]];
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister register)
	{
		for (int i = 0; i < 6; i++)
			icons[i] = TextureLoader.registerTexture(register, textureName, i);
	}
}
