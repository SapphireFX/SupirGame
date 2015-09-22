package com.sapphirefx.supirgame.ashley.EntityFactoryPacket;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.World;
import com.sapphirefx.supirgame.ashley.EntityFactoryPacket.Prototypes.PrototypeLightObject;
import com.sapphirefx.supirgame.ashley.EntityFactoryPacket.Prototypes.PrototypeObject;
import com.sapphirefx.supirgame.ashley.components.DimensionsComponent;
import com.sapphirefx.supirgame.ashley.components.LightObjectComponent;
import com.sapphirefx.supirgame.resources.IResourceRetriever;
import com.sapphirefx.supirgame.resources.ResourceManager;
import com.sapphirefx.supirgame.tools.ProjectInfo;

import box2dLight.ConeLight;
import box2dLight.PointLight;
import box2dLight.RayHandler;

/**
 * Created by sapphire on 21.09.15.
 */
public class LightComponentFactory extends ComponentFactory
{

    public LightComponentFactory(IResourceRetriever rm, World world, RayHandler rayHandler)
    {
        super(rm, world, rayHandler);
    }

    @Override
    public void createComponents(Entity root, Entity entity, PrototypeObject po)
    {
        createCommonComponents(entity, po, EntityFactory.LIGHT_TYPE);
        createParentNodeComponent(root, entity);
        createNodeComponent(root, entity);
        createLightObjectComponent(entity, (PrototypeLightObject) po);
    }

    @Override
    protected DimensionsComponent createDimensionsComponent(Entity entity, PrototypeObject po)
    {
        DimensionsComponent component = new DimensionsComponent();
        ProjectInfo projectInfoVO = rm.getProjectVO();
        component.boundBox = new Rectangle(-10f/projectInfoVO.pixelToWorld, -10f/projectInfoVO.pixelToWorld,
                20f/projectInfoVO.pixelToWorld, 20f/projectInfoVO.pixelToWorld);

        entity.add(component);
        return component;
    }

    protected LightObjectComponent createLightObjectComponent(Entity entity, PrototypeLightObject lo)
    {
        if(lo.softnessLength == -1f)
        {
            // TODO scale maybe here needed
            lo.softnessLength = lo.distance * 0.1f;
        }
        LightObjectComponent component = new LightObjectComponent(lo.type);
        component.coneDegree = lo.coneDegree;
        component.directionDegree = lo.directionDegree;
        component.distance = lo.distance;
        component.softnessLength = lo.softnessLength;
        component.isStatic = lo.isStatic;
        component.isXRay = lo.isXRay;
        component.rays = lo.rays;

        if (component.getType() == PrototypeLightObject.LightType.POINT)
        {
            component.lightObject = new PointLight(rayHandler, component.rays);
        } else
        {
            component.lightObject = new ConeLight(rayHandler, component.rays, Color.WHITE, 1, 0, 0, 0, 0);
        }

        component.lightObject.setSoftnessLength(component.softnessLength);

        entity.add(component);
        return component;
    }
}
