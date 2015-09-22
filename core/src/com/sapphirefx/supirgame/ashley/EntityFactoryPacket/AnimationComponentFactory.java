package com.sapphirefx.supirgame.ashley.EntityFactoryPacket;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.sapphirefx.supirgame.ashley.EntityFactoryPacket.Prototypes.PrototypeAnimationObject;
import com.sapphirefx.supirgame.ashley.EntityFactoryPacket.Prototypes.PrototypeObject;
import com.sapphirefx.supirgame.ashley.components.AnimationComponent;
import com.sapphirefx.supirgame.ashley.components.AnimationStateComponent;
import com.sapphirefx.supirgame.ashley.components.DimensionsComponent;
import com.sapphirefx.supirgame.ashley.components.TextureComponent;
import com.sapphirefx.supirgame.resources.IResourceRetriever;
import com.sapphirefx.supirgame.resources.ResourceManager;
import com.sapphirefx.supirgame.tools.FrameRange;
import com.sapphirefx.supirgame.tools.ProjectInfo;
import com.sapphirefx.supirgame.tools.ResolutionEntryVO;

import java.util.HashMap;

import box2dLight.RayHandler;

/**
 * Created by sapphire on 21.09.15.
 */
public class AnimationComponentFactory extends ComponentFactory
{
    public AnimationComponentFactory(IResourceRetriever rm, World world, RayHandler rayHandler)
    {
        super(rm, world, rayHandler);
    }

    @Override
    public void createComponents(Entity root, Entity entity, PrototypeObject po)
    {
        createCommonComponents(entity, po, EntityFactory.SPRITE_ANIMATION_TYPE);
        createParentNodeComponent(root, entity);
        createNodeComponent(root, entity);
        createSpriteAnimationDataComponent(entity, (PrototypeAnimationObject) po);
    }

    @Override
    protected DimensionsComponent createDimensionsComponent(Entity entity, PrototypeObject po)
    {
        DimensionsComponent component = new DimensionsComponent();

        PrototypeAnimationObject sVo = (PrototypeAnimationObject) po;
        Array<TextureAtlas.AtlasRegion> regions = rm.getSpriteAnimation(sVo.animationName).getRegions();

        ResolutionEntryVO resolutionEntryVO = rm.getLoadedResolution();
        ProjectInfo projectInfoVO = rm.getProjectVO();
        float multiplier = resolutionEntryVO.getMultiplier(rm.getProjectVO().originalResolution);
        component.width = (float) regions.get(0).getRegionWidth() * multiplier / projectInfoVO.pixelToWorld;
        component.height = (float) regions.get(0).getRegionHeight() * multiplier / projectInfoVO.pixelToWorld;

        entity.add(component);
        return component;
    }

    protected AnimationComponent createSpriteAnimationDataComponent(Entity entity, PrototypeAnimationObject ao)
    {
        AnimationComponent spriteAnimationComponent = new AnimationComponent();
        spriteAnimationComponent.animationName = ao.animationName;

        spriteAnimationComponent.frameRangeMap = new HashMap<String, FrameRange>();
        for(int i = 0; i < ao.frameRangeMap.size(); i++)
        {
            spriteAnimationComponent.frameRangeMap.put(ao.frameRangeMap.get(i).name, ao.frameRangeMap.get(i));
        }
        spriteAnimationComponent.fps = ao.fps;
        spriteAnimationComponent.currentAnimation = ao.currentAnimation;

        if(ao.playMode == 0) spriteAnimationComponent.playMode = Animation.PlayMode.NORMAL;
        if(ao.playMode == 1) spriteAnimationComponent.playMode = Animation.PlayMode.REVERSED;
        if(ao.playMode == 2) spriteAnimationComponent.playMode = Animation.PlayMode.LOOP;
        if(ao.playMode == 3) spriteAnimationComponent.playMode = Animation.PlayMode.LOOP_REVERSED;
        if(ao.playMode == 4) spriteAnimationComponent.playMode = Animation.PlayMode.LOOP_PINGPONG;
        if(ao.playMode == 5) spriteAnimationComponent.playMode = Animation.PlayMode.LOOP_RANDOM;
        if(ao.playMode == 6) spriteAnimationComponent.playMode = Animation.PlayMode.NORMAL;

        Array<TextureAtlas.AtlasRegion> regions = rm.getSpriteAnimation(spriteAnimationComponent.animationName).getRegions();

        AnimationComponent animationComponent = new AnimationComponent();
        AnimationStateComponent stateComponent = new AnimationStateComponent(regions);

        if(spriteAnimationComponent.frameRangeMap.isEmpty())
        {
            spriteAnimationComponent.frameRangeMap.put("Default", new FrameRange("Default", 0, regions.size-1));
        }
        if(spriteAnimationComponent.currentAnimation == null)
        {
            spriteAnimationComponent.currentAnimation = (String) spriteAnimationComponent.frameRangeMap.keySet().toArray()[0];
        }
        if(spriteAnimationComponent.playMode == null)
        {
            spriteAnimationComponent.playMode = Animation.PlayMode.LOOP;
        }

        stateComponent.set(spriteAnimationComponent);

        TextureComponent textureRegionComponent = new TextureComponent();
        textureRegionComponent.region = regions.get(0);

        entity.add(textureRegionComponent);
        entity.add(stateComponent);
        entity.add(animationComponent);
        entity.add(spriteAnimationComponent);

        return spriteAnimationComponent;
    }
}
