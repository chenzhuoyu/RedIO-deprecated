package com.magicbox.redio.emulator;

import com.magicbox.redio.script.objects.RedObject;

// @formatter:off
public interface IPacketRouterNode
{
	public String getNodeName();
	public RedObject dispatchPacket(IPacketRouterNode source, IPacketRouterNode previous, String destination, RedObject packet);
}
