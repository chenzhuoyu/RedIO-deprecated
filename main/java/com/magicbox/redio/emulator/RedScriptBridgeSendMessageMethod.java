package com.magicbox.redio.emulator;

import com.magicbox.redio.entities.EntityBusCable;
import com.magicbox.redio.entities.EntityProcessor;
import com.magicbox.redio.script.objects.RedNullObject;
import com.magicbox.redio.script.objects.RedObject;
import com.magicbox.redio.script.objects.array.RedArrayObject;
import com.magicbox.redio.script.objects.string.RedStringObject;

public class RedScriptBridgeSendMessageMethod extends RedScriptBridgeBaseMethod
{
	public RedScriptBridgeSendMessageMethod(EntityProcessor processor)
	{
		super(processor);
	}

	public RedObject __call__(RedArrayObject args)
	{
		if (args.size() != 2)
			throw new RuntimeException("RedIO.sendMessage() takes exactly 2 arguments.");

		RedObject arg0 = args.get(0);
		RedObject arg1 = args.get(1);

		if (!(arg0 instanceof RedStringObject))
			throw new RuntimeException("RedIO.sendMessage() only accepts string as its first argument.");

		for (EntityBusCable cable : processor.getConnectedCables())
		{
			RedObject result = cable.dispatchPacket(processor, processor, arg0.toString(), arg1);

			if (result != null && !result.isNull())
				return result;
		}

		return RedNullObject.nullObject;
	}
}
