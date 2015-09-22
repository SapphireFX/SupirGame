package com.sapphirefx.supirgame.ashley.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Affine2;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.sapphirefx.supirgame.ashley.components.CompositeTransformComponent;
import com.sapphirefx.supirgame.ashley.components.MainItemComponent;
import com.sapphirefx.supirgame.ashley.components.NodeComponent;
import com.sapphirefx.supirgame.ashley.components.ParentNodeComponent;
import com.sapphirefx.supirgame.ashley.components.TintComponent;
import com.sapphirefx.supirgame.ashley.components.TransformComponent;
import com.sapphirefx.supirgame.ashley.components.ViewPortComponent;
import com.sapphirefx.supirgame.render.DrawableLogicMapper;
import com.sapphirefx.supirgame.tools.ComponentRetriever;

import box2dLight.RayHandler;

/**
 * Created by sapphire on 18.09.15.
 */
public class RenderingSystem extends IteratingSystem
{
    private final float TIME_STEP = 1f/60;

    private Batch batch; // область отрисовки
    private Camera cam; // камера

	private DrawableLogicMapper drawableLogicMapper;
    private RayHandler rayHandler;
	private World world;
	private boolean isPhysicsOn = true;
    private float accumulator = 0;
	//private Box2DDebugRenderer debugRenderer = new Box2DDebugRenderer();
    public static float timeRunning = 0;

    private ComponentMapper<ViewPortComponent> viewPortMapper = ComponentMapper.getFor(ViewPortComponent.class);
	private ComponentMapper<TransformComponent> transformM;
    private ComponentMapper<CompositeTransformComponent>  compositeTransformMapper;
    private ComponentMapper<ParentNodeComponent> parentNodeMapper;
    private ComponentMapper<NodeComponent> nodeMapper;
	private ComponentMapper<MainItemComponent> mainItemComponentMapper;

    public RenderingSystem(Batch batch)
    {
        super(Family.all(ViewPortComponent.class).get());
        this.batch = batch;
		drawableLogicMapper = new DrawableLogicMapper();

		transformM = ComponentMapper.getFor(TransformComponent.class);
		compositeTransformMapper = ComponentMapper.getFor(CompositeTransformComponent.class);
		parentNodeMapper = ComponentMapper.getFor(ParentNodeComponent.class);
		nodeMapper = ComponentMapper.getFor(NodeComponent.class);
		mainItemComponentMapper = ComponentMapper.getFor(MainItemComponent.class);
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime)
    {
        timeRunning += deltaTime;

		ViewPortComponent ViewPortComponent = viewPortMapper.get(entity);
		cam = ViewPortComponent.viewPort.getCamera();
		cam.update();

		batch.setProjectionMatrix(cam.combined);
		batch.begin();
		drawRecursively(entity, 1f);
		batch.end();

		//debugRenderer.render(world, cam.combined);

		//TODO Spine rendere thing
	}

    public OrthographicCamera getCam()
    {
        return (OrthographicCamera)cam;
    }

    public Batch getBatch()
    {
        return batch;
    }

    private void drawRecursively(Entity rootEntity, float parentAlpha)
    {
        //currentComposite = rootEntity;

        CompositeTransformComponent curCompositeTransformComponent = compositeTransformMapper.get(rootEntity);

        if (curCompositeTransformComponent.transform)
        {
            computeTransform(rootEntity);
            applyTransform(rootEntity, batch);
        }
		TintComponent tintComponent = ComponentRetriever.get(rootEntity, TintComponent.class);
		parentAlpha *= tintComponent.color.a;
        drawChildren(rootEntity, batch, curCompositeTransformComponent, parentAlpha);
        if (curCompositeTransformComponent.transform) resetTransform(rootEntity, batch);
    }

