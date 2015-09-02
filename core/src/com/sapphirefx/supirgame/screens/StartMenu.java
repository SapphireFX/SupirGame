package com.sapphirefx.supirgame.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.sapphirefx.supirgame.SupirGame;

/**
 * Created by sapphire on 02.09.15.
 */
public class StartMenu implements Screen
{
    private SupirGame screenManager; // ссылка на менеджер окон чтобы просить его переключать их

	private Stage stage;
    private Table table;
    private TextureAtlas textureAtlas;
    private Skin skin;
    private TextButton playBtn, exitBtn;

    public StartMenu(SupirGame supirGame)
    {
        this.screenManager = supirGame;
    }

    @Override
    public void show()
    {
        stage = new Stage();
        Gdx.input.setInputProcessor(stage);

        textureAtlas = new TextureAtlas("ui/ui.pack");
        skin = new Skin(Gdx.files.internal("ui/ui.json"), textureAtlas);

        table = new Table(skin);
        table.setBounds(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        //table.debug();

        playBtn = new TextButton("Play", skin);
        playBtn.getLabelCell().padTop(20);
        playBtn.addListener(new ClickListener()
        {
            @Override
            public void clicked(InputEvent event, float x, float y)
            {
                screenManager.startEngine();
            }
        });
        exitBtn = new TextButton("Exit", skin);
        exitBtn.getLabelCell().padTop(20);
        exitBtn.addListener(new ClickListener()
        {
            @Override
            public void clicked(InputEvent event, float x, float y)
            {
                screenManager.exit();
            }
        });

        table.add(playBtn);
        table.add().row();
        table.add(exitBtn);

        stage.addActor(table);

    }

    @Override
    public void render(float delta)
    {
        Gdx.gl.glClearColor(1,1,1,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        stage.act(Gdx.graphics.getDeltaTime());
        stage.draw();
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
        stage.dispose();
        textureAtlas.dispose();
        skin.dispose();
    }
}
