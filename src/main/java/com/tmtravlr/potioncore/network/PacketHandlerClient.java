package com.tmtravlr.potioncore.network;

import io.netty.buffer.Unpooled;

import java.io.IOException;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.FMLLog;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

import com.tmtravlr.potioncore.PotionCore;
import com.tmtravlr.potioncore.effects.PotionDrown;
import com.tmtravlr.potioncore.potion.EntityPotionCorePotion;
import com.tmtravlr.potioncore.potion.ItemPotionCorePotion;

public class PacketHandlerClient implements IMessageHandler<SToCMessage, IMessage> {

	//Types of packets
	public static final int SET_DROWN = 1;
	public static final int KNOCK_UP = 2;
	public static final int POTION_ENTITY = 3;
	public static final int REVIVAL_HEARTS = 4;

	public IMessage onMessage(SToCMessage packet, MessageContext context)
	{
		PacketBuffer buff = new PacketBuffer(Unpooled.wrappedBuffer(packet.getData()));
		
		EntityPlayer player = PotionCore.proxy.getPlayer();
		World world = null;
		if(player != null) {
			world = player.worldObj;
		}
		
		int type = buff.readInt();

		switch(type) {
		case SET_DROWN: { 
			int air = buff.readInt();
			
			if(player != null) {
				player.getEntityData().setInteger(PotionDrown.TAG_NAME, air);
			}
			break;
		}
		case KNOCK_UP: { 
			int amount = buff.readInt();
			
			if(player != null) {
				player.motionY = amount;
			}
			break;
		}
		case POTION_ENTITY: { 
			try {
				int entityId = buff.readInt();
				ItemStack stack = buff.readItemStackFromBuffer();
				
				if(world != null) {
					Entity entity = world.getEntityByID(entityId);
					
					if(entity instanceof EntityPotionCorePotion) {
						if(stack.getItem() == null) {
							stack.setItem(ItemPotionCorePotion.instance);
						}
						((EntityPotionCorePotion) entity).potion = stack;
					}
				}
			} catch (IOException e) {
				FMLLog.severe("[Potion Core] Couldn't read potion item stack from packet!");
				e.printStackTrace();
			}
			break;
		}
		case REVIVAL_HEARTS: {
			int entityId = buff.readInt();
			
			if(world != null) {
				Entity entity = world.getEntityByID(entityId);
				
				if(entity != null) {
					for (int i = 0; i < 9; ++i)
		            {
						double offset = entity.width;
						double yOffset = entity.getEyeHeight();
						double xOffset = offset*Math.sin(i*160 / Math.PI);
						double zOffset = offset*Math.cos(i*160 / Math.PI);
						
		                entity.worldObj.spawnParticle(EnumParticleTypes.HEART, entity.posX + xOffset, entity.posY + yOffset, entity.posZ + zOffset, 0, 0, 0, new int[0]);
		            }
				}
			}
		}
		default:
			//Do nothing
		}

		//Don't return anything.
		return null;
	}
}
