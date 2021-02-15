package com.dyapp.anindaev.Models;

public class IlanVermePojo{
	private boolean tf;
	private int kulid;
	private int ilanid;

	public boolean isTf() {
		return tf;
	}

	public void setTf(boolean tf) {
		this.tf = tf;
	}

	public int getKulid() {
		return kulid;
	}

	public void setKulid(int kulid) {
		this.kulid = kulid;
	}

	public int getIlanid() {
		return ilanid;
	}

	public void setIlanid(int ilanid) {
		this.ilanid = ilanid;
	}

	@Override
	public String toString() {
		return "Response{" +
				"tf=" + tf +
				", kulid=" + kulid +
				", ilanid=" + ilanid +
				'}';
	}
}
