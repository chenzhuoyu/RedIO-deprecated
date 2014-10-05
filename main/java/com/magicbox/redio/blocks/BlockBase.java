package com.magicbox.redio.blocks;

import java.util.ArrayList;

import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;

import com.magicbox.redio.common.Utils;
import com.magicbox.redio.utils.TextureLoader;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public abstract class BlockBase extends BlockContainer
{
	private final ArrayList<IIcon> textures = new ArrayList<IIcon>();

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
	public void registerBlockIcons(IIconRegister register)
	{
		for (int i = 0; i < getTextureCount(); i++)
			textures.add(TextureLoader.registerTexture(register, textureName, i));
	}

	@Override
	public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase entity, ItemStack itemStack)
	{
		int facing = Utils.getPlayerFacing(entity);
		world.setBlockMetadataWithNotify(x, y, z, facing, 1);
	}
}
