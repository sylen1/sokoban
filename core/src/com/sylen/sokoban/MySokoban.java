package com.sylen.sokoban;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.sylen.sokoban.screens.MainMenu;

public class MySokoban extends Game {
	
	@Override
	public void create () {
		/* atlas */
		U.atlas = new TextureAtlas(U.folder + "atlas/atlas.pack");
		U.FPSfont = new BitmapFont();
		
		U.d = (Data)Save.load(U.SAVE_FILE);
		if (U.d == null) U.d = new Data(); //TODO enable
		//U.d = new Data(); //TODO delete
		
		setScreen(new MainMenu(this, null, null));
	}

	@Override
	public void render () {
		super.render();
	}
}
