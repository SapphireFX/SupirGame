package com.sapphirefx.supirgame.ashley.EntityFactoryPacket;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.sapphirefx.supirgame.ashley.EntityFactoryPacket.Prototypes.CompositeComponents;
import com.sapphirefx.supirgame.ashley.EntityFactoryPacket.Prototypes.Prototype9PatchObject;
import com.sapphirefx.supirgame.ashley.EntityFactoryPacket.Prototypes.PrototypeAnimationObject;
import com.sapphirefx.supirgame.ashley.EntityFactoryPacket.Prototypes.PrototypeCompositeObject;
import com.sapphirefx.supirgame.ashley.EntityFactoryPacket.Prototypes.PrototypeImageObject;
import com.sapphirefx.supirgame.ashley.EntityFactoryPacket.Prototypes.PrototypeLabelObject;
import com.sapphirefx.supirgame.ashley.EntityFactoryPacket.Prototypes.PrototypeLightObject;
import com.sapphirefx.supirgame.ashley.EntityFactoryPacket.Prototypes.PrototypeParticleEffectObject;
import com.sapphirefx.supirgame.ashley.components.CompositeTransformComponent;
import com.sapphirefx.supirgame.ashley.components.MainItemComponent;
import com.sapphirefx.supirgame.ashley.components.TransformComponent;
import com.sapphirefx.supirgame.ashley.components.ViewPortComponent;
import com.sapphirefx.supirgame.resources.IResourceRetriever;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

import box2dLight.RayHandler;

/**
 * Created by sapphire on 21.09.15.
 */
public class EntityFactory
{
    public static final int IMAGE_TYPE 		        = 1;
	public static final int LABEL_TYPE 		        = 2;
	public static final int SPRITE_ANIMATION_TYPE   = 3;
	public static final int COMPOSITE_TYPE 	        = 6;
	public static final int PARTICLE_TYPE 	        = 7;
	public static final int LIGHT_TYPE 		        = 8;
	public static final int NINE_PATCH 		        = 9;

    public RayHandler rayHandler;
	public World world;
	public IResourceRetriever rm = null;

	ComponentFactory compositeComponentFactory, lightComponentFactory, particleEffectComponentFactory, simpleImageComponentFactory,
            spriteComponentFactory,labelComponentFactory, ninePatchComponentFactory ;

    private HashMap<Integer, ComponentFactory> externalFactories = new HashMap<Integer, ComponentFactory>();

    private HashMap<Integer, Entity> entities = new HashMap<Integer, Entity>();

    public ComponentFactory getCompositeComponentFactory()
    {
		return compositeComponentFactory;
	}

    public EntityFactory(IResourceRetriever rm, World world, RayHandler rayHandler)
    {
        this.rayHandler = rayHandler;
        this.world = world;
        this.rm = rm;

		compositeComponentFactory = new CompositeComponentFactory(rm, world, rayHandler);
		lightComponentFactory = new LightComponentFactory(rm , world, rayHandler);
		particleEffectComponentFactory = new ParticleEffectComponentFactory(rm, world, rayHandler);
		simpleImageComponentFactory = new SimpleImageComponentFactory(rm, world, rayHandler);
		spriteComponentFactory = new AnimationComponentFactory(rm, world, rayHandler);
		labelComponentFactory = new LabelComponentFactory(rm, world, rayHandler);
		ninePatchComponentFactory = new NinePatchComponentFactory(rm, world, rayHandler);
	}


	public Entity createEntity(Entity root, PrototypeImageObject vo)
    {

		Entity entity = new Entity();

		simpleImageComponentFactory.createComponents(root, entity, vo);

		postProcessEntity(entity);

		return entity;
	}

	public Entity createEntity(Entity root, Prototype9PatchObject vo)
    {

		Entity entity = new Entity();

		ninePatchComponentFactory.createComponents(root, entity, vo);

		postProcessEntity(entity);

		return entity;
	}

	public Entity createEntity(Entity root, PrototypeLabelObject vo)
    {

		Entity entity = new Entity();

		labelComponentFactory.createComponents(root, entity, vo);

		postProcessEntity(entity);

		return entity;
	}

	public Entity createEntity(Entity root, PrototypeParticleEffectObject vo)
    {

		Entity entity = new Entity();

		particleEffectComponentFactory.createComponents(root, entity, vo);

		postProcessEntity(entity);

		return entity;
	}

