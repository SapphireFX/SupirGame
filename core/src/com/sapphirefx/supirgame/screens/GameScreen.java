package com.sapphirefx.supirgame.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.sapphirefx.supirgame.levels.Overlap2Dlvl;
import com.sapphirefx.supirgame.resources.ManagerSprites;
import com.sapphirefx.supirgame.SupirGame;
import com.sapphirefx.supirgame.ashley.systems.RenderingSystem;
import com.sapphirefx.supirgame.interfaceIO.InputInterface;
import com.sapphirefx.supirgame.levels.ManagerLVL;
import com.sapphirefx.supirgame.tools.Constants;

/**
 * Created by sapphire on 02.09.15.
 */
public class GameScreen implements Screen
{
    private SupirGame screenManager; // link

    private InputInterface inputInterface;
    private World world;
    private ManagerLVL managerLVL;
    private ManagerSprites managerSprites;
    private Overlap2Dlvl overlap2Dlvl;

    public GameScreen(SupirGame supirGame)
    {
        this.screenManager = supirGame;
    }

    @Override
    public void show()
    {
        world = new World(new Vector2(0f, -10f), true);
        overlap2Dlvl = new Overlap2Dlvl();
        managerSprites = new ManagerSprites();
        //inputInterface = new InputInterface(this);
        //Gdx.input.setInputProcessor(inputInterface.getInput());
    }

    @Override
    public void render(float delta)
    {
        world.step(Constants.TIMESTEP, Constants.VELOCITYITERATIONS, Constants.POSITIONITERATIONS);
        overlap2Dlvl.update(delta);
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

    public World getWorld()
    {
        return world;
    }

    public ManagerSprites getManagerSprites()
    {
        return managerSprites;
    }

    public InputInterface getInputInterface()
    {
        return inputInterface;
    }

    public OrthographicCamera getCam()
    {
        return overlap2Dlvl.getAshleyEngine().getSystem(RenderingSystem.class).getCam();
    }

    public Batch getBatch()
    {
        return overlap2Dlvl.getAshleyEngine().getSystem(RenderingSystem.class).getBatch();
    }
}
