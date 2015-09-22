package com.sapphirefx.supirgame.ashley.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;

/**
 * Created by sapphire on 17.09.15.
 */
public class TransformComponent implements Component
{
    public Vector3 pos;
	public Vector2 scale;
	public Vector2 origin;
	public float rotation;

	public TransformComponent()
	{
		pos = new Vector3();
		scale = new Vector2(1.0f, 1.0f);
		origin = new Vector2();
		rotation = 0.0f;
	}
}
