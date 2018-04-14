package com.example.dell.workshopmodule.model.response;

import java.util.List;

import com.example.dell.workshopmodule.model.global.CountryItem;
import com.google.gson.annotations.SerializedName;


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