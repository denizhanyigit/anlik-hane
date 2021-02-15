package com.dyapp.anindaev.Models;

public class DefaultResimPojo{
	private Boolean tf;
	private Integer kulid;
	private Integer ilanid;

	public void setTf(Boolean tf){
		this.tf = tf;
	}

	public boolean isTf(){
		return tf;
	}

	public void setKulid(Integer kulid){
		this.kulid = kulid;
	}

	public Integer getKulid(){
		return kulid;
	}

	public void setIlanid(Integer ilanid){
		this.ilanid = ilanid;
	}

	public Integer getIlanid(){
		return ilanid;
	}

	@Override
 	public String toString(){
		return 
			"DefaultResimPojo{" + 
			"tf = '" + tf + '\'' + 
			",kulid = '" + kulid + '\'' + 
			",ilanid = '" + ilanid + '\'' + 
			"}";
		}
}
