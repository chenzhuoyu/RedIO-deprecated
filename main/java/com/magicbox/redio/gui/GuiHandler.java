package com.magicbox.redio.gui;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

import com.magicbox.redio.common.Constants;
import com.magicbox.redio.container.ContainerProcessor;
import com.magicbox.redio.container.ContainerScriptStorage;
import com.magicbox.redio.entities.EntityProcessor;
import com.magicbox.redio.entities.EntityScriptStorage;

import cpw.mods.fml.common.network.IGuiHandler;

public class GuiHandler implements IGuiHandler
{
	@Override
	public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z)
	{
		TileEntity te = world.getTileEntity(x, y, z);
		switch (ID)
		{
			case Constants.ScriptStorage.GUI_ID:
				if (te instanceof EntityScriptStorage)
					return new ContainerScriptStorage(player.inventory, (EntityScriptStorage) te);
			break;
			case Constants.Processor.GUI_ID:
				if (te instanceof EntityProcessor)
					return new ContainerProcessor(player.inventory, (EntityProcessor) te);
			break;
		}
		return null;
	}

	@Override
	public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z)
	{
		TileEntity te = world.getTileEntity(x, y, z);
		switch (ID)
		{
			case Constants.ScriptStorage.GUI_ID:
				if (te instanceof EntityScriptStorage)
					return new GuiScriptStorage(player.inventory, (EntityScriptStorage) te);
			break;
			case Constants.Processor.GUI_ID:
				if (te instanceof EntityProcessor)
					return new GuiProcessor((EntityProcessor) te);
		}
		return null;
	}
}
