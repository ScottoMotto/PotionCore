package com.tmtravlr.potioncore.effects;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.world.WorldServer;

import com.tmtravlr.potioncore.potion.PotionCorePotion;

/**
 * Makes animals start breeding<br><br>
 * Instant: yes<br>
 * Amplifier affects it: no
 * 
 * @author Rebeca Rey (Tmtravlr)
 * @Date January 2016
 */
public class PotionLove extends PotionCorePotion {

	public static final String NAME = "love";
	public static PotionLove instance = null;
	
	public PotionLove() {
		super(NAME, false, 0xFF3333);
		instance = this;
    }

    @Override
    public boolean isInstant() {
        return true;
    }
    
    @Override
    public boolean canAmplify() {
		return false;
	}
    
    @Override
    public void affectEntity(Entity thrownPotion, Entity thrower, EntityLivingBase entity, int amplifier, double potency) {
    	if(entity instanceof EntityAnimal) {
    		EntityPlayer player = null;
    		
    		if(thrower instanceof EntityPlayer) {
    			player = (EntityPlayer) thrower;
    		}
    		
    		((EntityAnimal) entity).setInLove(player);
		}
		else {
			performEffect(entity, amplifier);
		}
	}
    
    @Override
    public void performEffect(EntityLivingBase entity, int amplifier) {
		if(entity instanceof EntityAnimal) {
			((EntityAnimal) entity).setInLove(null);
		}
		else {
			if(entity.worldObj instanceof WorldServer) {
				((WorldServer)entity.worldObj).spawnParticle(EnumParticleTypes.HEART, true, entity.posX, entity.posY, entity.posZ, 20, 0.5, 2, 0.5, 0.0, new int[0]);
			}
		}
	}
}
