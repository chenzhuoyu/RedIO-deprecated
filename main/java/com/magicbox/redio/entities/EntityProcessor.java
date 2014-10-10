package com.magicbox.redio.entities;

import java.util.HashMap;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.script.ScriptException;

import net.minecraft.nbt.NBTTagCompound;

import com.magicbox.redio.common.Constants;
import com.magicbox.redio.common.Utils;
import com.magicbox.redio.emulator.IPacketRouterNode;
import com.magicbox.redio.emulator.RedScriptBridge;
import com.magicbox.redio.network.Network;
import com.magicbox.redio.network.packets.PacketEntityProcessorUpdate;
import com.magicbox.redio.network.packets.PacketEntityUpdateBase;
import com.magicbox.redio.script.compiler.Compiler;
import com.magicbox.redio.script.engine.BytecodeBuffer;
import com.magicbox.redio.script.engine.Interpreter;
import com.magicbox.redio.script.objects.RedObject;
import com.magicbox.redio.script.objects.console.RedConsoleObject;

public class EntityProcessor extends EntityBase implements IPacketRouterNode
{
	private String name = "";
	private double heatValue = 0.0d;
	private boolean isDamaged = false;
	private boolean isPowered = false;

	private final Random random = new Random();
	private final Interpreter interpreter = new Interpreter();
	private final ExecutorService threadPool = Executors.newSingleThreadExecutor();
	private static final HashMap<String, BytecodeBuffer> compiledBytecodes = new HashMap<String, BytecodeBuffer>();

	public String getName()
	{
		return name;
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

	public void setName(String name)
	{
		this.name = name;
	}

	public void setDamaged(boolean isDamaged)
	{
		int metadata = worldObj.getBlockMetadata(xCoord, yCoord, zCoord) & 0x03;

		this.isDamaged = isDamaged;
		worldObj.setBlockMetadataWithNotify(xCoord, yCoord, zCoord, metadata | (isDamaged ? 0x04 : 0x00), 3);
	}

	public void setPowered(boolean isPowered)
	{
		this.isPowered = isPowered;
	}

	public void setHeatValue(double heatValue)
	{
		this.heatValue = heatValue;

		if (this.heatValue < 0.0d)
			this.heatValue = 0.0d;
	}

	public boolean loadScript(String filename, String script)
	{
		interpreter.reset();
		interpreter.addBuiltins("RedIO", new RedScriptBridge());
		interpreter.addBuiltins("Console", new RedConsoleObject());

		if (compiledBytecodes.containsKey(filename))
		{
			interpreter.setBytecodes(compiledBytecodes.get(filename));
		}
		else
		{
			try
			{
				interpreter.setBytecodes(Compiler.compile(filename, script));
			} catch (ScriptException e)
			{
				e.printStackTrace();
				return false;
			}
		}

		interpreter.run();
		return true;
	}

	@Override
	public void writeToNBT(NBTTagCompound nbt)
	{
		super.writeToNBT(nbt);
		nbt.setString("name", name);
		nbt.setDouble("heatValue", heatValue);
		nbt.setBoolean("isDamaged", isDamaged);
	}

	@Override
	public void readFromNBT(NBTTagCompound nbt)
	{
		super.readFromNBT(nbt);
		name = nbt.getString("name");
		heatValue = nbt.getDouble("heatValue");
		isDamaged = nbt.getBoolean("isDamaged");
	}

	@Override
	public int getTextureIndex(int side, int meta)
	{
		return Constants.FACING_SIDE[meta & 0x03][side] + (isPowered ? 0 : 6) + (!isDamaged ? 0 : 12);
	}

	@Override
	public void updateClientEntity()
	{
		if (heatValue >= 60.0d)
		{
			double x = xCoord + random.nextDouble();
			double z = zCoord + random.nextDouble();
			worldObj.spawnParticle("largesmoke", x, yCoord, z, 0.0d, 0.05d, 0.0d);
		}
	}

	@Override
	public void updateServerEntity()
	{
		setPowered(Utils.isEntityPowered(this));
		setHeatValue(heatValue + (isPowered ? isDamaged ? 1.0d : 0.5d : -0.2d));

		if (heatValue >= 80.0d)
			setDamaged(true);

		if (heatValue < 100.0d)
			Network.broadcastToClients(new PacketEntityProcessorUpdate(this));
		else
			worldObj.createExplosion(null, xCoord + 0.5d, yCoord + 0.5d, zCoord + 0.5d, 2.0f, true);
	}

	@Override
	public void handleUpdatePacket(PacketEntityUpdateBase packet)
	{
		PacketEntityProcessorUpdate updates = (PacketEntityProcessorUpdate)packet;

		setName(updates.getName());
		setDamaged(updates.getDamaged());
		setPowered(updates.getPowered());
		setHeatValue(updates.getHeatValue());
		worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
	}

	@Override
	public boolean dispatchPacket(String destination, RedObject packet)
	{
		if (!name.equals(destination))
			return false;

		return false;
	}
}
