package com.dp.dell.workshopmodule.view.ui.activity;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.Button;

import com.dp.dell.workshopmodule.R;
import com.dp.dell.workshopmodule.databinding.ActivityEditWorkshopProfileLayoutBinding;
import com.dp.dell.workshopmodule.model.global.BaseModel;
import com.dp.dell.workshopmodule.model.global.CountryItem;
import com.dp.dell.workshopmodule.utils.ConfigurationFile;
import com.dp.dell.workshopmodule.utils.CustomUtils;
import com.dp.dell.workshopmodule.view.ui.callback.BaseInterface;
import com.dp.dell.workshopmodule.view.ui.dialog.CitiesDialog;
import com.dp.dell.workshopmodule.view.ui.dialog.CountriesDialog;
import com.dp.dell.workshopmodule.view.ui.dialog.CustomBrandsDialog;
import com.dp.dell.workshopmodule.view.ui.dialog.CustomDialog;
import com.dp.dell.workshopmodule.viewmodel.EditProfileInfoViewModel;
import com.dp.dell.workshopmodule.viewmodel.EditProfileSpecializationViewModel;
import com.dp.dell.workshopmodule.viewmodel.HandleEditProfileViewModel;
import com.dp.dell.workshopmodule.viewmodel.ToolbarViewModel;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

/**
 * Created by DELL on 14/03/2018.
 */

public class EditProfileInfoActivity extends BaseActivity implements BaseInterface{

    private ActivityEditWorkshopProfileLayoutBinding binding;
    private  EditProfileInfoViewModel infoViewModel;
    private EditProfileSpecializationViewModel specializationViewModel;
    private HandleEditProfileViewModel handleEditProfileViewModel;
    private Dialog dialog=null;
    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initBinding();
        setUpActionBar();}

    @Override
    protected void onResume() {
        super.onResume();
        infoViewModel.onResume();}

    @Override
    protected void onPause() {
        super.onPause();
        infoViewModel.onPause();}


