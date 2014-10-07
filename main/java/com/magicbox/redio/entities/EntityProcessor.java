package com.magicbox.redio.entities;

import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.script.ScriptException;

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
	private final double heatValue = 0.0d;
	private boolean isPowered = false;

	private final Random random = new Random();
	private final Interpreter interpreter = new Interpreter();
	private final ExecutorService threadPool = Executors.newSingleThreadExecutor();

	public EntityProcessor()
	{
		try
		{
			interpreter.reset();
			interpreter.addBuiltins("Console", new RedConsoleObject());
			interpreter.setBytecodes(Compiler.compile("<string>", "func onPowerChanged(powered) {}"));
			interpreter.run();
		}
		catch (ScriptException e)
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
					}
					catch (Exception e)
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
	public void updateEntity()
	{
		super.updateEntity();
		setPowered(worldObj.isBlockIndirectlyGettingPowered(xCoord, yCoord, zCoord));

		if (isPowered)
		// worldObj.createExplosion(null, xCoord + 0.5d, yCoord + 0.5d, zCoord +
		// 0.5d, 3.0f, true);

		worldObj.spawnParticle("largesmoke", xCoord + random.nextDouble(), yCoord, zCoord + random.nextDouble(), 0.0d,
				0.05d, 0.0d);
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

	@Override
	public void handleUpdatePacket(PacketEntityUpdate packet)
	{
		setPowered(((PacketEntityProcessorUpdate) packet).getPowered());
	}
}
