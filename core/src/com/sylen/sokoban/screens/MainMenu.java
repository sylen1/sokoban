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
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.sylen.sokoban.Background;
import com.sylen.sokoban.Save;
import com.sylen.sokoban.U;

public class MainMenu implements Screen{
	/* must have */
	private Game game;
	private Batch batch;
	private Stage stage;
	
	/* buttons */
	private TextButton startButton;
	private TextButton exitButton;
	
	/* visuals */
	private Table tableInfo;
	private Background bg;
	private float logoHeight = 575f *U.H;
	private float logoWidth = 700f *U.W;
	private float logoOffY = 315f *U.H;
	
	/* sounds */
	private Music music;
	private TextButton muteButton;
	private Skin skin;
	private TextButtonStyle buttonStyle;

	
	/* constructor */
	public MainMenu(Game game ,Background bg, Music music){
		this.game = game;
		this.bg = bg;
		this.music = music;
	}
	
	
	/* show */
	@Override
	public void show() {
		/* instances */
		stage = new Stage();
		batch = stage.getBatch();
		if (bg == null){bg = new Background("bgMenu", 0.5f, 0f);}
		else {bg.setTexture("bgMenu"); bg.setDx(0.5f); bg.setDy(0f);}
		
		setupButtons();
		setupMusic();
		setupInfo();
		
		Gdx.input.setInputProcessor(stage);
	}
	
	
	private void setupButtons() {
		/* must have */
		Skin skin = new Skin();
		skin.addRegions(U.atlas);
		TextButtonStyle buttonStyle = new TextButtonStyle();
		buttonStyle.up = skin.getDrawable("button");
		buttonStyle.font = new BitmapFont(Gdx.files.internal(U.folder +"font/font.fnt"));
		buttonStyle.font.setScale(1f *U.W);
		buttonStyle.fontColor = Color.BLACK;
		
		/* instances */
		startButton = new TextButton("START", buttonStyle);
		exitButton = new TextButton("EXIT", buttonStyle);
		
		/* size */
		float scaleX = 1.5f *U.W;
		float scaleY = 1.9f *U.H;
		startButton.setWidth(startButton.getWidth() *scaleX);
		startButton.setHeight(startButton.getHeight() *scaleY);
		exitButton.setWidth(exitButton.getWidth() *scaleX);
		exitButton.setHeight(exitButton.getHeight() *scaleY);
		
		/* position */
		exitButton.setX(Gdx.graphics.getWidth()/2f - exitButton.getWidth()/2f);
		exitButton.setY(10*U.H);
		startButton.setX(Gdx.graphics.getWidth()/2f - startButton.getWidth()/2f);
		startButton.setY(exitButton.getY() + 10*U.H + startButton.getHeight());
		
		/*logo position*/
		logoOffY = startButton.getY() + startButton.getHeight() + 12*U.H;
		if((logoOffY + logoWidth)*U.H > 1000) {
			float scY = 0.8f;
			logoOffY *= scY;
			startButton.setHeight(startButton.getHeight() * scY);
			startButton.setY(startButton.getY() * scY);
			exitButton.setHeight(exitButton.getHeight() * scY);
			exitButton.setY(exitButton.getY() * scY);
			
			logoHeight *= 0.9f;
			logoWidth *= 0.9f;
		}
		
		
		if (exitButton.getY()<0) exitButton.setY(-15f);
		
		/* listeners */
		startButton.addListener(new InputListener(){
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
            	Gdx.input.setInputProcessor(null);
            	dispose();
            	game.setScreen(new LevelSelect(game, bg, music, stage));
            	return true;
            }
        });
		exitButton.addListener(new InputListener(){
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
            	Save.save(U.d, U.SAVE_FILE);
            	Gdx.app.exit();
            	return true;
            }
        });
		
		/* add to stage*/
		stage.addActor(exitButton);
		stage.addActor(startButton);
		
		
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
		if (music == null) {
			music = Gdx.audio.newMusic(Gdx.files.internal(U.folder +"sound/menu.wav"));
			music.setLooping(true);
			music.setVolume(0.9f);
			if (!U.d.mute) music.play();;
		}
		
	}
	
	
	/* update */
	private void update() {
		stage.act();
		bg.update();
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
		batch.draw(U.atlas.createSprite("logo"), Gdx.graphics.getWidth()/2f - logoWidth/2f, logoOffY, logoWidth, logoHeight);
		
		/* FPS */
		U.drawFps(batch);
		
		batch.end();
		
		/* stage */
		stage.draw();
		
		
	}
	
	
	/* dispose */
	@Override
	public void dispose() {
		/* must haves */
		
		/* sounds */
		//music.dispose();
		skin.dispose();
		
	}
	
	
	
	
	
	private void setupInfo(){
		TextButtonStyle is = new TextButtonStyle();
		is.font = new BitmapFont();
		is.up = skin.getDrawable("info");
		
		TextButton buttonInfo = new TextButton("", is);
		buttonInfo.setPosition(35f, 35f);
		
		buttonInfo.addListener(new InputListener(){
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
            	tableInfo.setVisible(!tableInfo.isVisible());
            	return true;
            }
        });
		
		tableInfo = new Table();
		LabelStyle ls = new LabelStyle();
		ls.font = new BitmapFont(Gdx.files.internal(U.folder +"font/font.fnt"));
		ls.font.setScale(0.4f);
		
		Label label = new Label(U.INFO, ls);
		label.setPosition(42f, 50f);	//TODO
		
		tableInfo.addActor(label);
		
		tableInfo.setBackground(skin.getDrawable("infoBg"));
		tableInfo.setSize(Gdx.graphics.getWidth() *0.8f, Gdx.graphics.getHeight());
		tableInfo.setPosition(Gdx.graphics.getWidth() *0.2f, 0f);
		tableInfo.setVisible(false);
		
		stage.addActor(tableInfo);
		stage.addActor(buttonInfo);
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
