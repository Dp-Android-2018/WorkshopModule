package com.dp.dell.workshopmodule.network;


import com.dp.dell.workshopmodule.model.global.MsgRequest;
import com.dp.dell.workshopmodule.model.request.AddAchievmentRequest;
import com.dp.dell.workshopmodule.model.request.AddAdvertisementRequest;
import com.dp.dell.workshopmodule.model.request.AddOfferRequest;
import com.dp.dell.workshopmodule.model.request.ChangePasswordRequest;
import com.dp.dell.workshopmodule.model.request.CheckCode;
import com.dp.dell.workshopmodule.model.request.CompleteRequestNotification;
import com.dp.dell.workshopmodule.model.request.EmailRequest;
import com.dp.dell.workshopmodule.model.request.ForgetPasswordRequest;
import com.dp.dell.workshopmodule.model.request.GetMobileCode;
import com.dp.dell.workshopmodule.model.request.LoginRequest;
import com.dp.dell.workshopmodule.model.request.MobileRequest;
import com.dp.dell.workshopmodule.model.request.RegisterRequest;
import com.dp.dell.workshopmodule.model.request.UpdateProfileRequest;
import com.dp.dell.workshopmodule.model.response.AchievmentResponse;
import com.dp.dell.workshopmodule.model.response.BrandsResponse;
import com.dp.dell.workshopmodule.model.response.CityResponse;
import com.dp.dell.workshopmodule.model.response.CountryResponse;
import com.dp.dell.workshopmodule.model.response.DefaultResponse;
import com.dp.dell.workshopmodule.model.response.LoginResponse;
import com.dp.dell.workshopmodule.model.response.ModelResponse;
import com.dp.dell.workshopmodule.model.response.OffersResponse;
import com.dp.dell.workshopmodule.model.response.SpecializationResponse;
import com.dp.dell.workshopmodule.model.response.UrgentRequestTypeResponse;
import com.dp.dell.workshopmodule.model.response.WinshResponse;
import com.dp.dell.workshopmodule.model.response.WorkShopCountResponse;
import com.dp.dell.workshopmodule.model.response.WorkShopRequestResponse;
import com.dp.dell.workshopmodule.model.response.WorkShopUrgentRequestResponse;
import com.dp.dell.workshopmodule.model.response.WorkshopOfferResponse;
import com.dp.dell.workshopmodule.utils.ConfigurationFile;


