package com.sapphirefx.supirgame.screens;

import com.badlogic.gdx.Screen;
import com.sapphirefx.supirgame.SupirGame;
import com.sapphirefx.supirgame.engine.EngineRenderer;
import com.sapphirefx.supirgame.levels.ManagerLVL;

/**
 * Created by sapphire on 02.09.15.
 */
public class Engine implements Screen
{
    private SupirGame screenManager;

    private EngineRenderer renderer;

    private ManagerLVL managerLVL;

    public Engine(SupirGame supirGame)
    {
        this.screenManager = supirGame;
    }

    @Override
    public void show()
    {

    }

    @Override
    public void render(float delta)
    {

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

}
