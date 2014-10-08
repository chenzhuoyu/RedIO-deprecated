package com.magicbox.redio.entities;

import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.script.ScriptException;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.Packet;

import com.magicbox.redio.common.Constants;
import com.magicbox.redio.common.Utils;
import com.magicbox.redio.network.Network;
import com.magicbox.redio.network.packets.PacketEntityProcessorUpdate;
import com.magicbox.redio.network.packets.PacketEntityUpdate;
import com.magicbox.redio.script.compiler.Compiler;
import com.magicbox.redio.script.engine.Interpreter;
import com.magicbox.redio.script.objects.console.RedConsoleObject;

public class EntityProcessor extends EntityBase
{
	private double heatValue = 0.0d;
	private boolean isDamaged = false;
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
		} catch (ScriptException e)
		{
			e.printStackTrace();
		}
	}

	@Override
	protected void updateClientEntity()
	{
		if (heatValue >= 60.0d)
		{
			double x = xCoord + random.nextDouble();
			double z = zCoord + random.nextDouble();
			worldObj.spawnParticle("largesmoke", x, yCoord, z, 0.0d, 0.05d, 0.0d);
		}
	}

	@Override
	protected void updateServerEntity()
	{
		isPowered = worldObj.isBlockIndirectlyGettingPowered(xCoord, yCoord, zCoord);
		heatValue += isPowered ? isDamaged ? 1.0d : 0.5d : -0.2d;

		if (heatValue < 0.0d)
			heatValue = 0.0d;

		if (heatValue >= 80.0d)
			isDamaged = true;

		if (heatValue < 100.0d)
			Network.broadcastToClients(new PacketEntityProcessorUpdate(this));
		else
			worldObj.createExplosion(null, xCoord + 0.5d, yCoord + 0.5d, zCoord + 0.5d, 5.0f, true);
	}

	public double getHeatValue()
	{
		return heatValue;
	}

	public boolean getDamaged()
	{
		return isDamaged;
	}

	public boolean getPowered()
	{
		return isPowered;
	}

	@Override
	public void writeToNBT(NBTTagCompound nbt)
	{
		super.writeToNBT(nbt);
		nbt.setDouble("heatValue", heatValue);
		nbt.setBoolean("isDamaged", isDamaged);
	}

	@Override
	public void readFromNBT(NBTTagCompound nbt)
	{
		super.readFromNBT(nbt);
		heatValue = nbt.getDouble("heatValue");
		isDamaged = nbt.getBoolean("isDamaged");
	}

	@Override
	public int getTextureIndex(int side, int meta)
	{
		return Constants.FACING_SIDE[meta][side] + (isPowered ? 0 : 6) + (!isDamaged ? 0 : 12);
	}

	@Override
	public Packet getDescriptionPacket()
	{
		return Utils.toPacket(new PacketEntityProcessorUpdate(this), 0);
	}

	@Override
	public void handleUpdatePacket(PacketEntityUpdate packet)
	{
		PacketEntityProcessorUpdate updates = (PacketEntityProcessorUpdate)packet;

		isDamaged = updates.getDamaged();
		isPowered = updates.getPowered();
		heatValue = updates.getHeatValue();

		worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
	}
}
