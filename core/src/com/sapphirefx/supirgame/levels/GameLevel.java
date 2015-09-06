package com.sapphirefx.supirgame.levels;

import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.objects.EllipseMapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.math.Ellipse;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.sapphirefx.supirgame.objects.Door;
import com.sapphirefx.supirgame.objects.GameObject;
import com.sapphirefx.supirgame.objects.Loot;
import com.sapphirefx.supirgame.objects.monsters.Monster;
import com.sapphirefx.supirgame.screens.Engine;
import com.sapphirefx.supirgame.tools.Constants;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.ListIterator;

/**
 * Created by sapphire on 03.05.15.
 */
public class GameLevel
{
    private transient Engine engine; // link
    private transient TiledMap map;
    private ArrayList<Loot> objects; // массив объектов на уровне
    private ArrayList<Door> doors; // дверки на карте
    private ArrayList<Monster> monsters; // монстрики на карте
    private String path; // путь до файла с картой
    public String name; // имя карты

        // использую для серриализации
    public GameLevel()
    {
        engine = null;
        objects = new ArrayList<Loot>();
        doors = new ArrayList<Door>();
        monsters = new ArrayList<Monster>();
        path = "";
        name = "";
    }

    public GameLevel(Engine engine, String path)
    {
        this.engine = engine;
        this.path = path;
        objects = new ArrayList<Loot>();
        doors = new ArrayList<Door>();
        monsters = new ArrayList<Monster>();
        preLoad();
    }

    private void preLoad()
    {
        map = new TmxMapLoader().load(path);
        name = (String)map.getProperties().get("name");

        // preLoad objects on map
        TiledMapTileLayer objectLayer = (TiledMapTileLayer)map.getLayers().get("objects");
        if(objectLayer != null)
            for(int i=0; i<objectLayer.getWidth(); i++)
            {
                for(int j=0; j<objectLayer.getHeight(); j++)
                {
                    if(objectLayer.getCell(i, j) != null && objectLayer.getCell(i, j).getTile().getProperties().containsKey("respawn"))
                    {
                        objects.add(new Loot(Constants.OBJECT_RESPAWN, new Vector2(i, j)));
                    }
                    if(objectLayer.getCell(i, j) != null && objectLayer.getCell(i, j).getTile().getProperties().containsKey("granade"))
                    {
                        objects.add(new Loot(Constants.OBJECT_GRANADE, new Vector2(i, j)));
                    }
                    if(objectLayer.getCell(i, j) != null && objectLayer.getCell(i, j).getTile().getProperties().containsKey("ak47"))
                    {
                        objects.add(new Loot(Constants.OBJECT_AK47, new Vector2(i, j)));
                    }
                }
            }

        // preLoad doors
        MapObjects doorsLayer = (MapObjects)map.getLayers().get("doors").getObjects();
        if(doorsLayer != null)
            for(Iterator<MapObject> i = doorsLayer.iterator(); i.hasNext();)
            {
                MapObject someObject = i.next();
                Rectangle rectangle = ((RectangleMapObject) someObject).getRectangle();
                int respawn = Constants.POSITION_NONE;
                if(someObject.getProperties().get("respawn").equals("right")) respawn = Constants.POSITION_RIGHT;
                else if(someObject.getProperties().get("respawn").equals("down")) respawn = Constants.POSITION_DOWN;
                else if(someObject.getProperties().get("respawn").equals("left")) respawn = Constants.POSITION_LEFT;
                else if(someObject.getProperties().get("respawn").equals("up")) respawn = Constants.POSITION_UP;
                doors.add(new Door(Constants.OBJECT_DOOR,new Vector2(rectangle.getX(), rectangle.getY()),
                        (String)someObject.getProperties().get("door"),
                        (String)someObject.getProperties().get("level"),
                        respawn));
            }

        // preload monsters
        MapObjects monstersLayer = (MapObjects)map.getLayers().get("monsters").getObjects();
        if(monstersLayer != null)
            for(Iterator<MapObject> i = monstersLayer.iterator(); i.hasNext();)
            {
                MapObject someObject = i.next();
                Ellipse ellipse = ((EllipseMapObject)someObject).getEllipse();
                int typeMonster = -1;
                if (someObject.getProperties().get("monster").equals("bat")) typeMonster = Constants.OBJECT_BAT;
                monsters.add(new Monster(typeMonster, new Vector2(ellipse.x * Constants.PIXELS_TO_METERS, ellipse.y * Constants.PIXELS_TO_METERS)));
            }

        // объекты презагрузили и можно пока освбодить память для карты уровня
        map.dispose();
    }

