package com.mygdx.game;

import java.io.IOException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.mygdx.game.components.B2dBodyComponent;
import com.mygdx.game.components.CollisionComponent;
import com.mygdx.game.components.EnemyComponent;
import com.mygdx.game.components.NPCComponent;
import com.mygdx.game.components.PlayerComponent;
import com.mygdx.game.components.TypeComponent;
import com.mygdx.game.entities.EntityHandler;
import com.mygdx.game.item.InventoryItem;
import com.mygdx.game.item.InventoryItem.ItemAttribute;
import com.mygdx.game.item.InventoryItem.ItemTypeID;
import com.mygdx.game.item.InventoryItem.ItemUseType;
import com.mygdx.game.levels.DoorBuilder;

public class B2dContactListener implements ContactListener {

	static Logger logger = LogManager.getLogger(B2dContactListener.class.getName());
	
	private EntityHandler parent;
	DoorBuilder db = DoorBuilder.getInstance();


	private TextureAtlas textureAtlas;
	private TextureAtlas lootAtlas;

	private boolean blessingLevelTwo = false;
	private boolean blessingLevelFour = false;
	private boolean blessingLevelFive = false;
	private boolean blessingLevelSix1 = false;
	private boolean blessingLevelSix2 = false;
	private boolean blessingLevelSix3 = false;

	private boolean keycard01 = false;
	private boolean keycard02 = false;
	private boolean keycard03 = false;
	private boolean keycard04 = false;
	private boolean keycard05 = false;

	private boolean monieLevelThree = false;
	private boolean monieLevelSeven = false;
	private boolean monieLevelEight = false;
	private boolean monieLevelNine = false;



	public B2dContactListener(EntityHandler parent) {
		this.parent = parent;

	}

