package com.sapphirefx.supirgame.ashley.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.utils.IntMap;

/**
 * Created by sapphire on 17.09.15.
 */
public class AnimationComponent implements Component
{
    public IntMap<Animation> animations = new IntMap<Animation>();
}
