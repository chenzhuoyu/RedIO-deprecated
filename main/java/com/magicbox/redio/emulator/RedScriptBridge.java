package com.magicbox.redio.emulator;

import com.magicbox.redio.entities.EntityProcessor;
import com.magicbox.redio.script.objects.RedObject;

public class RedScriptBridge extends RedObject
{
	public RedScriptBridge(EntityProcessor processor)
	{
		setAttribute("sendMessage", new RedScriptBridgeSendMessageMethod(processor));
	}
}
