package com.dazo66.fasttrading.eventhandler;

import com.dazo66.fasttrading.FastTrading;
import com.dazo66.fasttrading.client.gui.GuiMerchantOverride;
import com.dazo66.fasttrading.event.SetMerchantListEvent;
import com.dazo66.fasttrading.util.KeyLoader;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiMerchant;
import net.minecraft.entity.IMerchant;
import net.minecraft.util.text.TextComponentString;
import net.minecraftforge.client.event.GuiOpenEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent.KeyInputEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

/**
 * @author Dazo66
 */
public class FastTradingEventHandler {

    private Minecraft mc = Minecraft.getMinecraft();

    public FastTradingEventHandler() {

    }

    @SubscribeEvent
    public void onSetMerchantList(SetMerchantListEvent event) {
        if (mc.currentScreen instanceof GuiMerchantOverride) {
            GuiMerchantOverride gui = (GuiMerchantOverride) mc.currentScreen;
            gui.setMerchantRecipeList(event.list);
        }
    }

    @SubscribeEvent
    public void guiOpenEvent(GuiOpenEvent event0) {
        if (event0.getGui() instanceof GuiMerchant) {
            IMerchant iMerchant = ((GuiMerchant) event0.getGui()).getMerchant();
            GuiMerchantOverride guiMerchantOverride = new GuiMerchantOverride(mc.player.inventory, iMerchant, mc.player.world);
            event0.setGui(guiMerchantOverride);
        }

    }

    @SideOnly(Side.CLIENT)
    @SubscribeEvent
    public void onKeyInput(KeyInputEvent event) {
        if (KeyLoader.key_F4.isPressed()) {
            boolean isAuto = FastTrading.isAuto.getValue();
            FastTrading.isAuto.setValue(!FastTrading.isAuto.getValue());
            String msg = "FastTradingMod-" + (isAuto ? "ON" : "OFF");
            try {
                mc.player.sendMessage(new TextComponentString(msg));
            } catch (NullPointerException e) {
                FastTrading.logger.info(msg);
            }
        }
    }

}
