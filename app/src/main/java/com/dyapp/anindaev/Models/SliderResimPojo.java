package com.dyapp.anindaev.Models;

public class SliderResimPojo{
	private String result;
	private String resim;
	private Boolean tf;

	public void setResult(String result){
		this.result = result;
	}

	public String getResult(){
		return result;
	}

	public void setResim(String resim){
		this.resim = resim;
	}

	public String getResim(){
		return resim;
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
			"SliderResimPojo{" + 
			"result = '" + result + '\'' + 
			",resim = '" + resim + '\'' + 
			",tf = '" + tf + '\'' + 
			"}";
		}
}
