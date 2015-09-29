package com.sapphirefx.supirgame.ashley.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import com.sapphirefx.supirgame.ashley.components.PhysicBodyComponent;
import com.sapphirefx.supirgame.ashley.components.PolygonComponent;
import com.sapphirefx.supirgame.ashley.components.TransformComponent;
import com.sapphirefx.supirgame.tools.ComponentRetriever;
import com.sapphirefx.supirgame.tools.PhysicBodyLoader;

/**
 * Created by sapphire on 30.09.15.
 */
public class PhysicsSystem extends IteratingSystem
{
    protected ComponentMapper<TransformComponent> transformComponentMapper = ComponentMapper.getFor(TransformComponent.class);
    private World world;

    public PhysicsSystem(World world)
    {
        super(Family.all(PhysicBodyComponent.class).get());
        this.world = world;
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime)
    {
        TransformComponent transformComponent =  transformComponentMapper.get(entity);

		processBody(entity);

		PhysicBodyComponent physicBodyComponent = ComponentRetriever.get(entity, PhysicBodyComponent.class);
		Body body = physicBodyComponent.body;
        if(body == null) return;
		transformComponent.x = body.getPosition().x/ PhysicBodyLoader.getScale();
		transformComponent.y = body.getPosition().y/ PhysicBodyLoader.getScale();
		transformComponent.rotation = body.getAngle() * MathUtils.radiansToDegrees;
	}

	protected void processBody(Entity entity)
    {
		PhysicBodyComponent physicsBodyComponent = ComponentRetriever.get(entity, PhysicBodyComponent.class);
		PolygonComponent polygonComponent = ComponentRetriever.get(entity, PolygonComponent.class);
        TransformComponent transformComponent = ComponentRetriever.get(entity, TransformComponent.class);

		if(polygonComponent == null && physicsBodyComponent.body != null)
        {
			world.destroyBody(physicsBodyComponent.body);
			physicsBodyComponent.body = null;
		}

		if(physicsBodyComponent.body == null && polygonComponent != null)
        {
			if(polygonComponent.vertices == null) return;

			PhysicBodyComponent bodyPropertiesComponent = ComponentRetriever.get(entity, PhysicBodyComponent.class);
			physicsBodyComponent.body = PhysicBodyLoader.getInstance()
                    .createBody(world, bodyPropertiesComponent, polygonComponent.vertices, new Vector2(1, 1));

            physicsBodyComponent.body.setTransform(new Vector2(transformComponent.x * PhysicBodyLoader.getScale(),
                    transformComponent.y * PhysicBodyLoader.getScale()), physicsBodyComponent.body.getAngle());
			physicsBodyComponent.body.setUserData(entity);
		}
    }
}
