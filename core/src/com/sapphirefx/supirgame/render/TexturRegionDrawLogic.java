package com.sapphirefx.supirgame.render;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.PolygonSpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.sapphirefx.supirgame.ashley.components.DimensionsComponent;
import com.sapphirefx.supirgame.ashley.components.ShaderComponent;
import com.sapphirefx.supirgame.ashley.components.TextureComponent;
import com.sapphirefx.supirgame.ashley.components.TintComponent;
import com.sapphirefx.supirgame.ashley.components.TransformComponent;

/**
 * Created by sapphire on 20.09.15.
 */
public class TexturRegionDrawLogic implements Drawable
{
    private ComponentMapper<TintComponent> tintComponentComponentMapper;
	private ComponentMapper<TextureComponent> textureRegionMapper;
	private ComponentMapper<TransformComponent> transformMapper;
	private ComponentMapper<DimensionsComponent> dimensionsComponentComponentMapper;
	private ComponentMapper<ShaderComponent> shaderComponentMapper;

	public TexturRegionDrawLogic()
    {
        tintComponentComponentMapper = ComponentMapper.getFor(TintComponent.class);
		textureRegionMapper = ComponentMapper.getFor(TextureComponent.class);
		transformMapper = ComponentMapper.getFor(TransformComponent.class);
		dimensionsComponentComponentMapper = ComponentMapper.getFor(DimensionsComponent.class);
		shaderComponentMapper = ComponentMapper.getFor(ShaderComponent.class);
	}

	@Override
	public void draw(Batch batch, Entity entity, float parentAlpha)
    {
        TextureComponent entityTextureRegionComponent = textureRegionMapper.get(entity);
        if(shaderComponentMapper.has(entity))
        {
			ShaderComponent shaderComponent = shaderComponentMapper.get(entity);
            if(shaderComponent.getShader() != null)
            {
                batch.setShader(shaderComponent.getShader());
                GL20 gl = Gdx.gl20;
                int error;
                if ((error = gl.glGetError()) != GL20.GL_NO_ERROR)
                {
                    Gdx.app.log("opengl", "Error: " + error);
                    Gdx.app.log("opengl", shaderComponent.getShader().getLog());
                    throw new RuntimeException( ": glError " + error);
                }
            }
		}

        if(entityTextureRegionComponent.polygonSprite != null)
        {
//            if(entityTextureRegionComponent.isRepeat) {
            	drawTiledPolygonSprite(batch, entity);
//            } else {
//                drawPolygonSprite(batch, entity);
//            }
        } else
        {
            drawSprite(batch, entity, parentAlpha);
        }
        drawSprite(batch, entity, parentAlpha);

        if(shaderComponentMapper.has(entity))
        {
			batch.setShader(null);
		}
	}

    public void drawSprite(Batch batch, Entity entity, float parentAlpha)
    {
        TransformComponent entityTransformComponent = transformMapper.get(entity);
        TextureComponent entityTextureRegionComponent = textureRegionMapper.get(entity);
        DimensionsComponent dimensionsComponent = dimensionsComponentComponentMapper.get(entity);

        batch.draw(entityTextureRegionComponent.region,
                entityTransformComponent.pos.x, entityTransformComponent.pos.y,
                entityTransformComponent.origin.x, entityTransformComponent.origin.y,
                dimensionsComponent.width, dimensionsComponent.height,
                entityTransformComponent.scale.x, entityTransformComponent.scale.y,
                entityTransformComponent.rotation);
    }

    public void drawPolygonSprite(Batch batch, Entity entity) {
        TintComponent tintComponent = tintComponentComponentMapper.get(entity);
        TransformComponent entityTransformComponent = transformMapper.get(entity);
        TextureComponent entityTextureRegionComponent = textureRegionMapper.get(entity);

        DimensionsComponent dimensionsComponent = dimensionsComponentComponentMapper.get(entity);

        entityTextureRegionComponent.polygonSprite.setPosition(entityTransformComponent.pos.x, entityTransformComponent.pos.y);
        entityTextureRegionComponent.polygonSprite.setRotation(entityTransformComponent.rotation);
        entityTextureRegionComponent.polygonSprite.setOrigin(entityTransformComponent.origin.x, entityTransformComponent.origin.y);
        entityTextureRegionComponent.polygonSprite.setColor(tintComponent.color);
        entityTextureRegionComponent.polygonSprite.draw((PolygonSpriteBatch) batch);
    }

    public void drawTiledPolygonSprite(Batch batch, Entity entity)
    {
    	batch.flush();
        TintComponent tintComponent = tintComponentComponentMapper.get(entity);
        TransformComponent entityTransformComponent = transformMapper.get(entity);
        TextureComponent entityTextureRegionComponent = textureRegionMapper.get(entity);

        DimensionsComponent dimensionsComponent = dimensionsComponentComponentMapper.get(entity);
        float ppwu = dimensionsComponent.width/entityTextureRegionComponent.region.getRegionWidth();

        Vector2 atlasCoordsVector = new Vector2(entityTextureRegionComponent.region.getU(), entityTextureRegionComponent.region.getV());
        Vector2 atlasSizeVector = new Vector2(entityTextureRegionComponent.region.getU2()-entityTextureRegionComponent.region.getU(), entityTextureRegionComponent.region.getV2()-entityTextureRegionComponent.region.getV());

        batch.getShader().setUniformi("isRepeat", 1);
        batch.getShader().setUniformf("atlasCoord", atlasCoordsVector);
    	batch.getShader().setUniformf("atlasSize", atlasSizeVector);

        batch.setColor(tintComponent.color);
        entityTextureRegionComponent.polygonSprite.setOrigin(entityTransformComponent.origin.x, entityTransformComponent.origin.y);
        entityTextureRegionComponent.polygonSprite.setPosition(entityTransformComponent.pos.x, entityTransformComponent.pos.y);
        entityTextureRegionComponent.polygonSprite.setRotation(entityTransformComponent.rotation);
        entityTextureRegionComponent.polygonSprite.setScale(ppwu);
        entityTextureRegionComponent.polygonSprite.draw((PolygonSpriteBatch) batch);
        batch.flush();
        batch.getShader().setUniformi("isRepeat", 0);

    }
}
