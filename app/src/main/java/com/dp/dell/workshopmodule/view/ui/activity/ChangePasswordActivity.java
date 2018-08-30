package com.dp.dell.workshopmodule.view.ui.activity;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;

import com.dp.dell.workshopmodule.R;
import com.dp.dell.workshopmodule.databinding.ActivityChangePasswordLayoutBinding;
import com.dp.dell.workshopmodule.utils.ConfigurationFile;
import com.dp.dell.workshopmodule.view.ui.callback.BaseInterface;
import com.dp.dell.workshopmodule.viewmodel.ChangePasswordViewModel;
import com.dp.dell.workshopmodule.viewmodel.ToolbarViewModel;

/**
 * Created by DELL on 05/05/2018.
 */

public class ChangePasswordActivity extends BaseActivity implements BaseInterface{

    private ActivityChangePasswordLayoutBinding binding;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initBinding();
        setUpActionBar();
    }

    /*Inflating UI and Designing View Model */
    public void initBinding(){
        binding= DataBindingUtil.setContentView(ChangePasswordActivity.this, R.layout.activity_change_password_layout);
        ChangePasswordViewModel changePasswordViewModel=new ChangePasswordViewModel(ChangePasswordActivity.this,this);
        binding.setChangepasswordviewmodel(changePasswordViewModel );
    }
/* Define Action Bar Style and it's properties according to code which sent to toolbar view model*/
    public void setUpActionBar(){
        setSupportActionBar( binding.toolbar.toolbar);
        binding.toolbar.setViewmodel(new ToolbarViewModel(ChangePasswordActivity.this,ConfigurationFile.Constants.HANDLE_CHANGE_PASSWORD_TOOLBAR));
    }

    /* Update Ui According To Code Sent From View Model */
    @Override
    public void updateUi(int code) {

        if (code== ConfigurationFile.Constants.INVALID_PASSWORD_FORMAT){
                showSnackBar(R.string.password_length);
        }else if (code== ConfigurationFile.Constants.INVALID_PASSWORD_CONFIRMATION){
            showSnackBar(R.string.matches_password);
        }else if (code== ConfigurationFile.Constants.SUCCESS_CODE){
              showSnackBar(R.string.password_updated);
        }else if (code== ConfigurationFile.Constants.INVALID_DATA){
              showSnackBar(R.string.invalid_current_password);
        }else if (code== ConfigurationFile.Constants.CANT_COMPLETE_REQUEST_CODE){
            showSnackBar(R.string.cant_complete_your_request);
        }else if (code== ConfigurationFile.Constants.FILL_ALL_DATTA){
            showSnackBar(R.string.fill_data);
        }
    }

/*Display Snack Bar With Message */
    public void showSnackBar(int message){
        Snackbar.make(binding.llBase,message,Snackbar.LENGTH_LONG).show();
    }
}
