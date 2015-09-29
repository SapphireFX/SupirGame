package com.sapphirefx.supirgame.tools;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.sapphirefx.supirgame.ashley.components.PhysicBodyComponent;

/**
 * Created by sapphire on 28.09.15.
 */
public class PhysicBodyLoader
{
    private static PhysicBodyLoader ourInstance = new PhysicBodyLoader();
    private float scale;

    public static PhysicBodyLoader getInstance()
    {
        if(ourInstance == null)
        {
            ourInstance = new PhysicBodyLoader();
        }
        return ourInstance;
    }

    private PhysicBodyLoader()
    {
    }

    public void setScaleFromPPWU(float pixelPerWU)
    {
        scale = 1f/(20f*pixelPerWU);
    }

    public static float getScale()
    {
        return getInstance().scale;
    }

    public Body createBody(World world, PhysicBodyComponent pysicsComponent, Vector2[][] minPolygonData, Vector2 mulVec)
    {
        FixtureDef fixtureDef = new FixtureDef();

        if(pysicsComponent != null)
        {
            fixtureDef.density = pysicsComponent.density;
            fixtureDef.friction = pysicsComponent.friction;
            fixtureDef.restitution = pysicsComponent.restitution;
        }

        BodyDef bodyDef = new BodyDef();
        bodyDef.position.set(0, 0);

        if(pysicsComponent.bodyType == 0)
        {
            bodyDef.type = BodyDef.BodyType.StaticBody;
        } else if (pysicsComponent.bodyType == 1)
        {
            bodyDef.type = BodyDef.BodyType.KinematicBody;
        } else
        {
            bodyDef.type = BodyDef.BodyType.DynamicBody;
        }

        Body body = world.createBody(bodyDef);

        PolygonShape polygonShape = new PolygonShape();

        for(int i = 0; i < minPolygonData.length; i++)
        {
        	float[] verts = new float[minPolygonData[i].length * 2];
        	for(int j=0;j<verts.length;j+=2)
            {
        		verts[j] = minPolygonData[i][j/2].x * mulVec.x * scale;
        		verts[j+1] = minPolygonData[i][j/2].y * mulVec.y * scale;
        	}
            polygonShape.set(verts);
            fixtureDef.shape = polygonShape;
            body.createFixture(fixtureDef);
        }
        return body;
    }
}
