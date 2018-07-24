package com.dazo66.fasttrading.client.gui;

import com.dazo66.fasttrading.FastTrading;
import com.dazo66.fasttrading.config.ConfigJson;
import com.dazo66.fasttrading.util.DazoUtils;
import com.dazo66.fasttrading.util.TradingHelper;
import io.netty.buffer.Unpooled;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiMerchant;
import net.minecraft.entity.IMerchant;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.ClickType;
import net.minecraft.inventory.ContainerMerchant;
import net.minecraft.inventory.Slot;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.client.CPacketCustomPayload;
import net.minecraft.util.ResourceLocation;
import net.minecraft.village.MerchantRecipe;
import net.minecraft.village.MerchantRecipeList;
import net.minecraft.world.World;

import java.io.IOException;
import java.util.ArrayList;

import static com.dazo66.fasttrading.FastTrading.configLoader;

/**
 * @author Dazo66
 */
public class GuiMerchantOverride extends GuiMerchant {

    public TradingHelper helper;
    public MerchantRecipeList merchantRecipeList;
    private IMerchant iMerchant;
    private int lastClickTime;
    private GuiButton lastClickButton;
    private ArrayList<GuiRecipeButton> recipeButtonList = new ArrayList<>();
    private GuiIconButton onButton;
    private GuiIconButton offButton;
    private GuiIconButton plusButton;
    private GuiIconButton subtractButton;
    private GuiIconButton lockButton;
    private GuiIconButton unlockButton;

    public GuiMerchantOverride(InventoryPlayer inventoryPlayer, IMerchant iMerchant, World worldIn) {
        super(inventoryPlayer, iMerchant, worldIn);
        helper = new TradingHelper(this);
        this.iMerchant = iMerchant;
        configLoader.load();
    }

    @Override
    public void initGui() {
        super.initGui();
        recipeButtonList.clear();
        addMerchantButton(merchantRecipeList);
        addFunctionButton();
    }

    public void click(Slot slot, int mouseButton, ClickType type) {
        this.handleMouseClick(slot, slot.slotNumber, mouseButton, type);
    }


    @Override
    public void drawScreen(int mouseX, int mouseY, float p) {
        super.drawScreen(mouseX, mouseY, p);
        GuiButton button0 = getFirstButton();
        if (null != button0 && button0 instanceof GuiRecipeButton) {
            ((GuiRecipeButton) button0).tryProminent(mc, mouseX, mouseY, p, true);
            ((GuiRecipeButton) button0).tryRenderItemTooltip(mouseX, mouseY);
        }
        for (GuiButton button : buttonList) {
            if (button instanceof GuiButtonPlus) {
                ((GuiButtonPlus) button).drawTooltip(mc, mouseX, mouseY);
            }
            if (button == button0) {
                continue;
            }
            if (button instanceof GuiRecipeButton) {
                ((GuiRecipeButton) button).tryProminent(mc, mouseX, mouseY, p, false);
            }
        }

    }

