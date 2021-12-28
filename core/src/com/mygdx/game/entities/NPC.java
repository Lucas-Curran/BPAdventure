package com.mygdx.game.entities;

import java.util.HashMap;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.Align;
import com.mygdx.game.BodyFactory;
import com.mygdx.game.Utilities;
import com.mygdx.game.components.B2dBodyComponent;
import com.mygdx.game.components.CollisionComponent;
import com.mygdx.game.components.NPCComponent;
import com.mygdx.game.components.PlayerComponent;
import com.mygdx.game.components.TextureComponent;
import com.mygdx.game.components.TransformComponent;
import com.mygdx.game.components.TypeComponent;
import com.mygdx.game.ui.ShopWindow;

public class NPC extends EntityHandler {
	
	public Entity spawnNPC(int posx, int posy) {
		
				Entity entity = pooledEngine.createEntity();
				B2dBodyComponent b2dbody = pooledEngine.createComponent(B2dBodyComponent.class);
				TransformComponent position = pooledEngine.createComponent(TransformComponent.class);
				TextureComponent texture = pooledEngine.createComponent(TextureComponent.class);
				CollisionComponent colComp = pooledEngine.createComponent(CollisionComponent.class);
				NPCComponent npcComp = pooledEngine.createComponent(NPCComponent.class);
				TypeComponent type = pooledEngine.createComponent(TypeComponent.class);

				// create the data for the components and add them to the components
				b2dbody.body = bodyFactory.makeCirclePolyBody(posx, posy, 1, BodyFactory.OTHER, BodyType.KinematicBody,true, true);
				// set object position (x,y,z) z used to define draw order 0 first drawn
				position.position.set(b2dbody.body.getPosition().x, b2dbody.body.getPosition().y, 0);
				texture.region = tex;
				type.type = TypeComponent.NPC;
				npcComp.wares = getShopWares();
				
				b2dbody.body.setUserData(entity);
				
				// add the components to the entity
				entity.add(type);
				entity.add(colComp);
				entity.add(b2dbody);
				entity.add(position);
				entity.add(texture);
				entity.add(npcComp);
				
				return entity;
	}
	
	public HashMap<Label, Image> getShopWares() {
		
		HashMap<Label, Image> testMap = new HashMap<Label, Image>();
		
		Image tex1 = new Image(new Texture(Gdx.files.internal("thinkBubble.png")));
		Image tex2 = new Image(new Texture(Gdx.files.internal("thinkBubble.png")));
		Image tex3 = new Image(new Texture(Gdx.files.internal("thinkBubble.png")));
		Image tex4 = new Image(new Texture(Gdx.files.internal("thinkBubble.png")));
		Image tex5 = new Image(new Texture(Gdx.files.internal("thinkBubble.png")));
		Image tex6 = new Image(new Texture(Gdx.files.internal("thinkBubble.png")));
		Image tex7 = new Image(new Texture(Gdx.files.internal("thinkBubble.png")));
		Image tex8 = new Image(new Texture(Gdx.files.internal("thinkBubble.png")));
		Image tex9 = new Image(new Texture(Gdx.files.internal("thinkBubble.png")));
		Image tex10 = new Image(new Texture(Gdx.files.internal("thinkBubble.png")));
		Image tex11 = new Image(new Texture(Gdx.files.internal("thinkBubble.png")));
		Image tex12 = new Image(new Texture(Gdx.files.internal("thinkBubble.png")));
		
		
		Label label1 = new Label("Apple", Utilities.ACTUAL_UI_SKIN);
		Label label2 = new Label("Platebodyaaaaaaaaaaaaaaaaaaa", Utilities.ACTUAL_UI_SKIN);
		Label label3 = new Label("Legs", Utilities.ACTUAL_UI_SKIN);
		Label label4 = new Label("Present", Utilities.ACTUAL_UI_SKIN);	
		Label label5 = new Label("Ape", Utilities.ACTUAL_UI_SKIN);
		Label label6 = new Label("Plateaaaaaaaaaaaa", Utilities.ACTUAL_UI_SKIN);
		Label label7 = new Label("L", Utilities.ACTUAL_UI_SKIN);
		Label label8 = new Label("Pasddqwrt", Utilities.ACTUAL_UI_SKIN);
		Label label9 = new Label("Adwqqdwpe", Utilities.ACTUAL_UI_SKIN);
		Label label10 = new Label("Plate41421a", Utilities.ACTUAL_UI_SKIN);
		Label label11 = new Label("L214", Utilities.ACTUAL_UI_SKIN);
		Label label12 = new Label("Pr1q3rt", Utilities.ACTUAL_UI_SKIN);
		
		testMap.put(label1, tex1);
		testMap.put(label2, tex2);
		testMap.put(label3, tex3);
		testMap.put(label4, tex4);
		testMap.put(label5, tex5);
		testMap.put(label6, tex6);
		testMap.put(label7, tex7);
		testMap.put(label8, tex8);
		testMap.put(label9, tex9);
		testMap.put(label10, tex10);
		testMap.put(label11, tex11);
		testMap.put(label12, tex12);
		
		return testMap;
	}
	
}
