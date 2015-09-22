package com.sapphirefx.supirgame.render;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.sapphirefx.supirgame.ashley.components.DimensionsComponent;
import com.sapphirefx.supirgame.ashley.components.NinePatchComponent;
import com.sapphirefx.supirgame.ashley.components.TintComponent;
import com.sapphirefx.supirgame.ashley.components.TransformComponent;

/**
 * Created by sapphire on 20.09.15.
 */
public class NinePatchDrawableLogic implements Drawable
{
    private ComponentMapper<TintComponent> tintMapper;
    private ComponentMapper<NinePatchComponent> ninePatchMapper;
    private ComponentMapper<TransformComponent> transformMapper;
    private ComponentMapper<DimensionsComponent> dimensionMapper;

    public NinePatchDrawableLogic()
    {
        tintMapper = ComponentMapper.getFor(TintComponent.class);
        ninePatchMapper = ComponentMapper.getFor(NinePatchComponent.class);
        transformMapper = ComponentMapper.getFor(TransformComponent.class);
        dimensionMapper = ComponentMapper.getFor(DimensionsComponent.class);
    }

    @Override
    public void draw(Batch batch, Entity entity, float parentAlpha)
    {
        TintComponent tintComponent = tintMapper.get(entity);
        NinePatchComponent entityNinePatchComponent = ninePatchMapper.get(entity);
        TransformComponent entityTransformComponent = transformMapper.get(entity);
        DimensionsComponent entityDimensionsComponent = dimensionMapper.get(entity);
        batch.setColor(tintComponent.color);

        System.out.println("heeeeelp");
        System.out.println("+" + entityDimensionsComponent);

        entityNinePatchComponent.ninePatch.draw(batch,
                entityTransformComponent.pos.x, entityTransformComponent.pos.y,
                entityDimensionsComponent.width, entityDimensionsComponent.height);

    }
}
