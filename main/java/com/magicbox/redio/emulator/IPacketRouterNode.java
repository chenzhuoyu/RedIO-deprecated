package com.magicbox.redio.emulator;

import com.magicbox.redio.script.objects.RedObject;

public interface IPacketRouterNode
{
	public boolean dispatchPacket(String destination, RedObject packet);
}
