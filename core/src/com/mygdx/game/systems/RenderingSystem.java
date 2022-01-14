package com.mygdx.game.systems;

import java.util.Comparator;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.SortedIteratingSystem;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.PolygonSpriteBatch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.mygdx.game.Camera;
import com.mygdx.game.components.TextureComponent;
import com.mygdx.game.components.TransformComponent;

public class RenderingSystem extends SortedIteratingSystem {
	
	static final float PPM = 32.0f;
	
	static final float FRUSTUM_WIDTH = Gdx.graphics.getWidth()/PPM;
	static final float FRUSTUM_HEIGHT = Gdx.graphics.getHeight()/PPM;
	
	public static final float PIXELS_TO_METERS = 1.0f / PPM;
	
	private static Vector2 meterDimensions = new Vector2();
	private static Vector2 pixelDimensions = new Vector2();
	
	private SpriteBatch batch;
	private PolygonSpriteBatch polyBatch;
    private Array<Entity> renderQueue;
    private Comparator<Entity> comparator; 
    private Camera cam;
    
    private ComponentMapper<TextureComponent> textureM;
    private ComponentMapper<TransformComponent> transformM;

    
    
	public RenderingSystem(SpriteBatch batch) {
		
		super(Family.all(TransformComponent.class, TextureComponent.class).get(), new ZComparator());
		
		textureM = ComponentMapper.getFor(TextureComponent.class);
		transformM = ComponentMapper.getFor(TransformComponent.class);

		renderQueue = new Array<Entity>();
		this.batch = batch;
		
		comparator = new ZComparator();
		
		cam = new Camera();
	}
	
	public static Vector2 getScreenSizeInMeters() {
		meterDimensions.set(Gdx.graphics.getWidth()*PIXELS_TO_METERS, 
				Gdx.graphics.getHeight()*PIXELS_TO_METERS);
		return meterDimensions;
	}
	
	public static Vector2 getScreenSizeInPixels() {
		pixelDimensions.set(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		return pixelDimensions;
	}
	
	public static float PixelsToMeters(float pixelValue){
        return pixelValue * PIXELS_TO_METERS;
    }
	
	@Override
	public void update(float deltaTime) {
		super.update(deltaTime);
		
		renderQueue.sort(comparator);
		cam.getCamera().update();
		batch.setProjectionMatrix(cam.getCombined());
		batch.enableBlending();
		batch.begin();
		
		for (Entity entity : renderQueue) {
			TextureComponent tex = textureM.get(entity);
			TransformComponent t = transformM.get(entity);
	
			if (tex.region == null || t.isHidden) {
//				System.out.println("Null texture");
				continue;
			}
			
			float width = tex.region.getRegionWidth();
            float height = tex.region.getRegionHeight();
            
            float originX = width/2;
            float originY = height/2;
            
            batch.draw(tex.region,
                    t.position.x - originX + cam.getCamera().viewportWidth / 2,
                    t.position.y - originY + cam.getCamera().viewportHeight / 2,
                    originX, originY,
                    width, height,
                    PixelsToMeters(t.scale.x), PixelsToMeters(t.scale.y),
                    t.rotation);           
            
		}
		
		batch.end();
		renderQueue.clear();
		
	}
	
	@Override
	public void processEntity(Entity entity, float deltaTime) {
		renderQueue.add(entity);
	}
	
}
