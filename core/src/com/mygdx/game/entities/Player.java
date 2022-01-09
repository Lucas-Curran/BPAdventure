package com.mygdx.game.entities;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.mygdx.game.BodyFactory;
import com.mygdx.game.Map;
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
	
	private Entity entity;
	
	//true if fade in, false if fade out
	private boolean fadeDirection = true;
	
	private float alpha = 0;
	
	private ShapeRenderer shapeRenderer;
	
	Levels levels = new Levels(gameWorld.getInstance());
	
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
		position.position.set(b2dbody.body.getPosition().x, b2dbody.body.getPosition().x, 0);
		texture.region = tex;
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

		return entity;		
	}
	
	public int getDirection() {
		return entity.getComponent(PlayerComponent.class).direction;
	}
	
	public float getX() {
		return entity.getComponent(B2dBodyComponent.class).body.getPosition().x;
	}
	
	public float getY() {
		return entity.getComponent(B2dBodyComponent.class).body.getPosition().y;
	}
	
	public Entity getEntity() {
		return entity;
	}
	
	public void setPosition(float x, float y) {
		entity.getComponent(B2dBodyComponent.class).body.setTransform(new Vector2(x, y), 0);
		entity.getComponent(B2dBodyComponent.class).body.setLinearVelocity(new Vector2(0, 0));
	}
	
	public void setGravityScale(float scale) {
        entity.getComponent(B2dBodyComponent.class).body.setGravityScale(scale);
    }
	
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

	        System.out.println(alpha);

	        if (alpha >= 1) {
	            
	            setPosition(x, y);
	            Map.getInstance().mapBackground = new Texture(Gdx.files.internal(level));
	            fadeDirection =! fadeDirection;
	        }
	        //speed of fade
	        alpha += fadeDirection == true ? 0.015 : -0.015;

	        } else {

	            fadeDirection =! fadeDirection;
	            alpha = 0;

	            entity.getComponent(B2dBodyComponent.class).body.setAwake(true);

	            Map.getInstance().teleporting = false;

	        }
	    }
	
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
//            font = new BitmapFont();
//            batch.setProjectionMatrix(cam.getCombined());
//            batch.begin();
//            font.draw(batch, "you died", 50,50);
//            batch.end();
            Gdx.gl.glDisable(GL20.GL_BLEND);

            System.out.println(alpha);

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
                Map.getInstance().death = false;


            }
        }
}