	public Entity createEntity(Entity root, PrototypeLightObject vo)
    {

		Entity entity = new Entity();

		lightComponentFactory.createComponents(root, entity, vo);

		postProcessEntity(entity);

		return entity;
	}

	public Entity createEntity(Entity root, PrototypeAnimationObject vo)
    {

		Entity entity = new Entity();

		spriteComponentFactory.createComponents(root, entity, vo);

		postProcessEntity(entity);

		return entity;
	}

	public Entity createEntity(Entity root, PrototypeCompositeObject vo)
    {

		Entity entity = new Entity();

		compositeComponentFactory.createComponents(root, entity, vo);

		postProcessEntity(entity);

		return entity;
	}

	public Entity createRootEntity(CompositeComponents compositeObject, Viewport viewport)
    {

        PrototypeCompositeObject vo = new PrototypeCompositeObject();
		vo.composite = compositeObject;

		Entity entity = new Entity();

		compositeComponentFactory.createComponents(null, entity, vo);
		CompositeTransformComponent compositeTransform = new CompositeTransformComponent();
		TransformComponent transform = new TransformComponent();

		ViewPortComponent viewPortComponent = new ViewPortComponent();
		viewPortComponent.viewPort = viewport;

		//TODO: not sure if this line is okay
		viewPortComponent.viewPort.update(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), true);

		entity.add(transform);
		entity.add(viewPortComponent);

		postProcessEntity(entity);

		return entity;
	}

	public Integer postProcessEntity(Entity entity)
    {
		ComponentMapper<MainItemComponent> mainItemComponentComponentMapper = ComponentMapper.getFor(MainItemComponent.class);
		MainItemComponent mainItemComponent = mainItemComponentComponentMapper.get(entity);
		if(mainItemComponent.uniqueId == -1) mainItemComponent.uniqueId = getFreeId();
		entities.put(mainItemComponent.uniqueId, entity);

		return mainItemComponent.uniqueId;
	}

	private int getFreeId()
    {
		if(entities == null || entities.size() == 0) return 1;
		ArrayList<Integer> ids = new ArrayList<Integer>(entities.keySet());
		Collections.sort(ids);
		for(int i = 1; i < ids.size(); i++) {
			if(ids.get(i)-ids.get(i-1) > 1) {
				return ids.get(i-1)+1;
			}
		}
		return ids.get(ids.size()-1)+1;
	}

	public Integer updateMap(Entity entity)
    {
		ComponentMapper<MainItemComponent> mainItemComponentComponentMapper = ComponentMapper.getFor(MainItemComponent.class);
		MainItemComponent mainItemComponent = mainItemComponentComponentMapper.get(entity);
		entities.put(mainItemComponent.uniqueId, entity);

		return mainItemComponent.uniqueId;
	}

	public void initAllChildren(Engine engine, Entity entity, CompositeComponents vo)
    {
		for (int i = 0; i < vo.sImages.size(); i++)
        {
			Entity child = createEntity(entity, vo.sImages.get(i));
			engine.addEntity(child);
		}

		for (int i = 0; i < vo.sImage9patchs.size(); i++)
        {
			Entity child = createEntity(entity, vo.sImage9patchs.get(i));
			engine.addEntity(child);
		}

		for (int i = 0; i < vo.sLabels.size(); i++)
        {
			Entity child = createEntity(entity, vo.sLabels.get(i));
			engine.addEntity(child);
		}

		for (int i = 0; i < vo.sParticleEffects.size(); i++)
        {
			Entity child = createEntity(entity, vo.sParticleEffects.get(i));
			engine.addEntity(child);
		}

		for (int i = 0; i < vo.sLights.size(); i++)
        {
			Entity child = createEntity(entity, vo.sLights.get(i));
			engine.addEntity(child);
		}

		for (int i = 0; i < vo.sSpriteAnimations.size(); i++)
        {
			Entity child = createEntity(entity, vo.sSpriteAnimations.get(i));
			engine.addEntity(child);
		}

		for (int i = 0; i < vo.sComposites.size(); i++)
        {
			Entity child = createEntity(entity, vo.sComposites.get(i));
			engine.addEntity(child);
			initAllChildren(engine, child, vo.sComposites.get(i).composite);
		}
	}

	public Entity getEntityByUniqueId(Integer id)
    {
		return entities.get(id);
	}

}
