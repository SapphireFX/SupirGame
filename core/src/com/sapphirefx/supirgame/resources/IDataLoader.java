package com.sapphirefx.supirgame.resources;

import com.sapphirefx.supirgame.ashley.EntityFactoryPacket.Prototypes.SceneObject;
import com.sapphirefx.supirgame.tools.ProjectInfo;
import com.uwsoft.editor.renderer.SceneLoader;

/**
 * Created by sapphire on 22.09.15.
 */
public interface IDataLoader
{
    public SceneObject loadSceneVO(String sceneName);
    public ProjectInfo loadProjectVO();
}
