package com.findandfix.workshop.viewmodel.SecondStepRegistration;

import android.databinding.BaseObservable;

import com.findandfix.workshop.ui.Application.MyApplication;
import com.findandfix.workshop.ui.callback.DisplayDialogNavigator;
import com.findandfix.workshop.utils.ConfigurationFile;

import java.util.List;

/**
 * Created by DELL on 25/03/2018.
 */

public class SecondStepValidateViewModel extends BaseObservable {
    private DisplayDialogNavigator navigator;
    private int returnedvalue=0;
    public SecondStepValidateViewModel(DisplayDialogNavigator navigator) {
        this.navigator=navigator;
    }

   public int validator(int selectedvalue, List<Integer>ids){

        if (selectedvalue!=-1 && selectedvalue!=0){
            if(selectedvalue==2 && ids.size()>0){
                returnedvalue=1;
            }else if(selectedvalue==3){
                if(((MyApplication)(MyApplication.getAppContext())).getBasicspecializations().size()!=0){
                    if (((MyApplication)(MyApplication.getAppContext())).getBasicBrands().size()!=0){
                        if(((MyApplication)(MyApplication.getAppContext())).getBasicUrgentTypes().size()!=0){

                            returnedvalue=1;
                        }else {
                            navigator.updateUi(ConfigurationFile.Constants.EMPTY_WORKSHOP_URGENT_TYPES);
                        }

                    }else {
                        navigator.updateUi(ConfigurationFile.Constants.EMPTY_WORKSHOP_BRANDS);
                    }

                }else {
                    navigator.updateUi(ConfigurationFile.Constants.EMPTY_WORKSHOP_SPECIALIZATION);
                }
            }else  navigator.updateUi(ConfigurationFile.Constants.EMPTY_WENSH_TYPES);

        }else {
            navigator.updateUi(ConfigurationFile.Constants.EMPTY_PROVIDER_TYPE);
        }
        return returnedvalue;
   }


}
