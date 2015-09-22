package com.sapphirefx.supirgame.ashley.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;

/**
 * Created by sapphire on 20.09.15.
 */
public class ShaderComponent implements Component
{
    public String shaderName;
    private ShaderProgram shaderProgram = null;

    public void setShader(String name, ShaderProgram program)
    {
        shaderName = name;
        shaderProgram = program;
    }

    public ShaderProgram getShader()
    {
        return shaderProgram;
    }

    public void clear()
    {
        shaderName = null;
        shaderProgram = null;
    }
}
