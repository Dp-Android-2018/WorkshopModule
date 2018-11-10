package com.findandfix.workshop.ui.activity;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;

import com.findandfix.workshop.R;
import com.findandfix.workshop.databinding.ActivityThirdStepDaysLayoutBinding;
import com.findandfix.workshop.model.request.RegisterRequest;
import com.findandfix.workshop.ui.callback.BaseInterface;
import com.findandfix.workshop.utils.ConfigurationFile;
import com.findandfix.workshop.viewmodel.WorkingDaysViewModel;

/**
 * Created by DELL on 11/08/2018.
 */

public class WorkingDayActivity extends BaseActivity implements BaseInterface {
    private ActivityThirdStepDaysLayoutBinding binding;
    private WorkingDaysViewModel viewModel;
    private RegisterRequest registerRequest;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getObject();
        viewModel=new WorkingDaysViewModel(WorkingDayActivity.this,this,registerRequest);
        binding= DataBindingUtil.setContentView(this,R.layout.activity_third_step_days_layout);
        binding.setViewModel(viewModel);

    }

    public void getObject(){
        registerRequest=(RegisterRequest) getIntent().getSerializableExtra(ConfigurationFile.SharedPrefConstants.PREF_REGISTER_OBJECT);
    }

    @Override
    public void updateUi(int code) {
                if (code==ConfigurationFile.Constants.EMPTY_WORKSHOP_DAYS)
                    Snackbar.make(binding.getRoot(), R.string.choose_one_day,Snackbar.LENGTH_LONG).show();
                else if (code==ConfigurationFile.Constants.MOVE_TO_NEXT_ACTIVITY){
                    Intent i=new Intent(getApplicationContext(), FinalStepRegisterActivity.class);
                    i.putExtra(ConfigurationFile.SharedPrefConstants.PREF_REGISTER_OBJECT,registerRequest);
                    startActivity(i);
                }else if(code==ConfigurationFile.Constants.MOVE_TO_PREVIOUS_ACTIVITY){
                    finish();
                }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        viewModel.backToPreviousAct(binding.rlParent);
    }

    @Override
    protected void onResume() {
        super.onResume();
        viewModel.onResume();
    }
}
