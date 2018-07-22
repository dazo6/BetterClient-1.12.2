package com.dazo66.shulkerboxshower.config;//package com.dazo66.shulkerboxshower.config;
//
//import net.minecraftforge.common.config.Configuration;
//import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
//
//import java.io.File;
//
///**
// * @author Dazo66
// */
//public class ConfigLoader {
//
//    public static Configuration config;
//
//    public ConfigLoader(FMLPreInitializationEvent event) {
//        File configFile = event.getSuggestedConfigurationFile();
//        config = new Configuration(configFile);
//        configLoader();
//        initialize();
//    }
//
//    private void configLoader() {
//        config.load();
//    }
//
//    private void initialize() {
//        isOrganizing();
//        configSave();
//    }
//
//    public void configSave() {
//        config.save();
//    }
//
//    public boolean isOrganizing() {
//        String comment = "Organizing the items in one stack or not";
//        return config.getBoolean("Organizing The Items", "Common", false, comment);
//    }
//
//
//}
