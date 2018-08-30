package com.dp.dell.workshopmodule.model.response;

import java.util.List;

import com.dp.dell.workshopmodule.model.global.BaseModel;
import com.google.gson.annotations.SerializedName;


public class ModelResponse{

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
			"ModelResponse{" + 
			"data = '" + data + '\'' + 
			"}";
		}
}