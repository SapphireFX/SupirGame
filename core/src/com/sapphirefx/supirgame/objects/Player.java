package com.sapphirefx.supirgame.objects;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.sapphirefx.supirgame.ashley.components.AnimationComponent;
import com.sapphirefx.supirgame.ashley.components.PhysicBodyComponent;
import com.sapphirefx.supirgame.ashley.components.PlayerComponent;
import com.sapphirefx.supirgame.ashley.components.StateComponent;
import com.sapphirefx.supirgame.ashley.components.TextureComponent;
import com.sapphirefx.supirgame.ashley.components.TransformComponent;

/**
 * Created by sapphire on 03.09.15.
 */
public class Player
{
    public Player(PooledEngine ashleyEngine)
    {
        Entity entity = ashleyEngine.createEntity();

        AnimationComponent animation = ashleyEngine.createComponent(AnimationComponent.class);
        TransformComponent transform = ashleyEngine.createComponent(TransformComponent.class);
        PhysicBodyComponent physicBody = ashleyEngine.createComponent(PhysicBodyComponent.class);
        PlayerComponent playerComponent = ashleyEngine.createComponent(PlayerComponent.class);
        StateComponent state = ashleyEngine.createComponent(StateComponent.class);
        TextureComponent texture = ashleyEngine.createComponent(TextureComponent.class);

        // тут положить в анимацию набор для героя

        entity.add(animation);
        entity.add(transform);
        entity.add(physicBody);
        entity.add(playerComponent);
        entity.add(state);
        entity.add(texture);

        ashleyEngine.addEntity(entity);
    }

}
