package com.dazo66.fastcrafting.eventhandler;

import com.dazo66.fastcrafting.crafting.CraftingLoader;
import com.dazo66.fastcrafting.gui.GuiCraftingOverride;
import com.dazo66.fastcrafting.gui.GuiInventoryOverride;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.gui.recipebook.IRecipeShownListener;
import net.minecraftforge.client.event.GuiOpenEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

/**
 * @author Dazo66
 */
public class FastCraftingingEventHandler {

    @SubscribeEvent
    public void guiOpenEvent(GuiOpenEvent event) {
        if (event.getGui() instanceof IRecipeShownListener) {
            CraftingLoader.unlockRecipe();
            if (event.getGui() instanceof net.minecraft.client.gui.inventory.GuiInventory) {
                EntityPlayerSP player = Minecraft.getMinecraft().player;
                event.setGui(new GuiInventoryOverride(player));

            } else if (event.getGui() instanceof net.minecraft.client.gui.inventory.GuiCrafting) {
                EntityPlayerSP player = Minecraft.getMinecraft().player;
                event.setGui(new GuiCraftingOverride(player.inventory, player.world));
            }
        }
    }
}
