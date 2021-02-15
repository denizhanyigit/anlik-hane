package com.dyapp.anindaev.Models;

public class RegisterPojo {
	private String result;
	private boolean tf;
	private int dogrulamakodu;

	public void setResult(String result){
		this.result = result;
	}

	public String getResult(){
		return result;
	}

	public void setTf(boolean tf){
		this.tf = tf;
	}

	public boolean isTf(){
		return tf;
	}

	public void setDogrulamakodu(int dogrulamakodu){
		this.dogrulamakodu = dogrulamakodu;
	}

	public int getDogrulamakodu(){
		return dogrulamakodu;
	}

	@Override
 	public String toString(){
		return 
			"RegisterPojo{" + 
			"result = '" + result + '\'' + 
			",tf = '" + tf + '\'' + 
			",dogrulamakodu = '" + dogrulamakodu + '\'' + 
			"}";
		}
}
