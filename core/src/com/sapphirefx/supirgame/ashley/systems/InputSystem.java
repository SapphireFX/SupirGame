package com.sapphirefx.supirgame.ashley.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.sapphirefx.supirgame.ashley.components.PlayerComponent;

/**
 * Created by sapphire on 27.09.15.
 */
public class InputSystem extends IteratingSystem
{
    private ComponentMapper<PlayerComponent> playerMapper;

    public InputSystem()
    {
        super(Family.all(PlayerComponent.class).get());
        playerMapper = ComponentMapper.getFor(PlayerComponent.class);
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime)
    {
    }
}
