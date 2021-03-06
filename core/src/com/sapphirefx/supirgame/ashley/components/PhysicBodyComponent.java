package com.sapphirefx.supirgame.ashley.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;

/**
 * Created by sapphire on 18.09.15.
 */
public class PhysicBodyComponent implements Component
{
    public Body body;

    public int bodyType;
	public float mass;
	public Vector2 centerOfMass;
	public float rotationalInertia;
	public float damping;
	public float gravityScale;
	public boolean allowSleep;
	public boolean awake;
	public boolean bullet;

	public float density;
	public float friction;
	public float restitution;

    public PhysicBodyComponent()
    {
        bodyType = 0;
        mass = 1;
        centerOfMass = new Vector2(0, 0);
        rotationalInertia = 0;
        damping = 0;
        gravityScale = 0;
        allowSleep = true;
        awake = true;
        bullet = false;
        density = 1;
        friction = 1;
        restitution = 1;
    }
}
