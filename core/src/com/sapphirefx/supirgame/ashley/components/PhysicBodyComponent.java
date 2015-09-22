package com.sapphirefx.supirgame.ashley.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.physics.box2d.Body;

/**
 * Created by sapphire on 18.09.15.
 */
public class PhysicBodyComponent implements Component
{
    public Body body;

    public PhysicBodyComponent(Body body)
    {
        this.body = body;
    }
}
