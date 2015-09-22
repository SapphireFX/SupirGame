package com.sapphirefx.supirgame.resources;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.sapphirefx.supirgame.tools.Constants;

/**
 * Created by sapphire on 03.09.15.
 */
public class ManagerSprites
{

    public float scaleRatioX;
    public float scaleRatioY;

    private float animationElapsed; // тайминг для спрайтов с анимацией
    private Texture textureBulleteGranade;
    private Sprite bulleteGaranade;

    private Sprite granadeWeapon;
    private Sprite respawnDot;
    private Sprite ak47Weapon;
    private Texture textureBulleteAk47;
    private Sprite bulleteAk47;

    private TextureAtlas atlas_stand;
    private TextureAtlas atlas_move_L;
    private Animation hero_move_L;
    private Animation hero_move_R;
    private Animation hero_stand_r;
    private Animation hero_stand_l;

    private TextureAtlas atlas_monster;
    private Animation bat_move_Left;
    private Animation bat_move_Right;

    /**
     * инициализация всех спрайтов в игре
     */
    public ManagerSprites ()
    {
        scaleRatioX = (float) Constants.gameWidth / (float)Gdx.graphics.getWidth();
        scaleRatioY = (float) Constants.gameHeight / (float)Gdx.graphics.getHeight();
/*
        textureBulleteGranade = new Texture("img/granade.png");
        bulleteGaranade = new Sprite(textureBulleteGranade, 0,0,32,32);
        bulleteGaranade.setOriginCenter();
        bulleteGaranade.setSize(20, 20);
        //bulleteGaranade.setScale(scaleRatioX, scaleRatioY);
        granadeWeapon = new Sprite();
        granadeWeapon.setScale(scaleRatioX, scaleRatioY);

        respawnDot = new Sprite();
        ak47Weapon = new Sprite();
        textureBulleteAk47 = new Texture("img/sphere.png");
        bulleteAk47 = new Sprite(textureBulleteAk47, 0,0,64,64);
        bulleteAk47.setSize(10, 10);
        //bulleteAk47.setScale(scaleRatioX, scaleRatioY);

           // Ниже инициализируются все анимашки
        Array<Sprite> sprites;
        // анимация героя движения влево
        atlas_move_L = new TextureAtlas(Gdx.files.internal("animations/hero_move.pack"));
        sprites = new Array<Sprite>();
        sprites = atlas_move_L.createSprites();
        for (int i=0; i<sprites.size; i++)
        {
            sprites.get(i).setSize(Constants.PLAYER_SIZE, Constants.PLAYER_SIZE);
            sprites.get(i).setOriginCenter();
            sprites.get(i).setScale(scaleRatioX, scaleRatioY);
        }
        hero_move_L = new Animation(0.5f, sprites);
        // анимация героя движения вправо
        sprites = new Array<Sprite>();
        sprites = atlas_move_L.createSprites();
        for (int i=0; i<sprites.size; i++)
        {
            sprites.get(i).setSize(Constants.PLAYER_SIZE, Constants.PLAYER_SIZE);
            sprites.get(i).flip(true, false);
            sprites.get(i).setOriginCenter();
            sprites.get(i).setScale(scaleRatioX, scaleRatioY);
        }
        hero_move_R = new Animation(0.5f, sprites);
        // анимацмя движения монстрика влево
        atlas_monster = new TextureAtlas(Gdx.files.internal("animations/monster.txt"));
        sprites = new Array<Sprite>();
        sprites = atlas_monster.createSprites();
        for (int i=0; i<sprites.size; i++)
        {
            sprites.get(i).setSize(Constants.PLAYER_SIZE, Constants.PLAYER_SIZE);
            sprites.get(i).setOriginCenter();
            sprites.get(i).setScale(scaleRatioX, scaleRatioY);
        }
        bat_move_Left = new Animation(0.8f, sprites);
        // анимацмя движения монстрика вправо
        atlas_monster = new TextureAtlas(Gdx.files.internal("animations/bats_fly.atlas"));
        sprites = new Array<Sprite>();
        sprites = atlas_monster.createSprites();
        for (int i=0; i<sprites.size; i++)
        {
            sprites.get(i).setSize(32f,32f);
            sprites.get(i).flip(true, false);
            sprites.get(i).setScale(scaleRatioX, scaleRatioY);
        }
        bat_move_Right = new Animation(0.8f, sprites);
        atlas_stand = new TextureAtlas(Gdx.files.internal("animations/stand_hero.atlas"));
        sprites = new Array<Sprite>();
        sprites = atlas_stand.createSprites();
        for (int i=0; i<sprites.size; i++)
        {
            sprites.get(i).setSize(32f,32f);
            sprites.get(i).setScale(scaleRatioX, scaleRatioY);
        }
        hero_stand_l = new Animation(0.8f, sprites);
        sprites = new Array<Sprite>();
        sprites = atlas_stand.createSprites();
        for (int i=0; i<sprites.size; i++)
        {
            sprites.get(i).setSize(32f,32f);
            sprites.get(i).flip(true, false);
            sprites.get(i).setScale(scaleRatioX, scaleRatioY);
        }
        hero_stand_r = new Animation(0.8f, sprites);

/*
        // грузим текстурки (надо запихнуть в менеджер текстур)
        TiledMapTileLayer objectLayer = (TiledMapTileLayer)map.getLayers().get("objects");
        for (ListIterator<LevelObject> i = objects.listIterator(); i.hasNext();)
        {
            MapObject someObject = i.next();
            switch (object.type)
            {
                case Constants.OBJECT_RESPAWN:
                    rememberSprite(objectLayer.getCell((int) object.position.x, (int) object.position.y).
                            getTile().getTextureRegion(), Constants.OBJECT_RESPAWN);
                    break;
                case Constants.OBJECT_GRANADE:
                    rememberSprite(objectLayer.getCell((int) object.position.x, (int) object.position.y).
                            getTile().getTextureRegion(), Constants.OBJECT_GRANADE);
                    break;
                case Constants.OBJECT_AK47:
                    rememberSprite(objectLayer.getCell((int) object.position.x, (int) object.position.y).
                            getTile().getTextureRegion(), Constants.OBJECT_AK47);
                    break;
            }
        }
*/

    }

