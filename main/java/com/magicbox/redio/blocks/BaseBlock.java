package com.magicbox.redio.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.util.IIcon;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BaseBlock extends Block
{
	private IIcon [] icons;

	protected BaseBlock(Material material)
	{
		super(material);
	}

	@Override
	public String getTextureName()
	{
		return textureName;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIcon(int side, int meta)
	{
		return icons[side];
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister register)
	{
		icons = new IIcon [6];
		icons[0] = register.registerIcon(textureName + "/bottom");
		icons[1] = register.registerIcon(textureName + "/top");
		icons[2] = register.registerIcon(textureName + "/left");
		icons[3] = register.registerIcon(textureName + "/front");
		icons[4] = register.registerIcon(textureName + "/right");
		icons[5] = register.registerIcon(textureName + "/rear");
	}
}
