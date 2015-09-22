package com.sapphirefx.supirgame.ashley.EntityFactoryPacket.Prototypes;

import com.badlogic.ashley.core.Entity;
import com.sapphirefx.supirgame.ashley.components.SpineDataComponent;

/**
 * Created by sapphire on 22.09.15.
 */
public class PrototypeSpineAnimation extends PrototypeObject
{
        public String animationName = "";
    public String currentAnimationName = "";

    public PrototypeSpineAnimation()
    {

    }

    public PrototypeSpineAnimation(PrototypeSpineAnimation vo)
    {
        super(vo);
        animationName = vo.animationName;
        currentAnimationName = vo.currentAnimationName;
    }

    @Override
    public void loadFromEntity(Entity entity)
    {
        super.loadFromEntity(entity);

        SpineDataComponent spineDataComponent = entity.getComponent(SpineDataComponent.class);
        animationName = spineDataComponent.animationName;
        currentAnimationName = spineDataComponent.currentAnimationName;
    }
}
