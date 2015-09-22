package com.sapphirefx.supirgame.tools;

/**
 * Created by sapphire on 21.09.15.
 */
public class FrameRange
{
    public String name;
    public int startFrame;
    public int endFrame;

    public FrameRange()
    {
    }

    public FrameRange(String name, int startFrame, int endFrame)
    {
        this.name = name;
        this.startFrame = startFrame;
        this.endFrame = endFrame;
    }
}
