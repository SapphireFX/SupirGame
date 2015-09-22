package com.sapphirefx.supirgame.tools;

import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonWriter.OutputType;
import com.sapphirefx.supirgame.ashley.EntityFactoryPacket.Prototypes.PrototypeCompositeObject;
import com.sapphirefx.supirgame.ashley.EntityFactoryPacket.Prototypes.SceneObject;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by sapphire on 22.09.15.
 */
public class ProjectInfo
{
    public int pixelToWorld = 1;

    public ResolutionEntryVO originalResolution = new ResolutionEntryVO();

    public Array<ResolutionEntryVO> resolutions = new Array<ResolutionEntryVO>();
    public ArrayList<SceneObject> scenes = new ArrayList<SceneObject>();

    public HashMap<String, PrototypeCompositeObject> libraryItems = new HashMap<String, PrototypeCompositeObject>();

    public String constructJsonString()
    {
        String str = "";
        Json json = new Json();
        json.setOutputType(OutputType.json);
        str = json.toJson(this);
        json.prettyPrint(str);
        return str;
    }

    public ResolutionEntryVO getResolution(String name)
    {
        for (ResolutionEntryVO resolution : resolutions)
        {
            if (resolution.name.equalsIgnoreCase(name))
            {
                return resolution;
            }
        }
        return null;
    }
}