	@Override
	public void beginContact(Contact contact) {
		final Fixture fa = contact.getFixtureA();
		final Fixture fb = contact.getFixtureB();

		// invokes a new Runnable in a separate thread to edit the world
		try {
			Gdx.app.postRunnable(new Runnable() {

				@Override
				public void run() {
					// runs through all the doors and finds the one the user went through
					for (int i = 0; i < db.doors.size(); i++) {

						if (fa.getBody().getUserData() == db.doors.get(i).getUserData()) {
							if (fb.getBody().getUserData() instanceof Entity) {
								Entity entB = (Entity) fb.getBody().getUserData();
								if (entB.getComponent(PlayerComponent.class) != null) {
									parent.loadingZone = true;
									parent.setDestinationX(db.destinationsX.get(i));
									parent.setDestinationY(db.destinationsY.get(i));
									parent.setDestination(db.destinations.get(i));
									parent.setCreatedLevel(db.createdLevels.get(i));

									// depending on the parameter passed to the DoorBuilder,
									// a case here creates the appropriate level and destroys the previous
									if (!db.isTouched.get(i)) {
										System.out.println(db.createdLevels.get(i));
										switch (db.createdLevels.get(i)) {

										case OVERWORLD:
											parent.getLevels().getOverworld().create();
											logger.info("Overworld created.");
											break;
										case LVL_2:
											parent.spawnLevelTwo();
											logger.info("Level two enemies spawned.");
											if (!parent.getLevels().getLevelTwo().isCreated()) {
												parent.getLevels().getLevelTwo().create();
												logger.info("Level two created.");
											}
											break;
										case LVL_3:
											parent.removeLevelTwo();
											parent.spawnLevelThree();
											logger.info("Level three enemies spawned.");
											if (!parent.getLevels().getLevelThree().isCreated()) {
												parent.getLevels().getLevelThree().create();
												logger.info("Level three created.");
											}
											break;
										case LVL_4:
											parent.removeLevelThree();
											parent.spawnLevelFour();
											logger.info("Level four enemies spawned.");
											if (!parent.getLevels().getLevelFour().isCreated()) {
												parent.getLevels().getLevelFour().create();
												logger.info("Level four created.");
											}
											break;
										case LVL_5:
											parent.removeLevelFour();
											parent.removeIceDungeon();
											logger.info("Level five enemies spawned.");
											parent.spawnLevelFive();
											if (!parent.getLevels().getLevelFive().isCreated()) {
												parent.getLevels().getLevelFive().create();
												logger.info("Level five created.");
											}
											break;
										case LVL_6:
											parent.removeLevelFive();
											parent.spawnLevelSix();
											logger.info("Level six enemies spawned.");
											if (!parent.getLevels().getLevelSix().isCreated()) {
												parent.getLevels().getLevelSix().create();
												logger.info("Level six created.");
											}
											break;
										case LVL_7:
											parent.removeLevelSix();
											parent.spawnLevelSeven();
											logger.info("Level seven enemies spawned.");
											if (!parent.getLevels().getLevelSeven().isCreated()) {
												parent.getLevels().getLevelSeven().create();
												logger.info("Level seven created.");
											}
											break;
										case LVL_8:
											parent.removeLevelSeven();
											parent.spawnLevelEight();
											logger.info("Level eight enemies spawned.");
											if (!parent.getLevels().getLevelEight().isCreated()) {
												parent.getLevels().getLevelEight().create();
												logger.info("Level eight created.");
											}
											break;
										case LVL_9:
											parent.removeLevelEight();
											parent.spawnLevelNine();
											logger.info("Level nine enemies spawned.");
											if (!parent.getLevels().getLevelNine().isCreated()) {
												parent.getLevels().getLevelNine().create();
												logger.info("Level nine created.");
											}
											break;
										case LVL_10:
											parent.removeLevelNine();
											parent.spawnLevelTen();
											logger.info("Level ten enemies spawned.");
											if (!parent.getLevels().getLevelTen().isCreated()) {
												parent.getLevels().getLevelTen().create();
												logger.info("Level ten created.");
											}
											if (!parent.getLevels().getVictoryLevel().isCreated()) {
												parent.getLevels().getVictoryLevel().create();
												logger.info("Victory level created");
											}
											break;
										case INTERNAL:
											break;
										default:
											System.out.println("no enum sent");
											break;

										}

										db.isTouched.set(i, true);
									}

								}
							}
						} else if (fb.getBody().getUserData() == db.doors.get(i).getUserData()) {
							if (fa.getBody().getUserData() instanceof Entity) {
								Entity entA = (Entity) fa.getBody().getUserData();
								if (entA.getComponent(PlayerComponent.class) != null) {
									parent.loadingZone = true;
									parent.setDestinationX(db.destinationsX.get(i));
									parent.setDestinationY(db.destinationsY.get(i));
									parent.setDestination(db.destinations.get(i));
								}
							}
						}
					}
				}
			});
		} catch (Exception e) {
			logger.error(e.getMessage());
			try {
				CrashWriter cw = new CrashWriter(e);
				cw.writeCrash();
			} catch (IOException e1) {
				logger.error(e1.getMessage());
			}
		}
		// contact statements for other bodies in the game and the player

		if (fa.getBody().getUserData() == "gravityPillar") {
			logger.debug("Gravity pillar hit");
			parent.gravityZone = true;

		} else if (fb.getBody().getUserData() == "gravityPillar") {
			logger.debug("Gravity pillar hit");
			parent.gravityZone = true;

		}

		if (fa.getBody().getUserData() == "gravityPillar2") {
			logger.debug("Gravity pillar 2 hit");
			parent.gravityZone = false;

		} else if (fb.getBody().getUserData() == "gravityPillar2") {
			logger.debug("Gravity pillar 2 hit");
			parent.gravityZone = false;

		}

		if (fa.getBody().getUserData() == "lavaFloor" || fa.getBody().getUserData() == "lavaCeiling"
				|| fa.getBody().getUserData() == "lavaCeiling2") {
			logger.debug("Lava hit");
			parent.killZone = true;
			// endAllLevels();
			parent.gravityZone = false;

		} else if (fb.getBody().getUserData() == "lavaFloor" || fb.getBody().getUserData() == "lavaCeiling"
				|| fb.getBody().getUserData() == "lavaCeiling2") {
			logger.debug("Lava hit");
			parent.killZone = true;
			// endAllLevels();
			parent.gravityZone = false;

		}

		// Gives players items based on what blessing they activated
		
		try {

			textureAtlas = new TextureAtlas("atlas_levelTwo.txt");
			lootAtlas = new TextureAtlas("otherTextures.txt");

			if (fa.getBody().getUserData() == "levelTwoBlessing" && !blessingLevelTwo) {
				InventoryItem worldKey = new InventoryItem(textureAtlas.findRegion("World Key"),
						ItemAttribute.STACKABLE.getValue(), ItemUseType.WEAPON_ONEHAND.getValue(), ItemTypeID.KEY1);
				Map.getInstance().getPlayerHUD().getInventory().addItemToInventory(worldKey, "World Key");
				blessingLevelTwo = true;
			}

			if (fa.getBody().getUserData() == "levelFourBlessing" && !blessingLevelFour) {
				InventoryItem iceSword = new InventoryItem(lootAtlas.findRegion("iceSword"),
						ItemAttribute.EQUIPPABLE.getValue(), ItemUseType.WEAPON_ONEHAND.getValue(), ItemTypeID.ICESWORD);
				InventoryItem iceShield = new InventoryItem(lootAtlas.findRegion("iceShield"),
						ItemAttribute.EQUIPPABLE.getValue(), ItemUseType.ARMOR_SHIELD.getValue(), ItemTypeID.ICESHIELD);

				Map.getInstance().getPlayerHUD().getInventory().addItemToInventory(iceSword, "Ice Sword");
				Map.getInstance().getPlayerHUD().getInventory().addItemToInventory(iceShield, "Ice Shield");
				Map.getInstance().getMoney().setMoney(Map.getInstance().getMoney().getMoney() + 2);


				blessingLevelFour = true;
			}

			if (fa.getBody().getUserData() == "levelFiveBlessing" && !blessingLevelFive) {
				InventoryItem desertShield = new InventoryItem(lootAtlas.findRegion("desertShield"),
						ItemAttribute.EQUIPPABLE.getValue(), ItemUseType.ARMOR_SHIELD.getValue(), ItemTypeID.DESERTSHIELD);

				Map.getInstance().getPlayerHUD().getInventory().addItemToInventory(desertShield, "Desert Shield");
				Map.getInstance().getMoney().setMoney(Map.getInstance().getMoney().getMoney() + 10);

				blessingLevelFive = true;
			}

			if (fa.getBody().getUserData() == "levelSixBlessing1" && !blessingLevelSix1) {
				InventoryItem jungleStaff = new InventoryItem(lootAtlas.findRegion("jungleStaff"),
						ItemAttribute.EQUIPPABLE.getValue(), ItemUseType.WEAPON_ONEHAND.getValue(), ItemTypeID.JUNGLESTAFF);

				Map.getInstance().getPlayerHUD().getInventory().addItemToInventory(jungleStaff, "Jungle Staff");
				Map.getInstance().getMoney().setMoney(Map.getInstance().getMoney().getMoney() + 2);

				blessingLevelSix1 = true;
			}

			if (fa.getBody().getUserData() == "levelSixBlessing2" && !blessingLevelSix2) {
				InventoryItem jungleHelmet = new InventoryItem(lootAtlas.findRegion("jungleHelmet"),
						ItemAttribute.EQUIPPABLE.getValue(), ItemUseType.ARMOR_HELMET.getValue(), ItemTypeID.JUNGLEHELMET);

				Map.getInstance().getPlayerHUD().getInventory().addItemToInventory(jungleHelmet, "Jungle Helmet");

				blessingLevelSix2 = true;
			}

			if (fa.getBody().getUserData() == "levelSixBlessing3" && !blessingLevelSix3) {
				InventoryItem jungleChest = new InventoryItem(lootAtlas.findRegion("jungleChest"),
						ItemAttribute.EQUIPPABLE.getValue(), ItemUseType.ARMOR_CHEST.getValue(), ItemTypeID.JUNGLECHEST);

				Map.getInstance().getPlayerHUD().getInventory().addItemToInventory(jungleChest, "Jungle Chestplate");


				blessingLevelSix3 = true;
			}


			// gives players money based on their completion of a parkour level

			if (fa.getBody().getUserData() == "moneyBox3" && !monieLevelThree) {
				Map.getInstance().getMoney().setMoney(Map.getInstance().getMoney().getMoney() + 5);
				monieLevelThree = true;
			}

			if (fa.getBody().getUserData() == "moneyBox7" && !monieLevelSeven) {
				Map.getInstance().getMoney().setMoney(Map.getInstance().getMoney().getMoney() + 3);
				monieLevelSeven = true;
			}

			if (fa.getBody().getUserData() == "moneyBox8" && !monieLevelEight) {
				Map.getInstance().getMoney().setMoney(Map.getInstance().getMoney().getMoney() + 5);
				monieLevelEight = true;
			}

			if (fa.getBody().getUserData() == "moneyBox9" && !monieLevelNine) {
				Map.getInstance().getMoney().setMoney(Map.getInstance().getMoney().getMoney() + 15);
				monieLevelNine = true;
			}

			// gives players keycards after they collect them in level 7


			if (fa.getBody().getUserData() == "keycard1" && !keycard01) {
				InventoryItem keycard1 = new InventoryItem(Utilities.keyCard,
						ItemAttribute.STACKABLE.getValue(), ItemUseType.WEAPON_ONEHAND.getValue(), ItemTypeID.KEYCARD01);
				Map.getInstance().getPlayerHUD().getInventory().addItemToInventory(keycard1, "Keycard 1");
				keycard01 = true;
				System.out.println(parent.getWorld().isLocked());

			}
			if (fa.getBody().getUserData() == "keycard2" && !keycard02) {
				InventoryItem keycard2 = new InventoryItem(Utilities.keyCard,
						ItemAttribute.STACKABLE.getValue(), ItemUseType.WEAPON_ONEHAND.getValue(), ItemTypeID.KEYCARD02);
				Map.getInstance().getPlayerHUD().getInventory().addItemToInventory(keycard2, "Keycard 2");
				keycard02 = true;
			}
			if (fa.getBody().getUserData() == "keycard3" && !keycard03) {
				InventoryItem keycard3 = new InventoryItem(Utilities.keyCard,
						ItemAttribute.STACKABLE.getValue(), ItemUseType.WEAPON_ONEHAND.getValue(), ItemTypeID.KEYCARD03);
				Map.getInstance().getPlayerHUD().getInventory().addItemToInventory(keycard3, "Keycard 3");
				keycard03 = true;
			}
			if (fa.getBody().getUserData() == "keycard4" && !keycard04) {
				InventoryItem keycard4 = new InventoryItem(Utilities.keyCard,
						ItemAttribute.STACKABLE.getValue(), ItemUseType.WEAPON_ONEHAND.getValue(), ItemTypeID.KEYCARD04);
				Map.getInstance().getPlayerHUD().getInventory().addItemToInventory(keycard4, "Keycard 4");
				keycard04 = true;
			}
			if (fa.getBody().getUserData() == "keycard5" && !keycard05) {
				InventoryItem keycard5 = new InventoryItem(Utilities.keyCard,
						ItemAttribute.STACKABLE.getValue(), ItemUseType.WEAPON_ONEHAND.getValue(), ItemTypeID.KEYCARD05);
				Map.getInstance().getPlayerHUD().getInventory().addItemToInventory(keycard5, "Keycard 5");
				keycard05 = true;
			}



			if (fa.getBody().getUserData() instanceof Entity) {
				Entity ent = (Entity) fa.getBody().getUserData();
				entityCollision(ent, fb);
				return;
			} else if (fb.getBody().getUserData() instanceof Entity) {
				Entity ent = (Entity) fb.getBody().getUserData();
				entityCollision(ent, fa);
				return;
			}
		
		} catch (Exception e) {
			logger.error(e.getMessage());
			try {
				CrashWriter cw = new CrashWriter(e);
				cw.writeCrash();
			} catch (IOException e1) {
				logger.error(e1.getMessage());
			}
		}

	}

