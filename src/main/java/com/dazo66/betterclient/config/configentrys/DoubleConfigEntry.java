package com.dazo66.betterclient.config.configentrys;

import com.dazo66.betterclient.BetterClient;
import com.dazo66.betterclient.IFeature;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.common.config.Property;

import javax.annotation.Nullable;

/**
 * @author Dazo66
 */
public class DoubleConfigEntry implements IConfigEntry<Double> {

    private Configuration config;
    private String key;
    private double defultValue;
    private IFeature owner;
    private String comment;
    private Property property;

    public DoubleConfigEntry(String keyIn, double defultValueIn, IFeature ownerIn, @Nullable String commentIn) {
        config = BetterClient.config;
        key = keyIn;
        defultValue = defultValueIn;
        owner = ownerIn;
        comment = commentIn;
        property = getProperty();
    }

    public DoubleConfigEntry(String keyIn, double defultValueIn, IFeature ownerIn) {
        this(keyIn, defultValueIn, ownerIn, null);
    }

    @Override
    public String getKey() {
        return key;
    }

    @Override
    public Double getDefaultValue() {
        return defultValue;
    }

    @Override
    public IFeature getOwner() {
        return owner;
    }

    @Override
    public String getComment() {
        return comment;
    }

    @Override
    public Double getValue() {
        return property.getDouble(defultValue);
    }

    @Override
    public void setValue(Double dou) {
        property.set(dou);
        config.save();
    }

    @Override
    public Property getProperty() {
        return property != null ? property : config.get(owner.getID(), key, defultValue, comment);
    }
}
