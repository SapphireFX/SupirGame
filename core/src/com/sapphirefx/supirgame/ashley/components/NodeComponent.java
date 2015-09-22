package com.sapphirefx.supirgame.ashley.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.utils.SnapshotArray;

/**
 * Created by sapphire on 20.09.15.
 */
public class NodeComponent implements Component
{
	public SnapshotArray<Entity> children = new SnapshotArray<Entity>(true, 1, Entity.class);

	public void removeChild(Entity entity)
    {
		children.removeValue(entity, false);
	}

	public void addChild(Entity entity)
    {
		children.add(entity);
	}
}
