package com.dazo66.betterclient.config.configentrys;

import com.dazo66.betterclient.BetterClient;
import com.dazo66.betterclient.featuresbase.IFeature;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.common.config.Property;

import javax.annotation.Nullable;

/**
 * @author Dazo66
 */
public class StringArrayConfigEntry implements IConfigEntry<String[]> {

    String[] validValues;
    private Configuration config;
    private String key;
    private String[] defultValue;
    private IFeature owner;
    private String comment;
    private Property property;

    public StringArrayConfigEntry(String keyIn, String[] defultValueIn, IFeature ownerIn, @Nullable String commentIn, String[] validValuesIn) {
        config = BetterClient.config;
        key = keyIn;
        defultValue = defultValueIn;
        owner = ownerIn;
        comment = commentIn;
        property = getProperty();
        validValues = validValuesIn;
    }

    public StringArrayConfigEntry(String keyIn, String[] defultValueIn, IFeature ownerIn) {
        this(keyIn, defultValueIn, ownerIn, null, null);
    }

    @Override
    public String getKey() {
        return key;
    }

    @Override
    public String[] getDefaultValue() {
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
    public String[] getValue() {
        return property.getStringList();
    }

    @Override
    public void setValue(String[] dou) {
        property.set(dou);
        config.save();
    }

    @Override
    public Property getProperty() {
        if (property != null) {
            return property;
        }
        Property property = config.get(owner.getID(), key, defultValue, comment);
        if (validValues != null) {
            property.setValidValues(validValues);
        }
        property.setComment(comment + " [default: " + property.getDefault() + "]");
        return property;
    }

}
