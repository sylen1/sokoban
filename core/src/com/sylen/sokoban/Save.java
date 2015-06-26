package com.sylen.sokoban;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;

public class Save {
	
	public static void save(Object obj, String file) {
		ByteArrayOutputStream b = new ByteArrayOutputStream();
        ObjectOutputStream o = null;
        try{
        	o = new ObjectOutputStream(b);
        	o.writeObject(obj);
        } catch (Exception e){
        	e.printStackTrace();
        }
        
        b.toByteArray();
        
        FileHandle f = Gdx.files.local(file);
		f.writeBytes(b.toByteArray(), false);
        
        
	}
	
	public static Object load(String file) {
		FileHandle f = Gdx.files.local(file);
		if (!f.exists()) return null;
		ByteArrayInputStream bis = new ByteArrayInputStream(f.readBytes());	
		Object o = null;
		try{
			ObjectInputStream ois = new ObjectInputStream(bis);
        	o = ois.readObject();
		} catch (Exception e){
			e.printStackTrace();
		}
		return o;
	}
	
	
}
