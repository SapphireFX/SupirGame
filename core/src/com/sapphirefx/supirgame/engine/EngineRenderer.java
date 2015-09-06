package com.sapphirefx.supirgame.engine;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.RayCastCallback;
import com.badlogic.gdx.utils.Array;
import com.sapphirefx.supirgame.screens.Engine;
import com.sapphirefx.supirgame.tools.Constants;

import java.util.ArrayList;
import box2dLight.RayHandler;

/**
 * Created by sapphire on 02.09.15.
 */
public class EngineRenderer
{
    private Engine engine; // link

    private SpriteBatch batch; // область отрисовки
    private OrthographicCamera cam; // камера
    private OrthographicCamera camForLight; // камера
    private OrthogonalTiledMapRenderer mapRenderer; // отрисовщик для карты
    private ShapeRenderer shapeRenderer;  // отрисовщик геометрических фигур
    private Box2DDebugRenderer debugRenderer;
    private Array<Body> bodies;
    private ArrayList<ParticleEffect> effects;
    private ParticleEffect linkOnEffect;
    private RayHandler rayHandler;
    private RayCastCallback callBackRay;

    public EngineRenderer(Engine engine)
    {
        this.engine = engine;
        mapRenderer = new OrthogonalTiledMapRenderer(null);
        batch = (SpriteBatch)mapRenderer.getBatch();
        cam = new OrthographicCamera(Constants.gameWidth, Constants.gameHeight);
        cam.update();

        debugRenderer = new Box2DDebugRenderer();

        effects = new ArrayList<ParticleEffect>();

        bodies = new Array<Body>();

        shapeRenderer = new ShapeRenderer();
    }

    public void render()
    {
        Gdx.gl.glClearColor(0,0,0,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
    }

}
