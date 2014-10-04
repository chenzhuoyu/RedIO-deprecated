package com.magicbox.redio.blocks;

import net.minecraft.block.material.Material;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

import com.magicbox.redio.common.Constants;
import com.magicbox.redio.common.Instances;
import com.magicbox.redio.entities.EntityBase;
import com.magicbox.redio.entities.EntityBusCable;

public class BlockBusCable extends BlockBase
{
	public BlockBusCable(Material material)
	{
		super(material);
		System.out.print("\nBlock Created\n");
		setHardness(0.5f);
		setBlockName(Constants.BusCable.BLOCK_NAME);
		setCreativeTab(Instances.creativeTab);
		setBlockTextureName(Constants.getTextureName(Constants.BusCable.BLOCK_NAME));
		// setBlockBounds(0.0f, 0.0f, 0.0f, 1, 0.2f, 1);
	}

	@Override
	public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase entity, ItemStack itemStack)
	{
		int facing = MathHelper.floor_double(entity.rotationYaw * 4.0F / 360.0F + 0.5D) & 3;
		TileEntity tileEntity = world.getTileEntity(x, y, z);
		if (tileEntity instanceof EntityBase)
			((EntityBase)tileEntity).setFacing(facing);
		System.out.print(facing);
	}

	@Override
	public boolean isOpaqueCube()
	{
		return false;
	}

	@Override
	public int getTextureCount()
	{
		return 6;
	}

	@Override
	public TileEntity createNewTileEntity(World world, int meta)
	{
		System.out.print("\nentity created\n");
		return new EntityBusCable();
	}

	@Override
	public boolean renderAsNormalBlock()
	{
		return false;
	}

	@Override
	public int getRenderType()
	{
		return Instances.Renderers.rendererBusCable.getRenderId();
	}
}
