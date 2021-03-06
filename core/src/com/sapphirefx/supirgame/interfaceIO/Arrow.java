package com.sapphirefx.supirgame.interfaceIO;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;

/**
 * Created by sapphire on 07.09.15.
 */
public class Arrow extends Actor
{
    private TextureRegion texture;
    public Arrow (TextureRegion texture)
    {
        this.texture = texture;
    }

    @Override
    public void draw(Batch batch, float parentAlpha)
    {
        batch.draw(texture, getX(), getY(),
                getOriginX(), getOriginY(),
                getWidth(), getHeight(),
                getScaleX(), getScaleY(), getRotation());
    }
}
