package com.dyapp.anindaev.Models;

public class IlanlarimGuncellemePojo{
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
				"Sdasda{" +
						"sonuc = '" + sonuc + '\'' +
						"}";
	}
}
