package com.sapphirefx.supirgame.ashley.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.sapphirefx.supirgame.ashley.components.AnimationComponent;
import com.sapphirefx.supirgame.ashley.components.StateComponent;
import com.sapphirefx.supirgame.ashley.components.TextureComponent;

/**
 * Created by sapphire on 18.09.15.
 */
public class AnimationSystem extends IteratingSystem
{
    private ComponentMapper<AnimationComponent> am;
    private ComponentMapper<TextureComponent> tm;
    private ComponentMapper<StateComponent> sm;

    public AnimationSystem()
    {
        super(Family.all(AnimationComponent.class, TextureComponent.class, StateComponent.class).get());
        am = ComponentMapper.getFor(AnimationComponent.class);
        tm = ComponentMapper.getFor(TextureComponent.class);
        sm = ComponentMapper.getFor(StateComponent.class);
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime)
    {
        long id = entity.getId();
        AnimationComponent anim = am.get(entity);
        TextureComponent tex = tm.get(entity);
        StateComponent state = sm.get(entity);
/*
        Animation animation = anim.animations.get(state.get());
        if (animation != null)
        {
            tex.region = animation.getKeyFrame(state.time);
        }
        state.time += deltaTime;
*/
    }
}
