package com.dazo66.shulkerboxshower.client.render;

import com.dazo66.shulkerboxshower.ShulkerBoxViewer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.RenderItem;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Dazo66
 */
public class DrawItemInShulkerbox {

    private static final ResourceLocation GUI_TEXTURE = new ResourceLocation("textures/gui/container/shulker_box.png");
    public int x = 0;
    public int y = 0;
    private Minecraft mc = Minecraft.getMinecraft();

    public void draw(GuiScreen gui, ItemStack itemStack) {
        List<ItemStack> list = arrangementItem(itemStack);
        if (!list.isEmpty()) {
            drawItemStack(gui, list, x + 4, y - 100);
        }
    }

    public void draw(GuiScreen gui, ItemStack itemStack, ItemStack itemStack1, int x, int y) {
        List<ItemStack> list = arrangementItem(itemStack);
        if (!list.isEmpty()) {
            int size = list.size();
            int i = (size / 9) + (size % 9 == 0 ? 0 : 1);
            if (!ShulkerBoxViewer.isOrganizing.getValue()) {
                i = 3;
            }
            drawItemStack(gui, list, x + 7, y - 110 - 18 + 42 + i * 18);
        }
        List<ItemStack> list1 = arrangementItem(itemStack1);
        if (!list1.isEmpty()) {
            drawItemStack(gui, list1, x + 7, y - 100);
        }
    }

    private List<ItemStack> arrangementItem(ItemStack itemStack) {
        List<ItemStack> list = new ArrayList<>();
        NBTTagCompound nbttagcompound = itemStack.getTagCompound();
        if (nbttagcompound != null && nbttagcompound.hasKey("BlockEntityTag", 10)) {
            NBTTagCompound blockEntityTag = nbttagcompound.getCompoundTag("BlockEntityTag");
            if (blockEntityTag.hasKey("Items", 9)) {
                NonNullList<ItemStack> nonNullList = NonNullList.withSize(27, ItemStack.EMPTY);
                ItemStackHelper.loadAllItems(blockEntityTag, nonNullList);
                if (!ShulkerBoxViewer.isOrganizing.getValue()) {
                    return nonNullList;
                }

                for (ItemStack itemStack2 : nonNullList) {
                    if (itemStack2.isEmpty()) {
                        continue;
                    }
                    boolean flag = true;
                    for (ItemStack itemStack1 : list) {
                        if (itemStack2.isItemEqual(itemStack1) && ItemStack.areItemStackTagsEqual(itemStack1, itemStack2)) {
                            itemStack1.setCount(itemStack2.getCount() + itemStack1.getCount());
                            flag = false;
                        }
                    }
                    if (flag) {
                        list.add(itemStack2);
                    }
                }
            }
        }
        return list;
    }

    private void drawItemStack(GuiScreen gui, List<ItemStack> list, int x, int y) {

        GlStateManager.color(1F, 1F, 1F, 1f);
        GlStateManager.disableBlend();
        int i = (list.size() / 9) + (list.size() % 9 == 0 ? 0 : 1);
        int i1 = i;
        if (i1 == 3) {
            i = 1;
        } else if (i1 == 1) {
            i = 3;
        }
        this.mc.getTextureManager().bindTexture(GUI_TEXTURE);
        GlStateManager.disableDepth();
        GlStateManager.disableLighting();
        gui.drawTexturedModalRect(x - 8, y + 12 + i * 18, 0, 0, 176, 5);
        gui.drawTexturedModalRect(x - 8, y + 12 + i * 18 + 5, 0, 16, 176, i1 * 18);
        gui.drawTexturedModalRect(x - 8, y + 17 + i * 18 + i1 * 18, 0, 160, 176, 6);
        GlStateManager.enableDepth();
        GlStateManager.translate(0.0F, 0.0F, 32.0F);
        int size = list.size();
        for (int l = 0; l < size; l++) {
            drawItemStack(mc.getRenderItem(), list.get(l), (l % 9) * 18 + x, i * 18 + ((l / 9) + 1) * 18 + y + 1);
        }
        GlStateManager.disableLighting();
        mc.getRenderItem().zLevel = 0.0F;

    }

    private void drawItemStack1(List<ItemStack> nonNullList, int x, int y) {
        RenderItem itemRender = mc.getRenderItem();
        GlStateManager.translate(0.0F, 0.0F, 32.0F);

        for (int k = 0; k < 3; ++k) {
            for (int l = 0; l < 9; ++l) {
                drawItemStack(itemRender, nonNullList.get(l + k * 9), 8 + l * 18 + x - 15, 18 + k * 18 + y - 35);
            }
        }
        GlStateManager.disableLighting();
    }

    private void drawItemStack(RenderItem itemRender, ItemStack stack, int x, int y) {
        net.minecraft.client.gui.FontRenderer font = stack.getItem().getFontRenderer(stack);
        if (font == null) {
            font = mc.fontRenderer;
        }
        GlStateManager.enableDepth();
        itemRender.zLevel = 120.0F;
        RenderHelper.enableGUIStandardItemLighting();
        itemRender.renderItemAndEffectIntoGUI(stack, x, y);
        String count = stack.getCount() == 1 ? "" : String.valueOf(stack.getCount());
        String more = stack.getCount() % stack.getMaxStackSize() == 0 ? "" : "+" + stack.getCount() % stack.getMaxStackSize();
        String count1 = stack.getCount() == 1 ? "" : stack.getCount() / stack.getMaxStackSize() + "S" + more;
        itemRender.renderItemOverlayIntoGUI(font, stack, x, y, count);
        itemRender.zLevel = 0.0F;
    }
}
