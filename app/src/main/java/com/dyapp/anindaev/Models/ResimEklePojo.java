package com.dyapp.anindaev.Models;

public class ResimEklePojo{
	private Boolean tf;
	private String sonuc;

	public void setTf(Boolean tf){
		this.tf = tf;
	}

	public boolean isTf(){
		return tf;
	}

	public void setSonuc(String sonuc){
		this.sonuc = sonuc;
	}

	public String getSonuc(){
		return sonuc;
	}

	@Override
 	public String toString(){
		return 
			"ResimEklePojo{" + 
			"tf = '" + tf + '\'' + 
			",sonuc = '" + sonuc + '\'' + 
			"}";
		}
}
