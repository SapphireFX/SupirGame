package com.sapphirefx.supirgame.levels;

import com.badlogic.ashley.core.Component;
import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.PolygonSpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Scaling;
import com.badlogic.gdx.utils.reflect.ClassReflection;
import com.badlogic.gdx.utils.reflect.ReflectionException;
import com.badlogic.gdx.utils.viewport.ScalingViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.sapphirefx.supirgame.ashley.EntityFactoryPacket.EntityFactory;
import com.sapphirefx.supirgame.ashley.EntityFactoryPacket.Prototypes.CompositeComponents;
import com.sapphirefx.supirgame.ashley.EntityFactoryPacket.Prototypes.PrototypeCompositeObject;
import com.sapphirefx.supirgame.ashley.EntityFactoryPacket.Prototypes.SceneObject;
import com.sapphirefx.supirgame.ashley.components.MainItemComponent;
import com.sapphirefx.supirgame.ashley.systems.AnimationSystem;
import com.sapphirefx.supirgame.ashley.systems.LayerSystem;
import com.sapphirefx.supirgame.ashley.systems.LightSystem;
import com.sapphirefx.supirgame.ashley.systems.ParticleSystem;
import com.sapphirefx.supirgame.ashley.systems.PhysicsSystem;
import com.sapphirefx.supirgame.ashley.systems.RenderingSystem;
import com.sapphirefx.supirgame.ashley.systems.ResizeCompositeSystem;
import com.sapphirefx.supirgame.resources.IResourceRetriever;
import com.sapphirefx.supirgame.resources.ResourceManager;
import com.sapphirefx.supirgame.tools.ComponentRetriever;
import com.sapphirefx.supirgame.tools.PhysicBodyLoader;
import com.sapphirefx.supirgame.tools.ProjectInfo;
import com.sapphirefx.supirgame.tools.ResolutionEntryVO;

import box2dLight.RayHandler;

/**
 * Created by sapphire on 22.09.15.
 */
public class SceneLoader
{
    private String curResolution = "orig";
	private SceneObject sceneVO;
	private IResourceRetriever rm = null;

    public Engine engine = null;
	public RayHandler rayHandler;
	public World world;
	public Entity rootEntity;

	public EntityFactory entityFactory;

	private float pixelsPerWU = 1;

	private RenderingSystem renderer;
	private Entity root;

	public SceneLoader()
    {
		ResourceManager rm = new ResourceManager();
        rm.initAllResources();

		this.rm = rm;

		this.engine = new Engine();
		initSceneLoader();
    }

    public SceneLoader(IResourceRetriever rm)
    {
        this.engine = new Engine();
		this.rm = rm;
		initSceneLoader();
    }

	/**
	 * this method is called when rm has loaded all data
	 */
    private void initSceneLoader()
    {
        RayHandler.setGammaCorrection(true);
        RayHandler.useDiffuseLight(true);
        world = new World(new Vector2(0,-10), true);
        rayHandler = new RayHandler(world);
        rayHandler.setAmbientLight(1f, 1f, 1f, 1f);
        rayHandler.setCulling(true);
        rayHandler.setBlur(true);
        rayHandler.setBlurNum(3);
        rayHandler.setShadows(true);

        addSystems();

        entityFactory = new EntityFactory(rm, world, rayHandler);
    }

	public void setResolution(String resolutionName)
    {
		ResolutionEntryVO resolution = getRm().getProjectVO().getResolution(resolutionName);
		if(resolution != null) {
			curResolution = resolutionName;
		}
	}

	public SceneObject getSceneVO()
	{
		return sceneVO;
	}

	public SceneObject loadScene(String sceneName, Viewport viewport)
	{

		pixelsPerWU = rm.getProjectVO().pixelToWorld;
        PhysicBodyLoader.getInstance().setScaleFromPPWU(pixelsPerWU);
		engine.removeAllEntities();

		sceneVO = rm.getSceneVO(sceneName);
		world.setGravity(new Vector2(sceneVO.physicsPropertiesVO.gravityX, sceneVO.physicsPropertiesVO.gravityY));

		if(sceneVO.composite == null)
		{
			sceneVO.composite = new CompositeComponents();
		}

		rootEntity = entityFactory.createRootEntity(sceneVO.composite, viewport);
		engine.addEntity(rootEntity);

		if(sceneVO.composite != null)
		{
			entityFactory.initAllChildren(engine, rootEntity, sceneVO.composite);
		}

		setAmbienceInfo(sceneVO);
		rayHandler.useCustomViewport(viewport.getScreenX(), viewport.getScreenY(), viewport.getScreenWidth(), viewport.getScreenHeight());

		return sceneVO;
	}

	public SceneObject loadScene(String sceneName)
	{
		ProjectInfo projectVO = rm.getProjectVO();
		Viewport viewport = new ScalingViewport(Scaling.stretch, (float)projectVO.originalResolution.width/ pixelsPerWU,
				(float)projectVO.originalResolution.height/ pixelsPerWU, new OrthographicCamera());
		return loadScene(sceneName, viewport);
	}


