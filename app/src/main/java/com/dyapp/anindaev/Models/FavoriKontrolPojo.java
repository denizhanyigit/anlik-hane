package com.dyapp.anindaev.Models;

public class FavoriKontrolPojo{
	private String tf;
	private String text;

	public void setTf(String tf){
		this.tf = tf;
	}

	public String getTf(){
		return tf;
	}

	public void setText(String text){
		this.text = text;
	}

	public String getText(){
		return text;
	}

	@Override
 	public String toString(){
		return 
			"FavoriKontrolPojo{" + 
			"tf = '" + tf + '\'' + 
			",text = '" + text + '\'' + 
			"}";
		}
}
