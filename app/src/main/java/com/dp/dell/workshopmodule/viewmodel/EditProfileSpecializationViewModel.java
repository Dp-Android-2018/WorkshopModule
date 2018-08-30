package com.dp.dell.workshopmodule.viewmodel;

import android.app.Activity;
import android.content.Context;
import android.databinding.BaseObservable;
import android.databinding.ObservableField;
import android.databinding.ObservableInt;
import android.support.v4.app.FragmentManager;
import android.view.View;

import com.abdeveloper.library.MultiSelectDialog;
import com.abdeveloper.library.MultiSelectModel;
import com.dp.dell.workshopmodule.R;
import com.dp.dell.workshopmodule.model.global.BaseModel;
import com.dp.dell.workshopmodule.model.global.UserData;
import com.dp.dell.workshopmodule.model.global.WenshTypes;
import com.dp.dell.workshopmodule.model.request.UpdateProfileRequest;
import com.dp.dell.workshopmodule.model.response.SpecializationResponse;
import com.dp.dell.workshopmodule.model.response.WinshResponse;
import com.dp.dell.workshopmodule.network.ApiClient;
import com.dp.dell.workshopmodule.network.EndPoints;
import com.dp.dell.workshopmodule.utils.ConfigurationFile;
import com.dp.dell.workshopmodule.utils.CustomUtils;
import com.dp.dell.workshopmodule.utils.NetWorkConnection;
import com.dp.dell.workshopmodule.view.ui.Application.MyApplication;
import com.dp.dell.workshopmodule.view.ui.callback.BaseInterface;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Response;

/**
 * Created by DELL on 03/04/2018.
 */

public class EditProfileSpecializationViewModel extends BaseObservable {

    private UserData userData;
    public ObservableField<String>brands;
    public ObservableField<String>specialization;
    public ObservableField<String>urgentRequest;
    private BaseInterface callback;
    private UpdateProfileRequest updateProfileRequest;
    public ObservableInt visibility;
    public ObservableField<String>subSpecializationfields;
    private Context context;
    private FragmentManager fragmentManager;
    ArrayList<MultiSelectModel> subSpecializationSelector= new ArrayList<>();
    private List<BaseModel>subIDS=new ArrayList<>();
    public ObservableField<String>fields;
    public ObservableField<String>Wenshfields;
    ArrayList<MultiSelectModel> WinchtypesSelector= new ArrayList<>();
    ArrayList<Integer> selectedIds;
    private List<WenshTypes>wt;
    MultiSelectDialog subDialog=null;
    ArrayList<Integer> subSpecializeselectedIds;
    public EditProfileSpecializationViewModel(Context context, BaseInterface callback, FragmentManager fragmentManager) {
        userData = CustomUtils.getInstance().getSaveUserObject(context);
        this.callback = callback;
        this.fragmentManager=fragmentManager;
        this.context=context;
        visibility = new ObservableInt(View.VISIBLE);
        initializeData();
        if (userData.getWinch() == 0) {
            setWorkShopData();
        } else {
            visibility.set(View.GONE);

            // specialization.set(getDataAsText(userData.getWenshTypes()));
            String types="";
            for (int i = 0; i < userData.getWenshTypes().size(); i++) {
                    types=types+" "+userData.getWenshTypes().get(i).getwName();
            }
            specialization.set(types);
        }
    }

    public void initializeData(){
        brands=new ObservableField<>();
        specialization=new ObservableField<>();
        urgentRequest=new ObservableField<>();
        subSpecializationfields=new ObservableField<>();
        subSpecializeselectedIds=new ArrayList<>();
        wt=new ArrayList<>();
        Wenshfields=new ObservableField<>("");
        selectedIds=new ArrayList<>();
        fields=new ObservableField<>();
    }

    public void setWorkShopData(){
        brands.set(getDataAsText(userData.getBrands()));
        specialization.set(getDataAsText(userData.getSpecializations()));
        urgentRequest.set(getDataAsText(userData.getUrgentRequestTypes()));

        ((MyApplication) (MyApplication.getAppContext())).setBasicBrands(userData.getBrands());
        ((MyApplication) (MyApplication.getAppContext())).setBasicspecializations(userData.getSpecializations());
        ((MyApplication) (MyApplication.getAppContext())).setBasicUrgentTypes(userData.getUrgentRequestTypes());
    }



    public <E>String getDataAsText(List<E>data){
        String textValue="";
        for (E info :data){

            textValue=textValue+((BaseModel)info).getName()+"-";
        }
        textValue=textValue.substring(0,textValue.length()-1);
       return textValue;
    }

