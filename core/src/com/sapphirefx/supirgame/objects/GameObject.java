package com.sapphirefx.supirgame.objects;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Fixture;

/**
 * Created by sapphire on 03.09.15.
 */
public abstract class GameObject
{
    private Body body;
    private int type;
    private boolean isActive;
    private Fixture fixture;

    public GameObject()
    {
        //create body and fixture object
    }

    public abstract void draw(Batch batch);

}
