package com.magicbox.redio.common;

import java.util.ArrayList;
import java.util.HashMap;

import net.minecraft.block.Block;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.MathHelper;
import net.minecraftforge.oredict.ShapedOreRecipe;

public class Utils
{
	public static int getPlayerFacing(EntityLivingBase player)
	{
		return MathHelper.floor_double(player.rotationYaw * 4.0F / 360.0F + 0.5d) & 0x03;
	}

	public static void addCraftingRecipe(Block result, int count, Object... recipes)
	{
		if (recipes.length == 9)
		{
			char name = 'A';
			String recipe = "";
			ItemStack stack = new ItemStack(result, count);
			ArrayList<Object> args = new ArrayList<Object>();
			HashMap<Object, Character> map = new HashMap<Object, Character>();

			for (Object item : recipes)
			{
				if (item == null)
				{
					recipe += " ";
				}
				else
				{
					char current = name;

					if (!map.containsKey(item))
						map.put(item, name++);
					else
						current = map.get(item);

					recipe += current;
				}
			}

			args.add(recipe.substring(0, 3));
			args.add(recipe.substring(3, 6));
			args.add(recipe.substring(6, 9));

			for (Object item : map.keySet())
			{
				if (item != null)
				{
					args.add(map.get(item));
					args.add(item);
				}
			}

			CraftingManager.getInstance().getRecipeList().add(new ShapedOreRecipe(stack, args.toArray()).setMirrored(true));
		}
	}

	public static boolean isEntityPowered(TileEntity entity)
	{
		return entity.getWorldObj().isBlockIndirectlyGettingPowered(entity.xCoord, entity.yCoord, entity.zCoord);
	}
}
