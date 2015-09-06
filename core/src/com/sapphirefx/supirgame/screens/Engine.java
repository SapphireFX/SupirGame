package com.sapphirefx.supirgame.screens;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.sapphirefx.supirgame.Sprites.ManagerSprites;
import com.sapphirefx.supirgame.SupirGame;
import com.sapphirefx.supirgame.engine.EngineRenderer;
import com.sapphirefx.supirgame.levels.ManagerLVL;
import com.sapphirefx.supirgame.tools.Constants;

/**
 * Created by sapphire on 02.09.15.
 */
public class Engine implements Screen
{
    private SupirGame screenManager; // link

    private EngineRenderer renderer;
    private World world;
    private ManagerLVL managerLVL;
    private ManagerSprites managerSprites;

    public Engine(SupirGame supirGame)
    {
        this.screenManager = supirGame;
    }

    @Override
    public void show()
    {
        world = new World(new Vector2(0f, -10f), true);
        managerLVL = new ManagerLVL(this);
        renderer = new EngineRenderer(this);
    }

    @Override
    public void render(float delta)
    {
        world.step(Constants.TIMESTEP, Constants.VELOCITYITERATIONS, Constants.POSITIONITERATIONS);
        renderer.render();
    }

    @Override
    public void resize(int width, int height)
    {

    }

    @Override
    public void pause()
    {

    }

    @Override
    public void resume()
    {

    }

    @Override
    public void hide()
    {

    }

    @Override
    public void dispose()
    {

    }

    public EngineRenderer getRenderer()
    {
        return renderer;
    }

    public ManagerLVL getManagerLVL()
    {
        return managerLVL;
    }

    public World getWorld()
    {
        return world;
    }

    public ManagerSprites getManagerSprites()
    {
        return managerSprites;
    }
}
