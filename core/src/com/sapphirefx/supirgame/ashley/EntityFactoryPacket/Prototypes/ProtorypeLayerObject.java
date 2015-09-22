package com.sapphirefx.supirgame.ashley.EntityFactoryPacket.Prototypes;

/**
 * Created by sapphire on 22.09.15.
 */
public class ProtorypeLayerObject
{
    public String layerName = "";
	public boolean isLocked = false;
	public boolean isVisible = false;

	public ProtorypeLayerObject()
    {
	}

    public ProtorypeLayerObject(String name)
    {
        layerName = new String(name);
		isVisible = true;
    }

	public ProtorypeLayerObject(ProtorypeLayerObject vo)
    {
		layerName = new String(vo.layerName);
		isLocked = vo.isLocked;
		isVisible = vo.isVisible;
	}

	public static ProtorypeLayerObject createDefault()
    {
        ProtorypeLayerObject layerItemVO = new ProtorypeLayerObject();
		layerItemVO.layerName = "Default";
		layerItemVO.isVisible = true;
		return layerItemVO;
	}
}
