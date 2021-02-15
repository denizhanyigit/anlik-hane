package com.dyapp.anindaev.Models;

public class LoginPojo{
	private String ad;
	private String soyad;
	private String mail;
	private String sifre;
	private int id;

	public String getSoyad() {
		return soyad;
	}

	public void setSoyad(String soyad) {
		this.soyad = soyad;
	}

	public String getAd() {
		return ad;
	}

	public void setAd(String ad) {
		this.ad = ad;
	}

	public String getMail() {
		return mail;
	}

	public void setMail(String mail) {
		this.mail = mail;
	}

	public String getSifre() {
		return sifre;
	}

	public void setSifre(String sifre) {
		this.sifre = sifre;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	@Override
	public String toString() {
		return "LoginPojo{" +
				"ad='" + ad + '\'' +
				", soyad='" + soyad + '\'' +
				", mail='" + mail + '\'' +
				", sifre='" + sifre + '\'' +
				", id=" + id +
				'}';
	}
}
