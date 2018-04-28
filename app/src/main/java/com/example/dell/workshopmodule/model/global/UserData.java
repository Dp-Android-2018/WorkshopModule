package com.example.dell.workshopmodule.model.global;

import java.util.List;

import com.google.gson.annotations.SerializedName;


public class UserData {

	@SerializedName("country")
	private CountryItem country;

	@SerializedName("website")
	private String website;

	@SerializedName("brands")
	private List<BrandItem> brands;

	@SerializedName("specializations")
	private List<BaseModel> specializations;

	@SerializedName("city")
	private BaseModel city;

	@SerializedName("latitude")
	private String latitude;

	@SerializedName("mobile")
	private String mobile;

	@SerializedName("token")
	private String token;

	@SerializedName("subscribed")
	private boolean subscribed;

	@SerializedName("workdays")
	private List<WorkdaysItem> workdays;

	@SerializedName("urgent_request_types")
	private List<BaseModel> urgentRequestTypes;

	@SerializedName("name")
	private String name;

	@SerializedName("id")
	private int id;

	@SerializedName("email")
	private String email;

	@SerializedName("longitude")
	private String longitude;

	public void setCountry(CountryItem country){
		this.country = country;
	}

	public CountryItem getCountry(){
		return country;
	}

	public void setWebsite(String website){
		this.website = website;
	}

	public String getWebsite(){
		return website;
	}

	public void setBrands(List<BrandItem> brands){
		this.brands = brands;
	}

	public List<BrandItem> getBrands(){
		return brands;
	}

	public void setSpecializations(List<BaseModel> specializations){
		this.specializations = specializations;
	}

	public List<BaseModel> getSpecializations(){
		return specializations;
	}

	public void setCity(BaseModel city){
		this.city = city;
	}

	public BaseModel getCity(){
		return city;
	}

	public void setLatitude(String latitude){
		this.latitude = latitude;
	}

	public String getLatitude(){
		return latitude;
	}

	public void setMobile(String mobile){
		this.mobile = mobile;
	}

	public String getMobile(){
		return mobile;
	}

	public void setToken(String token){
		this.token = token;
	}

	public String getToken(){
		return token;
	}

	public void setSubscribed(boolean subscribed){
		this.subscribed = subscribed;
	}

	public boolean isSubscribed(){
		return subscribed;
	}

	public void setWorkdays(List<WorkdaysItem> workdays){
		this.workdays = workdays;
	}

	public List<WorkdaysItem> getWorkdays(){
		return workdays;
	}

	public void setUrgentRequestTypes(List<BaseModel> urgentRequestTypes){
		this.urgentRequestTypes = urgentRequestTypes;
	}

	public List<BaseModel> getUrgentRequestTypes(){
		return urgentRequestTypes;
	}

	public void setName(String name){
		this.name = name;
	}

	public String getName(){
		return name;
	}

	public void setId(int id){
		this.id = id;
	}

	public int getId(){
		return id;
	}

	public void setEmail(String email){
		this.email = email;
	}

	public String getEmail(){
		return email;
	}

	public void setLongitude(String longitude){
		this.longitude = longitude;
	}

	public String getLongitude(){
		return longitude;
	}

	@Override
 	public String toString(){
		return 
			"Data{" + 
			"country = '" + country + '\'' + 
			",website = '" + website + '\'' + 
			",brands = '" + brands + '\'' + 
			",specializations = '" + specializations + '\'' + 
			",city = '" + city + '\'' + 
			",latitude = '" + latitude + '\'' + 
			",mobile = '" + mobile + '\'' + 
			",token = '" + token + '\'' + 
			",subscribed = '" + subscribed + '\'' + 
			",workdays = '" + workdays + '\'' + 
			",urgent_request_types = '" + urgentRequestTypes + '\'' + 
			",name = '" + name + '\'' + 
			",id = '" + id + '\'' + 
			",email = '" + email + '\'' + 
			",longitude = '" + longitude + '\'' + 
			"}";
		}
}