package com.sapphirefx.supirgame.ashley.systems;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;

/**
 * Created by sapphire on 18.09.15.
 */
public class CollisionSystem extends IteratingSystem
{

    public CollisionSystem(Family family)
    {
        super(family);
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime)
    {
    }
}
