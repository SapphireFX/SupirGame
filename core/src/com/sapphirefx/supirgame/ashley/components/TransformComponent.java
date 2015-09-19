package com.sapphirefx.supirgame.ashley.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;

/**
 * Created by sapphire on 17.09.15.
 */
public class TransformComponent implements Component
{
    public final Vector3 pos = new Vector3();
	public final Vector2 scale = new Vector2(1.0f, 1.0f);
	public float rotation = 0.0f;
}
