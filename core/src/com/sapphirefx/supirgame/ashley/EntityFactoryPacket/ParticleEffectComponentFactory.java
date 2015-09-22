package com.sapphirefx.supirgame.ashley.EntityFactoryPacket;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.physics.box2d.World;
import com.sapphirefx.supirgame.ashley.EntityFactoryPacket.Prototypes.PrototypeObject;
import com.sapphirefx.supirgame.ashley.EntityFactoryPacket.Prototypes.PrototypeParticleEffectObject;
import com.sapphirefx.supirgame.ashley.components.DimensionsComponent;
import com.sapphirefx.supirgame.ashley.components.ParticleComponent;
import com.sapphirefx.supirgame.resources.IResourceRetriever;
import com.sapphirefx.supirgame.tools.ProjectInfo;

import box2dLight.RayHandler;

/**
 * Created by sapphire on 21.09.15.
 */
public class ParticleEffectComponentFactory extends ComponentFactory
{
    public ParticleEffectComponentFactory(IResourceRetriever rm, World world, RayHandler rayHandler)
    {
        super(rm, world, rayHandler);
    }

    @Override
    public void createComponents(Entity root, Entity entity, PrototypeObject po)
    {
        createCommonComponents(entity, po, EntityFactory.PARTICLE_TYPE);
        createParentNodeComponent(root, entity);
        createNodeComponent(root, entity);
        createParticleComponent(entity, (PrototypeParticleEffectObject) po);
    }

    @Override
    protected DimensionsComponent createDimensionsComponent(Entity entity, PrototypeObject po)
    {
        return null;
    }

    protected ParticleComponent createParticleComponent(Entity entity, PrototypeParticleEffectObject peo)
    {
        ParticleComponent component = new ParticleComponent();
        component.particleName = peo.particleName;
		ParticleEffect particleEffect = new ParticleEffect(rm.getParticleEffect(peo.particleName));
        component.particleEffect = particleEffect;

        ProjectInfo projectInfoVO = rm.getProjectVO();

        component.worldMultiplyer = 1f/projectInfoVO.pixelToWorld;

        entity.add(component);
        return component;
    }
}
