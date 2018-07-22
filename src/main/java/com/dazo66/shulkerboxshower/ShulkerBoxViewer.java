package com.dazo66.shulkerboxshower;

import com.dazo66.betterclient.IFeature;
import com.dazo66.betterclient.config.configentrys.BooleanConfigEntry;
import com.dazo66.betterclient.config.configentrys.IConfigEntry;
import com.dazo66.betterclient.coremod.IRegisterTransformer;
import com.dazo66.shulkerboxshower.eventhandler.ShulkerBoxViewerEventHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLLoadCompleteEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Dazo66
 */
public class ShulkerBoxViewer implements IFeature {
    public static final String VERSION = "1.5";
    private static final String ID = "shulkerboxviewer";
    private static final String NAME = "ShulkerBoxViewer";
    public static IConfigEntry<Boolean> isOrganizing;

    @Override
    public String getID() {
        return ID;
    }

    @Override
    public String getName() {
        return NAME;
    }

    @Override
    public String getVersion() {
        return VERSION;
    }

    @Override
    public List<Class<? extends IRegisterTransformer>> transformerClass() {
        return null;
    }

    @Override
    public Class eventHandlerClass() {
        return ShulkerBoxViewerEventHandler.class;
    }

    @Override
    public List<IConfigEntry> getConfigEntrys() {
        ArrayList<IConfigEntry> list = new ArrayList<>();
        list.add(isOrganizing = new BooleanConfigEntry("Organizing The Items", false, this));
        return list;
    }

    @Override
    public void preInit(FMLPreInitializationEvent event) {

    }

    @Override
    public void init(FMLInitializationEvent event) {

    }

    @Override
    public void postInit(FMLPostInitializationEvent event) {

    }

    @Override
    public void loadComplete(FMLLoadCompleteEvent event) {

    }

}
