package com.magicbox.redio.blocks;

import java.util.ArrayList;

import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;

import com.magicbox.redio.common.Constants;
import com.magicbox.redio.common.Instances;
import com.magicbox.redio.common.Utils;
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
		setStepSound(Block.soundTypeStone);
		setCreativeTab(Instances.creativeTab);
	}

	public abstract int getTextureCount();

	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIcon(int side, int meta)
	{
		return textures.get(Constants.FACING_SIDE[meta][side]);
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

	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float faceX, float faceY, float faceZ)
	{
		int facing = Utils.getPlayerFacing(player);
		((EntityBase)world.getTileEntity(x, y, z)).setFacing(facing);
		return true;
	}
}
