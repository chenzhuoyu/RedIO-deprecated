package com.magicbox.redio.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.util.ResourceLocation;

import com.magicbox.redio.common.Constants;
import com.magicbox.redio.entities.EntityProcessor;

public class GuiProcessor extends GuiContainer
{

	public static ResourceLocation guiTexture = Constants.getGuiTextureLocation(Constants.Processor.BLOCK_NAME);
	public EntityProcessor entity;

	public GuiProcessor(EntityProcessor entity)
	{
		super(null);
		xSize = Constants.Processor.GUI_X_SIZE;
		ySize = Constants.Processor.GUI_Y_SIZE;
		this.entity = entity;
	}

	@Override
	public void initGui()
	{
		super.initGui();
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float var1, int var2, int var3)
	{
		Minecraft.getMinecraft().getTextureManager().bindTexture(guiTexture);
		drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize);
	}
}
