package com.dazo66.shulkerboxshower;

import com.dazo66.betterclient.AbstractFeature;
import com.dazo66.betterclient.config.configentrys.BooleanConfigEntry;
import com.dazo66.betterclient.config.configentrys.IConfigEntry;
import com.dazo66.shulkerboxshower.eventhandler.ShulkerBoxViewerEventHandler;
import net.minecraft.client.resources.I18n;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

import java.util.LinkedList;

/**
 * @author Dazo66
 */
public class ShulkerBoxViewer extends AbstractFeature {
    public static final String VERSION = "1.5";
    private static final String ID = "shulkerboxviewer";
    public static IConfigEntry<Boolean> isOrganizing;

    @Override
    public String getID() {
        return ID;
    }

    @Override
    public String getName() {
        return I18n.format("betterclient.shulkerboxviewer.name");
    }

    @Override
    public String getVersion() {
        return VERSION;
    }

    @Override
    public String getAuthor() {
        return "Dazo66";
    }

    @Override
    public Class eventHandlerClass() {
        return ShulkerBoxViewerEventHandler.class;
    }

    @Override
    public LinkedList<IConfigEntry> getConfigEntrys() {
        LinkedList<IConfigEntry> list = new LinkedList<>();
        list.add(isOrganizing = new BooleanConfigEntry("Organized the items", false, this, "Organizing the items or not"));
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

}