	/**
	 * Detects whether an entity has collided with a box2d fixture
	 * @param ent - entity
	 * @param fb  - fixture body
	 */
	private void entityCollision(Entity ent, Fixture fb) {
		if (fb.getBody().getUserData() instanceof Entity) {
			Entity colEnt = (Entity) fb.getBody().getUserData();

			CollisionComponent col = ent.getComponent(CollisionComponent.class);
			CollisionComponent colb = colEnt.getComponent(CollisionComponent.class);
			
			try {
				if (ent.getComponent(TypeComponent.class) != null && colEnt.getComponent(TypeComponent.class) != null) {
					if (col != null) {
						col.collisionEntity = colEnt;
						if (colEnt.getComponent(TypeComponent.class).type == TypeComponent.NPC) {
							parent.npcX = colEnt.getComponent(B2dBodyComponent.class).body.getPosition().x;
							parent.npcY = colEnt.getComponent(B2dBodyComponent.class).body.getPosition().y;
							parent.talkingZone = true;
							parent.setCurrentNPCText(colEnt.getComponent(NPCComponent.class).text);
							parent.setHasOptions(colEnt.getComponent(NPCComponent.class).hasOptions);
						}
					} else if (colb != null) {
						colb.collisionEntity = ent;
						if (ent.getComponent(TypeComponent.class).type == TypeComponent.NPC) {
							parent.npcX = ent.getComponent(B2dBodyComponent.class).body.getPosition().x;
							parent.npcY = ent.getComponent(B2dBodyComponent.class).body.getPosition().y;
							parent.talkingZone = true;
							parent.setCurrentNPCText(ent.getComponent(NPCComponent.class).text);
							parent.setHasOptions(ent.getComponent(NPCComponent.class).hasOptions);
						}
					}
				}
			} catch (Exception e) {
				logger.error(e.getMessage());
				try {
					CrashWriter cw = new CrashWriter(e);
					cw.writeCrash();
				} catch (IOException e1) {
					logger.error(e1.getMessage());
				}
			}
		}
	}

