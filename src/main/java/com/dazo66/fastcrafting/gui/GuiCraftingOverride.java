package com.dazo66.fastcrafting.gui;

import com.dazo66.fastcrafting.crafting.CraftingHelper;
import com.dazo66.fastcrafting.crafting.GuiInventoryEnum;
import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.client.gui.inventory.GuiCrafting;
import net.minecraft.client.gui.recipebook.GuiRecipeBook;
import net.minecraft.client.gui.recipebook.RecipeBookPage;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.Container;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.NonNullList;
import net.minecraft.world.World;

import java.io.IOException;

/**
 * @author Dazo66
 */
public class GuiCraftingOverride extends GuiCrafting {

    private final GuiInventoryEnum guiType = GuiInventoryEnum.CRAFTING_TABLE;
    private final GuiRecipeBook guiRecipeBook = super.recipeBookGui;
    private final RecipeBookPage bookPage = guiRecipeBook.recipeBookPage;
    private CraftingHelper helper = new CraftingHelper(this);
    private Container inventorySlots = super.inventorySlots;

    public GuiCraftingOverride(InventoryPlayer player, World world) {
        super(player, world);
    }

    @Override
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {

        if (!this.guiRecipeBook.mouseClicked(mouseX, mouseY, mouseButton)) {
            if (!(width < 379) || !this.guiRecipeBook.isVisible()) {
                super.mouseClicked(mouseX, mouseY, mouseButton);
            }
        } else {

            IRecipe iRecipe = bookPage.getLastClickedRecipe();
            helper.setIRecipe(iRecipe);
            helper.setInventorySlots(this.inventorySlots);
            NonNullList<Ingredient> list = null;
            if (iRecipe == null) {
                return;
            }
            list = iRecipe.getIngredients();
            helper.setList(list);
            helper.setType(guiType);

            if (!bookPage.getLastClickedRecipeList().isCraftable(iRecipe)) {
                guiRecipeBook.setupGhostRecipe(iRecipe, inventorySlots.inventorySlots);
                return;
            }
            helper.craftMode(isShiftKeyDown(), isCtrlKeyDown());
            mc.getSoundHandler().playSound(PositionedSoundRecord.getMasterRecord(SoundEvents.UI_BUTTON_CLICK, 1.0F));
        }
    }

    //    protected void func_73864_a(int mouseX, int mouseY, int mouseButton) throws IOException {
    //        this.mouseClicked(mouseX, mouseY, mouseButton);
    //    }

    public void click(int mouseX, int mouseY) {
        try {
            super.mouseClicked(mouseX, mouseY, 0);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    public GuiInventoryEnum getGuiType() {
        return guiType;
    }
}
