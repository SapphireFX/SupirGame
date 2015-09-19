package com.sapphirefx.supirgame.objects;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.PooledEngine;
import com.sapphirefx.supirgame.ashley.components.LootComponent;
import com.sapphirefx.supirgame.ashley.components.TextureComponent;
import com.sapphirefx.supirgame.ashley.components.TransformComponent;

/**
 * Created by sapphire on 06.09.15.
 */
public class Loot
{
    public Loot(PooledEngine ashleyEngine)
    {
        Entity entity = ashleyEngine.createEntity();

        LootComponent lootComponent = ashleyEngine.createComponent(LootComponent.class);
        TextureComponent textureComponent = ashleyEngine.createComponent(TextureComponent.class);
        TransformComponent transformComponent = ashleyEngine.createComponent(TransformComponent.class);

        entity.add(lootComponent);
        entity.add(textureComponent);
        entity.add(transformComponent);

        ashleyEngine.addEntity(entity);
    }
}
