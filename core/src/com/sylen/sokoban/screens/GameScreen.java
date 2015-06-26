package com.sylen.sokoban.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.sylen.sokoban.Background;
import com.sylen.sokoban.Level;
import com.sylen.sokoban.U;

public class GameScreen implements Screen{
	/* must have */
	private Game game;
	private Batch batch;
	private Stage stage;
	
	/* level */
	private Level level;
	private String lvl;
	
	/* visuals */
	private Background bg;
	
	/* buttons */
	private TextButton lvlSelectButton;
	private TextButton restartButton;
	private TextButton right;
	private TextButton left;
	private TextButton up;
	private TextButton down;
	
	/* sounds */
	private Music music;
	private TextButton muteButton;
	private Skin skin;
	private TextButtonStyle buttonStyle;
	
	/* tip */
	private boolean showTip;
	
	
	/* constructor */
	public GameScreen(Game game, String lvl, Background bg, Stage stage){
		this.game = game;
		this.lvl = lvl;
		this.bg = bg;
		this.stage = stage;
	}
	
	@Override
	public void show() {
		/* instances */
		batch = stage.getBatch();
		stage.clear();
		level = new Level(lvl, this);
		if (bg == null){bg = new Background("bgGame", 0f, 0.5f);}
		else {bg.setTexture("bgGame"); bg.setDx(0f); bg.setDy(0.5f);}
		
		setupButtons();
		setupJoystick();
		setupMusic();
		showTip = lvl.equals("1");
		
		Gdx.input.setInputProcessor(stage);
	}
	
