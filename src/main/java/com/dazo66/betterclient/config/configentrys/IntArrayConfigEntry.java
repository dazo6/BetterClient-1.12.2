package com.dazo66.betterclient.config.configentrys;

import com.dazo66.betterclient.BetterClient;
import com.dazo66.betterclient.functionsbase.IFunction;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.common.config.Property;

import javax.annotation.Nullable;

/**
 * @author Dazo66
 */
public class IntArrayConfigEntry implements IConfigEntry<int[]> {

    private Configuration config;
    private String key;
    private int[] defultValue;
    private IFunction owner;
    private String comment;
    private Property property;

    public IntArrayConfigEntry(String keyIn, int[] defultValueIn, IFunction ownerIn, @Nullable String commentIn) {
        config = BetterClient.config;
        key = keyIn;
        defultValue = defultValueIn;
        owner = ownerIn;
        comment = commentIn;
        property = getProperty();
    }

    public IntArrayConfigEntry(String keyIn, int[] defultValueIn, IFunction ownerIn) {
        this(keyIn, defultValueIn, ownerIn, null);
    }

    @Override
    public String getKey() {
        return key;
    }

    @Override
    public int[] getDefaultValue() {
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
    public int[] getValue() {
        return property.getIntList();
    }

    @Override
    public void setValue(int[] ints) {
        property.set(ints);
        config.save();
    }

    @Override
    public Property getProperty() {
        return property != null ? property : config.get(owner.getID(), key, defultValue, comment);
    }
}
