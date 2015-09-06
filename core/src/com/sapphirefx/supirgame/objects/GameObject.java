package com.sapphirefx.supirgame.objects;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.sapphirefx.supirgame.Sprites.ManagerSprites;

/**
 * Created by sapphire on 03.09.15.
 */
public abstract class GameObject
{
    public transient Body body;
    private transient Fixture fixture;
    public int type;
    public boolean isActive;
    public Vector2 position;

    public abstract void createBody();

    public abstract void draw(ManagerSprites managerSprite, Batch batch);




    public boolean isActive()
    {
        return isActive;
    }

    public void setIsActive(boolean state)
    {
        isActive = state;
    }
}
