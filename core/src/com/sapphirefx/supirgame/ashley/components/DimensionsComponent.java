package com.sapphirefx.supirgame.ashley.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

/**
 * Created by sapphire on 20.09.15.
 */
public class DimensionsComponent implements Component
{
    public float width = 0;
    public float height = 0;

    public Rectangle boundBox;
    public Polygon polygon;
}
