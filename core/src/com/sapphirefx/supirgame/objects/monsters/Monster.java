package com.sapphirefx.supirgame.objects.monsters;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;
import com.sapphirefx.supirgame.Sprites.ManagerSprites;
import com.sapphirefx.supirgame.objects.GameObject;

/**
 * Created by sapphire on 05.09.15.
 */
public class Monster extends GameObject
{

    public Monster(int type, Vector2 position)
    {
        this.type = type;
        this.position = position;
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
