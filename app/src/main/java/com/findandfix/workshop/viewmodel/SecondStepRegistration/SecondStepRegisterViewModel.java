package com.findandfix.workshop.viewmodel.SecondStepRegistration;

import android.app.Activity;
import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.databinding.BaseObservable;
import android.databinding.ObservableField;
import android.databinding.ObservableInt;
import android.graphics.drawable.ColorDrawable;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

import com.abdeveloper.library.MultiSelectDialog;
import com.abdeveloper.library.MultiSelectModel;
import com.findandfix.workshop.R;
import com.findandfix.workshop.model.global.BaseModel;
import com.findandfix.workshop.model.global.BrandItem;
import com.findandfix.workshop.model.global.WenshTypes;
import com.findandfix.workshop.model.request.RegisterRequest;
import com.findandfix.workshop.model.response.SpecializationResponse;
import com.findandfix.workshop.network.ApiClient;
import com.findandfix.workshop.network.EndPoints;
import com.findandfix.workshop.ui.Application.MyApplication;
import com.findandfix.workshop.ui.adapter.SpecializeAdapter;
import com.findandfix.workshop.ui.callback.DisplayDialogNavigator;
import com.findandfix.workshop.ui.callback.NetworkCallback;
import com.findandfix.workshop.utils.ConfigurationFile;
import com.findandfix.workshop.utils.ConstantsCallsUtils;
import com.findandfix.workshop.utils.CustomUtils;
import com.findandfix.workshop.utils.NetWorkConnection;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by DELL on 21/03/2018.
 */

public class SecondStepRegisterViewModel extends BaseObservable {


    public ObservableInt selectedValue;
    private DisplayDialogNavigator navigator;
    private Activity activity;
    public ObservableField<String>fields;
    public ObservableField<String>Wenshfields;
    public ObservableField<String>subSpecializationfields;
    public ObservableField<String> specializationText;
    public ObservableField<String> urgentText;
    public ObservableField<String> brandsText;
    public ObservableField<Boolean> isActive;
    private SecondStepValidateViewModel validateViewModel;
    private RegisterRequest registerRequest;
    private BroadcastReceiver receiver;
    ArrayList<MultiSelectModel> WinchtypesSelector= new ArrayList<>();
    ArrayList<MultiSelectModel> subSpecializationSelector= new ArrayList<>();
    private FragmentManager fragmentManager;
    ArrayList<Integer> selectedIds;
    ArrayList<Integer> subSpecializeselectedIds;
    private List<WenshTypes>wt;
    private List<BaseModel>subIDS;
    RecyclerView spinnerRecycleView;
    MultiSelectDialog subDialog=null;
    MultiSelectDialog wenshDialog=null;
    Dialog dialog=null;
    private int selectedSpecialize=-1;
    public SecondStepRegisterViewModel(Activity activity, DisplayDialogNavigator navigator, SecondStepValidateViewModel validateViewModel, RegisterRequest registerRequest, FragmentManager fragmentManager) {
        this.activity=activity;
        this.navigator=navigator;
        this.validateViewModel=validateViewModel;
        this.fragmentManager=fragmentManager;
        this.registerRequest=registerRequest;
        selectedValue=new ObservableInt();
        isActive=new ObservableField<>(true);
        wt=new ArrayList<>();
        subIDS=new ArrayList<>();
        selectedValue.set(-1);
        fields=new ObservableField<>();
        subSpecializeselectedIds=new ArrayList<>();
        specializationText=new ObservableField<>();
        Wenshfields=new ObservableField<>("");
        urgentText=new ObservableField<>();
        brandsText=new ObservableField<>();
        subSpecializationfields=new ObservableField<>();
        selectedIds=new ArrayList<>();
        handleBrodcast();
    }

    public void onResume() {
        EventBus.getDefault().register(activity);
    }

    public void onPause() {
        EventBus.getDefault().unregister(activity);
    }