    /**
     * загружает спрайты из вне этого класса
     * @param which - тип спрайта
     */
    public void rememberSprite(Sprite sprite, int which)
    {
        switch (which)
        {
            case Constants.OBJECT_GRANADE:
                granadeWeapon.set(sprite);
                break;
            case Constants.OBJECT_AK47:
                ak47Weapon.set(sprite);
                break;
            case Constants.OBJECT_BULLET_GRANATE:
                bulleteGaranade.set(sprite);
                bulleteGaranade.setOriginCenter();
                break;
            case Constants.OBJECT_BULLET_AK47:
                bulleteAk47.set(sprite);
                bulleteAk47.setOriginCenter();
                break;
            case Constants.OBJECT_RESPAWN:
                respawnDot.set(sprite);
                break;
        }
    }
    public void rememberSprite(Texture texture, int which)
    {
        rememberSprite(new Sprite(texture), which);
    }
    public void rememberSprite(TextureRegion textureRegion, int which)
    {
        rememberSprite(new Sprite(textureRegion), which);
    }

    /**
     * Отрисовка объекта
     * @param which - тип объекта
     * @param action - что этот объект делает
     * @param position - позиция объекта
     * @param angle - угол (если объект наклонен)
     * @param batch - холст отрисовки
     */
    public void draw(int which, int action, Vector2 position, float angle, SpriteBatch batch, ControllerAnimation controllerAnimation)
    {
        switch (which)
        {
            case Constants.OBJECT_GRANADE:
                granadeWeapon.setPosition(position.x * Constants.METERS_TO_PIXELS + Constants.METERS_HALF_PIXEL - granadeWeapon.getWidth()/2,
                        position.y * Constants.METERS_TO_PIXELS + Constants.METERS_HALF_PIXEL - granadeWeapon.getHeight()/2);
                granadeWeapon.draw(batch);
                break;
            case Constants.OBJECT_AK47:
                ak47Weapon.setPosition(position.x * Constants.METERS_TO_PIXELS + Constants.METERS_HALF_PIXEL - ak47Weapon.getWidth(),
                        position.y * Constants.METERS_TO_PIXELS + Constants.METERS_HALF_PIXEL - ak47Weapon.getHeight());
                ak47Weapon.draw(batch);
                break;
            case Constants.OBJECT_BULLET_GRANATE:
                bulleteGaranade.setPosition(position.x*Constants.METERS_TO_PIXELS + Constants.METERS_HALF_PIXEL - bulleteGaranade.getWidth()/2,
                        position.y*Constants.METERS_TO_PIXELS + Constants.METERS_HALF_PIXEL - bulleteGaranade.getHeight()/2);
                bulleteGaranade.setRotation(angle);
                bulleteGaranade.draw(batch);
                break;
            case Constants.OBJECT_BULLET_AK47:
                bulleteAk47.setPosition(position.x*Constants.METERS_TO_PIXELS + Constants.METERS_HALF_PIXEL - bulleteAk47.getWidth()/2,
                        position.y*Constants.METERS_TO_PIXELS + Constants.METERS_HALF_PIXEL - bulleteAk47.getHeight()/2);
                bulleteAk47.setRotation(angle);
                bulleteAk47.draw(batch);
                break;
            case Constants.OBJECT_RESPAWN:
                respawnDot.setPosition(position.x * Constants.METERS_TO_PIXELS + Constants.METERS_HALF_PIXEL - respawnDot.getWidth(),
                        position.y * Constants.METERS_TO_PIXELS + Constants.METERS_HALF_PIXEL - respawnDot.getHeight());
                respawnDot.draw(batch);
                break;
            case Constants.ANIMAION_RUN_L:
                batch.draw(hero_move_L.getKeyFrame(animationElapsed, true), position.x + Constants.METERS_HALF_PIXEL - Constants.PLAYER_SIZE/2,
                        position.y + Constants.METERS_HALF_PIXEL - Constants.PLAYER_SIZE/2, Constants.PLAYER_SIZE, Constants.PLAYER_SIZE);
                break;
            case Constants.ANIMAION_RUN_R:
                batch.draw(hero_move_R.getKeyFrame(animationElapsed, true), position.x + Constants.METERS_HALF_PIXEL - Constants.PLAYER_SIZE/2,
                        position.y + Constants.METERS_HALF_PIXEL - Constants.PLAYER_SIZE/2, Constants.PLAYER_SIZE, Constants.PLAYER_SIZE);
                break;
            case Constants.ANIMAION_STAND_L:
                batch.draw(hero_stand_l.getKeyFrame(animationElapsed, true), position.x + Constants.METERS_HALF_PIXEL - Constants.PLAYER_SIZE/2,
                        position.y + Constants.METERS_HALF_PIXEL - Constants.PLAYER_SIZE/2, Constants.PLAYER_SIZE, Constants.PLAYER_SIZE);
                break;
            case Constants.ANIMAION_STAND_R:
                batch.draw(hero_stand_r.getKeyFrame(animationElapsed, true), position.x + Constants.METERS_HALF_PIXEL - Constants.PLAYER_SIZE/2,
                        position.y + Constants.METERS_HALF_PIXEL - Constants.PLAYER_SIZE/2, Constants.PLAYER_SIZE, Constants.PLAYER_SIZE);
                break;
            case Constants.OBJECT_FLY:
                if(action == Constants.MOVE_LEFT) ;
                else if(action == Constants.MOVE_RIGHT) ;
                break;
            case Constants.OBJECT_BAT:
                if(action == Constants.MOVE_LEFT)
                    batch.draw(bat_move_Left.getKeyFrame(animationElapsed, true),
                        position.x + Constants.OBJECT_BAT_WIDTH * Constants.PIXELS_TO_METERS / 2,
                        position.y + Constants.OBJECT_BAT_HEIGHT * Constants.PIXELS_TO_METERS / 2,
                        Constants.OBJECT_BAT_WIDTH,  Constants.OBJECT_BAT_HEIGHT);
                else if(action == Constants.MOVE_RIGHT)
                    batch.draw(bat_move_Right.getKeyFrame(animationElapsed, true),
                        position.x + Constants.OBJECT_BAT_WIDTH * Constants.PIXELS_TO_METERS / 2,
                        position.y + Constants.OBJECT_BAT_HEIGHT * Constants.PIXELS_TO_METERS / 2,
                        Constants.OBJECT_BAT_WIDTH, Constants.OBJECT_BAT_HEIGHT);
                else if(action == Constants.ANIMATION_DEATH)
                {
                    batch.draw(bat_move_Right.getKeyFrame(controllerAnimation.timeAnimation, true),
                            position.x + Constants.OBJECT_BAT_WIDTH * Constants.PIXELS_TO_METERS / 2,
                            position.y + Constants.OBJECT_BAT_HEIGHT * Constants.PIXELS_TO_METERS / 2,
                            Constants.OBJECT_BAT_WIDTH,Constants.OBJECT_BAT_HEIGHT);
                    if(bat_move_Right.isAnimationFinished(controllerAnimation.timeAnimation))
                    {
                        controllerAnimation.isActive = false;
                    }
                }
                break;
            case Constants.OBJECT_EYE:
                if(action == Constants.MOVE_LEFT) ;
                else if(action == Constants.MOVE_RIGHT) ;
                break;
            case Constants.OBJECT_SPIDER:
                if(action == Constants.MOVE_LEFT) ;
                else if(action == Constants.MOVE_RIGHT) ;
                break;
            case Constants.OBJECT_EYE_TOOTH:
                if(action == Constants.MOVE_LEFT) ;
                else if(action == Constants.MOVE_RIGHT) ;
                break;
            case Constants.OBJECT_EYEBOT:
                if(action == Constants.MOVE_LEFT) ;
                else if(action == Constants.MOVE_RIGHT) ;
                break;
            case Constants.OBJECT_ACIDOOZE:
                if(action == Constants.MOVE_LEFT) ;
                else if(action == Constants.MOVE_RIGHT) ;
                break;
        }
        animationElapsed += Gdx.graphics.getDeltaTime();
        if(controllerAnimation != null)
        {
            controllerAnimation.timeAnimation += Gdx.graphics.getDeltaTime();
            System.out.println();
        }
    }
    public void draw(int which, int action, float positionX, float positionY, float angle, SpriteBatch batch, ControllerAnimation controllerAnimation)
    {
        draw(which, action, new Vector2(positionX, positionY), angle, batch, controllerAnimation);
    }

