package com.sapphirefx.supirgame.resources;

import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.sapphirefx.supirgame.ashley.EntityFactoryPacket.Prototypes.SceneObject;
import com.sapphirefx.supirgame.tools.MySkin;
import com.sapphirefx.supirgame.tools.ProjectInfo;
import com.sapphirefx.supirgame.tools.ResolutionEntryVO;

/**
 * Created by sapphire on 22.09.15.
 */
public interface IResourceRetriever
{
    public TextureRegion getTextureRegion(String name);
    public ParticleEffect getParticleEffect(String name);
    public TextureAtlas getSkeletonAtlas(String name);
    public FileHandle getSkeletonJSON(String name);
    public FileHandle getSCMLFile(String name);
    public TextureAtlas getSpriteAnimation(String name);
    public BitmapFont getBitmapFont(String name, int size);
    public MySkin getSkin();

    public SceneObject getSceneVO(String sceneName);
    public ProjectInfo getProjectVO();

    public ResolutionEntryVO getLoadedResolution();
    public ShaderProgram getShaderProgram(String shaderName);
}