    public void setSpecializationText(){
        specializationText.set(" ");
        specializationText.set(CustomUtils.getInstance().setSpecializationText());}

    public void setUrgentText(){
        urgentText.set("");
        urgentText.set(CustomUtils.getInstance().setUrgentText());
    }

    public void setBrandsText() {
        brandsText.set("");
        brandsText.set(CustomUtils.getInstance().setBrandsText());
    }

    public void displaySpecializationDialog(View view){
            getSpecialization();


    }
    public void displayUrgentTypesDialog(View view){
        navigator.updateUi(ConfigurationFile.Constants.DISPLAY_URGENT_TYPES_DIALOG);
    }

    public void displayBrandsDialog(View view){
        navigator.updateUi(ConfigurationFile.Constants.SHOW_BRANDS_DIALOG_CODE);
    }

    public void displaySubSpecializationDialog(View view){
        if (selectedSpecialize!=-1)
        getSubSpecialization();
        else navigator.updateUi(ConfigurationFile.Constants.EMPTY_WORKSHOP_SPECIALIZATION);
    }

    public void getSpecialization(){
        ConstantsCallsUtils.getInstance().getSpecializations(new NetworkCallback() {
            @Override
            public <E> void onSuccess(Object data, int code) {
                if (code==ConfigurationFile.Constants.SUCCESS_CODE){
                    SpecializationResponse specializationResponse=(SpecializationResponse)data;
                    setSpecializationList(specializationResponse.getData());
                }

            }

            @Override
            public void onFailure(Throwable throwable) {

            }

            @Override
            public <E> void onUnuthenticated(int code) {
                CustomUtils.getInstance().endSession(activity);
            }
        },CustomUtils.getInstance().getAppLanguage(activity));
    }

    private void setSpecializationList( List<BaseModel>brandItems){
        SpecializeAdapter specializeAdapter = new SpecializeAdapter(activity,brandItems);
        setDialog(activity.getString(R.string.choose_specializations));
        spinnerRecycleView.setAdapter(specializeAdapter);
        specializeAdapter.setClickListener(position -> {
            subIDS.clear();
            subSpecializationSelector.clear();
            selectedSpecialize = brandItems.get(position).getId();
            System.out.println("Selected Specialize Id:"+selectedSpecialize);
            specializationText.set(brandItems.get(position).getName());
            dialog.cancel();

        });
    }

    private void setDialog(String title){

        if (dialog==null || !dialog.isShowing()){
            dialog = new Dialog(activity);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
            LayoutInflater factory = LayoutInflater.from(activity);
            final View dialogView = factory.inflate(R.layout.dialog_spinner, null);
            TextView titleTextView = dialogView.findViewById(R.id.tv_spinner_title);
            titleTextView.setText(title);
            spinnerRecycleView = dialogView.findViewById(R.id.rv_spinner_collections);
            spinnerRecycleView.setLayoutManager(new LinearLayoutManager(activity));
                dialog.setContentView(dialogView);
                dialog.show();
            }

    }

    public void validate(View view){
       int code=validateViewModel.validator(selectedValue.get(),selectedIds);
       if(code==1){
            if(selectedValue.get()==3) {
                registerRequest.setBrands(getBrandsIds());
                registerRequest.setSpecializations(getSpecializationIds());
                registerRequest.setUrgentTypes(getUrgentTypes());
                registerRequest.setWinch(0);
            }else if(selectedValue.get()==2){
                List<Integer>wensh=new ArrayList<>();
                wensh.add(4);
                registerRequest.setWinchTypes(selectedIds);
               // registerRequest.setUrgentTypes(wensh);
                registerRequest.setWinch(1);
            }
            moveTOThirdStep();
       }
    }

    public void wenshTypesActions(View view){
        getWenshTypes();
    }

