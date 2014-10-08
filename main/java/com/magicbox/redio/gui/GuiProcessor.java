package com.magicbox.redio.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.inventory.Container;
import net.minecraft.util.ResourceLocation;

import com.magicbox.redio.common.Constants;

public class GuiProcessor extends GuiContainer
{
	public static ResourceLocation guiTexture = Constants.getGuiTextureLocation(Constants.Processor.BLOCK_NAME);

	public GuiProcessor(Container par1Container)
	{
		super(par1Container);
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float var1, int var2, int var3)
	{
		Minecraft.getMinecraft().getTextureManager().bindTexture(guiTexture);
	}
}
