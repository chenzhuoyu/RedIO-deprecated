package com.magicbox.redio.blocks;

import java.util.ArrayList;

import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.util.MathHelper;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import com.magicbox.redio.entities.EntityBase;
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

	@SideOnly(Side.CLIENT)
	public void onRender(IBlockAccess blockAccess, int x, int y, int z)
	{
		TileEntity tileEntity = blockAccess.getTileEntity(x, y, z);

		if (tileEntity instanceof EntityBase)
			((EntityBase)tileEntity).onRender();
	}

	@Override
	public void onBlockPlacedBy(World world, int x, int z, int y, EntityLivingBase entity, ItemStack itemStack)
	{
		TileEntity tileEntity = world.getTileEntity(x, y, z);
		if (tileEntity instanceof EntityBase)
			((EntityBase)tileEntity).setFacing(MathHelper.floor_double(entity.rotationYaw * 4.0F / 360.0F + 0.5d) & 3);
	}
}
