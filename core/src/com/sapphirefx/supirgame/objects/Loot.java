package com.sapphirefx.supirgame.objects;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;
import com.sapphirefx.supirgame.Sprites.ManagerSprites;

/**
 * Created by sapphire on 06.09.15.
 */
public class Loot extends GameObject
{

    public Loot(int type, Vector2 position)
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
