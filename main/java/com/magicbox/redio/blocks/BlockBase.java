package com.magicbox.redio.blocks;

import java.util.ArrayList;

import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import com.magicbox.redio.common.Constants;
import com.magicbox.redio.common.Instances;
import com.magicbox.redio.entities.EntityBase;
import com.magicbox.redio.utils.TextureLoader;
import com.magicbox.redio.utils.Utils;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public abstract class BlockBase extends BlockContainer
{
	protected final ArrayList<IIcon> textures = new ArrayList<IIcon>();

	protected BlockBase(Material material)
	{
		super(material);
		setStepSound(Block.soundTypeStone);
		setCreativeTab(Instances.creativeTab);
	}

	public abstract int getTextureCount();

	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIcon(int side, int meta)
	{
		return textures.get(Constants.FACING_SIDE[meta & 0x03][side]);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIcon(IBlockAccess world, int x, int y, int z, int side)
	{
		int meta = world.getBlockMetadata(x, y, z);
		TileEntity entity = world.getTileEntity(x, y, z);

		if (!(entity instanceof EntityBase))
			return getIcon(side, meta & 0x03);

		int index = ((EntityBase)entity).getTextureIndex(side, meta);
		return index == -1 ? getIcon(side, meta & 0x03) : textures.get(index);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public int getRenderType()
	{
		return Instances.Renderers.rendererGlobal.getRenderId();
	}

	@Override
	@SideOnly(Side.CLIENT)
	public boolean isOpaqueCube()
	{
		return false;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public boolean renderAsNormalBlock()
	{
		return false;
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
