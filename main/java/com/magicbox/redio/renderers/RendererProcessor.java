package com.magicbox.redio.renderers;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.Tessellator;

import org.lwjgl.opengl.GL11;

public class RendererProcessor extends RendererGlobal
{
	@Override
	public void renderInventoryBlock(Block block, int metadata, int modelId, RenderBlocks renderer)
	{
		int texture = metadata == 0 ? 6 : 18;
		Tessellator tessellator = Tessellator.instance;

		block.setBlockBoundsForItemRender();
		renderer.setRenderBoundsFromBlock(block);

		GL11.glRotatef(-90.0f, 0.0f, 1.0f, 0.0f);
		GL11.glTranslatef(-0.5f, -0.5f, -0.5f);

		tessellator.startDrawingQuads();

		tessellator.setNormal(0.0f, 1.0f, 0.0f);
		renderFace(renderer, block, 0.0d, 0.0d, 0.0d, 0, 0, renderer.getBlockIconFromSideAndMetadata(block, texture, -1));

		tessellator.setNormal(0.0f, -1.0f, 0.0f);
		renderFace(renderer, block, 0.0d, 0.0d, 0.0d, 1, 0, renderer.getBlockIconFromSideAndMetadata(block, texture + 1, -1));

		tessellator.setNormal(-1.0f, 0.0f, 0.0f);
		renderFace(renderer, block, 0.0d, 0.0d, 0.0d, 2, 0, renderer.getBlockIconFromSideAndMetadata(block, texture + 2, -1));

		tessellator.setNormal(1.0f, 0.0f, 0.0f);
		renderFace(renderer, block, 0.0d, 0.0d, 0.0d, 3, 0, renderer.getBlockIconFromSideAndMetadata(block, texture + 3, -1));

		tessellator.setNormal(0.0f, 0.0f, -1.0f);
		renderFace(renderer, block, 0.0d, 0.0d, 0.0d, 4, 0, renderer.getBlockIconFromSideAndMetadata(block, texture + 4, -1));

		tessellator.setNormal(0.0f, 0.0f, 1.0f);
		renderFace(renderer, block, 0.0d, 0.0d, 0.0d, 5, 0, renderer.getBlockIconFromSideAndMetadata(block, texture + 5, -1));

		tessellator.draw();
		GL11.glTranslatef(0.5f, 0.5f, 0.5f);
	}
}
