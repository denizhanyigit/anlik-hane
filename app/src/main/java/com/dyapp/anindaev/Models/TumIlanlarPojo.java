package com.dyapp.anindaev.Models;

public class TumIlanlarPojo{
	private String result;
	private String resim;
	private Boolean tf;
	private String aciklama;
	private String il;
	private String ilce;
	private String tarih;
	private String ilanid;
	private String kulid;
	private String fiyat;
	private Integer sayi;
	private String baslik;

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

	public void setAciklama(String aciklama){
		this.aciklama = aciklama;
	}

	public String getAciklama(){
		return aciklama;
	}

	public void setIl(String il){
		this.il = il;
	}

	public String getIl(){
		return il;
	}

	public void setIlce(String ilce){
		this.ilce = ilce;
	}

	public String getIlce(){
		return ilce;
	}

	public void setTarih(String tarih){
		this.tarih = tarih;
	}

	public String getTarih(){
		return tarih;
	}

	public void setIlanid(String ilanid){
		this.ilanid = ilanid;
	}

	public String getIlanid(){
		return ilanid;
	}

	public void setKulid(String kulid){
		this.kulid = kulid;
	}

	public String getKulid(){
		return kulid;
	}

	public void setFiyat(String fiyat){
		this.fiyat = fiyat;
	}

	public String getFiyat(){
		return fiyat;
	}

	public void setSayi(Integer sayi){
		this.sayi = sayi;
	}

	public Integer getSayi(){
		return sayi;
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
			"TumIlanlarPojo{" + 
			"result = '" + result + '\'' + 
			",resim = '" + resim + '\'' + 
			",tf = '" + tf + '\'' + 
			",aciklama = '" + aciklama + '\'' + 
			",il = '" + il + '\'' + 
			",ilce = '" + ilce + '\'' + 
			",tarih = '" + tarih + '\'' + 
			",ilanid = '" + ilanid + '\'' + 
			",kulid = '" + kulid + '\'' + 
			",fiyat = '" + fiyat + '\'' + 
			",sayi = '" + sayi + '\'' + 
			",baslik = '" + baslik + '\'' + 
			"}";
		}
}
