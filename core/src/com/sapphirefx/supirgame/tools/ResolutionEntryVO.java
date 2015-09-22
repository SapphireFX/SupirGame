package com.sapphirefx.supirgame.tools;

/**
 * Created by sapphire on 21.09.15.
 */
public class ResolutionEntryVO
{
    public String name;

    public int width;
    public int height;
    public int base;

    @Override
    public String toString() {
        if (width == 0 && height == 0) {
            return name;
        }
        return width + "x" + height + " (" + name + ")";
    }

    public float getMultiplier(ResolutionEntryVO originalResolution) {
        float mul;
        if(base == 0) {
            mul = (float)originalResolution.width/width;
        } else {
            mul = (float)originalResolution.height/height;
        }
        return mul;
    }
}