	private void setupButtons(){
		/* must have */
		Skin skin = new Skin();
		skin.addRegions(U.atlas);
		TextButtonStyle buttonStyle = new TextButtonStyle();
		buttonStyle.up = skin.getDrawable("backButton");
		buttonStyle.font = new BitmapFont();
		TextButtonStyle restartStyle = new TextButtonStyle();
		restartStyle.up = skin.getDrawable("restartButton");
		restartStyle.font = new BitmapFont();
		
		float space = 32f *U.W;
		
		/* instances */
		lvlSelectButton = new TextButton("", buttonStyle);
		restartButton = new TextButton("", restartStyle);
		
		/* size */
		//lvlSelectButton.setSize(100f *U.W, 100f *U.W);
		//restartButton.setSize(100f *U.W, 100f *U.W);
		
		/* position */
		lvlSelectButton.setPosition(Gdx.graphics.getWidth() - lvlSelectButton.getWidth() -space, Gdx.graphics.getHeight() - lvlSelectButton.getHeight() -space);
		restartButton.setPosition(Gdx.graphics.getWidth() - restartButton.getWidth() -space, lvlSelectButton.getY() - restartButton.getHeight() -space);
		
		/* listeners */
		lvlSelectButton.addListener(new InputListener(){
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
            	back();
            	return true;
            }
        });
		restartButton.addListener(new InputListener(){
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
            	level.reset();
            	return true;
            }
        });
		
		/* add to stage*/
		stage.addActor(lvlSelectButton);
		stage.addActor(restartButton);
		
		/* dispose */
		skin.dispose();
	}
	
	private void setupJoystick(){
		/* must have */
		Skin skin = new Skin();
		skin.addRegions(U.atlas);
		TextButtonStyle rightStyle = new TextButtonStyle();
		rightStyle.up = skin.getDrawable("arrowRightUp");
		rightStyle.down = skin.getDrawable("arrowRightDown");
		rightStyle.font = new BitmapFont();
		TextButtonStyle leftStyle = new TextButtonStyle();
		leftStyle.up = skin.getDrawable("arrowLeftUp");
		leftStyle.down = skin.getDrawable("arrowLeftDown");
		leftStyle.font = new BitmapFont();
		TextButtonStyle upStyle = new TextButtonStyle();
		upStyle.up = skin.getDrawable("arrowUpUp");
		upStyle.down = skin.getDrawable("arrowUpDown");
		upStyle.font = new BitmapFont();
		TextButtonStyle downStyle = new TextButtonStyle();
		downStyle.up = skin.getDrawable("arrowDownUp");
		downStyle.down = skin.getDrawable("arrowDownDown");
		downStyle.font = new BitmapFont();
		
		/* instances */
		right = new TextButton("", rightStyle);
		right.setName("0");
		left = new TextButton("", leftStyle);
		left.setName("1");
		up = new TextButton("", upStyle);
		up.setName("2");
		down = new TextButton("", downStyle);
		down.setName("3");
		
		/* size */
		right.setSize(125f *U.W, 125f *U.W);
		left.setSize(right.getWidth(), right.getHeight());
		up.setSize(right.getHeight(), right.getWidth());
		down.setSize(right.getHeight(), right.getWidth());
		
		/* position */
		float space = 70;
		right.setPosition(left.getWidth() +space*U.W, 90*U.W);
		left.setPosition(0f, right.getY());
		up.setPosition(98 *U.W, 180 *U.W);
		down.setPosition(up.getX(), 0f);
		
		/* listeners */
		InputListener listener = new InputListener(){
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
            	level.move(Byte.parseByte(((TextButton)(event.getListenerActor())).getName()));
            	return true;
            }
        };
        right.addListener(listener);
        left.addListener(listener);
        up.addListener(listener);
        down.addListener(listener);
		
		/* add to stage*/
		stage.addActor(left);
		stage.addActor(right);
		stage.addActor(up);
		stage.addActor(down);
		
		
		/* dispose */
		skin.dispose();
	}
	
	private void setupMusic(){
		/* mute button */
		skin = new Skin();
		skin.addRegions(U.atlas);
		buttonStyle = new TextButtonStyle();
		buttonStyle.font = new BitmapFont(Gdx.files.internal(U.folder +"font/font.fnt"));
		buttonStyle.font.setScale(1f *U.W);
		buttonStyle.fontColor = Color.BLACK;
		buttonStyle.up = skin.getDrawable((U.d.mute) ? "mute_on" : "mute_off");
		
		muteButton = new TextButton("", buttonStyle);
		
		muteButton.setScale(U.W);
		muteButton.setPosition(Gdx.graphics.getWidth() -muteButton.getWidth() - 50f *U.W, 50f *U.W);
		
		
		muteButton.addListener(new InputListener(){
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
            	U.d.mute = !U.d.mute;
            	if (U.d.mute) {
            		music.pause();
            		buttonStyle.up = skin.getDrawable("mute_on");
            	} else {
            		music.play();
            		buttonStyle.up = skin.getDrawable("mute_off");
            	}
            	return true;
            }
        });
		
		
		stage.addActor(muteButton);
		
		/* music */
		music = Gdx.audio.newMusic(Gdx.files.internal(U.folder +"sound/game_0.wav"));
		music.setLooping(true);
		if (!U.d.mute) music.play();
	}
	
	
	private void update() {
		stage.act();
		bg.update();
		//level.update();
	}
	
	@Override
	public void render(float delta) {
		update();
		
		Gdx.gl.glClearColor(1, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		batch.begin();
		
		/* background */
		bg.draw(batch);
		
		/* level */
		level.draw(batch);
		
		/* tip */
		if (showTip){
			U.FPSfont.drawMultiLine(batch, "Push all brown boxes to blue portals.\n"
					+ "Tap restart button if you get stuck.\n"
					+ "...and try not to hurt yourself :)",
					350f *U.W, Gdx.graphics.getHeight() - 175 *U.H);
		}
		
		/* FPS */
		U.drawFps(batch);
		
		batch.end();
		
		stage.draw();
	}
	
	public void back(){
		Gdx.input.setInputProcessor(null);
		dispose();
    	game.setScreen(new LevelSelect(game, bg, null, stage));
	}
	
	public void win(){
		Gdx.input.setInputProcessor(null);
		dispose();
		game.setScreen(new WinScreen(game, lvl, stage));
	}
	
	
	

	@Override
	public void dispose() {
		level.dispose();
		bg.dispose();
		music.dispose();
		skin.dispose();
	}
	
	

	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	@Override
	public void resize(int width, int height) {
		
	}

	@Override
	public void hide() {
		
	}

	@Override
	public void pause() {
		
	}

	@Override
	public void resume() {
		
	}

	
}
