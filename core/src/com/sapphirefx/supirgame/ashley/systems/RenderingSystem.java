package com.sapphirefx.supirgame.ashley.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Batch;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Array;
import com.sapphirefx.supirgame.ashley.components.TextureComponent;
import com.sapphirefx.supirgame.ashley.components.TransformComponent;
import com.sapphirefx.supirgame.tools.Constants;

import java.util.Comparator;

/**
 * Created by sapphire on 18.09.15.
 */
public class RenderingSystem extends IteratingSystem
{
    private Batch batch; // область отрисовки
    private OrthographicCamera cam; // камера

    private Array<Entity> renderQueue;
    private Comparator<Entity> comparator;
    private ComponentMapper<TextureComponent> textureM;
	private ComponentMapper<TransformComponent> transformM;

    public RenderingSystem(Batch batch)
    {
        super(Family.all(TextureComponent.class, TransformComponent.class).get());
        this.batch = batch;
        cam = new OrthographicCamera(Constants.gameWidth, Constants.gameHeight);
        cam.update();

        textureM = ComponentMapper.getFor(TextureComponent.class);
		transformM = ComponentMapper.getFor(TransformComponent.class);
        renderQueue = new Array<Entity>();

        comparator = new Comparator<Entity>()
        {
			@Override
			public int compare(Entity entityA, Entity entityB)
            {
				return (int)Math.signum(transformM.get(entityB).pos.z - transformM.get(entityA).pos.z);
			}
		};


    }

    @Override
    protected void processEntity(Entity entity, float deltaTime)
    {
        renderQueue.add(entity);
    }

    public OrthographicCamera getCam()
    {
        return cam;
    }

    public Batch getBatch()
    {
        return batch;
    }


    @Override
    public void update(float deltaTime)
    {
        super.update(deltaTime);
        renderQueue.sort(comparator);

        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        cam.update();
        batch.setProjectionMatrix(cam.combined);
		batch.begin();

        		for (Entity entity : renderQueue) {
			TextureComponent tex = textureM.get(entity);

			if (tex.region == null) {
				continue;
			}

			TransformComponent t = transformM.get(entity);

			float width = tex.region.getRegionWidth();
			float height = tex.region.getRegionHeight();
			float originX = width * 0.5f;
			float originY = height * 0.5f;

			batch.draw(tex.region,
					   t.pos.x - originX, t.pos.y - originY,
					   originX, originY,
					   width, height,
					   t.scale.x * Constants.PIXELS_TO_METERS, t.scale.y * Constants.PIXELS_TO_METERS,
					   MathUtils.radiansToDegrees * t.rotation);
		}

		batch.end();
		renderQueue.clear();
    }
}
