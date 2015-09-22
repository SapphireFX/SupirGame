package com.sapphirefx.supirgame.ashley.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.sapphirefx.supirgame.tools.FrameRange;

import java.util.HashMap;

/**
 * Created by sapphire on 17.09.15.
 */
public class AnimationComponent implements Component
{
    public String animationName = "";
	public int fps = 24;
	public HashMap<String, FrameRange> frameRangeMap = new HashMap<String, FrameRange>();
    public String currentAnimation;
    public Animation.PlayMode playMode = Animation.PlayMode.LOOP;
}
