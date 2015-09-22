package com.sapphirefx.supirgame;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.sapphirefx.supirgame.screens.GameScreen;
import com.sapphirefx.supirgame.screens.StartMenu;

public class SupirGame extends Game
{
	private Screen startMenu;
	private Screen engine;

	@Override
	public void create ()
	{
		startMenu = new StartMenu(this);
		engine = new GameScreen(this);
		setScreen(startMenu);
	}

	public void startEngine()
	{
		getScreen().pause();
		setScreen(engine);
	}

	public void startMenu()
	{
		getScreen().pause();
		setScreen(startMenu);
	}

	@Override
    public void dispose()
    {
		startMenu.dispose();
        super.dispose();
    }

	public void exit()
    {
        Gdx.app.exit();
    }

}
