package com.sapphirefx.supirgame.ashley.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.physics.box2d.Fixture;

/**
 * Created by sapphire on 17.09.15.
 */
public class PlayerComponent implements Component
{
    public int life;
    public boolean canJump;
    public boolean viewOnLeft;
    private Fixture fixtureJump;
}
