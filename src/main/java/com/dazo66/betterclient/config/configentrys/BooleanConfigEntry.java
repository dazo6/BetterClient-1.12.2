package com.dazo66.betterclient.config.configentrys;

import com.dazo66.betterclient.BetterClient;
import com.dazo66.betterclient.functionsbase.IFunction;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.common.config.Property;

import javax.annotation.Nullable;

/**
 * @author Dazo66
 */
public class BooleanConfigEntry implements IConfigEntry<Boolean> {

    private Configuration config;
    private String key;
    private boolean defultValue;
    private IFunction owner;
    private String comment;
    private Property property;

    public BooleanConfigEntry(String keyIn, boolean defultValueIn, IFunction ownerIn, @Nullable String commentIn) {
        config = BetterClient.config;
        key = keyIn;
        defultValue = defultValueIn;
        owner = ownerIn;
        comment = commentIn;
        property = getProperty();
        getValue();
    }

    public BooleanConfigEntry(String keyIn, boolean defultValueIn, IFunction ownerIn) {
        this(keyIn, defultValueIn, ownerIn, null);
    }

    @Override
    public String getKey() {
        return key;
    }

    @Override
    public Boolean getDefaultValue() {
        return defultValue;
    }

    @Override
    public IFunction getOwner() {
        return owner;
    }

    @Override
    public String getComment() {
        return comment;
    }

    @Override
    public Boolean getValue() {
        return property.getBoolean();
    }

    @Override
    public void setValue(Boolean valueIn) {
        property.set(valueIn);
        config.save();
    }

    @Override
    public Property getProperty() {
        return property != null ? property : config.get(owner.getID(), key, defultValue, comment);
    }

}
