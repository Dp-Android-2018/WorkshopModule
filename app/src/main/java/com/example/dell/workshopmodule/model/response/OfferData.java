package com.example.dell.workshopmodule.model.response;


import com.google.gson.annotations.SerializedName;


public class OfferData {

	@SerializedName("cost_to")
	private int costTo;

	@SerializedName("duration_to")
	private int durationTo;

	@SerializedName("description")
	private String description;

	@SerializedName("id")
	private int id;

	@SerializedName("duration_from")
	private int durationFrom;

	@SerializedName("cost_from")
	private int costFrom;

	public void setCostTo(int costTo){
		this.costTo = costTo;
	}

	public int getCostTo(){
		return costTo;
	}

	public void setDurationTo(int durationTo){
		this.durationTo = durationTo;
	}

	public int getDurationTo(){
		return durationTo;
	}

	public void setDescription(String description){
		this.description = description;
	}

	public String getDescription(){
		return description;
	}

	public void setId(int id){
		this.id = id;
	}

	public int getId(){
		return id;
	}

	public void setDurationFrom(int durationFrom){
		this.durationFrom = durationFrom;
	}

	public int getDurationFrom(){
		return durationFrom;
	}

	public void setCostFrom(int costFrom){
		this.costFrom = costFrom;
	}

	public int getCostFrom(){
		return costFrom;
	}

}