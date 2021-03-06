package com.mygdx.game.entities;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.mygdx.game.BodyFactory;
import com.mygdx.game.Map;
import com.mygdx.game.Utilities;
import com.mygdx.game.components.B2dBodyComponent;
import com.mygdx.game.components.CollisionComponent;
import com.mygdx.game.components.PlayerComponent;
import com.mygdx.game.components.StateComponent;
import com.mygdx.game.components.SteeringComponent;
import com.mygdx.game.components.TextureComponent;
import com.mygdx.game.components.TransformComponent;
import com.mygdx.game.components.TypeComponent;
import com.mygdx.game.levels.Levels;

public class Player extends EntityHandler {
	
	static Logger logger = LogManager.getLogger(Player.class.getName());
	
	private Entity entity;
	
	//true if fade in, false if fade out
	private boolean fadeDirection = true;
	
	private float alpha = 0;
	
	private ShapeRenderer shapeRenderer;
	
	Levels levels = new Levels();
	
	private int defense = 0;
	private int damage = 1;
	
	/**
	 * Creates the player
	 * @param x - initial x position of player
	 * @param y - initial y position of player
	 * @return player entity
	 */
	public Entity createPlayer(float x, float y) {
		
		// Create the Entity and all the components that will go in the entity
		entity = pooledEngine.createEntity();
		B2dBodyComponent b2dbody = pooledEngine.createComponent(B2dBodyComponent.class);
		TransformComponent position = pooledEngine.createComponent(TransformComponent.class);
		TextureComponent texture = pooledEngine.createComponent(TextureComponent.class);
		PlayerComponent player = pooledEngine.createComponent(PlayerComponent.class);
		CollisionComponent colComp = pooledEngine.createComponent(CollisionComponent.class);
		TypeComponent type = pooledEngine.createComponent(TypeComponent.class);
		StateComponent stateCom = pooledEngine.createComponent(StateComponent.class);
		SteeringComponent steering = pooledEngine.createComponent(SteeringComponent.class);

		// create the data for the components and add them to the components
		b2dbody.body = bodyFactory.makeCirclePolyBody(x, y, 1, BodyFactory.OTHER, BodyType.DynamicBody, true, false);
		// set object position (x,y,z) z used to define draw order 0 first drawn
		position.position.set(b2dbody.body.getPosition().x, b2dbody.body.getPosition().x, 1);
		texture.region = Utilities.tex;
		player.player = true;
		type.type = TypeComponent.PLAYER;
		stateCom.set(StateComponent.STATE_NORMAL);
		
		b2dbody.body.setUserData(entity);
		steering.body = b2dbody.body;
		
		// add the components to the entity
		entity.add(b2dbody);
		entity.add(position);
		entity.add(texture);
		entity.add(player);
		entity.add(colComp);
		entity.add(type);
		entity.add(stateCom);
		entity.add(steering);
		
		logger.info("Player created.");

		return entity;		
	}
	
	/**
	 * Gets the current direction the player is facing
	 * @return player direction
	 */
	public int getDirection() {
		return entity.getComponent(PlayerComponent.class).direction;
	}
	
	/**
	 * Gets the x position of the player
	 * @return x position
	 */
	public float getX() {
		return entity.getComponent(B2dBodyComponent.class).body.getPosition().x;
	}
		
	/**
	 * Gets the y position of the player
	 * @return y position
	 */
	public float getY() {
		return entity.getComponent(B2dBodyComponent.class).body.getPosition().y;
	}
	
	/**
	 * Gets player entity
	 * @return player entity
	 */
	public Entity getEntity() {
		return entity;
	}
	
	/**
	 * Sets the position of the player body
	 * @param x - x position
	 * @param y - y position
	 */
	public void setPosition(float x, float y) {
		entity.getComponent(B2dBodyComponent.class).body.setTransform(new Vector2(x, y), 0);
		entity.getComponent(B2dBodyComponent.class).body.setLinearVelocity(new Vector2(0, 0));
		logger.info("Player position set to ("+x+","+y+").");
	}
	
	/**
	 * Sets the gravity scale of the player body
	 * @param scale - gravity scale
	 */
	public void setGravityScale(float scale) {
        entity.getComponent(B2dBodyComponent.class).body.setGravityScale(scale);
    }
	/**
	 * Teleports the player and begins the fading animation
	 * @param x - x coordinate of the teleport
	 * @param y - y coordinate of the teleport
	 * @param level - which level its going to
	 */
	public void fadePlayer(float x, float y, String level) {

	    if (alpha >= 0) {
	        Gdx.gl.glEnable(GL20.GL_BLEND);
	        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
	        shapeRenderer = new ShapeRenderer();
	        shapeRenderer.setColor(0, 0, 0, alpha);
	        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
	        shapeRenderer.rect(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
	        shapeRenderer.end();
	        Gdx.gl.glDisable(GL20.GL_BLEND);

	        if (alpha >= 1) {
	            
	            setPosition(x, y);
	            if (!level.equals("")) {
	            Map.getInstance().mapBackground = new Texture(Gdx.files.internal(level));
	            }
	            fadeDirection =! fadeDirection;
	        }
	        //speed of fade
	        alpha += fadeDirection == true ? 0.015 : -0.015;

	        } else {

	            fadeDirection =! fadeDirection;
	            alpha = 0;

	            entity.getComponent(B2dBodyComponent.class).body.setAwake(true);

	            Map.getInstance().teleporting = false;
	            Map.getInstance().getAudioManager().stopAll();
	            Map.getInstance().getAudioManager().updateAll();
	            Map.getInstance().getAudioManager().playCave();
	        }
	    }
	/**
	 * "kills" the player and teleports them back to the beginning
	 * @param x - x coordinate of overworld
	 * @param y - y coordinate of overworld
	 */
	public void fadePlayerToBeginning(float x, float y) {
		
        Map.getInstance().death = false;

        if (alpha >= 0) {
            Gdx.gl.glEnable(GL20.GL_BLEND);
            Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
            shapeRenderer = new ShapeRenderer();
            shapeRenderer.setColor(0, 0, 0, alpha);
            shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
            shapeRenderer.rect(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
            shapeRenderer.end();

            Gdx.gl.glDisable(GL20.GL_BLEND);

            if (alpha >= 1) {
                setPosition(x, y);
                Map.getInstance().mapBackground = new Texture(Gdx.files.internal("overworld_bg.png"));
                fadeDirection =! fadeDirection;
            }
            //speed of fade
            alpha += fadeDirection == true ? 0.015 : -0.015;

            } else {

                fadeDirection =! fadeDirection;
                alpha = 0;

                entity.getComponent(B2dBodyComponent.class).body.setAwake(true);
                setGravityScale(1);
                Map.getInstance().setGravitySwitch(false);
                Map.getInstance().getLevels().getLevelTwo();
                Map.getInstance().death = false;
                Map.getInstance().getPlayerHUD().getStatusUI().getHealthBar().setHP(100);
                Map.getInstance().getAudioManager().stopAll();
                Map.getInstance().getAudioManager().playOverworld();
                Map.getInstance().getAudioManager().playPlayerDeath();

            }
        }

	public int getDefense() {
		return defense;
	}

	public void setDefense(int defense) {
		this.defense = defense;
	}

	public int getDamage() {
		return damage;
	}

	public void setDamage(int damage) {
		this.damage = damage;
	}
	
	
}
