package com.dyapp.anindaev.Models;

public class IlanGuncellePojo{
	private String ilanid;
	private String katSayisi;
	private String banyoSayisi;
	private String il;
	private String fiyat;
	private String netMetre;
	private String kategori;
	private String mahalle;
	private String odaSayisi;
	private String bulunduguKat;
	private String brutMetre;
	private String result;
	private Boolean tf;
	private String aciklama;
	private String ilce;
	private String isitma;
	private String baslik;

	public String getIlanid() {
		return ilanid;
	}

	public void setIlanid(String ilanid) {
		this.ilanid = ilanid;
	}

	public void setKatSayisi(String katSayisi){
		this.katSayisi = katSayisi;
	}

	public String getKatSayisi(){
		return katSayisi;
	}

	public void setBanyoSayisi(String banyoSayisi){
		this.banyoSayisi = banyoSayisi;
	}

	public String getBanyoSayisi(){
		return banyoSayisi;
	}

	public void setIl(String il){
		this.il = il;
	}

	public String getIl(){
		return il;
	}

	public void setFiyat(String fiyat){
		this.fiyat = fiyat;
	}

	public String getFiyat(){
		return fiyat;
	}

	public void setNetMetre(String netMetre){
		this.netMetre = netMetre;
	}

	public String getNetMetre(){
		return netMetre;
	}

	public void setKategori(String kategori){
		this.kategori = kategori;
	}

	public String getKategori(){
		return kategori;
	}

	public void setMahalle(String mahalle){
		this.mahalle = mahalle;
	}

	public String getMahalle(){
		return mahalle;
	}

	public void setOdaSayisi(String odaSayisi){
		this.odaSayisi = odaSayisi;
	}

	public String getOdaSayisi(){
		return odaSayisi;
	}

	public void setBulunduguKat(String bulunduguKat){
		this.bulunduguKat = bulunduguKat;
	}

	public String getBulunduguKat(){
		return bulunduguKat;
	}

	public void setBrutMetre(String brutMetre){
		this.brutMetre = brutMetre;
	}

	public String getBrutMetre(){
		return brutMetre;
	}

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

	public void setAciklama(String aciklama){
		this.aciklama = aciklama;
	}

	public String getAciklama(){
		return aciklama;
	}

	public void setIlce(String ilce){
		this.ilce = ilce;
	}

	public String getIlce(){
		return ilce;
	}

	public void setIsitma(String isitma){
		this.isitma = isitma;
	}

	public String getIsitma(){
		return isitma;
	}

	public void setBaslik(String baslik){
		this.baslik = baslik;
	}

	public String getBaslik(){
		return baslik;
	}

	@Override
	public String toString(){
		return
				"SResponse{" +
						"katSayisi = '" + katSayisi + '\'' +
						",banyoSayisi = '" + banyoSayisi + '\'' +
						",il = '" + il + '\'' +
						",fiyat = '" + fiyat + '\'' +
						",netMetre = '" + netMetre + '\'' +
						",kategori = '" + kategori + '\'' +
						",mahalle = '" + mahalle + '\'' +
						",odaSayisi = '" + odaSayisi + '\'' +
						",bulunduguKat = '" + bulunduguKat + '\'' +
						",brutMetre = '" + brutMetre + '\'' +
						",result = '" + result + '\'' +
						",tf = '" + tf + '\'' +
						",aciklama = '" + aciklama + '\'' +
						",ilce = '" + ilce + '\'' +
						",isitma = '" + isitma + '\'' +
						",baslik = '" + baslik + '\'' +
						"}";
	}
}
