package com.sapphirefx.supirgame.ashley.components;

import com.badlogic.ashley.core.Component;
import com.sapphirefx.supirgame.ashley.EntityFactoryPacket.Prototypes.PrototypeLightObject.LightType;

import box2dLight.Light;

/**
 * Created by sapphire on 21.09.15.
 */
public class LightObjectComponent implements Component
{
    private LightType type;

	public LightObjectComponent(LightType type)
    {
		this.type = type;
	}

	public int rays = 12;
	public float distance = 300;
	public float directionDegree = 0;
	public float coneDegree = 30;
	public float softnessLength = 1f;
	public boolean isStatic = true;
	public boolean isXRay = true;
	public Light lightObject = null;

	public LightType getType()
    {
		return type;
	}

}