///////////////////////Inflating Ui and Setting View Model for basic Info + Specialization ////////////////////////////////////
    public void initBinding(){
        binding= DataBindingUtil.setContentView(EditProfileInfoActivity.this,R.layout.activity_edit_workshop_profile_layout);
        infoViewModel=new EditProfileInfoViewModel(EditProfileInfoActivity.this,this);
        specializationViewModel=new EditProfileSpecializationViewModel(EditProfileInfoActivity.this,this,getSupportFragmentManager());
        handleEditProfileViewModel=new HandleEditProfileViewModel(infoViewModel,specializationViewModel,EditProfileInfoActivity.this,this);
        binding.setViewmodel(infoViewModel);
        binding.setSpecializeviewmodel(specializationViewModel);
        binding.setEditprofileviewmodel(handleEditProfileViewModel);
    }

    ////////////////////////Handel Action Bar Layout ///////////////////////////////////////////////////////////////////////
    public void setUpActionBar(){
        setSupportActionBar( binding.toolbar.toolbar);
        binding.toolbar.setViewmodel(new ToolbarViewModel(EditProfileInfoActivity.this,ConfigurationFile.Constants.HANDLE_EDIT_PROFILE_TOOLBAR));}

        ////Handle Code Which Comes Back From View Model and Updat UI //////////////////////////////
    @Override
    public void updateUi(int code) {
        if(code== ConfigurationFile.Constants.SHOW_DIALOG_CODE){
            CountriesDialog dialog=new CountriesDialog(EditProfileInfoActivity.this);
            showDialog(dialog);
        }else if(code== ConfigurationFile.Constants.SHOW_CITIES_DIALOG_CODE){
            CitiesDialog dialog=new CitiesDialog(EditProfileInfoActivity.this,infoViewModel.countryId());
            showDialog(dialog);
        }else if(code== ConfigurationFile.Constants.UNAUTHENTICATED_CODE){
           CustomUtils.getInstance().endSession(EditProfileInfoActivity.this);
        } else if (code == ConfigurationFile.Constants.FILL_ALL_DATTA)
            showSnackBar(getResources().getString(R.string.fill_data));
        else if (code == ConfigurationFile.Constants.INVALID_EMAIL_FORMAT)
            showSnackBar(getResources().getString(R.string.fill_data));

        else if(code== ConfigurationFile.Constants.SHOW_BRANDS_DIALOG_CODE){
            CustomBrandsDialog customBrandsDialog=new CustomBrandsDialog(EditProfileInfoActivity.this,this);
            showDialog(customBrandsDialog);
        } else if(code== ConfigurationFile.Constants.SHOW_SPECIALIZE_DIALOG_CODE){
            CustomDialog customDialog = new CustomDialog(EditProfileInfoActivity.this, ConfigurationFile.Constants.DISPLAY_SPECIALIZATION_DIALOG, this);
            showDialog(customDialog);
        } else if(code== ConfigurationFile.Constants.SHOW_URGENTS_DIALOG_CODE){
            CustomDialog customDialog = new CustomDialog(EditProfileInfoActivity.this, ConfigurationFile.Constants.DISPLAY_URGENT_TYPES_DIALOG, this);
            showDialog(customDialog);
        }else if (code == ConfigurationFile.Constants.UPDATE_BRANDS_DIALOG)
            specializationViewModel.setBrandsText();
        else if (code == ConfigurationFile.Constants.DISPLAY_SPECIALIZATION_DIALOG_TEXT)
            specializationViewModel.setSpecializationText();

        else if (code == ConfigurationFile.Constants.DISPLAY_URGENT_TYPES_DIALOG_text)
            specializationViewModel.setUrgentText();

        else if (code == ConfigurationFile.Constants.FILL_ALL_DATTA)
            showSnackBar(getResources().getString(R.string.fill_data));

        else if (code == ConfigurationFile.Constants.SUCCESS_CODE) {
            showSnackBar(getString(R.string.updates_submitted_successfully));
            setDialog();
        }

        else if (code == ConfigurationFile.Constants.UNAUTHENTICATED_CODE) {
            showSnackBar(getString(R.string.unauthenticated));
            CustomUtils.getInstance().endSession(EditProfileInfoActivity.this);
        }

        else if (code == ConfigurationFile.Constants.INVALID_DATA)
            showSnackBar(getResources().getString(R.string.invalid_data));

        else if (code == ConfigurationFile.Constants.CHANGE_PASSWORD_ACT)
           moveToChangePasswordAct();
    }

////////////////Calling Change Password Act/////////////////////////////////////////////////
    public void moveToChangePasswordAct(){
        Intent i=new Intent(EditProfileInfoActivity.this,ChangePasswordActivity.class);
        startActivity(i);
    }

    ///////Using EventBus To Get Country Data From Dialog and Sent it to view model */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent( CountryItem countryItem){
        infoViewModel.setCountryData(countryItem);
    }

    ///////Using EventBus To Get City Data From Dialog and Sent it to view model */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onCityMessageEvent( BaseModel baseModel){
        if(!(baseModel instanceof CountryItem))
            infoViewModel.setCityData(baseModel);
    }

    /////////////////////////Displaying Custom Dialog ///////////////////////////////////////////
    public void showDialog(Dialog dialog){
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();
    }

    ////////////////////Displaying Snack Bar ////////////////////////////////////////
    public void showSnackBar(String message){
        Snackbar.make(binding.llContainer,message,Snackbar.LENGTH_LONG).show();
    }


    /////////////////Displaying Dialog After Updating Profile Successfully ///////////////////////

    private void setDialog(){

        dialog = new Dialog(EditProfileInfoActivity.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        LayoutInflater factory = LayoutInflater.from(getApplicationContext());
        final View dialogView = factory.inflate(R.layout.dialog_submit_edit_profule_layout, null);
        Button btnCancel = dialogView.findViewById(R.id.btn_done);
        btnCancel.setOnClickListener(v -> dialog.cancel());
        dialog.setContentView(dialogView);
        dialog.show();

    }

}
