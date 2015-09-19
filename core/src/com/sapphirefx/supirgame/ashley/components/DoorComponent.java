package com.sapphirefx.supirgame.ashley.components;

import com.badlogic.ashley.core.Component;

/**
 * Created by sapphire on 18.09.15.
 */
public class DoorComponent implements Component
{
    public  int respawn; // точка относительно которой ставить героя
    public  String description; // название телепорта
    public  String destination; // куда переносить игрока
}
