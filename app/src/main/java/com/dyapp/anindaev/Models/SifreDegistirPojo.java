package com.dyapp.anindaev.Models;

public class SifreDegistirPojo{
	private Integer tf;
	private String sonuc;

	public void setTf(Integer tf){
		this.tf = tf;
	}

	public Integer getTf(){
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
				"Responsess{" +
						"tf = '" + tf + '\'' +
						",sonuc = '" + sonuc + '\'' +
						"}";
	}
}
