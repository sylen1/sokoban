package com.sylen.sokoban;

import java.io.Serializable;

public class Data implements Serializable{
	private static final long serialVersionUID = 1L;
	
	public byte unlocked;
	public boolean mute;
	public boolean moveBg;
	
	public Data(){
		unlocked = 1;
		mute = false;
		moveBg = true;
	}
}
