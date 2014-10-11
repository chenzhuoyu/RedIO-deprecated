package com.magicbox.redio.renderers;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.EntityRenderer;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.IBlockAccess;

import com.magicbox.redio.common.Constants;
import com.magicbox.redio.entities.EntityBusCable;

public class RendererBusCable extends RendererGlobal
{
	private void renderBody(
		RenderBlocks renderer,
		IBlockAccess world,
		Block block,
		int texture,
		int x,
		int y,
		int z,
		float r,
		float g,
		float b,
		int facing,
		int brightness)
	{
		Tessellator tessellator = Tessellator.instance;

		tessellator.setBrightness(brightness);
		tessellator.setColorOpaque_F(r, g, b);
		renderFace(renderer, block, x, y, z, 0, facing, renderer.getBlockIcon(block, world, x, y, z, texture));

		tessellator.setBrightness(brightness);
		tessellator.setColorOpaque_F(r * 0.5f, g * 0.5f, b * 0.5f);
		renderFace(renderer, block, x, y, z, 1, facing, renderer.getBlockIcon(block, world, x, y, z, texture + 1));

		tessellator.setBrightness(brightness);
		tessellator.setColorOpaque_F(r * 0.6f, g * 0.6f, b * 0.6f);
		renderFace(renderer, block, x, y, z, 2, facing, renderer.getBlockIcon(block, world, x, y, z, texture + 2));

		tessellator.setBrightness(brightness);
		tessellator.setColorOpaque_F(r * 0.6f, g * 0.6f, b * 0.6f);
		renderFace(renderer, block, x, y, z, 3, facing, renderer.getBlockIcon(block, world, x, y, z, texture + 3));

		tessellator.setBrightness(brightness);
		tessellator.setColorOpaque_F(r * 0.8f, g * 0.8f, b * 0.8f);
		renderFace(renderer, block, x, y, z, 4, facing, renderer.getBlockIcon(block, world, x, y, z, texture + 4));

		tessellator.setBrightness(brightness);
		tessellator.setColorOpaque_F(r * 0.8f, g * 0.8f, b * 0.8f);
		renderFace(renderer, block, x, y, z, 5, facing, renderer.getBlockIcon(block, world, x, y, z, texture + 5));
	}

	@Override
	public boolean shouldRender3DInInventory(int modelId)
	{
		return false;
	}

	@Override
	public boolean renderWorldBlock(IBlockAccess world, int x, int y, int z, Block block, int modelId, RenderBlocks renderer)
	{
		TileEntity entity = world.getTileEntity(x, y, z);

		if (!(entity instanceof EntityBusCable))
			return super.renderWorldBlock(world, x, y, z, block, modelId, renderer);

		int f = block.colorMultiplier(world, x, y, z);
		int facing = world.getBlockMetadata(x, y, z) & 0x03;
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

		EntityBusCable busCable = (EntityBusCable)entity;

		// TODO: find correct texture index
		renderer.setRenderBounds(0.2d, 0.0d, 0.2d, 0.8d, 0.1d, 0.8d);
		renderBody(renderer, world, block, busCable.isCableConnected() ? 0 : 0, x, y, z, r, g, b, facing, brightness);

		if (busCable.isCableConnectedAt(Constants.BusCable.Direction.XNegative))
		{
			renderer.setRenderBounds(0.0d, 0.0d, 0.2d, 0.2d, 0.1d, 0.8d);
			renderBody(renderer, world, block, 0, x, y, z, r, g, b, facing, brightness);
		}

		if (busCable.isCableConnectedAt(Constants.BusCable.Direction.XPositive))
		{
			renderer.setRenderBounds(0.8d, 0.0d, 0.2d, 1.0d, 0.1d, 0.8d);
			renderBody(renderer, world, block, 0, x, y, z, r, g, b, facing, brightness);
		}

		if (busCable.isCableConnectedAt(Constants.BusCable.Direction.ZNegative))
		{
			renderer.setRenderBounds(0.2d, 0.0d, 0.0d, 0.8d, 0.1d, 0.2d);
			renderBody(renderer, world, block, 0, x, y, z, r, g, b, facing, brightness);
		}

		if (busCable.isCableConnectedAt(Constants.BusCable.Direction.ZPositive))
		{
			renderer.setRenderBounds(0.2d, 0.0d, 0.8d, 0.8d, 0.1d, 1.0d);
			renderBody(renderer, world, block, 0, x, y, z, r, g, b, facing, brightness);
		}

		return false;
	}
}
