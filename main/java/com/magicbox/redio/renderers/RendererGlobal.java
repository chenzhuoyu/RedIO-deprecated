package com.magicbox.redio.renderers;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.EntityRenderer;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;

import org.lwjgl.opengl.GL11;

import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler;
import cpw.mods.fml.client.registry.RenderingRegistry;

public class RendererGlobal implements ISimpleBlockRenderingHandler
{
	private final int renderId = RenderingRegistry.getNextAvailableRenderId();

	protected void renderFace(RenderBlocks renderer, Block block, double x, double y, double z, int face, IIcon texture)
	{
		if (renderer.hasOverrideBlockTexture())
			texture = renderer.overrideBlockTexture;

		Tessellator tessellator = Tessellator.instance;

		double minX = x + renderer.renderMinX;
		double maxX = x + renderer.renderMaxX;
		double minY = y + renderer.renderMinY;
		double maxY = y + renderer.renderMaxY;
		double minZ = z + renderer.renderMinZ;
		double maxZ = z + renderer.renderMaxZ;

		double minUx = texture.getInterpolatedU(renderer.renderMinX * 16.0d);
		double maxUx = texture.getInterpolatedU(renderer.renderMaxX * 16.0d);
		double minUz = texture.getInterpolatedU(renderer.renderMinZ * 16.0d);
		double maxUz = texture.getInterpolatedU(renderer.renderMaxZ * 16.0d);
		double minVz = texture.getInterpolatedV(renderer.renderMinZ * 16.0d);
		double maxVz = texture.getInterpolatedV(renderer.renderMaxZ * 16.0d);
		double minVy = texture.getInterpolatedV(16.0d - renderer.renderMinY * 16.0d);
		double maxVy = texture.getInterpolatedV(16.0d - renderer.renderMaxY * 16.0d);

		switch (face)
		{
			case 0:
			{
				tessellator.addVertexWithUV(minX, maxY, minZ, minUx, minVz);
				tessellator.addVertexWithUV(minX, maxY, maxZ, minUx, maxVz);
				tessellator.addVertexWithUV(maxX, maxY, maxZ, maxUx, maxVz);
				tessellator.addVertexWithUV(maxX, maxY, minZ, maxUx, minVz);
				break;
			}

			case 1:
			{
				tessellator.addVertexWithUV(minX, minY, maxZ, maxUx, maxVz);
				tessellator.addVertexWithUV(minX, minY, minZ, maxUx, minVz);
				tessellator.addVertexWithUV(maxX, minY, minZ, minUx, minVz);
				tessellator.addVertexWithUV(maxX, minY, maxZ, minUx, maxVz);
				break;
			}

			case 2:
			{
				tessellator.addVertexWithUV(maxX, minY, minZ, maxUz, minVy);
				tessellator.addVertexWithUV(maxX, maxY, minZ, maxUz, maxVy);
				tessellator.addVertexWithUV(maxX, maxY, maxZ, minUz, maxVy);
				tessellator.addVertexWithUV(maxX, minY, maxZ, minUz, minVy);
				break;
			}

			case 3:
			{
				tessellator.addVertexWithUV(minX, maxY, minZ, minUz, maxVy);
				tessellator.addVertexWithUV(minX, minY, minZ, minUz, minVy);
				tessellator.addVertexWithUV(minX, minY, maxZ, maxUz, minVy);
				tessellator.addVertexWithUV(minX, maxY, maxZ, maxUz, maxVy);
				break;
			}

			case 4:
			{
				tessellator.addVertexWithUV(minX, minY, minZ, maxUx, minVy);
				tessellator.addVertexWithUV(minX, maxY, minZ, maxUx, maxVy);
				tessellator.addVertexWithUV(maxX, maxY, minZ, minUx, maxVy);
				tessellator.addVertexWithUV(maxX, minY, minZ, minUx, minVy);
				break;
			}

			case 5:
			{
				tessellator.addVertexWithUV(minX, maxY, maxZ, minUx, maxVy);
				tessellator.addVertexWithUV(minX, minY, maxZ, minUx, minVy);
				tessellator.addVertexWithUV(maxX, minY, maxZ, maxUx, minVy);
				tessellator.addVertexWithUV(maxX, maxY, maxZ, maxUx, maxVy);
				break;
			}
		}
	}

	@Override
	public int getRenderId()
	{
		return renderId;
	}

	@Override
	public boolean shouldRender3DInInventory(int modelId)
	{
		return true;
	}

