package com.sapphirefx.supirgame.ashley.EntityFactoryPacket;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.sapphirefx.supirgame.ashley.EntityFactoryPacket.Prototypes.PrototypeLabelObject;
import com.sapphirefx.supirgame.ashley.EntityFactoryPacket.Prototypes.PrototypeObject;
import com.sapphirefx.supirgame.ashley.components.DimensionsComponent;
import com.sapphirefx.supirgame.ashley.components.LabelComponent;
import com.sapphirefx.supirgame.resources.IResourceRetriever;
import com.sapphirefx.supirgame.tools.ProjectInfo;

import box2dLight.RayHandler;

/**
 * Created by sapphire on 21.09.15.
 */
public class LabelComponentFactory extends ComponentFactory
{
    private static int labelDefaultSize = 12;

    public LabelComponentFactory(IResourceRetriever rm, World world, RayHandler rayHandler)
    {
        super(rm, world, rayHandler);
    }

    @Override
    public void createComponents(Entity root, Entity entity, PrototypeObject po)
    {
        createCommonComponents(entity, po, EntityFactory.LABEL_TYPE);
		 createParentNodeComponent(root, entity);
		 createNodeComponent(root, entity);
		 createLabelComponent(entity, (PrototypeLabelObject) po);
    }

    @Override
    protected DimensionsComponent createDimensionsComponent(Entity entity, PrototypeObject po)
    {
        return null;
    }

    protected LabelComponent createLabelComponent(Entity entity, PrototypeLabelObject lo)
        {
    	LabelComponent component = new LabelComponent(lo.text, generateStyle(rm, lo.style, lo.size));
        component.fontName = lo.style;
        component.fontSize = lo.size;
        component.setAlignment(lo.align);

        ProjectInfo projectInfoVO = rm.getProjectVO();
        component.setFontScale(1f / projectInfoVO.pixelToWorld);

        entity.add(component);
        return component;
    }


    public static Label.LabelStyle generateStyle(IResourceRetriever rManager, String fontName, int size)
    {

        if (size == 0) {
            size = labelDefaultSize;
        }
        return new Label.LabelStyle(rManager.getBitmapFont(fontName, size), null);
    }
}
