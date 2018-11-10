package com.findandfix.workshop.model.response;

import com.findandfix.workshop.model.global.BrandItem;
import com.google.gson.annotations.SerializedName;

import java.util.List;


public class BrandsResponse{

	@SerializedName("data")
	private List<BrandItem> data;

	public void setData(List<BrandItem> data){
		this.data = data;
	}

	public List<BrandItem> getData(){
		return data;
	}

	@Override
 	public String toString(){
		return 
			"BrandsResponse{" + 
			"data = '" + data + '\'' + 
			"}";
		}
}