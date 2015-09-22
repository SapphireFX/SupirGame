package com.sapphirefx.supirgame.ashley.EntityFactoryPacket.Prototypes;

import com.badlogic.ashley.core.Entity;
import com.sapphirefx.supirgame.ashley.components.TextureComponent;

/**
 * Created by sapphire on 21.09.15.
 */
public class PrototypeImageObject extends PrototypeObject
{
    public String imageName = "";
    public boolean isRepeat = false;
    public boolean isPolygon = false;

    public PrototypeImageObject()
    {
        super();
    }

    public PrototypeImageObject(PrototypeImageObject io)
    {
        super(io);
        imageName = new String(io.imageName);
        isRepeat = io.isRepeat;
        isPolygon = io.isPolygon;
    }

    @Override
    public void loadFromEntity(Entity entity)
    {
        super.loadFromEntity(entity);

        TextureComponent component = entity.getComponent(TextureComponent.class);
        imageName = component.regionName;
        isRepeat = component.isRepeat;
        isPolygon = component.isPolygon;
    }
}