    /**
     * Задает размеры спрайтов
     * @param which - для типа спрайта
     * @param radius - размер спрайта (спрайтик квадратный и это одна из сторон)
     */
    public void setSize(int which, float radius)
    {
        switch (which)
        {
            case Constants.OBJECT_GRANADE:
                granadeWeapon.setSize(2 * radius, 2 * radius);
                granadeWeapon.setOriginCenter();
                break;
            case Constants.OBJECT_AK47:
                ak47Weapon.setSize(2 * radius, 2 * radius);
                ak47Weapon.setOriginCenter();
                break;
            case Constants.OBJECT_BULLET_GRANATE:
                bulleteGaranade.setSize(2 * radius, 2 * radius);
                bulleteGaranade.setOriginCenter();
                break;
            case Constants.OBJECT_BULLET_AK47:
                bulleteAk47.setSize(2 * radius, 2 * radius);
                bulleteAk47.setOriginCenter();
                break;
            case Constants.OBJECT_RESPAWN:
                respawnDot.setSize(2 * radius, 2 * radius);
                respawnDot.setOriginCenter();
                break;
        }
    }

    /**
     * очистка памяти по завершении
     */
    public void dispose()
    {
        textureBulleteGranade.dispose();
        atlas_move_L.dispose();
        atlas_monster.dispose();
    }
}
