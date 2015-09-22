package com.sapphirefx.supirgame.tools;

/**
 * Created by sapphire on 02.09.15.
 */
public class Constants
{
            // параметры отображения
    public final static int gameWidth = 400;
    public final static int gameHeight = 300;
            // константы для box2d
    public static final float PRECISION = 0.1f; // точность 0.1 - 10 сантиметров
    public final static float TIMESTEP = 1 / 60f;
    public final static int VELOCITYITERATIONS = 4;
    public final static int POSITIONITERATIONS = 4;
    public final static int METERS_TO_PIXELS = 32;
    public final static int METERS_HALF_PIXEL = 16;
    public final static float PIXELS_TO_METERS = 0.03125f;
    public final static float SPEEDHERO = 3;
    public final static float PLAYER_SIZE = 20;
    public final static float SPEEDMONSTER = 2f;
    public final static short COLLISION_CATEGORY_MONSTER = 0x0001;
    public final static short COLLISION_CATEGORY_BULLET  = 0x0002;
    public final static short COLLISION_CATEGORY_OBJECT  = 0x0004;
    public final static short COLLISION_CATEGORY_PLAYER  = 0x0008;

           // константы столкновений скорее всего они не нужны будут и буду использовать событийную модель
    public final static int POSITION_NONE = 0;
    public final static int POSITION_RIGHT = 1;
    public final static int POSITION_DOWN = 2;
    public final static int POSITION_LEFT = 3;
    public final static int POSITION_UP = 4;

        // константы анимаций главного героя
    public final static int ANIMAION_NONE = -1;
    public final static int ANIMAION_STAND_R = 0;
    public final static int ANIMAION_STAND_L = 1;
    public final static int ANIMAION_RUN_R = 2;
    public final static int ANIMAION_RUN_L = 3;
    public final static int ANIMAION_JUMP = 4;
    public final static int ANIMAION_FALL = 5;
        // анимационные действия для объектов
    public final static int MOVE_RIGHT = 6;
    public final static int MOVE_LEFT = 7;
    public final static int ANIMATION_DEATH = 8;

        // типы динамических объектов в игре
    public final static int OBJECT_PLAYER = 6;
    public final static int OBJECT_BULLET_GRANATE = 7;
    public final static int OBJECT_BULLET_AK47 = 8;
    public final static int OBJECT_ENEMY = 9;
    public final static int OBJECT_GRANADE = 10;
    public final static int OBJECT_AK47 = 11;
    public final static int OBJECT_RESPAWN = 12;
    public final static int OBJECT_FLY = 13;
    public final static int OBJECT_EYE = 14;
    public final static int OBJECT_BAT = 15;
    public final static int OBJECT_SPIDER = 16;
    public final static int OBJECT_EYE_TOOTH = 17;
    public final static int OBJECT_ACIDOOZE = 18;
    public final static int OBJECT_EYEBOT = 19;
    public final static int OBJECT_SWORD = 20;
    public final static int OBJECT_SWORD_ATACK = 21;
    public static final int OBJECT_DOOR = 22;

        // состояние ловушек
    public final static int TRAP_PASSIVE = 1;
    public final static int TRAP_ACTIVE = 2;
        // команды пришедшие по сети
    public final static int COMMAND_HIT = 1;
    public final static int COMMAND_KILL = 2;
    public final static int COMMAND_RESTART = 3;
    public final static int COMMAND_PAUSE = 4;
    public final static int COMMAND_EFFECT = 5;

        // параметры моделей для объектов
    public final static float OBJECT_BAT_WIDTH = 15;
    public final static float OBJECT_BAT_HEIGHT = 15;
}
