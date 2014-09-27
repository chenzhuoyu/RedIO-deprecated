package com.magicbox.redio.blocks;

import java.util.ArrayList;

import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;

import com.magicbox.redio.entities.EntityBase;
import com.magicbox.redio.utils.TextureLoader;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public abstract class BlockBase extends BlockContainer
{
	private ArrayList<IIcon> textures = new ArrayList<IIcon>();

	protected BlockBase(Material material)
	{
		super(material);
	}

	public abstract int getTextureCount();

	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIcon(int side, int meta)
	{
		return textures.get(side);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIcon(IBlockAccess access, int x, int y, int z, int side)
	{
		TileEntity entity = access.getTileEntity(x, y, z);
		return textures.get(entity instanceof EntityBase ? ((EntityBase)entity).getTextureIndex(side) : side);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister register)
	{
		for (int i = 0; i < getTextureCount(); i++)
			textures.add(TextureLoader.registerTexture(register, textureName, i));
	}
}
