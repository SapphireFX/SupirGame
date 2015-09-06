package com.sapphirefx.supirgame.objects;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;
import com.sapphirefx.supirgame.Sprites.ManagerSprites;

/**
 * Created by sapphire on 05.09.15.
 */
public class Door extends GameObject
{

    public  int respawn; // точка относительно которой ставить героя
    public  String description; // название телепорта
    public  String destination; // куда переносить игрока

    public Door(int type , Vector2 position, String description, String destination, int respawn)
    {
        this.type = type;
        this.position = position;
        this.description = description;
        this.destination = destination;
        this.respawn = respawn;
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
