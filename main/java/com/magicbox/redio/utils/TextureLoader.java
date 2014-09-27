/*
 * This class is borrowed from IC2_exp
 */

package com.magicbox.redio.utils;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.resources.IResource;
import net.minecraft.client.resources.IResourceManager;
import net.minecraft.client.resources.data.AnimationMetadataSection;
import net.minecraft.util.IIcon;
import net.minecraft.util.ResourceLocation;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class TextureLoader extends TextureAtlasSprite
{
	private int iconIndex;
	private int mipmapLevels;

	private BufferedImage comparisonImage;
	private TextureAtlasSprite mappedTexture;
	private AnimationMetadataSection animationMeta;

	private static Map<String, CacheEntry> cachedImages = new HashMap();
	private static Map<Integer, List<TextureLoader>> existingTextures = new HashMap();

	private TextureLoader(String name, int iconIndex)
	{
		super(name);
		this.iconIndex = iconIndex;
	}

	public static IIcon registerTexture(IIconRegister register, String textureName, int index)
	{
		String name = textureName + ":" + index;
		TextureAtlasSprite result = new TextureLoader(name, index);

		((TextureMap)register).setTextureEntry(name, result);
		return result;
	}

	@Override
	public void copyFrom(TextureAtlasSprite textureSprite)
	{
		super.copyFrom(mappedTexture != null && textureSprite.getIconName().equals("missingno") ? mappedTexture : textureSprite);
	}

	@Override
	public void updateAnimation()
	{
		;
	}

	@Override
	public boolean hasCustomLoader(IResourceManager manager, ResourceLocation location)
	{
		return true;
	}

	@Override
	public boolean load(IResourceManager manager, ResourceLocation location)
	{
		String name = location.getResourcePath();

		if (name.indexOf(':') != -1)
			location = new ResourceLocation(location.getResourceDomain(), name.substring(0, name.indexOf(':')));

		location = new ResourceLocation(location.getResourceDomain(), "textures/blocks/" + location.getResourcePath() + ".png");

		Field mipmapLevel;
		Field anisotropicFiltering;
		try
		{
			mipmapLevel = TextureMap.class.getDeclaredField("field_147636_j");
			anisotropicFiltering = TextureMap.class.getDeclaredField("field_147637_k");
		} catch (NoSuchFieldException e)
		{
			try
			{
				mipmapLevel = TextureMap.class.getDeclaredField("mipmapLevels");
				anisotropicFiltering = TextureMap.class.getDeclaredField("anisotropicFiltering");
			} catch (NoSuchFieldException f)
			{
				throw new RuntimeException(f);
			}
		}

		mipmapLevel.setAccessible(true);
		anisotropicFiltering.setAccessible(true);
		try
		{
			mipmapLevels = mipmapLevel.getInt(Minecraft.getMinecraft().getTextureMapBlocks());
		} catch (Exception e)
		{
			throw new RuntimeException(e);
		}

		try
		{
			return loadSubImage(manager.getResource(location));
		} catch (IOException e)
		{
			e.printStackTrace();
		}
		return true;
	}

	public boolean loadSubImage(IResource res) throws IOException
	{
		String name = getIconName();

		CacheEntry cacheEntry = cachedImages.get(name);
		BufferedImage bufferedImage;
		if (cacheEntry != null)
		{
			bufferedImage = cacheEntry.image;
			animationMeta = cacheEntry.animationMeta;
		}
		else
		{
			bufferedImage = ImageIO.read(res.getInputStream());
			animationMeta = (AnimationMetadataSection)res.getMetadata("animation");

			cachedImages.put(name, new CacheEntry(bufferedImage, animationMeta));
		}

		int animationLength = 1;

		if (animationMeta != null && animationMeta.getFrameHeight() > 0)
		{
			animationLength = animationMeta.getFrameHeight();
			try
			{
				Field parentAnimationMeta = TextureAtlasSprite.class.getDeclaredField("field_110982_k");
				parentAnimationMeta.setAccessible(true);
				parentAnimationMeta.set(this, animationMeta);
			} catch (Exception e)
			{
				throw new RuntimeException(e);
			}
		}
		else
		{
			animationMeta = null;
		}

		int size = bufferedImage.getHeight() / animationLength;
		int count = bufferedImage.getWidth() / size;
		int index = iconIndex;

		if (count == 1 || count == 6 || count == 12)
			index %= count;
		else if (count == 2)
			index /= 6;
		else
			throw new IOException();

		width = size;
		height = size;

		return loadFrames(bufferedImage, index, animationLength);
	}

	public IIcon getRealTexture()
	{
		return mappedTexture == null ? this : mappedTexture;
	}

	private boolean loadFrames(BufferedImage image, int index, int animationLength)
	{
		assert animationLength > 0;

		int totalHeight = height * animationLength;
		int [] rgbaData = new int [width * totalHeight];

		comparisonImage = image.getSubimage(index * width, 0, width, totalHeight);
		comparisonImage.getRGB(0, 0, width, totalHeight, rgbaData, 0, width);

		int hash = Arrays.hashCode(rgbaData);

		List<TextureLoader> matchingTextures = existingTextures.get(Integer.valueOf(hash));

		if (matchingTextures != null)
		{
			int [] rgbaData2 = new int [width * totalHeight];

			for (TextureLoader matchingTexture : matchingTextures)
			{
				if (matchingTexture.width == width && matchingTexture.comparisonImage.getHeight() == totalHeight)
				{
					matchingTexture.comparisonImage.getRGB(0, 0, width, totalHeight, rgbaData2, 0, width);

					if (Arrays.equals(rgbaData, rgbaData2))
					{
						mappedTexture = matchingTexture;
						return true;
					}
				}
			}

			matchingTextures.add(this);
		}
		else
		{
			matchingTextures = new ArrayList();
			matchingTextures.add(this);

			existingTextures.put(Integer.valueOf(hash), matchingTextures);
		}

		int ppf = width * height;
		Method fixTransparentPixels;
		Method prepareAnisotropicFiltering;

		try
		{
			fixTransparentPixels = TextureAtlasSprite.class.getDeclaredMethod("func_147961_a", int [][].class);
			prepareAnisotropicFiltering =
				TextureAtlasSprite.class.getDeclaredMethod("func_147960_a", int [][].class, Integer.TYPE, Integer.TYPE);
		} catch (NoSuchMethodException e)
		{
			try
			{
				fixTransparentPixels = TextureAtlasSprite.class.getDeclaredMethod("fixTransparentPixels", int [][].class);
				prepareAnisotropicFiltering =
					TextureAtlasSprite.class.getDeclaredMethod("prepareAnisotropicFiltering", int [][].class, Integer.TYPE, Integer.TYPE);
			} catch (NoSuchMethodException f)
			{
				throw new RuntimeException(f);
			}
		}

		fixTransparentPixels.setAccessible(true);
		prepareAnisotropicFiltering.setAccessible(true);

		if (animationMeta != null && animationMeta.getFrameCount() > 0)
			for (Iterator it = animationMeta.getFrameIndexSet().iterator(); it.hasNext();)
			{
				Integer frameIndex = (Integer)it.next();

				if (frameIndex.intValue() >= animationLength)
					throw new RuntimeException("invalid frame index: " + frameIndex + " (" + getIconName() + ")");

				while (framesTextureData.size() <= frameIndex.intValue())
					framesTextureData.add(null);

				int [][] data = new int [1 + mipmapLevels] [];
				data[0] = Arrays.copyOfRange(rgbaData, frameIndex.intValue() * ppf, (frameIndex.intValue() + 1) * ppf);

				try
				{
					fixTransparentPixels.invoke(this, new Object []
					{
						data
					});
				} catch (Exception e)
				{
					throw new RuntimeException(e);
				}

				framesTextureData.set(frameIndex.intValue(), data);
			}
		else
		{
			for (int i = 0; i < animationLength; i++)
			{
				int [][] data = new int [1 + mipmapLevels] [];
				data[0] = Arrays.copyOfRange(rgbaData, i * ppf, (i + 1) * ppf);

				try
				{
					fixTransparentPixels.invoke(this, new Object []
					{
						data
					});
				} catch (Exception e)
				{
					throw new RuntimeException(e);
				}

				framesTextureData.add(data);
			}
		}

		return false;
	}

	public static void onPostStitch()
	{
		for (List<TextureLoader> textures : existingTextures.values())
		{
			for (TextureLoader texture : textures)
			{
				texture.comparisonImage = null;
			}

		}

		cachedImages.clear();
		existingTextures.clear();
	}

	static class CacheEntry
	{
		final BufferedImage image;
		final AnimationMetadataSection animationMeta;

		CacheEntry(BufferedImage image1, AnimationMetadataSection animationMeta1)
		{
			image = image1;
			animationMeta = animationMeta1;
		}
	}
}
