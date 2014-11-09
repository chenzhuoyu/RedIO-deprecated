package com.magicbox.redio.blocks;

import java.util.List;
import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.Explosion;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import com.magicbox.redio.common.Constants;
import com.magicbox.redio.common.Instances;
import com.magicbox.redio.entities.EntityBase;
import com.magicbox.redio.entities.EntityProcessor;
import com.magicbox.redio.entities.EntityScriptStorage;
import com.magicbox.redio.utils.Utils;

import cpw.mods.fml.common.network.internal.FMLNetworkHandler;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockProcessor extends BlockBase
{
	public BlockProcessor(Material material)
	{
		super(material);

		setHardness(3.0f);
		setResistance(3.0f);
		setHarvestLevel("pickaxe", 1);

		setBlockName(Constants.Processor.BLOCK_NAME);
		setBlockTextureName(Constants.getTextureName(Constants.Processor.BLOCK_NAME));
	}

	@Override
	@SideOnly(Side.CLIENT)
	public int getRenderType()
	{
		return Instances.Renderers.rendererProcessor.getRenderId();
	}

	@Override
	public int damageDropped(int metadata)
	{
		return metadata & 0x04;
	}

	@Override
	public int getTextureCount()
	{
		return 24;
	}

	@Override
	public int quantityDropped(Random random)
	{
		return 0;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIcon(int side, int meta)
	{
		return meta == -1 ? textures.get(side) : super.getIcon(side, meta);
	}

	@Override
	public boolean canConnectRedstone(IBlockAccess world, int x, int y, int z, int side)
	{
		TileEntity entity = world.getTileEntity(x, y, z);
		return entity instanceof EntityProcessor ? ((EntityProcessor)entity).getPowered() : false;
	}

	@Override
	public ItemStack getPickBlock(MovingObjectPosition target, World world, int x, int y, int z)
	{
		int damageValue = getDamageValue(world, x, y, z);
		return new ItemStack(Item.getItemFromBlock(this), 1, damageValue)
			.setStackDisplayName((damageValue & 0x04) == 0 ? "Processor" : "Processor (damaged)");
	}

	@Override
	public EntityBase createNewTileEntity(World world, int meta)
	{
		return new EntityProcessor();
	}

	@Override
	public void getSubBlocks(Item item, CreativeTabs tab, List subitems)
	{
		subitems.add(new ItemStack(this, 1, 0x00).setStackDisplayName("Processor"));
		subitems.add(new ItemStack(this, 1, 0x04).setStackDisplayName("Processor (damaged)"));
	}

	@Override
	public void breakBlock(World world, int x, int y, int z, Block block, int metadata)
	{
		TileEntity entity = world.getTileEntity(x, y, z);

		if (entity instanceof EntityProcessor)
			((EntityProcessor)entity).unregister();

		super.breakBlock(world, x, y, z, block, metadata);
	}

	@Override
	public void onBlockExploded(World world, int x, int y, int z, Explosion explosion)
	{
		TileEntity processor = world.getTileEntity(x, y, z);

		if (processor instanceof EntityProcessor)
			((EntityProcessor)processor).unregister();

		super.onBlockExploded(world, x, y, z, explosion);

		// @formatter:off
		TileEntity [] entities = new TileEntity[]
		{
			world.getTileEntity(x - 1, y, z),
			world.getTileEntity(x + 1, y, z),
			world.getTileEntity(x, y, z - 1),
			world.getTileEntity(x, y, z + 1),
			world.getTileEntity(x, y - 1, z),
		};

		// @formatter:on
		for (TileEntity entity : entities)
			if (entity instanceof EntityScriptStorage)
				if (!Utils.hasProcessorAround(world, entity.xCoord, entity.yCoord, entity.zCoord))
					world.setBlockToAir(entity.xCoord, entity.yCoord, entity.zCoord);
	}

	@Override
	public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase entity, ItemStack itemStack)
	{
		int damage = itemStack.getItemDamage();
		int facing = Utils.getPlayerFacing(entity);

		world.setBlockMetadataWithNotify(x, y, z, facing | damage, 1);
		((EntityProcessor)world.getTileEntity(x, y, z)).setDamaged((damage & 0x4) == 0x04);
	}

	@Override
	public void onBlockDestroyedByPlayer(World world, int x, int y, int z, int metadata)
	{
		TileEntity processor = world.getTileEntity(x, y, z);

		if (processor instanceof EntityProcessor)
			((EntityProcessor)processor).unregister();

		// @formatter:off
		TileEntity [] entities = new TileEntity[]
		{
			world.getTileEntity(x - 1, y, z),
			world.getTileEntity(x + 1, y, z),
			world.getTileEntity(x, y, z - 1),
			world.getTileEntity(x, y, z + 1),
			world.getTileEntity(x, y - 1, z),
		};

		// @formatter:on
		for (TileEntity entity : entities)
		{
			if (entity instanceof EntityScriptStorage)
			{
				BlockScriptStorage block = (BlockScriptStorage)((EntityScriptStorage)entity).getBlockType();

				if (!Utils.hasProcessorAround(world, entity.xCoord, entity.yCoord, entity.zCoord))
				{
					world.setBlockToAir(entity.xCoord, entity.yCoord, entity.zCoord);
					block.dropBlockAsItem(world, entity.xCoord, entity.yCoord, entity.zCoord, metadata, 0);
				}
			}
		}

		int damage = damageDropped(metadata);
		ItemStack stack = new ItemStack(Item.getItemFromBlock(this), 1, damage);
		dropBlockAsItem(world, x, y, z, stack.setStackDisplayName(damage == 0x00 ? "Processor" : "Processor (damaged)"));
	}

	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float hitx, float hity, float hitz)
	{
		if (player.isSneaking())
			return false;

		if (!world.isRemote)
			FMLNetworkHandler.openGui(player, Constants.MOD_ID, Constants.Processor.GUI_ID, world, x, y, z);

		// @formatter:off
		// Deliver messages to bus:
		// Utils.broadcastProcessorPacket((EntityProcessor)world.getTileEntity(x, y, z), "IO-1", RedBoolObject.trueObject);
		// @formatter:on
		return true;
	}
}
