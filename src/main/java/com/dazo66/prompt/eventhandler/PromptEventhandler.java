package com.dazo66.prompt.eventhandler;

import com.dazo66.prompt.Prompt;
import com.dazo66.prompt.render.PromptRender;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiChat;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

/**
 * @author Dazo66
 */
public class PromptEventhandler {

    private Minecraft mc = Minecraft.getMinecraft();

    @SubscribeEvent
    public void onRenderTick(TickEvent.RenderTickEvent event) {
        if(event.phase == TickEvent.Phase.END){
            if (mc.world != null) {
                EntityPlayer play = mc.player;
                if (play != null && Prompt.warnHeal != null) {
                    if (play.getHealth() / play.getMaxHealth() < Prompt.warnHeal.getValue()) {
                        if (!mc.gameSettings.hideGUI && !(mc.currentScreen instanceof GuiChat)) {
                            PromptRender.renderVignette();
                        }
                    }
                }
            }
        }
    }


}
