package com.sapphirefx.supirgame.render;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.graphics.g2d.Batch;

/**
 * Created by sapphire on 20.09.15.
 */
public interface Drawable
{
    public abstract void draw(Batch batch, Entity entity, float parentAlpha);
}
