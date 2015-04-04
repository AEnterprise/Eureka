package eureka.networking;

import io.netty.buffer.ByteBuf;

import net.minecraft.util.ChatComponentText;
import net.minecraft.util.ChatComponentTranslation;

import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;

import eureka.core.TextGetter;
/**
 * Copyright (c) 2014-2015, AEnterprise
 * http://buildcraftadditions.wordpress.com/
 * Buildcraft Additions is distributed under the terms of GNU GPL v3.0
 * Please check the contents of the license located in
 * http://buildcraftadditions.wordpress.com/wiki/licensing-stuff/
 */
public class MessageResearchFinished implements IMessage, IMessageHandler<MessageResearchFinished, IMessage> {
	public String key;

	public MessageResearchFinished() {
	}

	public MessageResearchFinished(String key) {
		this.key = key;
	}

	@Override
	public void fromBytes(ByteBuf buf) {
		int num = buf.readInt();
		key = "";
		for (int t = 0; t < num; t++)
			key += buf.readChar();
		key = key.trim();
	}

	@Override
	public void toBytes(ByteBuf buf) {
		buf.writeInt(key.length());
		for (char c : key.toCharArray())
			buf.writeChar(c);
	}

	@Override
	public IMessage onMessage(MessageResearchFinished message, MessageContext ctx) {
		FMLClientHandler.instance().getClient().thePlayer.addChatComponentMessage(new ChatComponentTranslation("EUREKA"));
		FMLClientHandler.instance().getClient().thePlayer.addChatComponentMessage(new ChatComponentText("Research finished: " + TextGetter.getTitle(message.key)));
		return null;
	}
}
