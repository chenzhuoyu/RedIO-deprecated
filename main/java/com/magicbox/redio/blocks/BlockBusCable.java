package com.magicbox.redio.blocks;

import net.minecraft.block.material.Material;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

import com.magicbox.redio.common.Constants;
import com.magicbox.redio.common.Instances;
import com.magicbox.redio.entities.EntityBusCable;

public class BlockBusCable extends BlockBase
{
	public int facing;
	public EntityBusCable entityBusCable;

	public BlockBusCable(Material material)
	{
		super(material);
		System.out.print("\nBlock Created\n");
		setHardness(0.5f);
		setBlockName(Constants.BusCable.BLOCK_NAME);
		setCreativeTab(Instances.creativeTab);
		setBlockTextureName(Constants.getTextureName(Constants.BusCable.BLOCK_NAME));
		// setBlockBounds(0.0f, 0.0f, 0.0f, 1, 0.2f, 1);
		facing = 0;
	}

	@Override
	public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase entity, ItemStack itemStack)
	{
		facing = MathHelper.floor_double(entity.rotationYaw * 4.0F / 360.0F + 0.5D) & 3;
		entityBusCable.setFacing(facing);
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
		entityBusCable = new EntityBusCable();
		return entityBusCable;
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
