package com.sapphirefx.supirgame.ashley.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;

/**
 * Created by sapphire on 17.09.15.
 */
public class TransformComponent implements Component
{
	public float x;
	public float y;
	public float scaleX	=	1f;
	public float scaleY	=	1f;
	public float originX;
	public float originY;
	public float rotation;

	TransformComponent backup = null;

    public TransformComponent()
    {
    }

	public TransformComponent(TransformComponent component)
	{
		x = component.x;
		y = component.y;
		scaleX = component.scaleX;
		scaleY = component.scaleY;
		rotation = component.rotation;
		originX = component.originX;
		originY = component.originY;
	}

	public void disableTransform()
	{
		backup = new TransformComponent(this);
		x = 0;
		y = 0;
		scaleX = 1f;
		scaleY = 1f;
		rotation = 0;
	}

	public void enableTransform()
	{
		if(backup == null) return;
		x = backup.x;
		y = backup.y;
		scaleX = backup.scaleX;
		scaleY = backup.scaleY;
		rotation = backup.rotation;
		originX = backup.originX;
		originY = backup.originY;
		backup = null;
	}
}
