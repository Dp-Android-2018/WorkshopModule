package com.dp.dell.workshopmodule.viewmodel;

import android.content.Context;
import android.content.Intent;
import android.databinding.BaseObservable;
import android.view.View;

import com.dp.dell.workshopmodule.model.global.AdvData;
import com.dp.dell.workshopmodule.utils.ConfigurationFile;
import com.dp.dell.workshopmodule.view.ui.activity.OffersDetailActivity;


public class ItemOfferViewModel extends BaseObservable {
    private Context context;
    private AdvData advData;
    public ItemOfferViewModel(Context context, AdvData advData) {
        this.context=context;
        this.advData=advData;
    }

    public void setAdv(AdvData adv){
        this.advData=adv;
        notifyChange();
    }

    public String getAdvTitle(){
        System.out.println("Adv Title :"+advData.getTitle());
        return advData.getTitle();};
    public String getAdvContent(){return  advData.getDescription();};

    public String getImage(){return  advData.getImage();};

    public void onItemClick(View view){
        Intent intent=new Intent(view.getContext(),OffersDetailActivity.class);
        intent.putExtra(ConfigurationFile.IntentConstants.WORKSHOP_ADV_OBJ,advData);
        context.startActivity(intent);
    }
}
