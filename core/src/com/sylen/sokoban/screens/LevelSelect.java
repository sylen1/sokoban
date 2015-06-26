package com.sylen.sokoban.screens;

import java.util.ArrayList;

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
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.sylen.sokoban.Background;
import com.sylen.sokoban.U;

public class LevelSelect implements Screen{
	/* must have */
	private Game game;
	private Batch batch;
	private Stage stage;
	
	/* visuals  */
	private Background bg;
	
	/* buttons */
	private ArrayList<TextButton> buttons;
	private TextButton backButton;
	
	/* sounds */
	private Music music;
	
	/* tables */
	private byte currentTable;
	private ArrayList<Table> tables;
	private TextButton next;
	private TextButton prev;
	private byte buttonsPerTable;
	
	/* constructor */
	public LevelSelect(Game game, Background bg, Music music, Stage stage){
		this.game = game;
		this.bg = bg;
		this.music = music;
		this.stage = stage;
	}
	
	
	/* show */
	@Override
	public void show() {
		/* instances */
		batch = stage.getBatch();
		stage.clear();
		buttons = new ArrayList<TextButton>();
		if (bg == null){bg = new Background("bgLvlSelect", -0.5f, 0f);}
		else {bg.setTexture("bgLvlSelect"); bg.setDx(-0.5f); bg.setDy(0f);}
		
		if(music == null) {
			music = Gdx.audio.newMusic(Gdx.files.internal(U.folder +"sound/menu.wav"));
			music.setLooping(true);
			music.setVolume(0.9f);
			if(!U.d.mute) music.play();
		}
		
		setupButtons();
		
		Gdx.input.setInputProcessor(stage);
	}
	
	private void setupButtons(){
		byte i;
		/* must have */
		Skin skin = new Skin();
		skin.addRegions(U.atlas);
		TextButtonStyle buttonStyle = new TextButtonStyle();
		buttonStyle.up = skin.getDrawable("lvlButtonEnabled");
		buttonStyle.disabled = skin.getDrawable("lvlButtonDisabled");
		buttonStyle.font = new BitmapFont(Gdx.files.internal(U.folder +"font/font.fnt"));
		buttonStyle.font.setScale(0.8f *U.W);
		buttonStyle.fontColor = Color.BLACK;
		TextButtonStyle backStyle = new TextButtonStyle();
		backStyle.font = new BitmapFont();
		backStyle.up = skin.getDrawable("backButton");
		
		/* tables */
		currentTable = 0;
		buttonsPerTable = (byte)(Gdx.graphics.getHeight()/(100*U.W) -2);
		buttonsPerTable *= 6;
		buttonsPerTable = 24;
		tables = new ArrayList<Table>();
		for(i = 0; i < (U.MAX_LEVELS/buttonsPerTable+1); i++){
			tables.add(new Table());
			stage.addActor(tables.get(i));
			tables.get(i).setVisible(false);
			//tables.get(i).setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		}
		tables.get(currentTable).setVisible(true);
		
		/* nexts */
		TextButtonStyle prevBs = new TextButtonStyle();
		prevBs.up = skin.getDrawable("lvlButtonDisabled"); //TODO
		prevBs.font = new BitmapFont();
		
		TextButtonStyle nextBs = new TextButtonStyle();
		nextBs.up = skin.getDrawable("lvlButtonDisabled"); //TODO
		nextBs.font = new BitmapFont();
		
		/* listener */
		InputListener nextListener = new InputListener(){
			 public boolean touchDown(InputEvent event, float x, float y, int pointer, int button){
				tables.get(currentTable).setVisible(false);
				if(((TextButton)(event.getListenerActor())).getName().equals("next")){
					currentTable++;
				} else {
					currentTable--;
				}
				if(currentTable < 0){
					currentTable = 0;
				} else if(currentTable > U.MAX_LEVELS/buttonsPerTable){
					currentTable = (byte) (U.MAX_LEVELS/buttonsPerTable);
				}
				tables.get(currentTable).setVisible(true);
				
				return false;
			}
		};
		
		next = new TextButton("", nextBs);
		next.setPosition(Gdx.graphics.getWidth() - next.getWidth() - 20f *U.W, 20f *U.W);
		next.setName("next");
		next.addListener(nextListener);
		//stage.addActor(next); //TODO
		
		prev = new TextButton("", prevBs);
		prev.setPosition(20f *U.W, 20f *U.W);
		prev.setName("prev");
		prev.addListener(nextListener);
		//stage.addActor(prev); //TODO
		
		
		/* instances */
		backButton = new TextButton("",backStyle);
		for(i = 0; i < U.MAX_LEVELS; i++){
			buttons.add(new TextButton(Integer.toString(i + 1), buttonStyle));
			buttons.get(i).setName(Integer.toString(i + 1));
			if(i >= U.d.unlocked) buttons.get(i).setDisabled(true);
		}
		
		
		/* size */
		//backButton.setSize(backButton.getWidth() *U.W, backButton.getWidth() *U.W);
		float buttonWidth = 100 *U.W;
		float buttonHeight = 100 *U.W;
		for(i = 0; i < U.MAX_LEVELS; i++){
			buttons.get(i).setSize(buttonWidth, buttonHeight);
		}
		
		/* position */
		backButton.setPosition(Gdx.graphics.getWidth() -backButton.getWidth()-32f*U.W, Gdx.graphics.getHeight()-backButton.getHeight()-32 *U.W);
		float spaceX = 25f *U.W;
		float spaceY = 25f *U.H;
		byte row = 6;
		float offX = Gdx.graphics.getWidth()/2f - (row * (buttonWidth + spaceX) -spaceX)/2f;
		float offY = Gdx.graphics.getHeight()*4f/5f - 75*U.H;
		byte y = 1;
		byte x = -1;
		byte a = 0;
		for(i = 0; i < U.MAX_LEVELS; i++){
			if (a % buttonsPerTable == 0) {a = 0; x = -1; y = 1;}
			x++;
			if(a % row == 0){
				y--;
				x = 0;
			}
			buttons.get(i).setPosition(offX + x*(buttonHeight + spaceX), offY + y*(buttonHeight +spaceY)-2*spaceY);
			a++;
		}
		
		
		/* listeners */
		backButton.addListener(new InputListener(){
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
				Gdx.input.setInputProcessor(null);
				dispose();
				game.setScreen(new MainMenu(game, bg, music));
            	return true;
            }
		});
		InputListener listener = new InputListener(){
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
            	if(!((TextButton)event.getListenerActor()).isDisabled()){
            		Gdx.input.setInputProcessor(null);
            		dispose();
            		music.dispose();
            		game.setScreen(new GameScreen(game, ((TextButton)event.getListenerActor()).getName(), bg, stage));
            	}
            	return true;
            }
        };
        for(i = 0; i < U.MAX_LEVELS; i++){
			buttons.get(i).addListener(listener);
		}
		
		/* add to stage*/
        stage.addActor(backButton);
        for(i = 0; i < U.MAX_LEVELS; i++){
			tables.get(i/buttonsPerTable).addActor(buttons.get(i));
		}
		
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
		
		/* FPS */
		U.drawFps(batch);
		
		batch.end();
		
		/* stage */
		stage.draw();
	}

	
	
	
	@Override
	public void dispose() {
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
