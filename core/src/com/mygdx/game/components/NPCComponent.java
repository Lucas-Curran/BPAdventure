package com.mygdx.game.components;

import java.util.HashMap;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.mygdx.game.item.ShopItem;

public class NPCComponent implements Component {
	public String name = null;
	public HashMap<Label, ShopItem> wares = null;
	public String[] text = null;
	public boolean hasOptions = false;
}
