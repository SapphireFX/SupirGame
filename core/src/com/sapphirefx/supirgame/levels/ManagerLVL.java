package com.sapphirefx.supirgame.levels;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Base64Coder;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonWriter;
import com.sapphirefx.supirgame.screens.Engine;

import java.util.ArrayList;
import java.util.ListIterator;

/**
 * Created by sapphire on 02.09.15.
 */
public class ManagerLVL
{
    private Engine engine; // Link
    private ArrayList<GameLevel> levels; // уровни
    private ArrayList<GameLevel> savedLevels; // уровни в которых побывал герой и они уже сохранены
    private ArrayList<GameLevel> playedLevels; // уровни которые герой посетил в этой игровой сессии (кандидаты на сохранение)
    private ArrayList<GameLevel> loadLevels; // загруженные уровни
    private GameLevel currentLvl;

    private FileHandle file = Gdx.files.local("bin/savedLevels.json");

    public ManagerLVL(Engine engine)
    {
        this.engine = engine;
        levels = new ArrayList<GameLevel>();
        savedLevels = new ArrayList<GameLevel>();
        playedLevels = new ArrayList<GameLevel>();
        currentLvl = null;
        levels.add(new GameLevel(engine, "maps/newtest.tmx"));
        levels.add(new GameLevel(engine, "maps/second.tmx"));
        levels.add(new GameLevel(engine, "maps/third.tmx"));
    }

    public boolean switchLvl(String name, String door)
    {
        for (ListIterator<GameLevel> i = levels.listIterator(); i.hasNext();)
        {
            GameLevel level = i.next();
            if (level.name.equals(name))
            {
                if(level.checkDoor(door))
                {
                    if(currentLvl != null)
                    {
                        if(!playedLevels.contains(currentLvl)) // если такого уровня нет в списке
                            playedLevels.add(currentLvl); // запоминаем уровень в котором поиграли
                        reset(currentLvl); // сбрасываем объекты
                    }
                    currentLvl = level;
                    currentLvl.Load();
                    return true;
                }
            }
        }
        return false;
    }

    public GameLevel getCurrentLvl()
    {
        return currentLvl;
    }

    public Vector2 getPositionPlayer(String doorName)
    {
        return currentLvl.getPositionPlayer(doorName);
    }

    private void saveThisSession()
    {
            // все сыграные уровни добавляем для сохранения
        for (ListIterator<GameLevel> i = playedLevels.listIterator(); i.hasNext();)
        {
            savedLevels.add(i.next());
        }
        Json json = new Json();
		json.setOutputType(JsonWriter.OutputType.json);
		file.writeString(Base64Coder.encodeString(json.toJson(savedLevels)), false);
    }

    private void loadSession()
    {
		Json json = new Json();
		loadLevels = json.fromJson(ArrayList.class, GameLevel.class, Base64Coder.decodeString(file.readString()));
        System.out.println(json.prettyPrint(loadLevels));
    }

    public void reset(GameLevel lvl)
    {
        //engine.resetWorld();
        lvl.dispose();
    }
}