import io.reactivex.Observable;
import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

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


    @GET(ConfigurationFile.UrlConstants.SUB_SPECIALIZE_URL)
    Observable<Response<SpecializationResponse>> getSubSpecializations(@Header("x-api-key") String key, @Header("Accept-Language") String lang, @Header("Content-Type") String contentType, @Header("Accept") String accept,@Path("parent_id") int Mid);


    @GET(ConfigurationFile.UrlConstants.URGENT_REQUEST_TYPES_URL)
    Observable<Response<UrgentRequestTypeResponse>> getUrgentTypes(@Header("x-api-key") String key, @Header("Accept-Language") String lang, @Header("Content-Type") String contentType, @Header("Accept") String accept);

    @POST(ConfigurationFile.UrlConstants.CHECK_EMAIL_URL)
    Observable<Response<Integer>> checkEmail(@Header("x-api-key") String key, @Header("Accept-Language") String lang, @Header("Content-Type") String contentType, @Header("Accept") String accept, @Body EmailRequest emailRequest);

    @POST(ConfigurationFile.UrlConstants.CHECK_MOBILE_URL)
    Observable<Response<Integer>> checkMobile(@Header("x-api-key") String key, @Header("Accept-Language") String lang, @Header("Content-Type") String contentType, @Header("Accept") String accept, @Body MobileRequest mobileRequest);

    @POST(ConfigurationFile.UrlConstants.REGISTER_URL)
    Observable<Response<LoginResponse>> register(@Header("x-api-key") String key, @Header("Accept-Language") String lang, @Header("Content-Type") String contentType, @Header("Accept") String accept, @Body RegisterRequest registerRequest);

    @GET(ConfigurationFile.UrlConstants.WORKSHOP_NORMAL_REQUESTS_URL)
    Observable<Response<WorkShopRequestResponse>> getWorkshopNormalRequests(@Header("x-api-key") String key, @Header("Accept-Language") String lang, @Header("Content-Type") String contentType, @Header("Accept") String accept,@Header("Authorization") String auth , @Query("page") int pageId);

    @GET(ConfigurationFile.UrlConstants.WORKSHOP_PENDING_REQUESTS_URL)
    Observable<Response<WorkShopRequestResponse>> getWorkshopPendingNormalRequests(@Header("x-api-key") String key, @Header("Accept-Language") String lang, @Header("Content-Type") String contentType, @Header("Accept") String accept,@Header("Authorization") String auth);


    @GET(ConfigurationFile.UrlConstants.WORKSHOP_IN_PROGRESS_REQUESTS_URL)
    Observable<Response<WorkShopRequestResponse>> getWorkshopInProgressRequests(@Header("x-api-key") String key, @Header("Accept-Language") String lang, @Header("Content-Type") String contentType, @Header("Accept") String accept,@Header("Authorization") String auth,@Query("page") int pageId );

    @GET(ConfigurationFile.UrlConstants.WORKSHOP_COMPLETED_REQUESTS_URL)
    Observable<Response<WorkShopRequestResponse>> getWorkshopCompletedRequests(@Header("x-api-key") String key, @Header("Accept-Language") String lang, @Header("Content-Type") String contentType, @Header("Accept") String accept,@Header("Authorization") String auth,@Query("page") int pageId);

    @GET(ConfigurationFile.UrlConstants.GET_WORKSHOP_OFFERS_URL)
    Observable<Response<WorkshopOfferResponse>> getWorkshopOffer(@Header("x-api-key") String key, @Header("Accept-Language") String lang, @Header("Content-Type") String contentType, @Header("Accept") String accept, @Header("Authorization") String auth, @Path("requestID") int rid);

    @POST(ConfigurationFile.UrlConstants.COMPLETE_WORKSHOP_REQUEST_URL)
    Observable<Response<DefaultResponse>> completeRequest(@Header("x-api-key") String key, @Header("Accept-Language") String lang, @Header("Content-Type") String contentType, @Header("Accept") String accept, @Header("Authorization") String auth, @Path("normalRequestID") int rid, @Body CompleteRequestNotification completeRequestNotification);


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


    @PUT(ConfigurationFile.UrlConstants.CHANGE_PASSWORD_URL)
    Observable<Response<DefaultResponse>> changePasswordApi(@Header("x-api-key") String key, @Header("Accept-Language") String lang, @Header("Content-Type") String contentType, @Header("Accept") String accept, @Header("Authorization") String auth,@Body ChangePasswordRequest changePasswordRequest);


    @POST(ConfigurationFile.UrlConstants.ADD_WORKSHOP_ADV)
    Observable<Response<DefaultResponse>> addWorkshopAdv(@Header("x-api-key") String key, @Header("Accept-Language") String lang, @Header("Content-Type") String contentType, @Header("Accept") String accept, @Header("Authorization") String auth, @Body AddAdvertisementRequest addAdvertisementRequest);


    @POST(ConfigurationFile.UrlConstants.ADD_WORKSHOP_ACHIEVMENT_URL)
    Observable<Response<DefaultResponse>> addAchievment(@Header("x-api-key") String key, @Header("Accept-Language") String lang, @Header("Content-Type") String contentType, @Header("Accept") String accept, @Header("Authorization") String auth, @Body AddAchievmentRequest addAchievmentRequest);


    @GET(ConfigurationFile.UrlConstants.GET_ACHIEVMENTS_URL)
    Observable<Response<AchievmentResponse>> getAchievments(@Header("x-api-key") String key, @Header("Accept-Language") String lang, @Header("Content-Type") String contentType, @Header("Accept") String accept, @Header("Authorization") String autht,@Query("page") int pageId);


    @GET(ConfigurationFile.UrlConstants.WORKSHOP_URGENT_REQUESTS_URL)
    Observable<Response<WorkShopUrgentRequestResponse>> getWorkshopUrgentRequests(@Header("x-api-key") String key, @Header("Accept-Language") String lang, @Header("Content-Type") String contentType, @Header("Accept") String accept, @Header("Authorization") String auth ,@Query("page") int pageId);

    @GET(ConfigurationFile.UrlConstants.WORKSHOP_PENDING_URGENT_REQUESTS_URL)
    Observable<Response<WorkShopUrgentRequestResponse>> getWorkshopPendingRequests(@Header("x-api-key") String key, @Header("Accept-Language") String lang, @Header("Content-Type") String contentType, @Header("Accept") String accept, @Header("Authorization") String auth,@Query("page") int pageId );

    @GET(ConfigurationFile.UrlConstants.WORKSHOP_IN_PROGRESS_URGENT_REQUESTS_URL)
    Observable<Response<WorkShopUrgentRequestResponse>> getWorkshopInProgressUrgentRequests(@Header("x-api-key") String key, @Header("Accept-Language") String lang, @Header("Content-Type") String contentType, @Header("Accept") String accept, @Header("Authorization") String auth,@Query("page") int pageId );

    @GET(ConfigurationFile.UrlConstants.WORKSHOP_COMPLETED_URGENT_REQUESTS_URL)
    Observable<Response<WorkShopUrgentRequestResponse>> getWorkshopCompletedUrgentRequests(@Header("x-api-key") String key, @Header("Accept-Language") String lang, @Header("Content-Type") String contentType, @Header("Accept") String accept, @Header("Authorization") String auth ,@Query("page") int pageId);


    @GET(ConfigurationFile.UrlConstants.WORKSHOP_ACCEPT_URGENT_REQUEST_URL)
    Observable<Response<DefaultResponse>> acceptUrgentRequest(@Header("x-api-key") String key, @Header("Accept-Language") String lang, @Header("Content-Type") String contentType, @Header("Accept") String accept, @Header("Authorization") String auth,@Path("requestID") int rid );

    @GET(ConfigurationFile.UrlConstants.COMPLETE_URGENT_WORKSHOP_REQUEST_URL)
    Observable<Response<DefaultResponse>> completedUrgentRequest(@Header("x-api-key") String key, @Header("Accept-Language") String lang, @Header("Content-Type") String contentType, @Header("Accept") String accept, @Header("Authorization") String auth, @Path("urgentRequestID") int rid);


    @GET(ConfigurationFile.UrlConstants.WORKSHOP_DEACTIVATE_ACCOUNT_URL)
    Observable<Response<DefaultResponse>> deactiveAccount(@Header("x-api-key") String key, @Header("Accept-Language") String lang, @Header("Content-Type") String contentType, @Header("Accept") String accept, @Header("Authorization") String auth);



    @GET(ConfigurationFile.UrlConstants.WORKSHOP_ACTIVATE_ACCOUNT_URL)
    Observable<Response<DefaultResponse>> activeAccount(@Header("x-api-key") String key, @Header("Accept-Language") String lang, @Header("Content-Type") String contentType, @Header("Accept") String accept, @Header("Authorization") String auth);


    @GET(ConfigurationFile.UrlConstants.WORKSHOP_DATA_COUNT_URL)
    Observable<Response<WorkShopCountResponse>> getCounts(@Header("x-api-key") String key, @Header("Accept-Language") String lang, @Header("Content-Type") String contentType, @Header("Accept") String accept, @Header("Authorization") String auth);


    @GET(ConfigurationFile.UrlConstants.WORKSHOP_LOGOUT_URL)
    Observable<Response<DefaultResponse>> logout(@Header("x-api-key") String key, @Header("Accept-Language") String lang, @Header("Content-Type") String contentType, @Header("Accept") String accept, @Header("Authorization") String auth);


    @POST(ConfigurationFile.UrlConstants.GET_REST_TOKEN_URL)
    Observable<Response<DefaultResponse>> getRestToken(@Header("x-api-key") String key, @Header("Accept-Language") String lang, @Header("Content-Type") String contentType, @Header("Accept") String accept, @Body GetMobileCode getMobileCode);


    @POST(ConfigurationFile.UrlConstants.HAS_REST_TOKEN_URL)
    Observable<Response<Integer>> hasRestToken(@Header("x-api-key") String key, @Header("Accept-Language") String lang, @Header("Content-Type") String contentType, @Header("Accept") String accept, @Body CheckCode checkCode);


    @POST(ConfigurationFile.UrlConstants.RESET_PASSWORD_URL)
    Observable<Response<DefaultResponse>> resetPassword(@Header("x-api-key") String key, @Header("Accept-Language") String lang, @Header("Content-Type") String contentType, @Header("Accept") String accept, @Body ForgetPasswordRequest forgetPasswordRequest);



    @POST(ConfigurationFile.UrlConstants.SEND_NOTIFICATION_URL)
    Observable<Response<DefaultResponse>> sendNotification(@Header("x-api-key") String key, @Header("Accept-Language") String lang, @Header("Content-Type") String contentType, @Header("Accept") String accept, @Body MsgRequest messageNotification);


    @GET(ConfigurationFile.UrlConstants.WENSH_TYPES_URL)
    Observable<Response<WinshResponse>> getWinchTypes(@Header("x-api-key") String key, @Header("Accept-Language") String lang, @Header("Content-Type") String contentType, @Header("Accept") String accept);

    @GET(ConfigurationFile.UrlConstants.GET_WORKSHOP_ADV_URL)
    Observable<Response<OffersResponse>> getWorkShopAdv(@Header("x-api-key") String key, @Header("Accept-Language") String lang, @Header("Content-Type") String contentType, @Header("Accept") String accept, @Header("Authorization") String auth,@Query("page") int pageId);


}
