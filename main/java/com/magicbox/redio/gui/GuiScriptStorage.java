package com.magicbox.redio.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;

import com.magicbox.redio.common.Constants;
import com.magicbox.redio.container.ContainerScriptStorage;
import com.magicbox.redio.entities.EntityScriptStorage;

public class GuiScriptStorage extends GuiContainer
{
	public static ResourceLocation guiTexture = Constants.getGuiTextureLocation(Constants.ScriptStorage.BLOCK_NAME);
	public EntityScriptStorage entity;

	public GuiScriptStorage(InventoryPlayer inventory, EntityScriptStorage entity)
	{
		super(new ContainerScriptStorage(inventory, entity));
		xSize = Constants.ScriptStorage.GUI_X_SIZE;
		ySize = Constants.ScriptStorage.GUI_Y_SIZE;
		this.entity = entity;
	}

	@Override
	public void initGui()
	{
		super.initGui();
		buttonList.add(new GuiButton(Constants.ScriptStorage.GUI_BUTTON_ADD_ID, 12 + Constants.Gui.OFFSET_X, 60 + Constants.Gui.OFFSET_Y,
				50, 20, "Add"));
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float var1, int var2, int var3)
	{
		Minecraft.getMinecraft().getTextureManager().bindTexture(guiTexture);
		drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize);
	}

	@Override
	public void actionPerformed(GuiButton button)
	{
		switch (button.id)
		{
			case Constants.ScriptStorage.GUI_BUTTON_ADD_ID:
				entity.addScript("hello", "world");
		}
	}
}
