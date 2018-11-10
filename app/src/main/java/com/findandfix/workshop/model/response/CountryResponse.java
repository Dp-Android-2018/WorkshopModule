package com.findandfix.workshop.model.response;

import com.findandfix.workshop.model.global.CountryItem;
import com.google.gson.annotations.SerializedName;

import java.util.List;


public class CountryResponse{

	@SerializedName("data")
	private List<CountryItem> data;

	public void setData(List<CountryItem> data){
		this.data = data;
	}

	public List<CountryItem> getData(){
		return data;
	}

	@Override
 	public String toString(){
		return 
			"CountryResponse{" + 
			"data = '" + data + '\'' + 
			"}";
		}
}