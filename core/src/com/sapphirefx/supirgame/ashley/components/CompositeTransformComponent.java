package com.sapphirefx.supirgame.ashley.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.math.Affine2;
import com.badlogic.gdx.math.Matrix4;

/**
 * Created by sapphire on 20.09.15.
 */
public class CompositeTransformComponent implements Component
{
    public boolean transform = true;
	public final Affine2 worldTransform = new Affine2();
	public final Matrix4 computedTransform = new Matrix4();
	public final Matrix4 oldTransform = new Matrix4();
}
