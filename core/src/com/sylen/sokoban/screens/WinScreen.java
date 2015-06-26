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
import com.sylen.sokoban.U;

public class WinScreen implements Screen{
	/* must have */
	private Game game;
	private String lvl;
	private Stage stage;
	private Batch batch;
	
	
	/* sounds */
	private Music music;
	
	/* buttons */
	private TextButton next;
	private TextButton select;
	
	/* visuals */
	private Background bg;
	private float logoHeight = 325f *U.W;
	private float logoWidth = 4 * logoHeight;
	//private final float logoOffsetY = 425f *U.H;
	private float logoOffsetY = 350f *U.H;
	
	
	/* constructor */
	public WinScreen(Game game, String lvl, Stage stage){
		this.game = game;
		this.lvl = Integer.toString(Integer.parseInt(lvl) +1);
		this.stage = stage;
	}
	
	
	/* show */
	@Override
	public void show() {
		/* instances */
		stage.clear();
		batch = stage.getBatch();
		bg = new Background("bgWin", 0f, 1f);
		bg.setSize(256f);
		
		music = Gdx.audio.newMusic(Gdx.files.internal(U.folder +"sound/win.wav"));
		//music.setLooping(true);//TODO delete
		music.setVolume(0.9f);
		if (!U.d.mute) music.play();
		
		setupButtons();
		
		Gdx.input.setInputProcessor(stage);
	}
	
	private void setupButtons(){
		/* must have */
		Skin skin = new Skin();
		skin.addRegions(U.atlas);
		TextButtonStyle buttonStyle = new TextButtonStyle();
		buttonStyle.up = skin.getDrawable("winButton");
		buttonStyle.font = new BitmapFont(Gdx.files.internal(U.folder +"font/font.fnt"));
		buttonStyle.font.setScale(1f *U.W);
		buttonStyle.fontColor = Color.BLACK;
		
		/* instances */
		next = new TextButton("NEXT LEVEL", buttonStyle);
		select = new TextButton("BACK", buttonStyle);
		
		/* size */
		float scaleX = 1.5f *U.W;
		float scaleY = 2.1f *U.H;
		next.setWidth(next.getWidth() *scaleX);
		next.setHeight(next.getHeight() *scaleY);
		select.setWidth(select.getWidth() *scaleX);
		select.setHeight(select.getHeight() *scaleY);
		
		/* position */
		select.setX(Gdx.graphics.getWidth()/2f - select.getWidth()/2f);
		select.setY(10*U.H);
		next.setX(Gdx.graphics.getWidth()/2f - next.getWidth()/2f);
		next.setY(select.getY() + select.getHeight() + 10*U.H);
		
		/* logo */
		logoOffsetY = next.getY() + next.getHeight() + 12*U.H;
		if((logoOffsetY + logoWidth)*U.H > 1000) {
			float scY = 0.8f;
			logoOffsetY *= scY;
			next.setHeight(next.getHeight() * scY);
			next.setY(next.getY() * scY);
			select.setHeight(select.getHeight() * scY);
			select.setY(select.getY() * scY);
			
			logoHeight *= 0.9f;
			logoWidth *= 0.9f;
		}
		
		/* listeners */
		next.addListener(new InputListener(){
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
            	Gdx.input.setInputProcessor(null);
            	dispose();
            	game.setScreen(new GameScreen(game, lvl, null, stage));
            	return true;
            }
        });
		select.addListener(new InputListener(){
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
            	Gdx.input.setInputProcessor(null);
            	dispose();
            	game.setScreen(new LevelSelect(game, null, null, stage));
            	return true;
            }
        });
		
		/* add to stage*/
		stage.addActor(next);
		stage.addActor(select);
		
		/* dispose */
		skin.dispose();
	}
	
	
	/* update */
	private void update(){
		bg.update();
		stage.act();
	}
	
	
	/* render */
	@Override
	public void render(float delta) {
		update();
		
		Gdx.gl.glClearColor(1, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		
		batch.begin();
		
		/* background */
		bg.draw(batch);
		
		/* logo */
		batch.draw(U.atlas.createSprite("winLogo"), Gdx.graphics.getWidth()/2f - logoWidth/2f, logoOffsetY, logoWidth, logoHeight);
		
		/* FPS */
		U.drawFps(batch);
		
		batch.end();
		
		stage.draw();
		
	}
	
	
	
	
	
	
	/* dispose */
	@Override
	public void dispose() {
		music.dispose();
		bg.dispose();
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