	@Override
	public boolean renderWorldBlock(IBlockAccess world, int x, int y, int z, Block block, int modelId, RenderBlocks renderer)
	{
		int f = block.colorMultiplier(world, x, y, z);
		int brightness = block.getMixedBrightnessForBlock(world, x, y, z);

		float b = (f >> 0 & 0xFF) / 255.0f;
		float g = (f >> 8 & 0xFF) / 255.0f;
		float r = (f >> 16 & 0xFF) / 255.0f;

		if (EntityRenderer.anaglyphEnable)
		{
			r = (r * 30.0f + g * 59.0f + b * 11.0f) / 100.0f;
			g = (r * 30.0f + g * 70.0f) / 100.0f;
			b = (r * 30.0f + b * 70.0f) / 100.0f;
		}

		Tessellator tessellator = Tessellator.instance;

		tessellator.setBrightness(brightness);
		tessellator.setColorOpaque_F(r, g, b);
		renderFace(renderer, block, x, y, z, 0, renderer.getBlockIcon(block, world, x, y, z, 0));

		tessellator.setBrightness(brightness);
		tessellator.setColorOpaque_F(r * 0.5f, g * 0.5f, b * 0.5f);
		renderFace(renderer, block, x, y, z, 1, renderer.getBlockIcon(block, world, x, y, z, 1));

		tessellator.setBrightness(brightness);
		tessellator.setColorOpaque_F(r * 0.6f, g * 0.6f, b * 0.6f);
		renderFace(renderer, block, x, y, z, 2, renderer.getBlockIcon(block, world, x, y, z, 2));

		tessellator.setBrightness(brightness);
		tessellator.setColorOpaque_F(r * 0.6f, g * 0.6f, b * 0.6f);
		renderFace(renderer, block, x, y, z, 3, renderer.getBlockIcon(block, world, x, y, z, 3));

		tessellator.setBrightness(brightness);
		tessellator.setColorOpaque_F(r * 0.8f, g * 0.8f, b * 0.8f);
		renderFace(renderer, block, x, y, z, 4, renderer.getBlockIcon(block, world, x, y, z, 4));

		tessellator.setBrightness(brightness);
		tessellator.setColorOpaque_F(r * 0.8f, g * 0.8f, b * 0.8f);
		renderFace(renderer, block, x, y, z, 5, renderer.getBlockIcon(block, world, x, y, z, 5));

		return false;
	}

	@Override
	public void renderInventoryBlock(Block block, int metadata, int modelId, RenderBlocks renderer)
	{
		Tessellator tessellator = Tessellator.instance;

		block.setBlockBoundsForItemRender();
		renderer.setRenderBoundsFromBlock(block);

		GL11.glRotatef(-90.0f, 0.0f, 1.0f, 0.0f);
		GL11.glTranslatef(-0.5f, -0.5f, -0.5f);

		tessellator.startDrawingQuads();

		tessellator.setNormal(0.0f, 1.0f, 0.0f);
		renderFace(renderer, block, 0.0d, 0.0d, 0.0d, 0, renderer.getBlockIconFromSideAndMetadata(block, 0, metadata));

		tessellator.setNormal(0.0f, -1.0f, 0.0f);
		renderFace(renderer, block, 0.0d, 0.0d, 0.0d, 1, renderer.getBlockIconFromSideAndMetadata(block, 1, metadata));

		tessellator.setNormal(-1.0f, 0.0f, 0.0f);
		renderFace(renderer, block, 0.0d, 0.0d, 0.0d, 2, renderer.getBlockIconFromSideAndMetadata(block, 2, metadata));

		tessellator.setNormal(1.0f, 0.0f, 0.0f);
		renderFace(renderer, block, 0.0d, 0.0d, 0.0d, 3, renderer.getBlockIconFromSideAndMetadata(block, 3, metadata));

		tessellator.setNormal(0.0f, 0.0f, -1.0f);
		renderFace(renderer, block, 0.0d, 0.0d, 0.0d, 4, renderer.getBlockIconFromSideAndMetadata(block, 4, metadata));

		tessellator.setNormal(0.0f, 0.0f, 1.0f);
		renderFace(renderer, block, 0.0d, 0.0d, 0.0d, 5, renderer.getBlockIconFromSideAndMetadata(block, 5, metadata));

		tessellator.draw();
		GL11.glTranslatef(0.5f, 0.5f, 0.5f);
	}
}
