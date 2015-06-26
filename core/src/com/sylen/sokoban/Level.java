package com.sylen.sokoban;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.utils.Disposable;
import com.sylen.sokoban.screens.GameScreen;

public class Level implements Disposable{
	
	private byte tiles[][];
	
	private float offX;
	private float offY;
	
	private byte portals;
	private byte px[];
	private byte py[];
	
	private Player player;
	
	private String lvl;
	
	private GameScreen gs;
	
	public Level(String lvl, GameScreen gs) {
		this.gs = gs;
		this.lvl = lvl;
		readFile();
		
		offX = Gdx.graphics.getWidth()/2f - U.MAP_WIDTH*U.TILE_SIZE*U.W/2f;
		offY = Gdx.graphics.getHeight()/2f - U.MAP_HEIGHT*U.TILE_SIZE*U.W/2f;
		
		
	}
	
	private void readFile() {
		FileHandle f = Gdx.files.internal("levels/" + lvl +".txt");
		String s = f.readString();
		portals = 0;
		px = new byte[U.MAX_PORTALS];
		py = new byte[U.MAX_PORTALS];
		
		tiles = new byte[U.MAP_HEIGHT][U.MAP_WIDTH];
		
		for(byte i = 0; i < U.MAP_HEIGHT; i++){
			for(byte j = 0; j < U.MAP_WIDTH; j++){
				tiles[i][j] = (byte) (s.charAt(j*U.MAP_WIDTH + i) - '0');
				if(tiles[i][j] == 4) {player = new Player(i, j); tiles[i][j] = 0;}	//found player
				else if(tiles[i][j] == 3) {px[portals]=i; py[portals] = j; portals++;}	//found portal
			}
		}
		/*
		 * 0 - space
		 * 1 - wall
		 * 2 - box
		 * 3 - portal
		 * 4 - player
		 * 
		 * */
	}
	
	public void move(byte key){
		/*
		 * 0 - right
		 * 1 - left
		 * 2 - up
		 * 3 - down
		 */
		
		/* UP */
		if(key == 2){
			if(tiles[player.x][player.y+1] == 0 || tiles[player.x][player.y+1] == 3){
				player.y++;
			} else if(tiles[player.x][player.y+1] == 2 && (tiles[player.x][player.y+2] == 0 || tiles[player.x][player.y+2] == 3)){
				tiles[player.x][player.y+2] = 2;
				tiles[player.x][player.y+1] = 0;
				for(byte i = 0; i < portals; i++){
					if(px[i]==player.x && py[i]==player.y+1){
						tiles[player.x][player.y+1] = 3;
						break;
					}
				}
				player.y++;
				check();
			}
			/* DOWN */
		} else if (key == 3){
			if(tiles[player.x][player.y-1] == 0 || tiles[player.x][player.y-1] == 3){
				player.y--;
			} else if(tiles[player.x][player.y-1] == 2 && (tiles[player.x][player.y-2] == 0 || tiles[player.x][player.y-2] == 3)){
				tiles[player.x][player.y-2] = 2;
				tiles[player.x][player.y-1] = 0;
				for(byte i = 0; i < portals; i++){
					if(px[i]==player.x && py[i]==player.y-1){
						tiles[player.x][player.y-1] = 3;
						break;
					}
				}
				player.y--;
				check();
			}
		}
		/* LEFT */
		else if (key == 1){
			if(tiles[player.x-1][player.y] == 0 || tiles[player.x-1][player.y] == 3){
				player.x--;
			} else if(tiles[player.x-1][player.y] == 2 && (tiles[player.x-2][player.y] == 0 || tiles[player.x-2][player.y] == 3)){
				tiles[player.x-2][player.y] = 2;
				tiles[player.x-1][player.y] = 0;
				for(byte i = 0; i < portals; i++){
					if(px[i]==player.x-1 && py[i]==player.y){
						tiles[player.x-1][player.y] = 3;
						break;
					}
				}
				player.x--;
				check();
			}
			/* RIGHT */
		} else if(key == 0){
			if(tiles[player.x+1][player.y] == 0 || tiles[player.x+1][player.y] == 3){
				player.x++;
			} else if(tiles[player.x+1][player.y] == 2 && (tiles[player.x+2][player.y] == 0 || tiles[player.x+2][player.y] == 3)){
				tiles[player.x+2][player.y] = 2;
				tiles[player.x+1][player.y] = 0;
				for(byte i = 0; i < portals; i++){
					if(px[i]==player.x+1 && py[i]==player.y){
						tiles[player.x+1][player.y] = 3;
						break;
					}
				}
				player.x++;
				check();
			}
		}
	}
	
	private void check(){
		byte check = 0;
		for(byte i = 0; i < portals; i++){
			if(tiles[px[i]][py[i]] == 2) check++;
		}
		
		if (check == portals) win();
	}
	
	private void win(){
		if(Integer.parseInt(lvl) == U.d.unlocked && U.d.unlocked <= U.MAX_LEVELS){
			U.d.unlocked++;
			Save.save(U.d, U.SAVE_FILE);
		}
		lvl = Byte.toString((byte) (Byte.parseByte(lvl)+1));
		if (Integer.parseInt(lvl) > U.MAX_LEVELS){
			gs.back();
		} else {
			gs.win();
		}
		
	}
	
	public void draw(Batch batch){
		
		for(int j = 0; j < U.MAP_HEIGHT; j++){
			for(int i = 0; i < U.MAP_WIDTH; i++){
				if(tiles[i][j] != 1){
					batch.draw(U.atlas.createSprite(Integer.toString(tiles[i][j])), offX +i*U.TILE_SIZE*U.W, offY+ j*U.TILE_SIZE*U.W, U.TILE_SIZE *U.W, U.TILE_SIZE *U.W);
				}
			}
		}
		batch.draw(U.atlas.createSprite("4"), offX +player.x*U.TILE_SIZE*U.W, offY+ player.y*U.TILE_SIZE*U.W, U.TILE_SIZE *U.W, U.TILE_SIZE *U.W);
	}
	
	public void reset(){
		readFile();
	}
	
	@Override
	public void dispose() {
		
	}
	
	
	
	
	
}
