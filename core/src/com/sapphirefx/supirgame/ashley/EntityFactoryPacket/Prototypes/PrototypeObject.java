package com.sapphirefx.supirgame.ashley.EntityFactoryPacket.Prototypes;

import com.badlogic.ashley.core.Entity;
import com.sapphirefx.supirgame.ashley.components.MainItemComponent;
import com.sapphirefx.supirgame.ashley.components.PhysicBodyComponent;
import com.sapphirefx.supirgame.ashley.components.ShaderComponent;
import com.sapphirefx.supirgame.ashley.components.TintComponent;
import com.sapphirefx.supirgame.ashley.components.TransformComponent;
import com.sapphirefx.supirgame.ashley.components.ZIndexComponent;

import java.util.Arrays;

/**
 * Created by sapphire on 21.09.15.
 */
public class PrototypeObject
{
    public int uniqueId = -1;
	public String itemIdentifier = "";
	public String itemName = "";
    public String[] tags = null;
    public String customVars = "";
	public float x;
	public float y;
	public float scaleX	=	1f;
	public float scaleY	=	1f;
	public float originX	=	Float.NaN;
	public float originY	=	Float.NaN;
	public float rotation;
	public int zIndex = 0;
	public String layerName = "";
	public float[] tint = {1, 1, 1, 1};

	public String shaderName = "";

    public PrototypePhysics prototypePhysics = null;


    public PrototypeObject()
    {
    }

    public PrototypeObject(PrototypeObject obj)
    {
        uniqueId = obj.uniqueId;
        itemIdentifier = new String(obj.itemIdentifier);
        itemName = new String(obj.itemName);
        if(obj.tags != null) tags = Arrays.copyOf(obj.tags, obj.tags.length);
        customVars = new String(obj.customVars);
        x = obj.x;
        y = obj.y;
        scaleX = obj.scaleX;
        scaleY = obj.scaleY;
        originX = obj.originX;
        originY = obj.originY;
        rotation = obj.rotation;
        zIndex = obj.zIndex;
        layerName = new String(obj.layerName);
        if(obj.tint != null) tint = Arrays.copyOf(obj.tint, obj.tint.length);
        shaderName = new String(obj.shaderName);

        if(prototypePhysics != null) prototypePhysics = new PrototypePhysics(obj.prototypePhysics);
    }

    public void loadFromEntity(Entity entity)
    {
        MainItemComponent mainItemComponent = entity.getComponent(MainItemComponent.class);
        uniqueId = mainItemComponent.uniqueId;
        itemIdentifier = mainItemComponent.itemIdentifier;
        itemName = mainItemComponent.libraryLink;
        tags = new String[mainItemComponent.tags.size()];
        tags = mainItemComponent.tags.toArray(tags);
        customVars = mainItemComponent.customVars;

        TransformComponent transformComponent = entity.getComponent(TransformComponent.class);
        x = transformComponent.pos.x;
        y = transformComponent.pos.y;
        scaleX = transformComponent.scale.x;
        scaleY = transformComponent.scale.y;
        originX = transformComponent.origin.x;
        originY = transformComponent.origin.y;
        rotation = transformComponent.rotation;

        TintComponent tintComponent = entity.getComponent(TintComponent.class);
        tint = new float[4];
		tint[0] = tintComponent.color.r;
		tint[1] = tintComponent.color.g;
		tint[2] = tintComponent.color.b;
		tint[3] = tintComponent.color.a;

        ZIndexComponent ZIndexComponent = entity.getComponent(ZIndexComponent.class);
        zIndex = ZIndexComponent.getZIndex();
        layerName = ZIndexComponent.layerName;

        PhysicBodyComponent physicBodyComponent = entity.getComponent(PhysicBodyComponent.class);
        if(physicBodyComponent != null)
        {
            prototypePhysics = new PrototypePhysics();
            prototypePhysics.loadFromComponent(physicBodyComponent);
        }

        ShaderComponent shaderComponent = entity.getComponent(ShaderComponent.class);
        if(shaderComponent != null && shaderComponent.shaderName != null)
        {
            shaderName = shaderComponent.shaderName;
        }
    }

}
