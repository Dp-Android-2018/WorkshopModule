package com.example.dell.workshopmodule.network;


import android.arch.lifecycle.ViewModelStore;

import com.example.dell.workshopmodule.model.request.AddOfferRequest;
import com.example.dell.workshopmodule.model.request.EmailRequest;
import com.example.dell.workshopmodule.model.request.LoginRequest;
import com.example.dell.workshopmodule.model.request.MobileRequest;
import com.example.dell.workshopmodule.model.request.RegisterRequest;
import com.example.dell.workshopmodule.model.request.UpdateProfileRequest;
import com.example.dell.workshopmodule.model.response.BrandsResponse;
import com.example.dell.workshopmodule.model.response.CityResponse;
import com.example.dell.workshopmodule.model.response.CountryResponse;
import com.example.dell.workshopmodule.model.response.DefaultResponse;
import com.example.dell.workshopmodule.model.response.LoginResponse;
import com.example.dell.workshopmodule.model.response.ModelResponse;
import com.example.dell.workshopmodule.model.response.SpecializationResponse;
import com.example.dell.workshopmodule.model.response.UrgentRequestTypeResponse;
import com.example.dell.workshopmodule.model.response.WorkShopRequestResponse;
import com.example.dell.workshopmodule.model.response.WorkshopOfferResponse;
import com.example.dell.workshopmodule.utils.ConfigurationFile;


