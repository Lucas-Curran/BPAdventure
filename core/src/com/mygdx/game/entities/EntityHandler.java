package com.mygdx.game.entities;

import java.util.ArrayList;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.PolygonSpriteBatch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.game.B2dContactListener;
import com.mygdx.game.BodyFactory;
import com.mygdx.game.Camera;
import com.mygdx.game.Engine;
import com.mygdx.game.GameWorld;
import com.mygdx.game.Map;
import com.mygdx.game.Utilities;
import com.mygdx.game.components.*;
import com.mygdx.game.components.BulletComponent.Owner;
import com.mygdx.game.levels.Levels;
import com.mygdx.game.levels.Levels.LevelDestination;
import com.mygdx.game.systems.*;

public class EntityHandler implements ApplicationListener {

	Engine engine;
	protected PooledEngine pooledEngine;
	BodyFactory bodyFactory;
	GameWorld gameWorld;
	RenderingSystem renderingSystem;
	public SpriteBatch batch;
	public Camera cam;
	
	TextureAtlas textureAtlas;
	protected TextureRegion tex;
	Levels levels;

	TextureAtlas levelTwoAtlas;
	public TextureAtlas levelSevenAtlas;
	protected TextureRegion rockMob;
	protected TextureRegion spikyRockMob;
	protected TextureRegion unknownBeing;
	protected TextureRegion normalMan;
	protected TextureRegion bulletLeft;
	protected TextureRegion slimyMob;
	protected TextureRegion oldMan;
	protected TextureRegion spikySlime;
	protected TextureRegion squirrelMan;
	
	private Player player;
	public Enemy enemies;
	private NPC npc;
	private Bullet bullets;
	
	public boolean loadingZone;
	public boolean talkingZone;
	public boolean killZone;
	public boolean gravityZone;
	public float npcX;
	public float npcY;
	
	private Texture talkTexture;
	private PolygonSpriteBatch polygonSpriteBatch;
	float destinationX, destinationY;
	String destination;
	LevelDestination createdLevel;
	
	public LevelDestination getCreatedLevel() {
		return createdLevel;
	}

	public void setCreatedLevel(LevelDestination createdLevel) {
		this.createdLevel = createdLevel;
	}

	private String[] currentNPCText;
	private boolean hasOptions;
	
	public float getDestinationX() {
        return destinationX;
    }

    public void setDestinationX(float destinationX) {
        this.destinationX = destinationX;
    }

    public float getDestinationY() {
        return destinationY;
    }

    public void setDestinationY(float destinationY) {
        this.destinationY = destinationY;
    }
    
    public String getDestination() {
    	return destination;
    }
    
    public void setDestination(String destination) {
        this.destination = destination;
    }
	
	public EntityHandler(Levels levels) {
		engine = new Engine();
		gameWorld = new GameWorld();
		bodyFactory = BodyFactory.getInstance(gameWorld.getInstance());
		pooledEngine = engine.getInstance();
		cam = new Camera();
		this.levels = levels;
		
		textureAtlas = new TextureAtlas("textures.txt");
		tex = new TextureRegion(textureAtlas.findRegion("IceCharacter"));
		
		levelTwoAtlas = new TextureAtlas("atlas_leveltwo.txt");
		rockMob = new TextureRegion(levelTwoAtlas.findRegion("RockMobEnemy"));
		spikyRockMob = new TextureRegion(levelTwoAtlas.findRegion("SpikyRockEnemy"));
		normalMan = new TextureRegion(levelTwoAtlas.findRegion("BPA Characters/normalMan"));
		unknownBeing = new TextureRegion(levelTwoAtlas.findRegion("BPA Characters/UnknownBeing"));
		
		levelSevenAtlas = new TextureAtlas("moreSprites.txt");
		bulletLeft = new TextureRegion(levelSevenAtlas.findRegion("bullet(left)"));
		spikySlime = new TextureRegion(levelSevenAtlas.findRegion("spikySlime"));
		slimyMob = new TextureRegion(levelSevenAtlas.findRegion("slimyMob"));
		talkTexture = new Texture(Gdx.files.internal("thinkBubble.png"));
		
		gameWorld.getInstance().setContactListener(new B2dContactListener(this));
		
		batch = new SpriteBatch();
		
		renderingSystem = new RenderingSystem(batch);
		
		batch.setProjectionMatrix(cam.getCombined());
		
		pooledEngine.addSystem(renderingSystem);
		pooledEngine.addSystem(new AnimationSystem());
		pooledEngine.addSystem(new PhysicsSystem(gameWorld.getInstance()));
		pooledEngine.addSystem(new PhysicsDebugSystem(gameWorld.getInstance(), cam.getCamera()));		
		pooledEngine.addSystem(new PlayerControlSystem());
		pooledEngine.addSystem(new EnemySystem());
		pooledEngine.addSystem(new SteeringSystem());
		pooledEngine.addSystem(new BulletSystem());
		
		loadingZone = false;
		talkingZone = false;
		gravityZone = false;
		
		polygonSpriteBatch = new PolygonSpriteBatch();
		polygonSpriteBatch.setProjectionMatrix(cam.getCombined());
		
	}
	
