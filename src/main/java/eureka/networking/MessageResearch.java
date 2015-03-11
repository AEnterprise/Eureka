package eureka.networking;

import java.util.HashMap;

import io.netty.buffer.ByteBuf;

import net.minecraft.entity.player.EntityPlayer;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;

import eureka.Eureka;
import eureka.api.EurekaAPI;
import eureka.api.IEurekaInfo;
import eureka.core.PlayerResearch;
/**
 * Copyright (c) 2014-2015, AEnterprise
 * http://buildcraftadditions.wordpress.com/
 * Eureka is distributed under the terms of GNU GPL v3.0
 * Please check the contents of the license located in
 * http://buildcraftadditions.wordpress.com/wiki/licensing-stuff/
 */
public class MessageResearch implements IMessage, IMessageHandler<MessageResearch, IMessage> {
	public PlayerResearch research;
	public HashMap<String, Integer> progress = new HashMap<String, Integer>();
	public HashMap<String, Boolean> finished = new HashMap<String, Boolean>();

	public MessageResearch() {

	}

	public MessageResearch(EntityPlayer player) {
		research = PlayerResearch.get(player);
	}

	@Override
	public void fromBytes(ByteBuf buf) {
		for (IEurekaInfo info : EurekaAPI.API.getAllKeys()) {
			progress.put(info.getName(), buf.readInt());
			finished.put(info.getName(), buf.readBoolean());
		}
	}

	@Override
	public void toBytes(ByteBuf buf) {
		for (IEurekaInfo info : EurekaAPI.API.getAllKeys()) {
			buf.writeInt(research.getProgress(info.getName()));
			buf.writeBoolean(research.isFinished(info.getName()));
		}
	}

	@Override
	public IMessage onMessage(MessageResearch message, MessageContext ctx) {
		research = PlayerResearch.get(Eureka.proxy.getPlayer());
		for (IEurekaInfo info : EurekaAPI.API.getAllKeys()) {
			research.overrideProgress(info.getName(), message.progress.get(info.getName()));
			research.overrideFinished(info.getName(), message.finished.get(info.getName()));
		}
		return null;
	}
}
