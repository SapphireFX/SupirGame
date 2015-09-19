package com.sapphirefx.supirgame.levels;

import com.badlogic.ashley.core.Engine;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.uwsoft.editor.renderer.SceneLoader;
import com.uwsoft.editor.renderer.resources.ResourceManager;
import com.uwsoft.editor.renderer.utils.ItemWrapper;

/**
 * Created by sapphire on 13.09.15.
 */
public class Overlap2Dlvl
{
    private Stage stage;
    private SceneLoader sl;

    public Overlap2Dlvl()
    {
        sl = new SceneLoader();
        sl.loadScene("MainScene");
        ItemWrapper itemRoot = new ItemWrapper(sl.getRoot());
    }

    public Engine getOverlapEngine()
    {
        return sl.getEngine();
    }
}
