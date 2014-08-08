package eureka.network;

import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import eureka.core.EurekaKnowledge;
import eureka.core.EurekaRegistry;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;

/**
 * Copyright (c) 2014, AEnterprise
 * http://buildcraftadditions.wordpress.com/
 * Buildcraft Additions is distributed under the terms of the Minecraft Mod Public
 * License 1.0, or MMPL. Please check the contents of the license located in
 * http://buildcraftadditions.wordpress.com/wiki/licensing-stuff/
 */
public class EurekaMessage implements IMessage, IMessageHandler<EurekaMessage, IMessage> {
	public int progress, keyNumber;
	public boolean isFinished;
	public EntityPlayer player;

	public EurekaMessage(){}

	public EurekaMessage(EntityPlayer player, String key){
		this.player = player;
		this.keyNumber = EurekaRegistry.getKeys().indexOf(key);
		this.progress = EurekaKnowledge.getProgress(player, key);
		this.isFinished = EurekaKnowledge.isFinished(player, key);
	}

	@Override
	public void fromBytes(ByteBuf buf) {
		this.keyNumber = buf.readInt();
		this.progress = buf.readInt();
		this.isFinished = buf.readBoolean();
	}

	@Override
	public void toBytes(ByteBuf buf) {
		buf.writeInt(keyNumber);
		buf.writeInt(progress);
		buf.writeBoolean(isFinished);
	}

	@Override
	public IMessage onMessage(EurekaMessage message, MessageContext ctx) {
		EntityPlayer player = FMLClientHandler.instance().getClient().thePlayer;
		NBTTagCompound tag = EurekaKnowledge.getTag(player);
		EurekaKnowledge.setKey(tag, EurekaRegistry.getKeys().get(message.keyNumber) + "Progress", message.progress);
		EurekaKnowledge.setKey(tag, EurekaRegistry.getKeys().get(message.keyNumber) + "Finished", message.isFinished);
		return null;
	}
}
