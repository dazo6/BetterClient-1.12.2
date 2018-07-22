package com.dazo66.shulkerboxshower.config;//package com.dazo66.shulkerboxshower.config;
//
//import com.dazo66.shulkerboxshower.ShulkerBoxViewer;
//import net.minecraft.client.Minecraft;
//import net.minecraft.client.gui.GuiScreen;
//import net.minecraftforge.common.config.ConfigElement;
//import net.minecraftforge.fml.client.IModGuiFactory;
//import net.minecraftforge.fml.client.config.GuiConfig;
//import net.minecraftforge.fml.client.config.GuiConfigEntries;
//import net.minecraftforge.fml.client.config.IConfigElement;
//
//import java.util.List;
//import java.util.Set;
//
///**
// * @author Dazo66
// */
//public class ConfigFactory implements IModGuiFactory {
//
//    public static final String CLASS_NAME = "com.dazo66.shulkerboxshower.config.ConfigFactory";
//
//    @Override
//    public GuiScreen createConfigGui(GuiScreen parent) {
//        return new ShulkerBoxViewerConfigGui(parent, new ConfigElement(ConfigLoader.config.getCategory("common")).getChildElements(), ShulkerBoxViewer.ID, "ShulkerBoxViewerConfigGui.Common");
//    }
//
//    @Override
//    public boolean hasConfigGui() {
//        return true;
//    }
//
//    @Override
//    public void initialize(Minecraft minecraftInstance) {
//    }
//
//    public Class<? extends GuiScreen> mainConfigGuiClass() {
//        return ShulkerBoxViewerConfigGui.class;
//    }
//
//    @Override
//    public Set<RuntimeOptionCategoryElement> runtimeGuiCategories() {
//        return null;
//    }
//
//    public static class ShulkerBoxViewerConfigGui extends GuiConfig {
//
//        public ShulkerBoxViewerConfigGui(GuiScreen parent) {
//            this(parent, new ConfigElement(ConfigLoader.config.getCategory("common")).getChildElements(), ShulkerBoxViewer.ID, "ShulkerBoxViewerConfigGui.Common");
//        }
//
//        public ShulkerBoxViewerConfigGui(GuiScreen parentScreen, List<IConfigElement> list, String modid, String title) {
//            super(parentScreen, list, modid, false, false, title);
//        }
//
//        @Override
//        public void onGuiClosed() {
//            ShulkerBoxViewer.config.configSave();
//        }
//
//        public GuiConfigEntries.IConfigEntry getCategoryEntry() {
//            for (GuiConfigEntries.IConfigEntry entry : this.entryList.listEntries) {
//                if ("common".equals(entry.getName())) {
//                    return entry;
//                }
//            }
//            return null;
//        }
//
//    }
//
//}
