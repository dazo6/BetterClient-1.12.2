package com.dazo66.shulkerboxshower.eventhandler;

import com.dazo66.shulkerboxshower.client.render.DrawItemInShulkerbox;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemShulkerBox;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.translation.I18n;
import net.minecraftforge.client.event.GuiScreenEvent;
import net.minecraftforge.client.event.RenderTooltipEvent;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Dazo66
 */
public class ShulkerBoxViewerEventHandler {

    public static ShulkerBoxViewerEventHandler instance = new ShulkerBoxViewerEventHandler();
    private DrawItemInShulkerbox drawer = new DrawItemInShulkerbox();
    private Minecraft mc = Minecraft.getMinecraft();

    @SubscribeEvent
    public void onTooltipGen(ItemTooltipEvent event) {
        if (event.getItemStack().getItem() instanceof ItemShulkerBox) {
            List<String> list = event.getToolTip();
            List<String> temp = new ArrayList<>();
            for (String s : list) {
                if (s.matches("^.*\\sx\\d+$")) {
                    temp.add(s);
                }
            }
            for (String s : temp) {
                list.remove(s);
            }
            if (list.size() < 2) {
                return;
            }
            String[] strings = I18n.translateToLocal("container.shulkerBox.more").split("%s");
            if (list.get(1).contains(strings[0]) && list.get(1).contains(strings[1])) {
                list.remove(1);
            }

        }
    }

    @SubscribeEvent
    public void afterDrawGui(GuiScreenEvent.DrawScreenEvent.Post event) {
        if (event.getGui() instanceof GuiContainer) {
            GuiContainer gui = (GuiContainer) event.getGui();
            Slot slotUnderMouse = gui.getSlotUnderMouse();
            ItemStack itemInHand = mc.player.inventory.getItemStack();
            if (null == slotUnderMouse) {
                if (!itemInHand.isEmpty() && itemInHand.getItem() instanceof ItemShulkerBox) {
                    //空格子手里有物品
                    drawer.draw(gui, itemInHand, ItemStack.EMPTY, event.getMouseX() + 10, event.getMouseY());
                }
            } else {
                if (slotUnderMouse.getHasStack()) {
                    ItemStack itemUnderMouse = slotUnderMouse.getStack();
                    if (itemUnderMouse.getItem() instanceof ItemShulkerBox) {
                        boolean flag = !itemInHand.isEmpty() && itemInHand.getItem() instanceof ItemShulkerBox;
                        if (flag) {
                            //鼠标下是潜影盒，同时手里拿着的情况
                            drawer.draw(gui, itemInHand, itemUnderMouse, event.getMouseX() + 10, event.getMouseY());
                        } else {
                            //鼠标下是潜影盒，手里没有拿着的情况
                            drawer.draw(gui, itemUnderMouse);
                        }
                    } else if (itemInHand.getItem() instanceof ItemShulkerBox) {
                        //光手里拿着的情况下，格子里有其他物品
                        drawer.draw(gui, itemInHand, ItemStack.EMPTY, event.getMouseX() + 10, event.getMouseY());
                    }
                } else if (itemInHand.getItem() instanceof ItemShulkerBox) {
                    //光手里拿着的情况下，格子没其他物品
                    drawer.draw(gui, itemInHand, ItemStack.EMPTY, event.getMouseX() + 10, event.getMouseY());
                }
            }
        }
    }

    @SubscribeEvent
    public void onTooltipRender(RenderTooltipEvent.PostBackground event) {
        //1.11中空格子itemStack为null 而不是ItemStack.EMPTY
        //没有这行的话1.11无法工作
        if (null == event.getStack()) {
            return;
        }
        if (event.getStack().getItem() instanceof ItemShulkerBox) {
            drawer.x = event.getX();
            drawer.y = event.getY();
        }
    }
}
