package com.dazo66.prompt.eventhandler;

import com.dazo66.betterclient.event.GuiCloseEvent;
import com.dazo66.prompt.Prompt;
import com.dazo66.prompt.render.PromptRender;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.PositionedSound;
import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.client.gui.GuiChat;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.inventory.GuiFurnace;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.init.SoundEvents;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.client.event.sound.PlaySoundEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * @author Dazo66
 */
public class PromptEventhandler {

    private Minecraft mc = Minecraft.getMinecraft();
    private BlockPos furnacePos;
    private List<BlockPos> checkList = new ArrayList<>();

    @SubscribeEvent
    public void onClientTick(TickEvent.ClientTickEvent event) {
        String state;
        if (furnacePos != null && !checkList.contains(furnacePos)) {
            state = mc.world.getBlockState(furnacePos).toString();
            if (state.contains("lit_furnace")) {
                checkList.add(furnacePos);
            }
        }
        Iterator<BlockPos> iterable = checkList.iterator();
        while (iterable.hasNext()) {
            state = mc.world.getBlockState(iterable.next()).toString();
            if (!state.contains("furnace")) {
                iterable.remove();
            } else {
                if (!state.contains("lit")) {
                    iterable.remove();
                    playDingSound();
                }
            }
        }
    }

    @SubscribeEvent
    public void onRightClickFurnace(PlayerInteractEvent.RightClickBlock event) {
        BlockPos pos = event.getPos();
        String state = mc.world.getBlockState(pos).toString();
        if (state.contains("furnace")) {
            furnacePos = pos;
        }
    }

    @SubscribeEvent
    public void onRenderTick(TickEvent.RenderTickEvent event) {
        if (event.phase == TickEvent.Phase.END) {
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

    @SubscribeEvent
    public void onSoundPlay(PlaySoundEvent event) {
        if (event.getName().equals("entity.bobber.splash")) {
            playDingSound();
        }
    }


    private void playDingSound() {
        PositionedSound sound1 = new PositionedSoundRecord(SoundEvents.ENTITY_ARROW_HIT_PLAYER, SoundCategory.PLAYERS, 0.18f, 0.45f, (float) mc.player.posX, (float) mc.player.posY, (float) mc.player.posZ);
        mc.getSoundHandler().playSound(sound1);
    }


}