	private void drawChildren(Entity rootEntity, Batch batch, CompositeTransformComponent curCompositeTransformComponent, float parentAlpha)
    {
		NodeComponent nodeComponent = nodeMapper.get(rootEntity);
		Entity[] children = nodeComponent.children.begin();
		if (curCompositeTransformComponent.transform)
        {
			for (int i = 0, n = nodeComponent.children.size; i < n; i++)
            {
				Entity child = children[i];

				MainItemComponent childMainItemComponent = mainItemComponentMapper.get(child);
				if(!childMainItemComponent.visible)
                {
					continue;
				}

				int entityType = childMainItemComponent.entityType;
                //TODO Alpha thing

				NodeComponent childNodeComponent = nodeMapper.get(child);

				if(childNodeComponent == null)
                {
					//Find logic from the mapper and draw it
					drawableLogicMapper.getDrawable(entityType).draw(batch, child, parentAlpha);
				}else
                {
					//Step into Composite
					drawRecursively(child, parentAlpha);
				}
			}
		} else
        {
			// No transform for this group, offset each child.
			TransformComponent compositeTransform = transformM.get(rootEntity);

			float offsetX = compositeTransform.pos.x, offsetY = compositeTransform.pos.y;
			// TODO
/*
			if(viewPortMapper.has(rootEntity))
            {
				offsetX = 0;
				offsetY = 0;
			}
*/
			for (int i = 0, n = nodeComponent.children.size; i < n; i++)
            {
				Entity child = children[i];

				//TODO visibility and parent Alpha thing
				//if (!child.isVisible()) continue;
				//if (!child.isVisible()) continue;

				TransformComponent childTransformComponent = transformM.get(child);
				float cx = childTransformComponent.pos.x, cy = childTransformComponent.pos.y;
				childTransformComponent.pos.x = cx + offsetX;
				childTransformComponent.pos.y = cy + offsetY;

				NodeComponent childNodeComponent = nodeMapper.get(child);
				int entityType = mainItemComponentMapper.get(child).entityType;

				if(childNodeComponent ==null)
                {
					//Finde the logic from mapper and draw it
					drawableLogicMapper.getDrawable(entityType).draw(batch, child, parentAlpha);
				}else
                {
					//Step into Composite
					drawRecursively(child, parentAlpha);
				}
				childTransformComponent.pos.x = cx;
				childTransformComponent.pos.y = cy;

				if(childNodeComponent !=null)
                {
					drawRecursively(child, parentAlpha);
				}
			}
		}
		nodeComponent.children.end();
	}

    	/** Returns the transform for this group's coordinate system.
	 * @param rootEntity */
	protected Matrix4 computeTransform (Entity rootEntity)
	{
		CompositeTransformComponent curCompositeTransformComponent = compositeTransformMapper.get(rootEntity);
		//NodeComponent nodeComponent = nodeMapper.get(rootEntity);
		ParentNodeComponent parentNodeComponent = parentNodeMapper.get(rootEntity);
		TransformComponent curTransform = transformM.get(rootEntity);
		Affine2 worldTransform = curCompositeTransformComponent.worldTransform;
		//TODO origin thing
		float originX = 0;
		float originY = 0;
		float x = curTransform.pos.x;
		float y = curTransform.pos.y;
		float rotation = curTransform.rotation;
		float scaleX = curTransform.scale.x;
		float scaleY = curTransform.scale.y;

		worldTransform.setToTrnRotScl(x + originX, y + originY, rotation, scaleX, scaleY);
		if (originX != 0 || originY != 0) worldTransform.translate(-originX, -originY);

		// Find the first parent that transforms.

		CompositeTransformComponent parentTransformComponent = null;
		//NodeComponent parentNodeComponent;

		Entity parentEntity = null;
		if(parentNodeComponent != null)
		{
			parentEntity = parentNodeComponent.parentEntity;
		}
//		if (parentEntity != null){
//
//		}
//		while (parentEntity != null){
//			parentNodeComponent = nodeMapper.get(parentEntity);
//			if (parentTransformComponent.transform) break;
//			System.out.println("Gand");
//			parentEntity = parentNodeComponent.parentEntity;
//			parentTransformComponent = compositeTransformMapper.get(parentEntity);
//
//		}
		if (parentEntity != null)
		{
			parentTransformComponent = compositeTransformMapper.get(parentEntity);
			worldTransform.preMul(parentTransformComponent.worldTransform);
			//MainItemComponent main = parentEntity.getComponent(MainItemComponent.class);
			//System.out.println("NAME " + main.itemIdentifier);
		}

		curCompositeTransformComponent.computedTransform.set(worldTransform);
		return curCompositeTransformComponent.computedTransform;
	}

	protected void applyTransform (Entity rootEntity, Batch batch)
	{
		CompositeTransformComponent curCompositeTransformComponent = compositeTransformMapper.get(rootEntity);
		curCompositeTransformComponent.oldTransform.set(batch.getTransformMatrix());
		batch.setTransformMatrix(curCompositeTransformComponent.computedTransform);
	}

	protected void resetTransform (Entity rootEntity, Batch batch)
	{
		CompositeTransformComponent curCompositeTransformComponent = compositeTransformMapper.get(rootEntity);
		batch.setTransformMatrix(curCompositeTransformComponent.oldTransform);
	}

}
