package com.dazo66.prompt.util;

import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.PositionedSound;
import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.init.SoundEvents;
import net.minecraft.util.SoundCategory;

/**
 * @author Dazo66
 */
public class PromptUtils {

    private static Minecraft mc = Minecraft.getMinecraft();

    public static void playDingSound() {
        PositionedSound sound1 = new PositionedSoundRecord(SoundEvents.ENTITY_ARROW_HIT_PLAYER, SoundCategory.PLAYERS, 0.18f, 0.45f, (float) mc.player.posX, (float) mc.player.posY, (float) mc.player.posZ);
        mc.getSoundHandler().playSound(sound1);
    }

}
