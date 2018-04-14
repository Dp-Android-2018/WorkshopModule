package com.example.dell.workshopmodule.model.response;


import com.google.gson.annotations.SerializedName;


public class WorkshopOfferResponse{

	@SerializedName("data")
	private OfferData data;

	public void setData(OfferData data){
		this.data = data;
	}

	public OfferData getData(){
		return data;
	}


}