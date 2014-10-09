package com.magicbox.redio.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;

import com.magicbox.redio.common.Constants;
import com.magicbox.redio.container.ContainerScriptStorage;
import com.magicbox.redio.entities.EntityScriptStorage;

public class GuiScriptStorage extends GuiContainer
{
	public static ResourceLocation guiTexture = Constants.getGuiTextureLocation(Constants.ScriptStorage.BLOCK_NAME);

	public GuiScriptStorage(InventoryPlayer inventory, EntityScriptStorage entity)
	{
		super(new ContainerScriptStorage(inventory, entity));
		xSize = Constants.ScriptStorage.GUI_X_SIZE;
		ySize = Constants.ScriptStorage.GUI_Y_SIZE;
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float var1, int var2, int var3)
	{
		Minecraft.getMinecraft().getTextureManager().bindTexture(guiTexture);
		drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize);
	}
}
