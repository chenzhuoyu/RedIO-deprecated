package com.magicbox.redio.emulator;

import com.magicbox.redio.entities.EntityProcessor;
import com.magicbox.redio.script.objects.RedObject;

public class RedScriptBridgeBaseMethod extends RedObject
{
	protected final EntityProcessor processor;

	public RedScriptBridgeBaseMethod(EntityProcessor processor)
	{
		this.processor = processor;
	}
}
