package eureka.networking;

import io.netty.buffer.ByteBuf;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
/**
 * Copyright (c) 2014-2015, AEnterprise
 * http://buildcraftadditions.wordpress.com/
 * Buildcraft Additions is distributed under the terms of GNU GPL v3.0
 * Please check the contents of the license located in
 * http://buildcraftadditions.wordpress.com/wiki/licensing-stuff/
 */
public class MessageBookSave implements IMessage, IMessageHandler<MessageBookSave, IMessage> {
	public int categoryOffset, chapterOffset, page, category, chapter;

	public MessageBookSave() {
	}

	public MessageBookSave(int categoryOffset, int chapteroffset, int page, int category, int chapter) {
		this.categoryOffset = categoryOffset;
		this.chapterOffset = chapteroffset;
		this.page = page;
		this.category = category;
		this.chapter = chapter;
	}

	@Override
	public void fromBytes(ByteBuf buf) {
		categoryOffset = buf.readInt();
		chapterOffset = buf.readInt();
		page = buf.readInt();
		category = buf.readInt();
		chapter = buf.readInt();
	}

	@Override
	public void toBytes(ByteBuf buf) {
		buf.writeInt(categoryOffset);
		buf.writeInt(chapterOffset);
		buf.writeInt(page);
		buf.writeInt(category);
		buf.writeInt(chapter);
	}

	@Override
	public IMessage onMessage(MessageBookSave message, MessageContext ctx) {
		ItemStack stack = ctx.getServerHandler().playerEntity.getCurrentEquippedItem();
		if (stack != null) {
			if (stack.stackTagCompound == null)
				stack.stackTagCompound = new NBTTagCompound();
			NBTTagCompound tag = stack.stackTagCompound;
			tag.setInteger("categoryOffset", message.categoryOffset);
			tag.setInteger("chapterOffset", message.chapterOffset);
			tag.setInteger("page", message.page);
			tag.setInteger("category", message.category);
			tag.setInteger("chapter", message.chapter);
		}
		return null;
	}
}
