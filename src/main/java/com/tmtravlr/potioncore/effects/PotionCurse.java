package com.tmtravlr.potioncore.effects;

import net.minecraft.entity.EntityLivingBase;

import com.tmtravlr.potioncore.PotionCoreHelper;
import com.tmtravlr.potioncore.potion.PotionCorePotion;

/**
 * Gives you one random negative effect per level.<br><br>
 * Instant: yes<br>
 * Amplifier affects it: yes
 * 
 * @author Rebeca Rey (Tmtravlr)
 * @Date January 2016
 */
public class PotionCurse extends PotionCorePotion {

	public static final String NAME = "curse";
	public static PotionCurse instance = null;

	public static String[] blacklist = {};
	
	public PotionCurse() {
		super(NAME, true, 0x000000);
		instance = this;
    }

    @Override
    public boolean isInstant() {
        return true;
    }
    
    @Override
    public void performEffect(EntityLivingBase entity, int amplifier) {
    	for(int i = 0; i <= amplifier; i++) {
    		PotionCoreHelper.curseEntities.add(entity);
    	}
	}
}
