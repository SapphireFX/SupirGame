package com.sapphirefx.supirgame.levels;

/**
 * Created by sapphire on 02.09.15.
 */
public class ManagerLVL
{
    private ArrayList<GameLevel> levels; // уровни
    private ArrayList<GameLevel> savedLevels; // уровни в которых побывал герой и они уже сохранены
    private ArrayList<GameLevel> playedLevels; // уровни которые герой посетил в этой игровой сессии (кандидаты на сохранение)
    private ArrayList<GameLevel> loadLevels; // загруженные уровни
    private GameLevel currentLvl;
}
