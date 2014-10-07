package com.magicbox.redio.entities;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.script.ScriptException;

import net.minecraft.block.Block;

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
			interpreter.setBytecodes(Compiler.compile("<string>", "func onPowerChanged(powered) { Console.println(powered); }"));
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

	public void setPowered(boolean powered)
	{
		if (powered != isPowered)
		{
			isPowered = powered;
			invokePowerChanged();
		}
	}

	public void onNeighborBlockChanged(Block block)
	{
		setPowered(worldObj.isBlockIndirectlyGettingPowered(xCoord, yCoord, zCoord));
	}
}
