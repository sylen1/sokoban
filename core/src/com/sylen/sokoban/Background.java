package com.sylen.sokoban;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Disposable;

public class Background implements Disposable{
	Texture img;
	float size;
	
	int nx;
	int ny;
	
	float x;
	float y;
	float dx;
	float dy;
	
	/* constructor */
	public Background(String name, float dx, float dy){
		img = new Texture(U.folder + "backgrounds/" + name  + ".png");
		
		setSize(48f);
		
		x = 0f;
		y = 0f;
		this.dx = dx;
		this.dy = dy;
	}
	
	public void update(){
		if (U.d.moveBg){
			x = (x+dx) % (size *U.W);
			y = (y+dy) % (size *U.W);
		}
	}
	
	public void draw(SpriteBatch batch){
		for(int j = -1; j < ny +1; j++){
			for(int i = -1; i < nx +1; i++){
				batch.draw(img, x +i*size *U.W, y +j*size *U.W, size *U.W, size *U.W);
			}
		}
		
	}
	
	public void draw(Batch batch){
		for(int j = -1; j < ny +1; j++){
			for(int i = -1; i < nx +1; i++){
				batch.draw(img, x +i*size *U.W, y +j*size *U.W, size *U.W, size *U.W);
			}
		}
		//batch.draw(U.atlas.createSprite("shadow"),0 ,0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
	}
	
	
	
	
	@Override
	public void dispose() {
		img.dispose();
	}
	
	public void setDx(float dx){
		this.dx = dx;
	}
	
	public void setDy(float dy){
		this.dy = dy;
	}
	
	public void setTexture(String name){
		img.dispose();
		img = new Texture(U.folder + "backgrounds/" + name + ".png");
	}
	
	public void setSize(float size){
		this.size = size;
		nx = (int) (Gdx.graphics.getWidth() / (size *U.W)) +1;
		ny = (int) (Gdx.graphics.getHeight() / (size *U.W)) +1;
	}
}
