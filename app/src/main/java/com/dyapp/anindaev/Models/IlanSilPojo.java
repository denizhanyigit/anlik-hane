package com.dyapp.anindaev.Models;

public class IlanSilPojo{
	private String result;
	private Boolean tf;

	public void setResult(String result){
		this.result = result;
	}

	public String getResult(){
		return result;
	}

	public void setTf(Boolean tf){
		this.tf = tf;
	}

	public boolean isTf(){
		return tf;
	}

	@Override
 	public String toString(){
		return 
			"IlanSilPojo{" + 
			"result = '" + result + '\'' + 
			",tf = '" + tf + '\'' + 
			"}";
		}
}