	public EntityHandler() {
		engine = new Engine();
		gameWorld = new GameWorld();
		bodyFactory = BodyFactory.getInstance(gameWorld.getInstance());
		pooledEngine = engine.getInstance();
		cam = new Camera();
		
		textureAtlas = new TextureAtlas("textures.txt");
		tex = new TextureRegion(textureAtlas.findRegion("IceCharacter"));
		
		levelTwoAtlas = new TextureAtlas("atlas_leveltwo.txt");
//		normalMan = new TextureRegion(levelTwoAtlas.findRegion("BPA Characters/normalMan"));
//		unknownBeing = new TextureRegion(levelTwoAtlas.findRegion("BPA Characters/UnknownBeing"));
		
		levelSevenAtlas = new TextureAtlas("moreSprites.txt");
		bulletLeft = new TextureRegion(levelSevenAtlas.findRegion("bullet(left)"));
		spikySlime = new TextureRegion(levelSevenAtlas.findRegion("spikySlime"));
		slimyMob = new TextureRegion(levelSevenAtlas.findRegion("slimyMob"));
		
		gameWorld.getInstance().setContactListener(new B2dContactListener(this));
		
		batch = new SpriteBatch();
		
		renderingSystem = new RenderingSystem(batch);
		
		batch.setProjectionMatrix(cam.getCombined());
		
		pooledEngine.addSystem(renderingSystem);
		pooledEngine.addSystem(new AnimationSystem());
		pooledEngine.addSystem(new PhysicsSystem(gameWorld.getInstance()));
		pooledEngine.addSystem(new PhysicsDebugSystem(gameWorld.getInstance(), cam.getCamera()));		
		pooledEngine.addSystem(new PlayerControlSystem());
		pooledEngine.addSystem(new EnemySystem());
		pooledEngine.addSystem(new SteeringSystem());
		pooledEngine.addSystem(new BulletSystem());
		
		loadingZone = false;
		talkingZone = false;
		gravityZone = false;
		
		polygonSpriteBatch = new PolygonSpriteBatch();
		polygonSpriteBatch.setProjectionMatrix(cam.getCombined());
	}
	
	@Override
	public void create() {
		talkTexture = new Texture(Gdx.files.internal("thinkBubble.png"));
		player = new Player();
		enemies = new Enemy();
		npc = new NPC();
		bullets = new Bullet();
		pooledEngine.addSystem(new CollisionSystem());
		
		pooledEngine.addEntity(player.createPlayer(cam.getCamera().position.x, cam.getCamera().position.y));
		
		setCreatedLevel(LevelDestination.OVERWORLD);
		
	}

	@Override
	public void resize(int width, int height) {
		cam.resize(width, height);
	}

	@Override
	public void render() {	
		pooledEngine.update(1/20f);
		updateCamera();
		updateEntities();
		renderSpeechBubble();
		Utilities.renderAllTextures(cam, polygonSpriteBatch, bodyFactory.getBodies());
		teleportPlayer(destinationX, destinationY, destination);
		killPlayer(17, 1.5f);
		setJumpScale();//call this
	}
	
	@Override
	public void pause() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void dispose() {
		cam.dispose();
		batch.dispose();
	}
	
