package com.sapphirefx.supirgame.ashley.EntityFactoryPacket;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.physics.box2d.World;
import com.sapphirefx.supirgame.ashley.EntityFactoryPacket.Prototypes.PrototypeImageObject;
import com.sapphirefx.supirgame.ashley.EntityFactoryPacket.Prototypes.PrototypeObject;
import com.sapphirefx.supirgame.ashley.components.DimensionsComponent;
import com.sapphirefx.supirgame.ashley.components.TextureComponent;
import com.sapphirefx.supirgame.resources.IResourceRetriever;
import com.sapphirefx.supirgame.resources.ResourceManager;
import com.sapphirefx.supirgame.tools.ComponentRetriever;
import com.sapphirefx.supirgame.tools.ProjectInfo;
import com.sapphirefx.supirgame.tools.ResolutionEntryVO;

import box2dLight.RayHandler;

/**
 * Created by sapphire on 21.09.15.
 */
public class SimpleImageComponentFactory extends ComponentFactory
{
    public SimpleImageComponentFactory(IResourceRetriever rm, World world, RayHandler rayHandler)
    {
        super(rm, world, rayHandler);
    }

    @Override
    public void createComponents(Entity root, Entity entity, PrototypeObject po)
    {
        createCommonComponents( entity, po, EntityFactory.IMAGE_TYPE);
        createTextureRegionComponent(entity, (PrototypeImageObject) po);
        createParentNodeComponent(root, entity);
        createNodeComponent(root, entity);
    }

    @Override
    protected DimensionsComponent createDimensionsComponent(Entity entity, PrototypeObject po)
    {
        DimensionsComponent component = new DimensionsComponent();

        entity.add(component);
        return component;
    }

    protected TextureComponent createTextureRegionComponent(Entity entity, PrototypeImageObject io)
    {
        TextureComponent component = new TextureComponent();
        component.regionName = io.imageName;
        component.region = rm.getTextureRegion(io.imageName);
        component.isRepeat = io.isRepeat;
        component.isPolygon = io.isPolygon;

        ResolutionEntryVO resolutionEntryVO = rm.getLoadedResolution();
        ProjectInfo projectInfoVO = rm.getProjectVO();
        float multiplier = resolutionEntryVO.getMultiplier(rm.getProjectVO().originalResolution);

        DimensionsComponent dimensionsComponent = ComponentRetriever.get(entity, DimensionsComponent.class);

        dimensionsComponent.width = (float) component.region.getRegionWidth() * multiplier / projectInfoVO.pixelToWorld;
        dimensionsComponent.height = (float) component.region.getRegionHeight() * multiplier / projectInfoVO.pixelToWorld;


        entity.add(component);

        return component;
    }
}