	@Override
	public void endContact(Contact contact) {
		Fixture fa = contact.getFixtureA();
		Fixture fb = contact.getFixtureB();
		try {
			for (int i = 0; i < db.doors.size(); i++) {
				if (fa.getBody().getUserData() == db.doors.get(i).getUserData()) {
					if (fb.getBody().getUserData() instanceof Entity) {
						Entity entB = (Entity) fb.getBody().getUserData();
						if (entB.getComponent(TypeComponent.class).type == TypeComponent.PLAYER) {
							logger.debug("Door loading zone exited.");
							parent.loadingZone = false;
						}
					}
				} else if (fb.getBody().getUserData() == db.doors.get(i).getUserData()) {
					if (fa.getBody().getUserData() instanceof Entity) {
						Entity entA = (Entity) fa.getBody().getUserData();
						if (entA.getComponent(TypeComponent.class).type == TypeComponent.PLAYER) {
							logger.debug("Door loading zone exited.");
							parent.loadingZone = false;
						}
					}
				}
			}

			if (fa.getBody().getUserData() instanceof Entity && fb.getBody().getUserData() instanceof Entity) {
				Entity entA = (Entity) fa.getBody().getUserData();
				Entity entB = (Entity) fb.getBody().getUserData();
				if (entA.getComponent(TypeComponent.class) != null && entB.getComponent(TypeComponent.class) != null) {
					if (entA.getComponent(TypeComponent.class).type == TypeComponent.NPC
							&& entB.getComponent(TypeComponent.class).type == TypeComponent.PLAYER) {
						logger.debug("NPC talking zone exited.");
						parent.talkingZone = false;
					} else if (entB.getComponent(TypeComponent.class).type == TypeComponent.NPC
							&& entA.getComponent(TypeComponent.class).type == TypeComponent.PLAYER) {
						logger.debug("NPC talking zone exited.");
						parent.talkingZone = false;
					}
				}
			}

			if (fa.getBody().getUserData() == "lavaFloor" || fa.getBody().getUserData() == "lavaCeiling"
					|| fa.getBody().getUserData() == "lavaCeiling2") {
				parent.killZone = false;

			} else if (fb.getBody().getUserData() == "lavaFloor" || fb.getBody().getUserData() == "lavaCeiling"
					|| fb.getBody().getUserData() == "lavaCeiling2") {
				parent.killZone = false;
			}

			if (fa.getBody().getUserData() == "Sword") {
				if (fb.getBody().getUserData() instanceof Entity) {
					Entity ent = (Entity) fb.getBody().getUserData();
					if (ent.getComponent(TypeComponent.class).type == TypeComponent.ENEMY) {
						ent.getComponent(EnemyComponent.class).health -= 1;

					}
				}
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
			try {
				CrashWriter cw = new CrashWriter(e);
				cw.writeCrash();
			} catch (IOException e1) {
				logger.error(e1.getMessage());
			}
		}
	}

	@Override
	public void preSolve(Contact contact, Manifold oldManifold) {
	}

	@Override
	public void postSolve(Contact contact, ContactImpulse impulse) {
	}

}
