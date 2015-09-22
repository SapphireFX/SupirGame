package com.sapphirefx.supirgame.ashley.EntityFactoryPacket;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.sapphirefx.supirgame.ashley.EntityFactoryPacket.Prototypes.PrototypeObject;
import com.sapphirefx.supirgame.ashley.components.*;
import com.sapphirefx.supirgame.resources.IResourceRetriever;

import java.util.Arrays;
import java.util.HashSet;

import box2dLight.RayHandler;

/**
 * Created by sapphire on 21.09.15.
 */
public abstract class ComponentFactory
{
    protected IResourceRetriever rm;
    protected World world;
    protected RayHandler rayHandler;
    protected ComponentMapper<NodeComponent> nodeComponentMapper;

    public ComponentFactory(IResourceRetriever rm, World world, RayHandler rayHandler)
    {
        this.rm = rm;
        this.world = world;
        this.rayHandler = rayHandler;
        nodeComponentMapper = ComponentMapper.getFor(NodeComponent.class);
    }

    public abstract void createComponents(Entity root, Entity entity, PrototypeObject po);

    protected void createCommonComponents(Entity entity, PrototypeObject po, int entityType)
    {
        DimensionsComponent dimensionsComponent = createDimensionsComponent(entity, po);
        createMainItemComponent(entity, po, entityType);
        createTransformComponent(entity, po, dimensionsComponent);
        createTintComponent(entity, po);
        createZindexComponent(entity, po);
        //createScriptComponent(entity, po);
        //createMeshComponent(entity, po);
        createPhysicsComponents(entity, po);
        createShaderComponent(entity, po);
    }

    protected abstract DimensionsComponent createDimensionsComponent(Entity entity, PrototypeObject po);

    protected ShaderComponent createShaderComponent(Entity entity, PrototypeObject po)
    {
        if (po.shaderName == null || po.shaderName.isEmpty())
        {
            return null;
        }
        ShaderComponent component = new ShaderComponent();
        component.setShader(po.shaderName, rm.getShaderProgram(po.shaderName));
        entity.add(component);
        return component;
    }

    protected MainItemComponent createMainItemComponent(Entity entity, PrototypeObject po, int entityType)
    {
        MainItemComponent component = new MainItemComponent();
        component.customVars = po.customVars;
        component.uniqueId = po.uniqueId;
        component.itemIdentifier = po.itemIdentifier;
        component.libraryLink = po.itemName;
        if (po.tags != null)
        {
            component.tags = new HashSet<String>(Arrays.asList(po.tags));
        }
        component.entityType = entityType;

        entity.add(component);

        return component;
    }

    protected TransformComponent createTransformComponent(Entity entity, PrototypeObject po, DimensionsComponent dimensionsComponent)
    {
        TransformComponent component = new TransformComponent();
        component.rotation = po.rotation;
        component.scale.x = po.scaleX;
        component.scale.y = po.scaleY;
        component.pos.x = po.x;
        component.pos.y = po.y;

        if (Float.isNaN(po.originX)) component.origin.x = dimensionsComponent.width / 2f;
        else component.pos.x = po.originX;

        if (Float.isNaN(po.originY)) component.origin.y = dimensionsComponent.height / 2f;
        else component.pos.y = po.originY;

        entity.add(component);

        return component;
    }

    protected TintComponent createTintComponent(Entity entity, PrototypeObject vo)
    {
        TintComponent component = new TintComponent();
        component.color.set(vo.tint[0], vo.tint[1], vo.tint[2], vo.tint[3]);

        entity.add(component);

        return component;
    }

    protected ZIndexComponent createZindexComponent(Entity entity, PrototypeObject po)
    {
        ZIndexComponent component = new ZIndexComponent();

        if(po.layerName == "" || po.layerName == null) po.layerName = "Default";

        component.layerName = po.layerName;
        component.setZIndex(po.zIndex);
        component.needReOrder = false;
        entity.add(component);

        return component;
    }

    protected void createNodeComponent(Entity root, Entity entity)
    {
        NodeComponent component = nodeComponentMapper.get(root);
        component.children.add(entity);
    }

    protected void createPhysicsComponents(Entity entity, PrototypeObject po)
    {
        if(po.prototypePhysics == null)
        {
            return;
        }
        createPhysicsBodyPropertiesComponent(entity, po);
    }

    protected PhysicBodyComponent createPhysicsBodyPropertiesComponent(Entity entity, PrototypeObject po)
    {
        BodyDef bodyDef = new BodyDef();
        FixtureDef fixtureDef = new FixtureDef();

        Body body = world.createBody(bodyDef);
        body.createFixture(fixtureDef);
        PhysicBodyComponent component = new PhysicBodyComponent(body);

        entity.add(component);

        return component;
    }

    protected ParentNodeComponent createParentNodeComponent(Entity root, Entity entity)
    {
        ParentNodeComponent component = new ParentNodeComponent();
        component.parentEntity = root;
        entity.add(component);

        //set visible to true depending on parent
        // TODO: I do not likes this part
        //MainItemComponent mainItemComponent = ComponentRetriever.get(entity, MainItemComponent.class);
        //LayerMapComponent layerMapComponent = ComponentRetriever.get(root, LayerMapComponent.class);
        //ZIndexComponent zIndexComponent = ComponentRetriever.get(root, ZIndexComponent.class);
        //mainItemComponent.visible = layerMapComponent.isVisible(zIndexComponent.layerName);

        return component;
    }

}