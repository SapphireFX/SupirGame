package com.sapphirefx.supirgame.ashley.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.utils.SnapshotArray;
import com.sapphirefx.supirgame.ashley.components.CompositeTransformComponent;
import com.sapphirefx.supirgame.ashley.components.DimensionsComponent;
import com.sapphirefx.supirgame.ashley.components.NodeComponent;
import com.sapphirefx.supirgame.ashley.components.TransformComponent;

/**
 * Created by sapphire on 24.09.15.
 */
public class ResizeCompositeSystem extends IteratingSystem
{

    private ComponentMapper<DimensionsComponent> dimensionsMapper;
	private ComponentMapper<TransformComponent> transformMapper;
	private ComponentMapper<NodeComponent> nodeMapper;

	private DimensionsComponent dimensionsComponent;
	private TransformComponent transformComponent;
	private NodeComponent nodeComponent;

    public ResizeCompositeSystem()
    {
        super(Family.all(CompositeTransformComponent.class).get());

		dimensionsMapper = ComponentMapper.getFor(DimensionsComponent.class);
		transformMapper = ComponentMapper.getFor(TransformComponent.class);
		nodeMapper = ComponentMapper.getFor(NodeComponent.class);
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime)
    {
        dimensionsComponent = dimensionsMapper.get(entity);
		nodeComponent = nodeMapper.get(entity);
		recalculateSize();
    }

    public void recalculateSize()
    {
        float lowerX = 0, lowerY = 0, upperX = 0, upperY = 0;
        SnapshotArray<Entity> entities = nodeComponent.children;
        for (int i = 0; i < entities.size; i++)
        {
            Entity entity = entities.get(i);
            transformComponent = transformMapper.get(entity);
            DimensionsComponent childDimentionsComponent = dimensionsMapper.get(entity);
            if (i == 0)
            {
                if (transformComponent.scaleX > 0 && childDimentionsComponent.width * transformComponent.scaleX > 0)
                {
                    lowerX = transformComponent.x;
                    upperX = transformComponent.x + childDimentionsComponent.width * transformComponent.scaleX;
                } else
                {
                    upperX = transformComponent.x;
                    lowerX = transformComponent.x + childDimentionsComponent.width * transformComponent.scaleX;
                }

                if (transformComponent.scaleY > 0 && childDimentionsComponent.height * transformComponent.scaleY > 0)
                {
                    lowerY = transformComponent.y;
                    upperY = transformComponent.y + childDimentionsComponent.height * transformComponent.scaleY;
                } else
                {
                    upperY = transformComponent.y;
                    lowerY = transformComponent.y + childDimentionsComponent.height * transformComponent.scaleY;
                }
            }
            if (transformComponent.scaleX > 0 && childDimentionsComponent.width > 0)
            {
                if (lowerX > transformComponent.x) lowerX = transformComponent.x;
                if (upperX < transformComponent.x + childDimentionsComponent.width * transformComponent.scaleX) upperX = transformComponent.x + childDimentionsComponent.width * transformComponent.scaleX;
            } else
            {
                if (upperX < transformComponent.x) upperX = transformComponent.x;
                if (lowerX > transformComponent.x + childDimentionsComponent.width * transformComponent.scaleX) lowerX = transformComponent.x + childDimentionsComponent.width * transformComponent.scaleX;
            }
            if (transformComponent.scaleY > 0 && childDimentionsComponent.height * transformComponent.scaleY > 0)
            {
                if (lowerY > transformComponent.y) lowerY = transformComponent.y;
                if (upperY < transformComponent.y + childDimentionsComponent.height * transformComponent.scaleY) upperY = transformComponent.y + childDimentionsComponent.height * transformComponent.scaleY;
            } else
            {
                if (upperY < transformComponent.y) upperY = transformComponent.y;
                if (lowerY > transformComponent.y + childDimentionsComponent.height * transformComponent.scaleY) lowerY = transformComponent.y + childDimentionsComponent.height * transformComponent.scaleY;
            }
        }

        dimensionsComponent.width = (upperX - 0);
        dimensionsComponent.height = (upperY - 0);
    }
}
