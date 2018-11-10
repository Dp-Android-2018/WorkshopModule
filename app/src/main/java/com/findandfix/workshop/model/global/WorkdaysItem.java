package com.findandfix.workshop.model.global;


import com.google.gson.annotations.SerializedName;

import java.io.Serializable;


public class WorkdaysItem implements Serializable{

	@SerializedName("from")
	private String from;

	@SerializedName("to")
	private String to;

	@SerializedName("day")
	private String day;

	public void setFrom(String from){
		this.from = from;
	}

	public String getFrom(){
		return from;
	}

	public void setTo(String to){
		this.to = to;
	}

	public String getTo(){
		return to;
	}

	public void setDay(String day){
		this.day = day;
	}

	public String getDay(){
		return day;
	}

	@Override
 	public String toString(){
		return 
			"WorkdaysItem{" + 
			"from = '" + from + '\'' + 
			",to = '" + to + '\'' + 
			",day = '" + day + '\'' + 
			"}";
		}
}