package com.dazo66.fasttrading.util;

import com.dazo66.fasttrading.FastTrading;
import com.dazo66.fasttrading.client.gui.GuiMerchantOverride;
import com.dazo66.fasttrading.config.ConfigJson;
import com.google.common.collect.Lists;
import net.minecraft.client.Minecraft;
import net.minecraft.inventory.ClickType;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.village.MerchantRecipe;
import net.minecraft.village.MerchantRecipeList;

import java.util.HashMap;
import java.util.List;

/**
 * @author Dazo66
 */
public class TradingHelper {

    public HashMap<MerchantRecipe, ConfigJson.SimpleRecipe> map = new HashMap<>();
    private Minecraft mc;
    private Container inventorySlots;
    private GuiMerchantOverride gui;
    private Slot buy1;
    private Slot buy2;
    private Slot sell;

    public TradingHelper(GuiMerchantOverride guiIn) {
        gui = guiIn;
        inventorySlots = gui.inventorySlots;
        buy1 = inventorySlots.getSlot(0);
        buy2 = inventorySlots.getSlot(1);
        sell = inventorySlots.getSlot(2);
        mc = Minecraft.getMinecraft();
        buy1 = inventorySlots.getSlot(0);
        buy2 = inventorySlots.getSlot(1);
        sell = inventorySlots.getSlot(2);
    }

    public void init(MerchantRecipeList list) {
        for (MerchantRecipe recipe : list) {
            map.put(recipe, ConfigJson.getRecipe(recipe, FastTrading.configLoader.recipeList));
        }
        if (FastTrading.isAuto.getValue()) {
            trading(list);
        }
    }

    protected Slot findItem(ItemStack itemStack, Slot startSlot) {
        if (itemStack.isEmpty() || null == startSlot) {
            return null;
        }
        int startIndex = startSlot.slotNumber;
        NonNullList<ItemStack> itemList = inventorySlots.inventoryItemStacks;
        int size = itemList.size() - 1;
        if (startIndex > itemList.size()) {
            return null;
        }
        Slot lastHave = null;
        for (; startIndex <= size; startIndex++) {
            Slot slot = inventorySlots.getSlot(startIndex);
            ItemStack temp = inventorySlots.getSlot(startIndex).getStack();
            if (temp.isItemEqual(itemStack)) {
                if (temp.getCount() >= itemStack.getCount()) {
                    return slot;
                } else {
                    lastHave = slot;
                }
            }
        }
        if (null != lastHave) {
            gui.click(lastHave, 0, ClickType.PICKUP);
            gui.click(lastHave, 0, ClickType.PICKUP_ALL);
            gui.click(lastHave, 0, ClickType.PICKUP);
            if (lastHave.getStack().getCount() >= itemStack.getCount()) {
                return lastHave;
            }
        }
        return null;
    }

    public void trading(MerchantRecipeList list) {
        int i = 0;
        for (MerchantRecipe recipe : list) {
            ConfigJson.SimpleRecipe simpleRecipe = map.get(recipe);
            if (null != simpleRecipe) {
                if (simpleRecipe.lockPrice) {
                    if (ConfigJson.isRecipeEqual(recipe, simpleRecipe)) {
                        trading(recipe, i);
                    }
                } else {
                    trading(recipe, i);
                }
            }
            i++;
        }
    }

    public void trading(MerchantRecipe recipe, int index) {
        if (recipe.isRecipeDisabled()) {
            return;
        }
        ItemStack buy1Item = recipe.getItemToBuy();
        ItemStack buy2Item = recipe.getSecondItemToBuy();
        Slot buy1Slot = findItem(buy1Item, inventorySlots.getSlot(2));
        Slot buy2Slot = findItem(buy2Item, inventorySlots.getSlot(2));
        while (!recipe.isRecipeDisabled()) {
            if (null == buy1Slot) {
                return;
            }
            if (recipe.hasSecondItemToBuy() && null == buy2Slot) {
                return;
            }
            gui.setCurrentRecipe(index);
            tradingStack(recipe, buy1Slot, buy2Slot);
            if (!recipe.isRecipeDisabled()) {
                buy1Slot = findItem(buy1Item, buy1Slot);
                buy2Slot = findItem(buy2Item, buy2Slot);
            }
        }
    }

