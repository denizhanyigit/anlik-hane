package com.dyapp.anindaev.Models;

public class ProfilGuncellePojo{
	private String sonuc;

	public void setSonuc(String sonuc){
		this.sonuc = sonuc;
	}

	public String getSonuc(){
		return sonuc;
	}

	@Override
 	public String toString(){
		return 
			"ProfilGuncellePojo{" + 
			"sonuc = '" + sonuc + '\'' + 
			"}";
		}
}
