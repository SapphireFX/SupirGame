package com.sapphirefx.supirgame.ashley.EntityFactoryPacket;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.physics.box2d.World;
import com.sapphirefx.supirgame.ashley.EntityFactoryPacket.Prototypes.PrototypeObject;
import com.sapphirefx.supirgame.ashley.components.PlayerComponent;
import com.sapphirefx.supirgame.resources.IResourceRetriever;

import box2dLight.RayHandler;

/**
 * Created by sapphire on 27.09.15.
 */
public class PlayerComponentFactory extends AnimationComponentFactory
{
    public PlayerComponentFactory(IResourceRetriever rm, World world, RayHandler rayHandler)
    {
        super(rm, world, rayHandler);
    }

    @Override
    protected void createCommonComponents(Entity entity, PrototypeObject po, int entityType)
    {
        super.createCommonComponents(entity, po, entityType);
        createPlayerComponent(entity);
    }

    private void createPlayerComponent(Entity entity)
    {
        PlayerComponent playerComponent = new PlayerComponent();
        playerComponent.life = 100;

        entity.add(playerComponent);
    }
}
