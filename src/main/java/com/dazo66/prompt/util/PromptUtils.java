package com.dazo66.prompt.util;

import com.dazo66.fasttrading.client.audio.FakeSubtitleSound;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.ISound;
import net.minecraft.client.audio.PositionedSound;
import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.init.SoundEvents;
import net.minecraft.util.SoundCategory;

/**
 * @author Dazo66
 */
public class PromptUtils {

    private static Minecraft mc = Minecraft.getMinecraft();

    public static void playDingSound(String subtitle) {
        ISound sound = FakeSubtitleSound.getRecord(SoundEvents.ENTITY_ARROW_HIT_PLAYER, 0.5f, 0.05F, ISound.AttenuationType.LINEAR, (float) mc.player.posX, (float) mc.player.posY, (float) mc.player.posZ, subtitle);
        mc.getSoundHandler().playSound(sound);
    }

}