    public void showDialog(View view){
            if(view.getId()== R.id.iv_brands || view.getId()==R.id.rl_brands){
                    callback.updateUi(ConfigurationFile.Constants.SHOW_BRANDS_DIALOG_CODE);
            }else if (view.getId()==R.id.iv_specialization  || view.getId()==R.id.rl_specialize){
                if (userData.getWinch()==0)
                    getSubSpecialization();
                else getWenshTypes();

            }else if (view.getId()==R.id.iv_urgents || view.getId()==R.id.rl_urgents){
                callback.updateUi(ConfigurationFile.Constants.SHOW_URGENTS_DIALOG_CODE);
            }
    }



    public void getSubSpecialization(){

        if (subDialog==null || !subDialog.isVisible()) {
            if (subIDS.size() == 0) {
                if (NetWorkConnection.isConnectingToInternet(context)) {
                    System.out.println("Sub Data Selected Specialize :" + CustomUtils.getInstance().getSaveUserObject(context).getParentId());
                    ApiClient.getClient().create(EndPoints.class).getSubSpecializations(ConfigurationFile.Constants.API_KEY, CustomUtils.getInstance().getAppLanguage(context), ConfigurationFile.Constants.Content_Type, ConfigurationFile.Constants.Content_Type, CustomUtils.getInstance().getSaveUserObject(context).getParentId())
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(specializationResponseResponse -> {

                                if (specializationResponseResponse.code() == ConfigurationFile.Constants.SUCCESS_CODE) {
                                    System.out.println("Sub Data Size :" + specializationResponseResponse.body().getData().size());
                                    initializeSubList(specializationResponseResponse.body().getData());
                                    subIDS.addAll(specializationResponseResponse.body().getData());
                                }else if (specializationResponseResponse.code() == ConfigurationFile.Constants.UNAUTHENTICATED_CODE ||
                                        specializationResponseResponse.code() == ConfigurationFile.Constants.UNSUBSCRIBE_CODE){
                                    CustomUtils.getInstance().endSession((Activity)context);
                                }
                            }, throwable -> System.out.println("Sub Specialize :" + throwable.getMessage()));
                } else {
                    callback.updateUi(ConfigurationFile.Constants.NO_INTERNET_CONNECTION_CODE);
                }
            } else initializeSubList(subIDS);
        }
    }

    public void initializeSubList(List<BaseModel>data){
        if (subSpecializationSelector.size()==0) {
            for (int i = 0; i < data.size(); i++) {
                subSpecializationSelector.add(new MultiSelectModel(data.get(i).getId(), data.get(i).getName()));
            }
        }
        showSubSpecializeDialog();
    }

    public void showSubSpecializeDialog(){
        if (subDialog==null || !subDialog.isVisible()) {
            subDialog = new MultiSelectDialog()
                    .title(context.getString(R.string.sub_specialization)) //setting title for dialog
                    .titleSize(25)

                    .positiveText(context.getResources().getString(R.string.ok))
                    .negativeText(context.getResources().getString(R.string.cancel))
                    .setMinSelectionLimit(1) //you can set minimum checkbox selection limit (Optional)
                    .setMaxSelectionLimit(subSpecializationSelector.size()) //you can set maximum checkbox selection limit (Optional)
                    .multiSelectList(subSpecializationSelector) // the multi select model list with ids and name
                    .onSubmit(new MultiSelectDialog.SubmitCallbackListener() {
                        @Override
                        public void onSelected(ArrayList<Integer> selectedIdss, ArrayList<String> selectedNames, String dataString) {
                            //will return list of selected IDS
                            subSpecializationfields.set("");
                            specialization.set("");
                            subSpecializeselectedIds = selectedIdss;

                            List<BaseModel>selectedSpecializes=new ArrayList<>();
                            for (int i = 0; i < selectedIdss.size(); i++) {
                                BaseModel baseModel=new BaseModel();
                                baseModel.setName(selectedNames.get(i));
                                baseModel.setId(selectedIdss.get(i));
                                subSpecializationfields.set(subSpecializationfields.get() + "-" + selectedNames.get(i));
                                specialization.set(specialization.get()+"-"+selectedNames.get(i));
                                selectedSpecializes.add(baseModel);
                            }
                            ((MyApplication)(MyApplication.getAppContext())).setBasicspecializations(selectedSpecializes);

                        }

                        @Override
                        public void onCancel() {
                            // Log.d(TAG,"Dialog cancelled");
                        }


                    });

            subDialog.show(fragmentManager, "multiSelectDialog");
        }
    }

    public void setSpecializationText(){
       String specializationText=CustomUtils.getInstance().setSpecializationText();
       specialization.set(specializationText);
       }

    public void setBrandsText() {
        String brandsText = CustomUtils.getInstance().setBrandsText();
        System.out.println("Specializations :"+brandsText);
        brands.set(brandsText);
    }

