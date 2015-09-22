package com.sapphirefx.supirgame.levels;

import com.badlogic.ashley.core.Engine;
import com.badlogic.gdx.graphics.g2d.PolygonSpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.sapphirefx.supirgame.ashley.systems.AnimationSystem;
import com.sapphirefx.supirgame.ashley.systems.RenderingSystem;
import com.sapphirefx.supirgame.resources.ResourceManager;

/**
 * Created by sapphire on 13.09.15.
 */
public class Overlap2Dlvl
{
    private Engine ashleyEngine;
    private ResourceManager rm;
    private SceneLoader sl;

    public Overlap2Dlvl()
    {
        ashleyEngine = new Engine();
        rm = new ResourceManager();
        rm.initAllResources();
        sl = new SceneLoader(rm, ashleyEngine);
        sl.loadScene("MainScene");
        addSystems();
    }

    private void addSystems()
    {
		AnimationSystem animationSystem = new AnimationSystem();
        RenderingSystem renderer = new RenderingSystem(new PolygonSpriteBatch(2000, createDefaultShader()));

        ashleyEngine.addSystem(animationSystem);
        ashleyEngine.addSystem(renderer);
	}

    public void update(float delta)
    {
        ashleyEngine.update(delta);
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

    public Engine getAshleyEngine()
    {
        return ashleyEngine;
    }
}
