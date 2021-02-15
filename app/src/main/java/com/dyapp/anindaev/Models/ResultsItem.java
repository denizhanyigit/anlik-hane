package com.dyapp.anindaev.Models;

public class ResultsItem{
	private String messageId;

	public void setMessageId(String messageId){
		this.messageId = messageId;
	}

	public String getMessageId(){
		return messageId;
	}

	@Override
 	public String toString(){
		return 
			"ResultsItem{" + 
			"message_id = '" + messageId + '\'' + 
			"}";
		}
}
