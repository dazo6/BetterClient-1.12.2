package com.dazo66.customf3.eventhandler;

import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

/**
 * @author Dazo66
 */
public class CustomF3EventHandler {

    @SubscribeEvent
    public void onOverlayDebug(RenderGameOverlayEvent.Text event) {
        event.getLeft().clear();
        event.getRight().clear();
        //TODO 覆盖新的gui上去
    }

}
