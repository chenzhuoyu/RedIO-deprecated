package com.magicbox.redio.entities;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.script.ScriptException;

import net.minecraft.block.Block;
import net.minecraft.network.Packet;

import com.magicbox.redio.common.Constants;
import com.magicbox.redio.common.Utils;
import com.magicbox.redio.network.Network;
import com.magicbox.redio.network.PacketEntityProcessorUpdate;
import com.magicbox.redio.network.PacketEntityUpdate;
import com.magicbox.redio.script.compiler.Compiler;
import com.magicbox.redio.script.engine.Interpreter;
import com.magicbox.redio.script.objects.RedBoolObject;
import com.magicbox.redio.script.objects.RedObject;
import com.magicbox.redio.script.objects.console.RedConsoleObject;

public class EntityProcessor extends EntityBase
{
	private boolean isPowered = false;
	private Interpreter interpreter = new Interpreter();
	private ExecutorService threadPool = Executors.newSingleThreadExecutor();

	public EntityProcessor()
	{
		try
		{
			interpreter.reset();
			interpreter.addBuiltins("Console", new RedConsoleObject());
			interpreter.setBytecodes(Compiler.compile("<string>", "func onPowerChanged(powered) {}"));
			interpreter.run();
		} catch (ScriptException e)
		{
			e.printStackTrace();
		}
	}

	private void invokePowerChanged()
	{
		final RedObject callable = interpreter.getObject("onPowerChanged");

		if (callable != null)
		{
			threadPool.execute(new Runnable()
			{
				@Override
				public void run()
				{
					try
					{
						callable.invoke(RedBoolObject.fromBoolean(isPowered));
					} catch (Exception e)
					{
						e.printStackTrace();
					}
				}
			});
		}
	}

	public boolean getPowered()
	{
		return isPowered;
	}

	public void setPowered(boolean powered)
	{
		if (powered != isPowered)
		{
			isPowered = powered;
			invokePowerChanged();
			worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
			Network.broadcastToClients(new PacketEntityProcessorUpdate(this));
		}
	}

	@Override
	public int getTextureIndex(int side, int meta)
	{
		return Constants.FACING_SIDE[meta][side] + (isPowered ? 0 : 6);
	}

	@Override
	public Packet getDescriptionPacket()
	{
		return Utils.toPacket(new PacketEntityProcessorUpdate(this), 0);
	}

	public void onNeighborBlockChanged(Block block)
	{
		setPowered(worldObj.isBlockIndirectlyGettingPowered(xCoord, yCoord, zCoord));
	}

	@Override
	public void handleUpdatePacket(PacketEntityUpdate packet)
	{
		setPowered(((PacketEntityProcessorUpdate)packet).getPowered());
	}
}
