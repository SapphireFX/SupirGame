package com.sapphirefx.supirgame.interfaceIO;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Scaling;
import com.badlogic.gdx.utils.viewport.ScalingViewport;
import com.sapphirefx.supirgame.screens.Engine;

/**
 * Created by sapphire on 07.09.15.
 */
public class InputInterface
{
    private Engine engine; // Link

    private Texture arrow;
    private TextureRegion textureRegion;
    private Arrow left;
    private Arrow up;
    private Arrow right;
    private Group allElements;
    private Actor window;
    private Vector2 startTouch;
    private Vector2 endTouch;
    private Stage stage;
    //private HealthBar healthBar;

    private Skin skinUI;
    private TextureAtlas textureAtlasUI;
    private Table table;
    private TextButton pauseBtn;

    public InputInterface(Engine engine)
    {
        this.engine = engine;
        startTouch = new Vector2();
        endTouch = new Vector2();

        arrow = new Texture("ui/arrow.png");
        textureRegion = new TextureRegion(arrow);
        left = new Arrow(textureRegion);
        left.setSize(60 / engine.getManagerSprites().scaleRatioX, 60 / engine.getManagerSprites().scaleRatioY);
        left.setPosition(0 / engine.getManagerSprites().scaleRatioX, 30 / engine.getManagerSprites().scaleRatioY);
        left.addListener(new InputListener()
        {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button)
            {
                //engine.getPlayer().keyDown(Input.Keys.A);
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button)
            {
                //engine.getPlayer().keyUp(Input.Keys.A);
            }
        });

        right = new Arrow(textureRegion);
        right.setRotation(180);
        right.setSize(60 / engine.getManagerSprites().scaleRatioX, 60 / engine.getManagerSprites().scaleRatioY);
        right.setOrigin(right.getWidth() / 2, right.getHeight() / 2);
        right.setPosition(80 / engine.getManagerSprites().scaleRatioX, 0 / engine.getManagerSprites().scaleRatioX);
        right.addListener(new InputListener()
        {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button)
            {
                //engine.getPlayer().keyDown(Input.Keys.D);
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button)
            {
                //engine.getPlayer().keyUp(Input.Keys.D);
            }
        });

        up = new Arrow(textureRegion);
        up.setRotation(-90);
        up.setSize(60 / engine.getManagerSprites().scaleRatioX, 60 / engine.getManagerSprites().scaleRatioY);
        up.setOrigin(up.getWidth() / 2, up.getHeight() / 2);
        up.setPosition(Gdx.graphics.getWidth() - 100 / engine.getManagerSprites().scaleRatioX, 0 / engine.getManagerSprites().scaleRatioX);
        up.addListener(new InputListener()
        {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button)
            {
                //engine.getPlayer().keyDown(Input.Keys.W);
                return true;
            }
        });

        window = new Actor();
        window.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight() - 100 / engine.getManagerSprites().scaleRatioY);
        window.setPosition(0 / engine.getManagerSprites().scaleRatioX, 100 / engine.getManagerSprites().scaleRatioY);
        window.addListener(new InputListener()
        {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button)
            {
                startTouch.set(x, y);
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button)
            {
                endTouch.set(x - startTouch.x, y - startTouch.y);
                /*if (engine.getPlayer().getWeapon().canFireing())
                {
                    engine.getPlayer().getWeapon().setFireing(
                            engine.getPlayer().getPosition().x,
                            engine.getPlayer().getPosition().y, endTouch.angleRad());
                }*/
                startTouch.set(x, y);
            }
        });

        allElements = new Group();
        allElements.addActor(left);
        allElements.addActor(right);
        allElements.addActor(up);
        allElements.addActor(window);
        allElements.setScale(engine.getManagerSprites().scaleRatioX, engine.getManagerSprites().scaleRatioY);
        allElements.addListener(new InputListener()
        {
            @Override
            public boolean keyDown(InputEvent event, int keycode)
            {
                //engine.getPlayer().keyDown(keycode);
                return true;
            }

            @Override
            public boolean keyUp(InputEvent event, int keycode)
            {
                //engine.getPlayer().keyUp(keycode);
                return true;
            }
        });
/*
        healthBar = new HealthBar()
        {
            @Override
            public void draw(Batch batch, float parentAlpha)
            {
                draw((SpriteBatch)batch, engine.getRenderer().getCam().viewportWidth - 32*5, 20);
            }
        };
*/

        stage = new Stage(new ScalingViewport(Scaling.stretch,
                engine.getCam().viewportWidth,
                engine.getCam().viewportHeight),
                engine.getBatch());
        stage.addActor(allElements);
//        stage.addActor(healthBar);
        stage.setKeyboardFocus(allElements);

        textureAtlasUI = new TextureAtlas("ui/ui.pack");
        skinUI = new Skin(Gdx.files.internal("ui/ui.json"), textureAtlasUI);
        table = new Table(skinUI);
        table.setBounds(0, 200, 50, 50);
        table.debug();
        pauseBtn = new TextButton("II", skinUI);
        pauseBtn.padTop(20);
        pauseBtn.setPosition(0, 0, Align.bottomLeft);
        table.add(pauseBtn);

        stage.addActor(table);

    }

    public Stage getInput()
    {
        return stage;
    }

    public void draw()
    {
        stage.draw();
    }

    public void dispose()
    {
        arrow.dispose();
        stage.dispose();
    }
}