import io.reactivex.Observable;
import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface EndPoints {

    @POST(ConfigurationFile.UrlConstants.LOGIN_URL)
    Observable<Response<LoginResponse>> login(@Header("x-api-key") String key, @Header("Accept-Language") String lang, @Header("Content-Type") String contentType, @Header("Accept") String accept, @Body LoginRequest loginRequest);

    @GET(ConfigurationFile.UrlConstants.COUNTRIES_URL)
    Observable<Response<CountryResponse>> getCountries(@Header("x-api-key") String key, @Header("Accept-Language") String lang, @Header("Content-Type") String contentType, @Header("Accept") String accept);

    @GET(ConfigurationFile.UrlConstants.CITIES_URL)
    Observable<Response<CityResponse>> getCities(@Header("x-api-key") String key, @Header("Accept-Language") String lang, @Header("Content-Type") String contentType, @Header("Accept") String accept,@Path("country_id") int Cid);

    @GET(ConfigurationFile.UrlConstants.BRANDS_URL)
    Observable<Response<BrandsResponse>> getBrands(@Header("x-api-key") String key, @Header("Accept-Language") String lang, @Header("Content-Type") String contentType, @Header("Accept") String accept);

    @GET(ConfigurationFile.UrlConstants.MODELS_URL)
    Observable<Response<ModelResponse>> getModels(@Header("x-api-key") String key, @Header("Accept-Language") String lang, @Header("Content-Type") String contentType, @Header("Accept") String accept,@Path("brand_id") int Mid);

    @GET(ConfigurationFile.UrlConstants.SPECIALIZE_URL)
    Observable<Response<SpecializationResponse>> getSpecializations(@Header("x-api-key") String key, @Header("Accept-Language") String lang, @Header("Content-Type") String contentType, @Header("Accept") String accept);


    @GET(ConfigurationFile.UrlConstants.URGENT_REQUEST_TYPES_URL)
    Observable<Response<UrgentRequestTypeResponse>> getUrgentTypes(@Header("x-api-key") String key, @Header("Accept-Language") String lang, @Header("Content-Type") String contentType, @Header("Accept") String accept);

    @POST(ConfigurationFile.UrlConstants.CHECK_EMAIL_URL)
    Observable<Response<Integer>> checkEmail(@Header("x-api-key") String key, @Header("Accept-Language") String lang, @Header("Content-Type") String contentType, @Header("Accept") String accept, @Body EmailRequest emailRequest);

    @POST(ConfigurationFile.UrlConstants.CHECK_MOBILE_URL)
    Observable<Response<Integer>> checkMobile(@Header("x-api-key") String key, @Header("Accept-Language") String lang, @Header("Content-Type") String contentType, @Header("Accept") String accept, @Body MobileRequest mobileRequest);

    @POST(ConfigurationFile.UrlConstants.REGISTER_URL)
    Observable<Response<LoginResponse>> register(@Header("x-api-key") String key, @Header("Accept-Language") String lang, @Header("Content-Type") String contentType, @Header("Accept") String accept, @Body RegisterRequest registerRequest);

    @GET(ConfigurationFile.UrlConstants.WORKSHOP_NORMAL_REQUESTS_URL)
    Observable<Response<WorkShopRequestResponse>> getWorkshopNormalRequests(@Header("x-api-key") String key, @Header("Accept-Language") String lang, @Header("Content-Type") String contentType, @Header("Accept") String accept,@Header("Authorization") String auth );

    @GET(ConfigurationFile.UrlConstants.WORKSHOP_PENDING_REQUESTS_URL)
    Observable<Response<WorkShopRequestResponse>> getWorkshopPendingNormalRequests(@Header("x-api-key") String key, @Header("Accept-Language") String lang, @Header("Content-Type") String contentType, @Header("Accept") String accept,@Header("Authorization") String auth);


    @GET(ConfigurationFile.UrlConstants.WORKSHOP_IN_PROGRESS_REQUESTS_URL)
    Observable<Response<WorkShopRequestResponse>> getWorkshopInProgressRequests(@Header("x-api-key") String key, @Header("Accept-Language") String lang, @Header("Content-Type") String contentType, @Header("Accept") String accept,@Header("Authorization") String auth );

    @GET(ConfigurationFile.UrlConstants.WORKSHOP_COMPLETED_REQUESTS_URL)
    Observable<Response<WorkShopRequestResponse>> getWorkshopCompletedRequests(@Header("x-api-key") String key, @Header("Accept-Language") String lang, @Header("Content-Type") String contentType, @Header("Accept") String accept,@Header("Authorization") String auth);

    @GET(ConfigurationFile.UrlConstants.GET_WORKSHOP_OFFERS_URL)
    Observable<Response<WorkshopOfferResponse>> getWorkshopOffer(@Header("x-api-key") String key, @Header("Accept-Language") String lang, @Header("Content-Type") String contentType, @Header("Accept") String accept, @Header("Authorization") String auth, @Path("requestID") int rid);

    @POST(ConfigurationFile.UrlConstants.COMPLETE_WORKSHOP_REQUEST_URL)
    Observable<Response<DefaultResponse>> completeRequest(@Header("x-api-key") String key, @Header("Accept-Language") String lang, @Header("Content-Type") String contentType, @Header("Accept") String accept, @Header("Authorization") String auth, @Path("offerID") int rid);


    @POST(ConfigurationFile.UrlConstants.ADD_WORKSHOP_OFFER_URL)
    Observable<Response<DefaultResponse>> addWorkshopOffer(@Header("x-api-key") String key, @Header("Accept-Language") String lang, @Header("Content-Type") String contentType, @Header("Accept") String accept, @Header("Authorization") String auth, @Body AddOfferRequest addOfferRequest, @Path("requestID") int rid);

    @PUT(ConfigurationFile.UrlConstants.UPDATE_DELETE_WORKSHOP_OFFER_URL)
    Observable<Response<DefaultResponse>> updateWorkshopOffer(@Header("x-api-key") String key, @Header("Accept-Language") String lang, @Header("Content-Type") String contentType, @Header("Accept") String accept, @Header("Authorization") String auth, @Body AddOfferRequest addOfferRequest, @Path("offerID") int oid);

    @DELETE(ConfigurationFile.UrlConstants.UPDATE_DELETE_WORKSHOP_OFFER_URL)
    Observable<Response<DefaultResponse>> deleteWorkshopOffer(@Header("x-api-key") String key, @Header("Accept-Language") String lang, @Header("Content-Type") String contentType, @Header("Accept") String accept, @Header("Authorization") String auth, @Path("offerID") int oid);


    @PUT(ConfigurationFile.UrlConstants.UPDATE_WORKSHOP_PROFILE_URL)
    Observable<Response<DefaultResponse>> updateWorkshopProfile(@Header("x-api-key") String key, @Header("Accept-Language") String lang, @Header("Content-Type") String contentType, @Header("Accept") String accept, @Header("Authorization") String auth,@Body UpdateProfileRequest updateProfileRequest);


    @GET(ConfigurationFile.UrlConstants.SUBSCRIBED_URL)
    Observable<Response<DefaultResponse>> isWorkshopSubScribed(@Header("x-api-key") String key, @Header("Accept-Language") String lang, @Header("Content-Type") String contentType, @Header("Accept") String accept, @Header("Authorization") String autht);




}
