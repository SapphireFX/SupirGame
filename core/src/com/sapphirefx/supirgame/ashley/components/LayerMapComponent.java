package com.sapphirefx.supirgame.ashley.components;

import com.badlogic.ashley.core.Component;
import com.sapphirefx.supirgame.ashley.EntityFactoryPacket.Prototypes.ProtorypeLayerObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

/**
 * Created by sapphire on 21.09.15.
 */
public class LayerMapComponent implements Component
{
    private ArrayList<ProtorypeLayerObject> layers = new ArrayList<ProtorypeLayerObject>();

    private HashMap<String, ProtorypeLayerObject> layerMap = new HashMap<String, ProtorypeLayerObject>();

    public void setLayers(ArrayList<ProtorypeLayerObject> layers)
    {
        this.layers = layers;
        layerMap.clear();
        for (ProtorypeLayerObject vo : layers)
        {
            layerMap.put(vo.layerName, vo);
        }
    }

    public ProtorypeLayerObject getLayer(String name)
    {
        return layerMap.get(name);
    }

    public int getIndexByName(String name)
    {
        if(layerMap.containsKey(name))
        {
            return layers.indexOf(layerMap.get(name));
        }

        return 0;
    }

    public boolean isVisible(String name)
    {
        ProtorypeLayerObject vo = getLayer(name);
        if (vo != null)
        {
            return vo.isVisible;
        }

        return true;
    }

    public void addLayer(int index, ProtorypeLayerObject layerVo)
    {
        layers.add(index, layerVo);
        layerMap.put(layerVo.layerName, layerVo);
    }

    public void addLayer(ProtorypeLayerObject layerVo)
    {
        layers.add(layerVo);
        layerMap.put(layerVo.layerName, layerVo);
    }

    public ArrayList<ProtorypeLayerObject> getLayers()
    {
        return layers;
    }

    public void deleteLayer(String layerName)
    {
        layers.remove(getIndexByName(layerName));
        layerMap.remove(layerName);
    }

    public void rename(String prevName, String newName)
    {
        ProtorypeLayerObject vo = layerMap.get(prevName);
        layerMap.remove(prevName);
        layerMap.put(newName, vo);
    }

    public void swap(String source, String target)
    {
        ProtorypeLayerObject sourceVO = getLayer(source);
        ProtorypeLayerObject targetVO = getLayer(target);
        Collections.swap(layers, layers.indexOf(sourceVO), layers.indexOf(targetVO));
    }
}
