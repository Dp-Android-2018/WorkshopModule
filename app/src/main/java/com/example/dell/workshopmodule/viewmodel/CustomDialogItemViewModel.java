package com.example.dell.workshopmodule.viewmodel;

import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.view.View;
import android.widget.CheckBox;

import com.example.dell.workshopmodule.BR;
import com.example.dell.workshopmodule.model.global.BaseModel;
import com.example.dell.workshopmodule.utils.ConfigurationFile;
import com.example.dell.workshopmodule.view.ui.Application.MyApplication;

/**
 * Created by DELL on 21/03/2018.
 */

public class CustomDialogItemViewModel extends BaseObservable {


    private BaseModel model;
    private boolean checked;
    private int checkCode;
    public CustomDialogItemViewModel(BaseModel baseModel) {

        this.model=baseModel;
        checkCode=((MyApplication)(MyApplication.getAppContext())).getCustomDialogCode();
        setChecked();
    }

    public String getItemName(){
        return model.getName();
    }

    public void setModel(BaseModel model){
        this.model=model;
        notifyChange();
    }

    public void setCheckedListener(View v){
        if(((CheckBox)v).isChecked()) {
            if(checkCode== ConfigurationFile.Constants.DISPLAY_SPECIALIZATION_DIALOG)
                        handleSpecializeData(1);
            else
                        handleUrgentTypesData(1);

                          checked=true;
        } else {

            if(checkCode== ConfigurationFile.Constants.DISPLAY_SPECIALIZATION_DIALOG)
                    handleSpecializeData(2);
             else
                     handleUrgentTypesData(2);

                         checked=false;

        }
        notifyChange();
        notifyPropertyChanged(BR.checked);
    }

    public void setChecked(){
        if(checkCode== ConfigurationFile.Constants.DISPLAY_SPECIALIZATION_DIALOG)
                  handleSpecializeData(3);
        else
                 handleUrgentTypesData(3);
    }

    @Bindable
    public boolean getChecked(){
            return checked;
    }


    public void handleSpecializeData(int checker){
        if(checker==1)
              ((MyApplication)(MyApplication.getAppContext())).addSpecialize(model);
        else if(checker==2)
              ((MyApplication)(MyApplication.getAppContext())).removeSpecialization(model);
        else if(checker==3) {
            for (BaseModel baseModel : ((MyApplication) (MyApplication.getAppContext())).getBasicspecializations()) {
                if (baseModel.getId() == model.getId()) {
                    checked = true;
                    notifyPropertyChanged(BR.checked);
                    ((MyApplication) (MyApplication.getAppContext())).addSpecialize(model);
                }
            }
        }

    }



    public void handleUrgentTypesData(int checker){
        if(checker==1)
            ((MyApplication)(MyApplication.getAppContext())).addUrgentType(model);
        else if(checker==2)
            ((MyApplication)(MyApplication.getAppContext())).removeUrgentType(model);
        else if(checker==3) {
            for (BaseModel baseModel : ((MyApplication) (MyApplication.getAppContext())).getBasicUrgentTypes()) {
                if (baseModel.getId() == model.getId()) {
                    checked = true;
                    notifyPropertyChanged(BR.checked);
                    ((MyApplication) (MyApplication.getAppContext())).addUrgentType(model);
                }
            }
        }
    }


}