    public void tradingOnce(MerchantRecipe recipe, int index) {
        if (!recipe.isRecipeDisabled()) {
            ItemStack buy1Item = recipe.getItemToBuy();
            ItemStack buy2Item = recipe.getSecondItemToBuy();
            Slot buy1Slot = findItem(buy1Item, inventorySlots.getSlot(2));
            Slot buy2Slot = findItem(buy2Item, inventorySlots.getSlot(2));
            if (null == buy1Slot) {
                return;
            }
            if (recipe.hasSecondItemToBuy() && null == buy2Slot) {
                return;
            }
            gui.setCurrentRecipe(index);
            tradingOne(recipe, buy1Slot, buy2Slot);
        }
    }

    private void tradingOne(MerchantRecipe recipe, Slot buy1Slot, Slot buy2Slot) {
        if (null == buy1Slot) {
            return;
        }
        if (recipe.hasSecondItemToBuy() && null == buy2Slot) {
            return;
        }
        moveItem(buy1Slot, buy1);
        if (recipe.hasSecondItemToBuy()) {
            moveItem(buy2Slot, buy2);
        }
        ItemStack stack = recipe.getItemToSell();
        gui.click(sell, 0, ClickType.PICKUP);
        for (Slot slot : clientQuickMoveSlot(stack)) {
            gui.click(slot, 0, ClickType.PICKUP);
        }
        clearSlot(buy1, buy2);
    }

    private void tradingStack(MerchantRecipe recipe, Slot buy1Slot, Slot buy2Slot) {
        if (null == buy1Slot) {
            return;
        }
        if (recipe.hasSecondItemToBuy() && null == buy2Slot) {
            return;
        }
        moveItem(buy1Slot, buy1);
        if (recipe.hasSecondItemToBuy()) {
            moveItem(buy2Slot, buy2);
        }
        gui.click(sell, 0, ClickType.QUICK_MOVE);
        clearSlot(buy1, buy2);
    }

    private void clearSlot(Slot... slots) {
        for (Slot slot : slots) {
            if (slot.getHasStack()) {
                gui.click(slot, 0, ClickType.QUICK_MOVE);
            }
        }
    }

    private List<Slot> clientQuickMoveSlot(ItemStack stack) {
        List<Slot> returnSlot = Lists.newArrayList();
        List<Slot> slots = inventorySlots.inventorySlots;
        int count = 0;
        for (int i = slots.size() - 1; i >= 3; i--) {
            ItemStack temp = slots.get(i).getStack();
            if (DazoUtils.areItemEqualIgnoreCount(stack, temp)) {
                if (temp.getMaxStackSize() > temp.getCount()) {
                    count = temp.getMaxStackSize() - temp.getCount() + count;
                    returnSlot.add(slots.get(i));
                }
            }
            if (count >= stack.getCount()) {
                return returnSlot;
            }
        }
        for (int i = slots.size() - 1; i >= 3; i--) {
            ItemStack temp = slots.get(i).getStack();
            if (temp.isEmpty()) {
                count = stack.getMaxStackSize() + count;
                returnSlot.add(slots.get(i));
            }

            if (count >= stack.getCount()) {
                return returnSlot;
            }
        }
        return returnSlot;
    }

    private void moveItem(Slot from, Slot to) {
        gui.click(from, 0, ClickType.PICKUP);
        gui.click(to, 0, ClickType.PICKUP);
        if (!mc.player.inventory.getItemStack().isEmpty()) {
            gui.click(from, 0, ClickType.PICKUP);
        }
    }
}