    public void getSubSpecialization(){

        if (subDialog==null || !subDialog.isVisible()) {
            if (subIDS.size() == 0) {
                if (NetWorkConnection.isConnectingToInternet(activity)) {
                    System.out.println("Sub Data Selected Specialize :" + selectedSpecialize);
                    ApiClient.getClient().create(EndPoints.class).getSubSpecializations(ConfigurationFile.Constants.API_KEY, CustomUtils.getInstance().getAppLanguage(activity), ConfigurationFile.Constants.Content_Type, ConfigurationFile.Constants.Content_Type, selectedSpecialize)
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(specializationResponseResponse -> {

                                if (specializationResponseResponse.code() == ConfigurationFile.Constants.SUCCESS_CODE) {
                                    System.out.println("Sub Data Size :" + specializationResponseResponse.body().getData().size());
                                    initializeSubList(specializationResponseResponse.body().getData());
                                    subIDS.addAll(specializationResponseResponse.body().getData());
                                }else if (specializationResponseResponse.code() == ConfigurationFile.Constants.UNAUTHENTICATED_CODE ||
                                        specializationResponseResponse.code() == ConfigurationFile.Constants.UNSUBSCRIBE_CODE){
                                    CustomUtils.getInstance().endSession(activity);
                                }
                            }, throwable -> System.out.println("Sub Specialize :" + throwable.getMessage()));
                } else {
                    navigator.updateUi(ConfigurationFile.Constants.NO_INTERNET_CONNECTION_CODE);
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
    public void getWenshTypes(){
        if (wenshDialog==null || !wenshDialog.isVisible()) {
            if (wt.size() == 0) {
                if (NetWorkConnection.isConnectingToInternet(activity)) {

                    ApiClient.getClient().create(EndPoints.class).getWinchTypes(ConfigurationFile.Constants.API_KEY, CustomUtils.getInstance().getAppLanguage(activity), ConfigurationFile.Constants.Content_Type, ConfigurationFile.Constants.Content_Type)
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(winshResponseResponse -> {

                                if (winshResponseResponse.code() == ConfigurationFile.Constants.SUCCESS_CODE) {
                                    //  showWenshDialog(winshResponseResponse.body().getData());
                                    initalizeList(winshResponseResponse.body().getData());
                                    wt.addAll(winshResponseResponse.body().getData());
                                }else if (winshResponseResponse.code() == ConfigurationFile.Constants.UNSUBSCRIBE_CODE ||
                                        winshResponseResponse.code() == ConfigurationFile.Constants.UNAUTHENTICATED_CODE){
                                    CustomUtils.getInstance().endSession(activity);
                                }

                            }, throwable -> {
                                System.out.println("throwable :" + throwable.getMessage());
                                CustomUtils.getInstance().cancelDialog();
                            });
                } else {
                    navigator.updateUi(ConfigurationFile.Constants.NO_INTERNET_CONNECTION_CODE);
                }
            } else initalizeList(wt);
        }
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
        if (wenshDialog==null || !wenshDialog.isVisible()) {
            wenshDialog = new MultiSelectDialog()
                    .title(activity.getResources().getString(R.string.wensh_types)) //setting title for dialog
                    .titleSize(25)

                    .positiveText(activity.getResources().getString(R.string.ok))
                    .negativeText(activity.getResources().getString(R.string.cancel))
                    .setMinSelectionLimit(1) //you can set minimum checkbox selection limit (Optional)
                    .setMaxSelectionLimit(WinchtypesSelector.size()) //you can set maximum checkbox selection limit (Optional)
                    .multiSelectList(WinchtypesSelector) // the multi select model list with ids and name
                    .onSubmit(new MultiSelectDialog.SubmitCallbackListener() {
                        @Override
                        public void onSelected(ArrayList<Integer> selectedIdss, ArrayList<String> selectedNames, String dataString) {
                            //will return list of selected IDS
                            Wenshfields.set("");
                            selectedIds = selectedIdss;
                            for (int i = 0; i < selectedIdss.size(); i++) {
                                Wenshfields.set(Wenshfields.get() + "-" + selectedNames.get(i));
                            }


                        }

                        @Override
                        public void onCancel() {
                            // Log.d(TAG,"Dialog cancelled");
                        }


                    });

            wenshDialog.show(fragmentManager, "multiSelectDialog");
        }
    }



    public void showSubSpecializeDialog(){
        if (subDialog==null || !subDialog.isVisible()) {
            subDialog = new MultiSelectDialog()
                    .title(activity.getString(R.string.sub_specialization)) //setting title for dialog
                    .titleSize(25)

                    .positiveText(activity.getResources().getString(R.string.ok))
                    .negativeText(activity.getResources().getString(R.string.cancel))
                    
                    .setMinSelectionLimit(1) //you can set minimum checkbox selection limit (Optional)
                    .setMaxSelectionLimit(subSpecializationSelector.size()) //you can set maximum checkbox selection limit (Optional)
                    .multiSelectList(subSpecializationSelector) // the multi select model list with ids and name
                    .onSubmit(new MultiSelectDialog.SubmitCallbackListener() {
                        @Override
                        public void onSelected(ArrayList<Integer> selectedIdss, ArrayList<String> selectedNames, String dataString) {
                            //will return list of selected IDS
                            subSpecializationfields.set("");
                            subSpecializeselectedIds = selectedIdss;

                            List<BaseModel>selectedSpecializes=new ArrayList<>();
                            for (int i = 0; i < selectedIdss.size(); i++) {
                                BaseModel baseModel=new BaseModel();
                                baseModel.setName(selectedNames.get(i));
                                baseModel.setId(selectedIdss.get(i));
                                subSpecializationfields.set(subSpecializationfields.get() + "-" + selectedNames.get(i));
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

    public List<Integer> getSpecializationIds(){
        List<Integer>specializeId=new ArrayList<>();
        for(BaseModel model:(((MyApplication)MyApplication.getAppContext()).getBasicspecializations())){
                specializeId.add(model.getId());
        }
        return specializeId;
    }

    public List<Integer> getBrandsIds(){
        List<Integer>brandsIds=new ArrayList<>();
        for(BrandItem brandItem:(((MyApplication)MyApplication.getAppContext()).getBasicBrands())){
            brandsIds.add(brandItem.getId());
        }
        return brandsIds;
    }

    public List<Integer> getUrgentTypes(){
        List<Integer>urgentTypesId=new ArrayList<>();
        for(BaseModel model:(((MyApplication)MyApplication.getAppContext()).getBasicUrgentTypes())){
            urgentTypesId.add(model.getId());
        }
        return urgentTypesId;
    }

    public void moveTOThirdStep(){
            navigator.NavigateBetweenActivities(registerRequest,ConfigurationFile.Constants.MOVE_TO_NEXT_ACTIVITY);}




    public void showDialog(View view) {
        navigator.updateUi(ConfigurationFile.Constants.SHOW_CITIES_DIALOG_CODE);
    }

    public void setFieldsData(BaseModel baseModel) {
        System.out.println("Validator Event :"+baseModel.getName());
        System.out.println("Selected Id :"+baseModel.getId());
        selectedValue.set(baseModel.getId());
        fields.set(baseModel.getName());
    }

    public void handleBackButton(View view) {
        (((MyApplication)MyApplication.getAppContext()).getBasicspecializations()).clear();
        (((MyApplication)MyApplication.getAppContext()).getBasicBrands()).clear();
        (((MyApplication)MyApplication.getAppContext()).getBasicUrgentTypes()).clear();
        navigator.NavigateBetweenActivities(null,ConfigurationFile.Constants.MOVE_TO_PREVIOUS_ACTIVITY);
        unRegisterBroadcast();
    }


    public void handleBrodcast(){
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("com.package.ACTION_LOGOUT");
        receiver=new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                activity.finish();
            }
        };
        activity.registerReceiver(receiver, intentFilter);
    }

    public void unRegisterBroadcast(){
        if (receiver != null) {
            activity.unregisterReceiver(receiver);
            receiver = null;
        }
    }



}
