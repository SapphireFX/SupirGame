package com.sapphirefx.supirgame.ashley.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;

/**
 * Created by sapphire on 20.09.15.
 */
public class ParticleComponent implements Component
{
    public String particleName = "";
	public ParticleEffect particleEffect;
	public float worldMultiplyer = 1f;
}
