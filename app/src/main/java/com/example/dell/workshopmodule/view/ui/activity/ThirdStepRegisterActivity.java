package com.example.dell.workshopmodule.view.ui.activity;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;

import com.example.dell.workshopmodule.R;
import com.example.dell.workshopmodule.databinding.ActivityThirdStepLayoutBinding;
import com.example.dell.workshopmodule.model.request.RegisterRequest;
import com.example.dell.workshopmodule.utils.ConfigurationFile;
import com.example.dell.workshopmodule.view.ui.callback.DisplayDialogNavigator;
import com.example.dell.workshopmodule.viewmodel.ThirdStepRegistration.FriDayViewModel;
import com.example.dell.workshopmodule.viewmodel.ThirdStepRegistration.MonDayViewModel;
import com.example.dell.workshopmodule.viewmodel.ThirdStepRegistration.SaturdayViewModel;
import com.example.dell.workshopmodule.viewmodel.ThirdStepRegistration.SunDayViewModel;
import com.example.dell.workshopmodule.viewmodel.ThirdStepRegistration.ThirdStepRegisterViewModel;
import com.example.dell.workshopmodule.viewmodel.ThirdStepRegistration.ThursDayViewModel;
import com.example.dell.workshopmodule.viewmodel.ThirdStepRegistration.TuesdayViewModel;
import com.example.dell.workshopmodule.viewmodel.ThirdStepRegistration.WednesDayViewModel;

/**
 * Created by DELL on 25/03/2018.
 */

public class ThirdStepRegisterActivity extends AppCompatActivity implements DisplayDialogNavigator {
    private ActivityThirdStepLayoutBinding binding;
    private ThirdStepRegisterViewModel viewModel;
    private SaturdayViewModel saturdayViewModel;
    private SunDayViewModel sunDayViewModel;
    private MonDayViewModel monDayViewModel;
    private TuesdayViewModel tuesdayViewModel;
    private WednesDayViewModel wednesDayViewModel;
    private ThursDayViewModel thursDayViewModel;
    private FriDayViewModel friDayViewModel;
    private RegisterRequest registerRequest;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getObject();
        initBinding();
    }

    public void initBinding(){
        saturdayViewModel=new SaturdayViewModel(ThirdStepRegisterActivity.this);
        sunDayViewModel=new SunDayViewModel(ThirdStepRegisterActivity.this);
        monDayViewModel=new MonDayViewModel(ThirdStepRegisterActivity.this);
        tuesdayViewModel=new TuesdayViewModel(ThirdStepRegisterActivity.this);
        wednesDayViewModel=new WednesDayViewModel(ThirdStepRegisterActivity.this);
        thursDayViewModel=new ThursDayViewModel(ThirdStepRegisterActivity.this);
        friDayViewModel=new FriDayViewModel(ThirdStepRegisterActivity.this);
        viewModel=new ThirdStepRegisterViewModel(ThirdStepRegisterActivity.this,this,registerRequest);
        binding=DataBindingUtil.setContentView(this,R.layout.activity_third_step_layout);
        binding.setViewModel(viewModel);
        binding.setSatviewModel(saturdayViewModel);
        binding.setSunviewModel(sunDayViewModel);
        binding.setMonviewModel(monDayViewModel);
        binding.setTueviewModel(tuesdayViewModel);
        binding.setWedviewModel(wednesDayViewModel);
        binding.setThuviewModel(thursDayViewModel);
        binding.setFriviewModel(friDayViewModel);
    }

    @Override
    public void displayDialog(int code) {

    }

    @Override
    public void updateWorkshopData(int code) {

    }

    @Override
    public void displayBrandsDialog() {

    }

    @Override
    public void updateUi(int code) {
        Snackbar.make(binding.rlParent, R.string.choose_working_day,Snackbar.LENGTH_LONG).show();
    }

    @Override
    public void NavigateBetweenActivities(RegisterRequest registerRequest, int code) {
        if(code==ConfigurationFile.Constants.MOVE_TO_NEXT_ACTIVITY){
            Intent i=new Intent(getApplicationContext(), FinalStepRegisterActivity.class);
            i.putExtra(ConfigurationFile.SharedPrefConstants.PREF_REGISTER_OBJECT,registerRequest);
            startActivity(i);
        }else if(code==ConfigurationFile.Constants.MOVE_TO_PREVIOUS_ACTIVITY){
            finish();
        }
    }

    public void getObject(){
        registerRequest=(RegisterRequest) getIntent().getSerializableExtra(ConfigurationFile.SharedPrefConstants.PREF_REGISTER_OBJECT);
    }
}