    @Override
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
        if (isShiftKeyDown()) {
            Slot slot = this.getSlotAtPosition(mouseX, mouseY);
            if (null != slot && slot.getHasStack() && slot.slotNumber != 0 && slot.slotNumber != 1 && slot.slotNumber != 2) {
                if (!inventorySlots.getSlot(0).getHasStack()) {
                    moveItem(slot, inventorySlots.getSlot(0));
                } else if (!inventorySlots.getSlot(1).getHasStack()) {
                    moveItem(slot, inventorySlots.getSlot(1));
                }
            }
        }
        super.mouseClicked(mouseX, mouseY, mouseButton);
    }

    private void moveItem(Slot from, Slot to) {
        click(from, 0, ClickType.PICKUP);
        click(to, 0, ClickType.PICKUP);
        if (!mc.player.inventory.getItemStack().isEmpty()) {
            click(from, 0, ClickType.PICKUP);
        }
    }

    private Slot getSlotAtPosition(int x, int y) {
        for (int i = 0; i < this.inventorySlots.inventorySlots.size(); ++i) {
            Slot slot = this.inventorySlots.inventorySlots.get(i);

            if (super.isPointInRegion(slot.xPos, slot.yPos, 16, 16, x, y) && slot.isEnabled()) {
                return slot;
            }
        }

        return null;
    }

    private GuiButton getFirstButton() {
        ArrayList<GuiButton> list = new ArrayList<>();
        for (GuiButton button : buttonList) {
            if (button.isMouseOver()) {
                list.add(button);
            }
        }
        for (GuiButton button1 : list) {
            if (button1 instanceof GuiRecipeButton && ((GuiRecipeButton) button1).hasBeenMove) {
                return button1;
            }
        }
        if (!list.isEmpty()) {
            return list.get(0);
        }
        return null;
    }

    @Override
    public void onGuiClosed() {
        super.onGuiClosed();
        configLoader.onSave();
    }

    @Override
    public void updateScreen() {
        super.updateScreen();
        functionButtonUpdate();
    }

    @Override
    protected void actionPerformed(GuiButton button) throws IOException {
        int currentTime = (int) System.currentTimeMillis() % Integer.MAX_VALUE;
        super.actionPerformed(button);
        if (merchantRecipeList == null) {
            merchantRecipeList = iMerchant.getRecipes(mc.player);
        }
        GuiButton button1 = getFirstButton();
        if (button == button1) {
            MerchantRecipe recipe = merchantRecipeList.get(selectedMerchantRecipe);
            ConfigJson.SimpleRecipe currentRecipe = helper.map.get(recipe);

            if (!button.enabled || !button.visible) {
                return;
            }
            if (recipeButtonList.contains(button)) {
                int recipeIndex = button.id - 300;
                setCurrentRecipe(recipeIndex);
                if (lastClickTime > currentTime - 500 && lastClickButton == button) {
                    if (isShiftKeyDown()) {
                        helper.trading(merchantRecipeList.get(recipeIndex), recipeIndex);
                    } else {
                        helper.tradingOnce(merchantRecipeList.get(recipeIndex), recipeIndex);
                    }
                    lastClickButton = null;
                    lastClickTime = 0;
                } else {
                    lastClickButton = button;
                    lastClickTime = currentTime;
                }
            } else if (button.id == 250) {
                FastTrading.isAuto.setValue(true);
            } else if (button.id == 251) {
                FastTrading.isAuto.setValue(false);
            } else if (button.id == 252) {
                ConfigJson.SimpleRecipe recipe1 = new ConfigJson.SimpleRecipe(false, recipe);
                configLoader.recipeList.add(recipe1);
                helper.map.put(recipe, recipe1);
            } else if (button.id == 253) {
                configLoader.recipeList.remove(currentRecipe);
                helper.map.remove(recipe);
            } else if (button.id == 254) {
                ConfigJson.SimpleRecipe recipe1 = new ConfigJson.SimpleRecipe(true, recipe);
                configLoader.recipeList.remove(currentRecipe);
                configLoader.recipeList.add(recipe1);
                helper.map.put(recipe, recipe1);
            } else if (button.id == 255) {
                if (null != currentRecipe) {
                    currentRecipe.setLockPrice(false);
                }
            }
        }

    }

    private void addFunctionButton() {
        onButton = null;
        offButton = null;
        plusButton = null;
        subtractButton = null;
        lockButton = null;
        unlockButton = null;
        ResourceLocation ON = new ResourceLocation(FastTrading.MODID, "textures/gui/on.png");
        ResourceLocation OFF = new ResourceLocation(FastTrading.MODID, "textures/gui/off.png");
        ResourceLocation PLUS = new ResourceLocation(FastTrading.MODID, "textures/gui/plus.png");
        ResourceLocation SUBTRACT = new ResourceLocation(FastTrading.MODID, "textures/gui/subtract.png");
        ResourceLocation LOCK = new ResourceLocation(FastTrading.MODID, "textures/gui/lock.png");
        ResourceLocation UNLOCK = new ResourceLocation(FastTrading.MODID, "textures/gui/unlock.png");
        onButton = new GuiIconButton(250, guiLeft + 3, guiTop + 3, 10, 10, DazoUtils.tooltipI18n("fasttrading.tooltip.enablebutton"), ON);
        offButton = new GuiIconButton(251, guiLeft + 3, guiTop + 3, 10, 10, DazoUtils.tooltipI18n("fasttrading.tooltip.disablebutton"), OFF);
        plusButton = new GuiIconButton(252, guiLeft + 14, guiTop + 3, 10, 10, DazoUtils.tooltipI18n("fasttrading.tooltip.addbutton"), PLUS);
        subtractButton = new GuiIconButton(253, guiLeft + 14, guiTop + 3, 10, 10, DazoUtils.tooltipI18n("fasttrading.tooltip.removebutton"), SUBTRACT);
        lockButton = new GuiIconButton(254, guiLeft + 25, guiTop + 3, 10, 10, DazoUtils.tooltipI18n("fasttrading.tooltip.lockbutton"), LOCK);
        unlockButton = new GuiIconButton(255, guiLeft + 25, guiTop + 3, 10, 10, DazoUtils.tooltipI18n("fasttrading.tooltip.unlockbutton"), UNLOCK);
        addButton(onButton);
        addButton(offButton);
        addButton(plusButton);
        addButton(subtractButton);
        addButton(lockButton);
        addButton(unlockButton);
        functionButtonUpdate();
    }

    private void functionButtonUpdate() {
        if (FastTrading.isAuto.getValue()) {
            onButton.visible = false;
            offButton.visible = true;
        } else {
            onButton.visible = true;
            offButton.visible = false;
        }
        if (null == merchantRecipeList || merchantRecipeList.isEmpty()) {
            return;
        }
        MerchantRecipe recipe = merchantRecipeList.get(selectedMerchantRecipe);
        ConfigJson.SimpleRecipe simpleRecipe = helper.map.get(recipe);
        if (null == simpleRecipe) {
            plusButton.visible = true;
            subtractButton.visible = false;
            lockButton.visible = false;
            unlockButton.visible = false;
        } else {
            plusButton.visible = false;
            subtractButton.visible = true;
            lockButton.visible = true;
            unlockButton.visible = false;
            if (simpleRecipe.lockPrice) {
                if (ConfigJson.isRecipeEqual(recipe, simpleRecipe)) {
                    unlockButton.visible = true;
                    lockButton.visible = false;
                } else {
                    unlockButton.visible = false;
                    lockButton.visible = true;
                }
            }
        }
    }

    private void addMerchantButton(MerchantRecipeList list) {
        if (list == null || list.isEmpty()) {
            list = iMerchant.getRecipes(mc.player);
            if (list != null) {
                addMerchantButton(list);
            }
            return;
        }
        int i = 0;
        int spacing = 25;
        int top = guiTop;
        if (list.size() * 25 > 166 && list.size() * 15 < 166) {
            spacing = 166 / list.size();
        } else if (list.size() * 15 >= 166) {
            spacing = 15;
            top = top - (list.size() * 15 - 166) / 2;
        }
        for (MerchantRecipe merchantRecipe : list) {
            GuiRecipeButton button = new GuiRecipeButton(300 + i++, guiLeft - 89 - 1, top - spacing + i * spacing, this, merchantRecipe);
            for (GuiButton button1 : buttonList) {
                if (button1.id == button.id) {
                    return;
                }
            }
            buttonList.add(button);
            recipeButtonList.add(button);
        }
    }

    public void setCurrentRecipe(int index) {
        if (index != selectedMerchantRecipe) {
            selectedMerchantRecipe = index;
            ((ContainerMerchant) this.inventorySlots).setCurrentRecipeIndex(this.selectedMerchantRecipe);
            PacketBuffer packetbuffer = new PacketBuffer(Unpooled.buffer());
            packetbuffer.writeInt(this.selectedMerchantRecipe);
            this.mc.getConnection().sendPacket(new CPacketCustomPayload("MC|TrSel", packetbuffer));
        }
    }

    public void setMerchantRecipeList(MerchantRecipeList list) {
        merchantRecipeList = list;
        addMerchantButton(merchantRecipeList);
        helper.init(merchantRecipeList);
    }

}
