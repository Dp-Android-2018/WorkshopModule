package com.findandfix.workshop.ui.activity;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;

import com.findandfix.workshop.R;
import com.findandfix.workshop.databinding.ActivityThirdStepLayoutBinding;
import com.findandfix.workshop.model.request.RegisterRequest;
import com.findandfix.workshop.ui.callback.DisplayDialogNavigator;
import com.findandfix.workshop.utils.ConfigurationFile;
import com.findandfix.workshop.viewmodel.ThirdStepRegistration.FriDayViewModel;
import com.findandfix.workshop.viewmodel.ThirdStepRegistration.MonDayViewModel;
import com.findandfix.workshop.viewmodel.ThirdStepRegistration.SaturdayViewModel;
import com.findandfix.workshop.viewmodel.ThirdStepRegistration.SunDayViewModel;
import com.findandfix.workshop.viewmodel.ThirdStepRegistration.ThirdStepRegisterViewModel;
import com.findandfix.workshop.viewmodel.ThirdStepRegistration.ThursDayViewModel;
import com.findandfix.workshop.viewmodel.ThirdStepRegistration.TuesdayViewModel;
import com.findandfix.workshop.viewmodel.ThirdStepRegistration.WednesDayViewModel;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

/**
 * Created by DELL on 25/03/2018.
 */

public class ThirdStepRegisterActivity extends BaseActivity implements DisplayDialogNavigator {
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


    @Override
    public void updateUi(int code) {
        if(code==ConfigurationFile.Constants.TIME_ERROR)
            showSnackbar(getString(R.string.smaller_time));
        else
            showSnackbar(getResources().getString(R.string.choose_working_day));
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

    @Override
    protected void onResume() {
        super.onResume();
        viewModel.onResume();
    }


    public void initBinding(){
        saturdayViewModel=new SaturdayViewModel(ThirdStepRegisterActivity.this,this);
        sunDayViewModel=new SunDayViewModel(ThirdStepRegisterActivity.this,this);
        monDayViewModel=new MonDayViewModel(ThirdStepRegisterActivity.this,this);
        tuesdayViewModel=new TuesdayViewModel(ThirdStepRegisterActivity.this,this);
        wednesDayViewModel=new WednesDayViewModel(ThirdStepRegisterActivity.this,this);
        thursDayViewModel=new ThursDayViewModel(ThirdStepRegisterActivity.this,this);
        friDayViewModel=new FriDayViewModel(ThirdStepRegisterActivity.this,this);
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
    protected void onPause() {
        super.onPause();
        viewModel.onPause();
    }

    public void getObject(){
        registerRequest=(RegisterRequest) getIntent().getSerializableExtra(ConfigurationFile.SharedPrefConstants.PREF_REGISTER_OBJECT);
    }

    public void showSnackbar(String message){
        Snackbar.make(binding.rlParent,message,Snackbar.LENGTH_LONG).show();
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onFinishEvent(boolean finishActivity){
        if(finishActivity) {
            System.out.println("Third Act :"+finishActivity);
            finish();
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        viewModel.backToPreviousAct(binding.rlParent);
    }
}