    public void setUrgentText(){
        String urgentText="";
        urgentText=CustomUtils.getInstance().setUrgentText();
        urgentRequest.set(urgentText);}


    public void setProfileObject(UpdateProfileRequest updateProfileRequest){
        this.updateProfileRequest=updateProfileRequest;
    }

    public UpdateProfileRequest getProfileObject(){
        if (userData.getWinch()==0) {
            updateProfileRequest.setBrands(CustomUtils.getInstance().getBrandsIds());
            updateProfileRequest.setSpecializations(CustomUtils.getInstance().getSpecializationIds());
            updateProfileRequest.setUrgentTypes(CustomUtils.getInstance().getUrgentIds());
        }else updateProfileRequest.setWinchTypes(selectedIds);
        return updateProfileRequest;
    }

    public boolean validate(){
        if (userData.getWinch()==0){

            System.out.println("Sub Specialize Size :"+CustomUtils.getInstance().getSpecializationIds().size()+"  ");
        if(CustomUtils.getInstance().getBrandsIds().isEmpty() || CustomUtils.getInstance().getSpecializationIds().isEmpty() ||  CustomUtils.getInstance().getUrgentIds().isEmpty()) {
            callback.updateUi(ConfigurationFile.Constants.FILL_ALL_DATTA);
            return false;
        }else return true;
        }else if (userData.getWinch()==1){
            if (selectedIds.size()==0)
                return false;
            else return true;
        }
        return false;
    }

    public void changePasswordAct(View view){
        callback.updateUi(ConfigurationFile.Constants.CHANGE_PASSWORD_ACT);
    }

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////


    public void getWenshTypes(){
        if (wt.size()==0) {
            if (NetWorkConnection.isConnectingToInternet((Activity)context)) {

                ApiClient.getClient().create(EndPoints.class).getWinchTypes(ConfigurationFile.Constants.API_KEY, CustomUtils.getInstance().getAppLanguage(context), ConfigurationFile.Constants.Content_Type, ConfigurationFile.Constants.Content_Type)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(winshResponseResponse -> {

                            if (winshResponseResponse.code() == ConfigurationFile.Constants.SUCCESS_CODE) {
                                //  showWenshDialog(winshResponseResponse.body().getData());
                                initalizeList(winshResponseResponse.body().getData());
                                wt.addAll(winshResponseResponse.body().getData());
                            }else if (winshResponseResponse.code() == ConfigurationFile.Constants.UNSUBSCRIBE_CODE ||
                                    winshResponseResponse.code() == ConfigurationFile.Constants.UNAUTHENTICATED_CODE){
                                CustomUtils.getInstance().endSession((Activity)context);
                            }

                        }, throwable -> {
                            System.out.println("throwable :" + throwable.getMessage());
                            CustomUtils.getInstance().cancelDialog();
                        });
            } else {
                callback.updateUi(ConfigurationFile.Constants.NO_INTERNET_CONNECTION_CODE);
            }
        }else initalizeList(wt);
    }

    public void initalizeList(List<WenshTypes>wenshTypes){
        if (WinchtypesSelector.size()==0) {
            for (int i = 0; i < wenshTypes.size(); i++) {
                WinchtypesSelector.add(new MultiSelectModel(wenshTypes.get(i).getId(), wenshTypes.get(i).getwName()));
            }
        }
        showWenshDialog();
    }

    public void showWenshDialog(){
        MultiSelectDialog multiSelectDialog = new MultiSelectDialog()
                .title(context.getResources().getString(R.string.wensh_types)) //setting title for dialog
                .titleSize(25)

                .positiveText(context.getResources().getString(R.string.done))
                .negativeText(context.getResources().getString(R.string.cancel))
                .setMinSelectionLimit(1) //you can set minimum checkbox selection limit (Optional)
                .setMaxSelectionLimit(WinchtypesSelector.size()) //you can set maximum checkbox selection limit (Optional)
                .multiSelectList(WinchtypesSelector) // the multi select model list with ids and name
                .onSubmit(new MultiSelectDialog.SubmitCallbackListener() {
                    @Override
                    public void onSelected(ArrayList<Integer> selectedIdss, ArrayList<String> selectedNames, String dataString) {
                        //will return list of selected IDS
                        Wenshfields.set("");
                        selectedIds=selectedIdss;
                        specialization.set("");
                        for (int i = 0; i < selectedIdss.size(); i++) {
                            Wenshfields.set(Wenshfields.get()+"-"+selectedNames.get(i));
                            specialization.set(specialization.get()+"-"+selectedNames.get(i));
                        }


                    }

                    @Override
                    public void onCancel() {
                        // Log.d(TAG,"Dialog cancelled");
                    }


                });

        if (!multiSelectDialog.isVisible())
        multiSelectDialog.show(fragmentManager, "multiSelectDialog");
    }


/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

}