	private void addSystems()
	{
		AnimationSystem animationSystem = new AnimationSystem();
        RenderingSystem renderer = new RenderingSystem(new PolygonSpriteBatch(2000, createDefaultShader()));
        LayerSystem layerSystem = new LayerSystem();
        ParticleSystem particleSystem = new ParticleSystem();
        LightSystem lightSystem = new LightSystem();
        ResizeCompositeSystem resizeCompositeSystem = new ResizeCompositeSystem();
        PhysicsSystem physicsSystem = new PhysicsSystem(world);
        renderer.setWorld(world);

        engine.addSystem(layerSystem);
		engine.addSystem(animationSystem);
		engine.addSystem(particleSystem);
		engine.addSystem(lightSystem);
        engine.addSystem(resizeCompositeSystem);
        engine.addSystem(physicsSystem);
		engine.addSystem(renderer);
	}

	public Entity loadFromLibrary(String libraryName)
	{
		ProjectInfo projectInfoVO = getRm().getProjectVO();
		PrototypeCompositeObject compositeItemVO = projectInfoVO.libraryItems.get(libraryName);

		if(compositeItemVO != null)
		{
			Entity entity = entityFactory.createEntity(null, compositeItemVO);
			return entity;
		}
		return null;
	}

    public PrototypeCompositeObject loadVoFromLibrary(String libraryName)
	{
        ProjectInfo projectInfoVO = getRm().getProjectVO();
		PrototypeCompositeObject compositeItemVO = projectInfoVO.libraryItems.get(libraryName);

       return compositeItemVO;
    }

    public void addComponentsByTagName(String tagName, Class componentClass)
	{
        ImmutableArray<Entity> entities = engine.getEntities();
        for(Entity entity: entities)
		{
            MainItemComponent mainItemComponent = ComponentRetriever.get(entity,MainItemComponent.class);
            if(mainItemComponent.tags.contains(tagName))
			{
                try
				{
                    entity.add(ClassReflection.<Component>newInstance(componentClass));
                } catch (ReflectionException e)
				{
                    e.printStackTrace();
                }
            }
        }
    }

	/**
	 * Sets ambient light to the one specified in scene from editor
	 *
	 * @param vo
	 *            - Scene data file to invalidate
	 */
	public void setAmbienceInfo(SceneObject vo)
	{
        if(!vo.lightSystemEnabled)
		{
            rayHandler.setAmbientLight(1f, 1f, 1f, 1f);
            return;
        }
		if (vo.ambientColor != null)
		{
			Color clr = new Color(vo.ambientColor[0], vo.ambientColor[1],
					vo.ambientColor[2], vo.ambientColor[3]);
			rayHandler.setAmbientLight(clr);
		}
	}


	public EntityFactory getEntityFactory() {
		return entityFactory;
	}

	 public IResourceRetriever getRm() {
	 	return rm;
	 }

    public Engine getEngine() {
        return engine;
    }

	public Entity getRoot() {
		return rootEntity;
	}

	/** Returns a new instance of the default shader used by SpriteBatch for GL2 when no shader is specified. */
	static public ShaderProgram createDefaultShader ()
	{
		String vertexShader = "attribute vec4 " + ShaderProgram.POSITION_ATTRIBUTE + ";\n" //
			+ "attribute vec4 " + ShaderProgram.COLOR_ATTRIBUTE + ";\n" //
			+ "attribute vec2 " + ShaderProgram.TEXCOORD_ATTRIBUTE + "0;\n" //
			+ "uniform mat4 u_projTrans;\n" //
			+ "varying vec4 v_color;\n" //
			+ "varying vec2 v_texCoords;\n" //
			+ "\n" //
			+ "void main()\n" //
			+ "{\n" //
			+ "   v_color = " + ShaderProgram.COLOR_ATTRIBUTE + ";\n" //
			+ "   v_color.a = v_color.a * (255.0/254.0);\n" //
			+ "   v_texCoords = " + ShaderProgram.TEXCOORD_ATTRIBUTE + "0;\n" //
			+ "   gl_Position =  u_projTrans * " + ShaderProgram.POSITION_ATTRIBUTE + ";\n" //
			+ "}\n";
		String fragmentShader = "#ifdef GL_ES\n" //
			+ "#define LOWP lowp\n" //
			+ "precision mediump float;\n" //
			+ "#else\n" //
			+ "#define LOWP \n" //
			+ "#endif\n" //
			+ "varying LOWP vec4 v_color;\n" //
			+ "varying vec2 v_texCoords;\n" //
			+ "uniform sampler2D u_texture;\n" //
			+ "uniform vec2 atlasCoord;\n" //
			+ "uniform vec2 atlasSize;\n" //
			+ "uniform int isRepeat;\n" //
			+ "void main()\n"//
			+ "{\n" //
			+ "vec4 textureSample = vec4(0.0,0.0,0.0,0.0);\n"//
			+ "if(isRepeat == 1)\n"//
			+ "{\n"//
			+ "textureSample = v_color * texture2D(u_texture, atlasCoord+mod(v_texCoords, atlasSize));\n"//
			+ "}\n"//
			+ "else\n"//
			+ "{\n"//
			+ "textureSample = v_color * texture2D(u_texture, v_texCoords);\n"//
			+ "}\n"//
			+ "  gl_FragColor = textureSample;\n" //
			+ "}";

		ShaderProgram shader = new ShaderProgram(vertexShader, fragmentShader);
		if (shader.isCompiled() == false) throw new IllegalArgumentException("Error compiling shader: " + shader.getLog());
		return shader;
	}

}
