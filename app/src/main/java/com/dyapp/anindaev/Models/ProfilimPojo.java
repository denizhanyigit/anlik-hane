package com.dyapp.anindaev.Models;

public class ProfilimPojo{
	private String result;
	private Boolean tf;
	private String kulsoyad;
	private String email;
	private String telno;
	private String kulad;

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

	public void setKulsoyad(String kulsoyad){
		this.kulsoyad = kulsoyad;
	}

	public String getKulsoyad(){
		return kulsoyad;
	}

	public void setEmail(String email){
		this.email = email;
	}

	public String getEmail(){
		return email;
	}

	public void setTelno(String telno){
		this.telno = telno;
	}

	public String getTelno(){
		return telno;
	}

	public void setKulad(String kulad){
		this.kulad = kulad;
	}

	public String getKulad(){
		return kulad;
	}

	@Override
 	public String toString(){
		return 
			"ProfilimPojo{" + 
			"result = '" + result + '\'' + 
			",tf = '" + tf + '\'' + 
			",kulsoyad = '" + kulsoyad + '\'' + 
			",email = '" + email + '\'' + 
			",telno = '" + telno + '\'' + 
			",kulad = '" + kulad + '\'' + 
			"}";
		}
}
