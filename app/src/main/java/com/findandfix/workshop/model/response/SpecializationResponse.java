package com.findandfix.workshop.model.response;

import com.findandfix.workshop.model.global.BaseModel;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class SpecializationResponse{

	@SerializedName("data")
	private List<BaseModel> data;

	public void setData(List<BaseModel> data){
		this.data = data;
	}

	public List<BaseModel> getData(){
		return data;
	}

	@Override
 	public String toString(){
		return 
			"SpecializationResponse{" + 
			"data = '" + data + '\'' + 
			"}";
		}
}