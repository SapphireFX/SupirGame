package com.sapphirefx.supirgame.objects;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.PooledEngine;
import com.sapphirefx.supirgame.ashley.components.DoorComponent;
import com.sapphirefx.supirgame.ashley.components.TextureComponent;
import com.sapphirefx.supirgame.ashley.components.TransformComponent;

/**
 * Created by sapphire on 05.09.15.
 */
public class Door
{
    public Door(PooledEngine ashleyEngine)
    {
        Entity entity = ashleyEngine.createEntity();

        DoorComponent doorComponent = ashleyEngine.createComponent(DoorComponent.class);
        TextureComponent textureComponent = ashleyEngine.createComponent(TextureComponent.class);
        TransformComponent transformComponent = ashleyEngine.createComponent(TransformComponent.class);

        entity.add(doorComponent);
        entity.add(textureComponent);
        entity.add(transformComponent);

        ashleyEngine.addEntity(entity);
    }

}
