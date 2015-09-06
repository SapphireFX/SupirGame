package com.sapphirefx.supirgame.objects;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.sapphirefx.supirgame.Sprites.ManagerSprites;

/**
 * Created by sapphire on 03.09.15.
 */
public class Player extends GameObject
{
    public int life;
    public boolean canJump;
    public boolean viewOnLeft;
    private Fixture fixtureJump;

    public Player()
    {
    }

    @Override
    public void createBody()
    {

    }

    @Override
    public void draw(ManagerSprites managerSprite, Batch batch)
    {
    }

}
