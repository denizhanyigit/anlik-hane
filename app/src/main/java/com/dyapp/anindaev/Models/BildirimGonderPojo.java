package com.dyapp.anindaev.Models;

import java.util.List;

public class BildirimGonderPojo{
	private Integer canonicalIds;
	private Integer success;
	private Integer failure;
	private List<ResultsItem> results;
	private Long multicastId;

	public void setCanonicalIds(Integer canonicalIds){
		this.canonicalIds = canonicalIds;
	}

	public Integer getCanonicalIds(){
		return canonicalIds;
	}

	public void setSuccess(Integer success){
		this.success = success;
	}

	public Integer getSuccess(){
		return success;
	}

	public void setFailure(Integer failure){
		this.failure = failure;
	}

	public Integer getFailure(){
		return failure;
	}

	public void setResults(List<ResultsItem> results){
		this.results = results;
	}

	public List<ResultsItem> getResults(){
		return results;
	}

	public void setMulticastId(Long multicastId){
		this.multicastId = multicastId;
	}

	public Long getMulticastId(){
		return multicastId;
	}

	@Override
 	public String toString(){
		return 
			"BildirimGonderPojo{" + 
			"canonical_ids = '" + canonicalIds + '\'' + 
			",success = '" + success + '\'' + 
			",failure = '" + failure + '\'' + 
			",results = '" + results + '\'' + 
			",multicast_id = '" + multicastId + '\'' + 
			"}";
		}
}