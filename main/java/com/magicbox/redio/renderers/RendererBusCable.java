package com.magicbox.redio.renderers;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;

import com.magicbox.redio.entities.EntityBusCable;
import com.magicbox.redio.utils.Directions;

public class RendererBusCable extends RendererBase
{
	private static final float[] faceColors = { 0.6F, 0.6F, 0.5F, 1.0F, 0.8F, 0.8F };

	@Override
	public void renderInventoryBlock(Block block, int metadata, int modelID, RenderBlocks renderer)
	{
	}

	@Override
	public boolean renderWorldBlock(IBlockAccess blockAccess, int x, int y, int z, Block block, int modelId,
			RenderBlocks renderblocks)
	{
		super.renderWorldBlock(blockAccess, x, y, z, block, modelId, renderblocks);

		TileEntity te = blockAccess.getTileEntity(x, y, z);
		if (!(te instanceof EntityBusCable)) return true;

		EntityBusCable cable = (EntityBusCable) te;

		float th = 1f;
		float sp = (1.0F - th) / 2.0F;

		int connectivity = 0;
		int renderSide = 0;

		IIcon[] textures = new IIcon[6];

		for (int side = 0; side < 6; side++)
		{
			IIcon icon = block.getIcon(blockAccess, x, y, z, side);

			if (icon != null) textures[side] = icon;
			else
			{
				textures[side] = getMissingIcon(TextureMap.locationBlocksTexture);
			}
		}

		Tessellator tessellator = Tessellator.instance;

		tessellator.setBrightness(block.getMixedBrightnessForBlock(blockAccess, x, y, z));

		if (connectivity == 0)
		{
			renderblocks.setRenderBounds(sp, sp, sp, sp + th, sp + th, sp + th);

			for (Directions face : Directions.directions)
				renderFace(tessellator, renderblocks, block, x, y, z, textures, face);
		}
		else
		{
			boolean centerRendered = false;

			for (Directions dir : Directions.directions)
			{
				int dirIndex = dir.ordinal();
				if (dirIndex % 2 == 0)
				{
					int mask = 3 << dirIndex;
					if ((connectivity & mask) == mask)
					{
						float[] dim = { sp, sp, sp, sp + th, sp + th, sp + th };

						dim[(dirIndex / 2)] = 0.0F;
						dim[(dirIndex / 2 + 3)] = 1.0F;

						renderblocks.setRenderBounds(dim[0], dim[1], dim[2], dim[3], dim[4], dim[5]);

						for (Directions face : Directions.directions)
						{
							if ((face.ordinal() / 2 != dirIndex / 2) || ((renderSide & 1 << face.ordinal()) != 0))
							{
								renderFace(tessellator, renderblocks, block, x, y, z, textures, face);
							}
						}
						connectivity &= (mask ^ 0xFFFFFFFF);
						centerRendered = true;
					}
				}
			}
			for (Directions dir : Directions.directions)
			{
				int dirIndex = dir.ordinal();
				int mask = 1 << dirIndex;
				if ((connectivity & mask) != 0)
				{
					float[] dim = { sp, sp, sp, sp + th, sp + th, sp + th };

					float min = centerRendered ? sp + th : sp;
					float max = centerRendered ? sp : sp + th;

					dim[(dirIndex / 2)] = (dirIndex % 2 == 0 ? 0.0F : min);
					dim[(dirIndex / 2 + 3)] = (dirIndex % 2 == 0 ? max : 1.0F);

					renderblocks.setRenderBounds(dim[0], dim[1], dim[2], dim[3], dim[4], dim[5]);

					for (Directions face : Directions.directions)
					{
						if ((face != dir) || ((renderSide & mask) != 0))
						{
							renderFace(tessellator, renderblocks, block, x, y, z, textures, face);
						}
					}

					centerRendered = true;
				}
			}
		}
		renderblocks.setRenderBounds(0.0D, 0.0D, 0.0D, 1.0D, 1.0D, 1.0D);

		return true;
	}

	@Override
	public boolean shouldRender3DInInventory(int modelId)
	{
		return false;
	}

	private static void renderFace(Tessellator tessellator, RenderBlocks renderBlocks, Block block, int x, int y,
			int z, IIcon[] textures, Directions face)
	{
		int dirIndex = face.ordinal();
		tessellator.setColorOpaque_F(faceColors[dirIndex], faceColors[dirIndex], faceColors[dirIndex]);

		switch (face.ordinal())
		{
			case 1:
				renderBlocks.renderFaceXNeg(block, x, y, z, textures[dirIndex]);
			break;
			case 2:
				renderBlocks.renderFaceXPos(block, x, y, z, textures[dirIndex]);
			break;
			case 3:
				renderBlocks.renderFaceYNeg(block, x, y, z, textures[dirIndex]);
			break;
			case 4:
				renderBlocks.renderFaceYPos(block, x, y, z, textures[dirIndex]);
			break;
			case 5:
				renderBlocks.renderFaceZNeg(block, x, y, z, textures[dirIndex]);
			break;
			case 6:
				renderBlocks.renderFaceZPos(block, x, y, z, textures[dirIndex]);
		}
	}
}
