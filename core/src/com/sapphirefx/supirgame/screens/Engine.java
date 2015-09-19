package com.sapphirefx.supirgame.screens;

import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.PolygonSpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.sapphirefx.supirgame.Sprites.ManagerSprites;
import com.sapphirefx.supirgame.SupirGame;
import com.sapphirefx.supirgame.ashley.systems.AnimationSystem;
import com.sapphirefx.supirgame.ashley.systems.CollisionSystem;
import com.sapphirefx.supirgame.ashley.systems.RenderingSystem;
import com.sapphirefx.supirgame.engine.EngineRenderer;
import com.sapphirefx.supirgame.interfaceIO.InputInterface;
import com.sapphirefx.supirgame.levels.ManagerLVL;
import com.sapphirefx.supirgame.tools.Constants;

/**
 * Created by sapphire on 02.09.15.
 */
public class Engine implements Screen
{
    private SupirGame screenManager; // link

    private PooledEngine ashleyEngine;
    private InputInterface inputInterface;
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
        ashleyEngine = new PooledEngine();
        ashleyEngine.addSystem(new AnimationSystem());
        //ashleyEngine.addSystem(new CollisionSystem());
        ashleyEngine.addSystem(new RenderingSystem(new PolygonSpriteBatch(2000, createDefaultShader())));

        managerSprites = new ManagerSprites();
        inputInterface = new InputInterface(this);
        Gdx.input.setInputProcessor(inputInterface.getInput());
    }

    @Override
    public void render(float delta)
    {
        world.step(Constants.TIMESTEP, Constants.VELOCITYITERATIONS, Constants.POSITIONITERATIONS);
        ashleyEngine.update(delta);
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

    	/** Returns a new instance of the default shader used by SpriteBatch for GL2 when no shader is specified. */
	static public ShaderProgram createDefaultShader () {
		String vertexShader = "attribute vec4 " + ShaderProgram.POSITION_ATTRIBUTE + ";\n" //
			+ "attribute vec4 " + ShaderProgram.COLOR_ATTRIBUTE + ";\n" //
			+ "attribute vec2 " + ShaderProgram.TEXCOORD_ATTRIBUTE + "0;\n" //
			+ "uniform mat4 u_projTrans;\n" //
			+ "varying vec4 v_color;\n" //
			+ "varying vec2 v_texCoords;\n" //
			+ "\n" //
			+ "void main()\n" //
			+ "{\n" //
			+ "   v_color = " + ShaderProgram.COLOR_ATTRIBUTE + ";\n" //
			+ "   v_color.a = v_color.a * (255.0/254.0);\n" //
			+ "   v_texCoords = " + ShaderProgram.TEXCOORD_ATTRIBUTE + "0;\n" //
			+ "   gl_Position =  u_projTrans * " + ShaderProgram.POSITION_ATTRIBUTE + ";\n" //
			+ "}\n";
		String fragmentShader = "#ifdef GL_ES\n" //
			+ "#define LOWP lowp\n" //
			+ "precision mediump float;\n" //
			+ "#else\n" //
			+ "#define LOWP \n" //
			+ "#endif\n" //
			+ "varying LOWP vec4 v_color;\n" //
			+ "varying vec2 v_texCoords;\n" //
			+ "uniform sampler2D u_texture;\n" //
			+ "uniform vec2 atlasCoord;\n" //
			+ "uniform vec2 atlasSize;\n" //
			+ "uniform int isRepeat;\n" //
			+ "void main()\n"//
			+ "{\n" //
			+ "vec4 textureSample = vec4(0.0,0.0,0.0,0.0);\n"//
			+ "if(isRepeat == 1)\n"//
			+ "{\n"//
			+ "textureSample = v_color * texture2D(u_texture, atlasCoord+mod(v_texCoords, atlasSize));\n"//
			+ "}\n"//
			+ "else\n"//
			+ "{\n"//
			+ "textureSample = v_color * texture2D(u_texture, v_texCoords);\n"//
			+ "}\n"//
			+ "  gl_FragColor = textureSample;\n" //
			+ "}";

		ShaderProgram shader = new ShaderProgram(vertexShader, fragmentShader);
		if (shader.isCompiled() == false) throw new IllegalArgumentException("Error compiling shader: " + shader.getLog());
		return shader;
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
        return ashleyEngine.getSystem(RenderingSystem.class).getCam();
    }

    public Batch getBatch()
    {
        return ashleyEngine.getSystem(RenderingSystem.class).getBatch();
    }
}
