package com.findandfix.workshop.viewmodel;

import android.content.Context;
import android.content.Intent;
import android.databinding.BaseObservable;
import android.view.View;

import com.findandfix.workshop.model.global.AdvData;
import com.findandfix.workshop.ui.activity.OffersDetailActivity;
import com.findandfix.workshop.utils.ConfigurationFile;


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