	private void updateCamera() {
		float minCameraX = cam.getCamera().viewportWidth / 2 - 36;
		float maxCameraX = cam.getViewport().getWorldWidth() - minCameraX + 10;
		float minCameraY = cam.getCamera().viewportHeight / 2;
		float maxCameraY = cam.getViewport().getWorldHeight() - minCameraY;
		
		cam.getCamera().position.set(Math.min(maxCameraX, Math.max(player.getX(), minCameraX)),
				Math.min(maxCameraY, Math.max(player.getY(), minCameraY)), 0);
		cam.getCamera().position.set(new Vector3(player.getX(), player.getY(), 0));
		cam.getCamera().update();
	}
	
	private void updateEntities() {
		for (Entity entity : pooledEngine.getEntities()) {
			entity.getComponent(TransformComponent.class).position.set(
					entity.getComponent(B2dBodyComponent.class).body.getPosition().x - cam.getCamera().position.x, 
					entity.getComponent(B2dBodyComponent.class).body.getPosition().y - cam.getCamera().position.y,
					0);
		}
	}		

	public void spawnLevelTwo() {
		for (Entity enemy : enemies.getLevelTwo()) {
			pooledEngine.addEntity(enemy);
		}
	}
	
	public void spawnLevelThree() {
		for (Entity enemy : enemies.getLevelThree()) {
			pooledEngine.addEntity(enemy);
		}
	}
	public void spawnLevelFour() {
		for (Entity enemy : enemies.getLevelFour()) {
			pooledEngine.addEntity(enemy);
		}
	}
	public void spawnLevelFive() {
		for (Entity enemy : enemies.getLevelFive()) {
			pooledEngine.addEntity(enemy);
		}
	}
	public void spawnLevelSix() {
		for (Entity enemy : enemies.getLevelSix()) {
			pooledEngine.addEntity(enemy);
		}
	}
	
	public void spawnLevelSeven() {
		for (Entity enemy : enemies.getLevelSeven()) {
			pooledEngine.addEntity(enemy);
		}
	}
	public void spawnLevelEight() {
		for (Entity enemy : enemies.getLevelEight()) {
			pooledEngine.addEntity(enemy);
		}
	}
	public void spawnLevelNine() {
		for (Entity enemy : enemies.getLevelNine()) {
			pooledEngine.addEntity(enemy);
		}
	}
	public void spawnLevelTen() {
		for (Entity enemy : enemies.getLevelTen()) {
			pooledEngine.addEntity(enemy);
		}
	}
	
	public void spawnIceDungeon() {
		for (Entity enemy : enemies.getIceDungeon()) {
			pooledEngine.addEntity(enemy);
		}
	}
	
	public void spawnBullets() {
		for (Entity bullet : bullets.getBullets()) {
			pooledEngine.addEntity(bullet);
		}
	}
	
	public void teleportPlayer(float x, float y, String destination) {
		if (Map.getInstance().teleporting == true) {
			player.fadePlayer(x, y, destination);
		}
	}
	
	public void killPlayer(float x, float y) {
        if (Map.getInstance().death == true) {
            player.fadePlayerToBeginning(x, y);
        }
    }
	
	public void setJumpScale() {
		if (Map.getInstance().gravitySwitch == true) {
			pooledEngine.getSystem(PlayerControlSystem.class).setJumpScale(-40);
		} else if (Map.getInstance().gravitySwitch == false){
			pooledEngine.getSystem(PlayerControlSystem.class).setJumpScale(40);
			
		}
	}

	
	public void renderSpeechBubble() {
		if (talkingZone) {
			batch.setProjectionMatrix(cam.getCombined());
			batch.begin();
			batch.draw(talkTexture, npcX, npcY, 1f, 1f);
			batch.end();
		}
	}
	
	public NPC getNPC() {
		return npc;
	}
	
	public SpriteBatch getBatch() {
		return batch;
	}
	
	public Player getPlayer() {
		return player;
	}
	
	public Vector3 getCameraPosition() {
		return cam.getCamera().position;
	}
	
	public World getWorld() {
		// TODO Auto-generated method stub
		return gameWorld.getInstance();
	}
	
	public Levels getLevels() {
		return levels;
	}
	
	public void setCurrentNPCText(String[] currentNPCText) {
		this.currentNPCText = currentNPCText;
	}
	
	public String[] getCurrentNPCText() {
		return currentNPCText;
	}
	
	public PooledEngine getPooledEngine() {
		return pooledEngine;
	}
	
	public boolean hasOptions() {
		return hasOptions;
	}
	
	public void setHasOptions(boolean hasOptions) {
		this.hasOptions = hasOptions;
	}
	
}
