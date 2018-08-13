package com.dazo66.prompt.subfunction.fishingprompt.eventhandler;

import net.minecraftforge.client.event.sound.PlaySoundEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import static com.dazo66.prompt.util.PromptUtils.playDingSound;

/**
 * @author Dazo66
 */
public class FishingPromptEventHandler {

    @SubscribeEvent
    public void onSoundPlay(PlaySoundEvent event) {
        if (event.getName().equals("entity.bobber.splash")) {
            playDingSound();
        }
    }
}
