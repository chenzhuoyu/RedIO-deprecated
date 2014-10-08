package com.magicbox.redio.gui;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

import com.magicbox.redio.container.ContainerScriptStorage;
import com.magicbox.redio.entities.EntityScriptStorage;

import cpw.mods.fml.common.network.IGuiHandler;

public class GuiHandler implements IGuiHandler
{
	@Override
	public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z)
	{
		TileEntity te = world.getTileEntity(x, y, z);
		if (te instanceof EntityScriptStorage)
			return new ContainerScriptStorage(player.inventory, (EntityScriptStorage) te);
		else
			return null;
	}

	@Override
	public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z)
	{
		TileEntity te = world.getTileEntity(x, y, z);
		if (te instanceof EntityScriptStorage)
			return new GuiScriptStorage(player.inventory, (EntityScriptStorage) te);
		else
			return null;
	}
}
