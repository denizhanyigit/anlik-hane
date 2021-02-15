package com.dyapp.anindaev.Models;

public class MailGonderPojo{
	private String result;
	private String mail;
	private String id;

	public void setResult(String result){
		this.result = result;
	}

	public String getResult(){
		return result;
	}

	public void setMail(String mail){
		this.mail = mail;
	}

	public String getMail(){
		return mail;
	}

	public void setId(String id){
		this.id = id;
	}

	public String getId(){
		return id;
	}

	@Override
 	public String toString(){
		return 
			"MailGonderPojo{" + 
			"result = '" + result + '\'' + 
			",mail = '" + mail + '\'' + 
			",id = '" + id + '\'' + 
			"}";
		}
}