    /**
     * create all physic objects
     */
    public TiledMap Load()
    {
        map = new TmxMapLoader().load(path);

        // создаем пол, стенки, потолки
        MapObjects collisionObjects = (MapObjects)map.getLayers().get("collision").getObjects();
        if(collisionObjects != null)
            for(Iterator<MapObject> i = collisionObjects.iterator(); i.hasNext();)
            {
                MapObject someObject = i.next();
                if(someObject instanceof RectangleMapObject)
                {
                    Rectangle rectangle = ((RectangleMapObject) someObject).getRectangle();
                    BodyDef bodyDef = new BodyDef();
                    bodyDef.type = BodyDef.BodyType.StaticBody;
                    bodyDef.position.set((rectangle.getX() + rectangle.getWidth()/2 - 16f) * Constants.PIXELS_TO_METERS,
                            (rectangle.getY() + rectangle.getHeight()/2 - 16f) * Constants.PIXELS_TO_METERS );
                    PolygonShape polygonShape = new PolygonShape();
                    polygonShape.setAsBox(rectangle.getWidth() * Constants.PIXELS_TO_METERS / 2,
                            rectangle.getHeight() * Constants.PIXELS_TO_METERS / 2);
                    FixtureDef fixtureDef = new FixtureDef();
                    fixtureDef.shape = polygonShape;
                    fixtureDef.filter.categoryBits = Constants.COLLISION_CATEGORY_OBJECT;
                    fixtureDef.filter.maskBits = Constants.COLLISION_CATEGORY_BULLET |
                            Constants.COLLISION_CATEGORY_OBJECT | Constants.COLLISION_CATEGORY_PLAYER | Constants.COLLISION_CATEGORY_MONSTER;
                    Body body = engine.getWorld().createBody(bodyDef);
                    body.createFixture(fixtureDef);
                    body.setActive(true);
                    polygonShape.dispose();
                }else
                {
                    System.out.println("Объект не распознан");
                }
            }
        // объекты на карте
        TiledMapTileLayer objectLayer = (TiledMapTileLayer)map.getLayers().get("objects");
        for (ListIterator<Loot> i = objects.listIterator(); i.hasNext();)
        {
            GameObject object = i.next();

            BodyDef bodyDef = new BodyDef();
            bodyDef.type = BodyDef.BodyType.StaticBody;
            bodyDef.position.set(object.position);
            CircleShape circleShape = new CircleShape();
            circleShape.setRadius(0.5f);
            FixtureDef fixtureDef = new FixtureDef();
            fixtureDef.shape = circleShape;
            fixtureDef.isSensor = true;
            fixtureDef.filter.categoryBits = Constants.COLLISION_CATEGORY_OBJECT;
            fixtureDef.filter.maskBits = Constants.COLLISION_CATEGORY_OBJECT | Constants.COLLISION_CATEGORY_PLAYER;
            Body body = engine.getWorld().createBody(bodyDef);
            body.createFixture(fixtureDef).setUserData(object);
            body.setActive(true);
            object.isActive = true;
            circleShape.dispose();
        }

        // создаем doors
        for (ListIterator<Door> i = doors.listIterator(); i.hasNext();)
        {
            Door door = i.next();
            BodyDef bodyDef = new BodyDef();
            bodyDef.type = BodyDef.BodyType.StaticBody;
            bodyDef.position.set(door.position.x * Constants.PIXELS_TO_METERS, door.position.y * Constants.PIXELS_TO_METERS);
            PolygonShape polygonShape = new PolygonShape();
            if(door.respawn == Constants.POSITION_UP || door.respawn == Constants.POSITION_DOWN) polygonShape.setAsBox(0.5f, 0.1f);
            else if(door.respawn == Constants.POSITION_LEFT || door.respawn == Constants.POSITION_RIGHT) polygonShape.setAsBox(0.1f, 0.5f);
            FixtureDef fixtureDef = new FixtureDef();
            fixtureDef.shape = polygonShape;
            fixtureDef.isSensor = true;
            Body body = engine.getWorld().createBody(bodyDef);
            body.createFixture(fixtureDef).setUserData(door);
            body.setActive(true);
            polygonShape.dispose();
        }

        return map;
    }

    public TiledMap getMap()
    {
        return map;
    }

    public ArrayList<Monster> loadMonsters()
    {
        for (ListIterator<Monster> i = monsters.listIterator(); i.hasNext();)
        {
            Monster monster = i.next();
            monster.createBody();
        }
        return monsters;
    }

    public boolean checkDoor(String doorName)
    {
        for (ListIterator<Door> i = doors.listIterator(); i.hasNext();)
        {
            Door door = i.next();
            if (door.description.equals(doorName)) return true;
        }
        return false;
    }

    public Vector2 getPositionPlayer(String doorName)
    {
        for (ListIterator<Door> i = doors.listIterator(); i.hasNext();)
        {
            Door door = i.next();
            if (door.description.equals(doorName))
            {
                Vector2 position = new Vector2(door.position.x * Constants.PIXELS_TO_METERS, door.position.y * Constants.PIXELS_TO_METERS);
                System.out.println(position);
                switch (door.respawn)
                {
                    case Constants.POSITION_RIGHT:
                        position.x += 1;
                        break;
                    case Constants.POSITION_LEFT:
                        position.x -= 1;
                        break;
                    case Constants.POSITION_UP:
                        position.y += 1;
                        break;
                    case Constants.POSITION_DOWN:
                        position.y -= 1;
                        break;
                }
                return position;
            }
        }
        return new Vector2(1, 1);
    }

    public void dispose()
    {
        map.dispose();
    }
}
