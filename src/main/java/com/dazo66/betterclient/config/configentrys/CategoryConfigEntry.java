//package com.dazo66.betterclient.config.configentrys;
//
//import com.dazo66.betterclient.BetterClient;
//import com.dazo66.betterclient.functionsbase.IFunction;
//import net.minecraftforge.common.config.ConfigCategory;
//import net.minecraftforge.common.config.Configuration;
//import net.minecraftforge.common.config.Property;
//
//import java.util.ArrayList;
//import java.util.Arrays;
//
///**
// * @author Dazo66
// */
//public class CategoryConfigEntry implements IConfigEntry<ConfigCategory> {
//
//    private Configuration config;
//    private String key;
//    private IFunction owner;
//    private String comment;
//    private IConfigEntry[] elements;
//    private Property property;
//
//    public CategoryConfigEntry(String keyIn, IFunction ownerIn, String commentIn, IConfigEntry... elementsIn) {
//        config = BetterClient.config;
//        key = keyIn;
//        owner = ownerIn;
//        comment = commentIn;
//        elements = elementsIn;
//        property = getProperty();
//    }
//
//    @Override
//    public IFunction getOwner() {
//        return owner;
//    }
//
//    @Override
//    public String getKey() {
//        return key;
//    }
//
//    @Override
//    public ConfigCategory getDefaultValue() {
//        return null;
//    }
//
//    @Override
//    public String getComment() {
//        return comment;
//    }
//
//    @Override
//    public ConfigCategory getValue() {
//        return config.getCategory(key);
//    }
//
//    @Override
//    public void setValue(ConfigCategory valueIn) {
//
//    }
//
//    @Override
//    public Property getProperty() {
//        return null;
//    }
//
//    public IConfigEntry[] addEntry(IConfigEntry entry) {
//        if (elements == null) {
//            elements = new IConfigEntry[]{};
//        }
//        ArrayList<IConfigEntry> list = new ArrayList<>(Arrays.asList(elements));
//        list.add(entry);
//        return elements = list.toArray(elements);
//    }
//
//    public IConfigEntry[] removeEntry(IConfigEntry entry) {
//        if (elements == null) {
//            elements = new IConfigEntry[]{};
//        }
//        ArrayList<IConfigEntry> list = new ArrayList<>(Arrays.asList(elements));
//        list.remove(entry);
//        return elements = list.toArray(elements);
//    }
//
//    public IConfigEntry[] getElements() {
//        return elements;
//    }
//}
