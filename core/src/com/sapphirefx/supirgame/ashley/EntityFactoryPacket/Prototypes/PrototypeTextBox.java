package com.sapphirefx.supirgame.ashley.EntityFactoryPacket.Prototypes;

/**
 * Created by sapphire on 22.09.15.
 */
public class PrototypeTextBox extends PrototypeObject
{
    public String defaultText = "";
	public float width = 0;
	public float height = 0;
	public String	style	=  "";

	public PrototypeTextBox()
    {
		super();
	}

	public PrototypeTextBox(PrototypeTextBox vo)
    {
		super(vo);
		defaultText = new String(vo.defaultText);
		width 		= vo.width;
		height 		= vo.height;
		style 		= new String(vo.style);
	}
}
