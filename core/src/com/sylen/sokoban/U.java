package com.sylen.sokoban;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;

public class U {
	public static String folder = "default/";
	public static final String SAVE_FILE = "profile.so";
	public static Data d;
	
	public static TextureAtlas atlas;
	
	public static final float W = Gdx.graphics.getWidth() / 1000f;
	public static final float H = Gdx.graphics.getHeight() / 1000f;
	//public static final float W = 1f;
	//public static final float H = 1f;
	
	public static final float TILE_SIZE = 48f;
	public static final byte MAP_WIDTH = 11;
	public static final byte MAP_HEIGHT = 11;
	public static final byte MAX_PORTALS = 20;
	public static final byte MAX_LEVELS = 20;
	
	public static final boolean showFPS = false;
	public static final CharSequence INFO = "Graphics and coding made by Sylen,\nCoded with libGDX\nGraphics made in Ms Paint :-)\nMusic used under a Creative Commons\nLicense from UniqueTracks Inc.\n\n\n\n";
	
	public static BitmapFont FPSfont;
	
	public static void drawFps(Batch batch){
		if(showFPS){
			FPSfont.draw(batch, "FPS: " + Gdx.graphics.getFramesPerSecond(), 5f, 20f);
			FPSfont.draw(batch, "version 0.9", 800*U.W, 20f);
		}
	}
}
