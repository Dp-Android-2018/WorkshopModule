package com.dp.dell.workshopmodule.view.ui.fragment;

import android.app.Activity;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.dp.dell.workshopmodule.R;
import com.dp.dell.workshopmodule.databinding.FragmentOffersLayoutBinding;
import com.dp.dell.workshopmodule.model.global.AdvData;
import com.dp.dell.workshopmodule.model.global.UserData;
import com.dp.dell.workshopmodule.model.response.OffersResponse;
import com.dp.dell.workshopmodule.network.ApiClient;
import com.dp.dell.workshopmodule.network.EndPoints;
import com.dp.dell.workshopmodule.utils.ConfigurationFile;
import com.dp.dell.workshopmodule.utils.CustomUtils;
import com.dp.dell.workshopmodule.utils.NetWorkConnection;
import com.dp.dell.workshopmodule.view.ui.adapter.OffersAdapter;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Response;

/**
 * Created by DELL on 04/08/2018.
 */

public class OffersFragment extends Fragment {
    FragmentOffersLayoutBinding binding;
    private UserData userData;
    private List<AdvData> offers;
    private OffersAdapter offersAdapter;

    ///////////////////////////////////////////////////////////
    private boolean loading=false;
    private String next=null;
    private int pageId=0;
    private int pos=0;
    ///////////////////////////////////////////////////////////
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding= DataBindingUtil.inflate(inflater, R.layout.fragment_offers_layout,container,false);
        View view=binding.getRoot();
        userData=CustomUtils.getInstance().getSaveUserObject(getActivity());
        offers=new ArrayList<>();
        binding.rvOffers.setLayoutManager(new LinearLayoutManager(getActivity().getApplicationContext(),LinearLayoutManager.VERTICAL,false));
        binding.rvOffers.setItemAnimator(new DefaultItemAnimator());
        binding.rvOffers.addOnScrollListener(new RecyclerView.OnScrollListener() {
                                                 @Override
                                                 public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                                                     super.onScrolled(recyclerView, dx, dy);
                                                     if (((LinearLayoutManager) binding.rvOffers.getLayoutManager()).findLastVisibleItemPosition() == offers.size() - 1) {
                                                         if (next != null && loading == false) {
                                                             loading = true;
                                                             System.out.println("Load More Data Success ");
                                                             getWorkShopAdv();
                                                         }

                                                     }
                                                 }
                                             });
        loading=true;
        getWorkShopAdv();
        return view;
    }

    public void getWorkShopAdv(){

        if (NetWorkConnection.isConnectingToInternet(getActivity())) {
            // binding.progressBar.setVisibility(View.VISIBLE);
            CustomUtils.getInstance().showProgressDialog(getActivity());
            System.out.println("Offers Token :"+userData.getToken());
            ApiClient.getClient().create(EndPoints.class).getWorkShopAdv(ConfigurationFile.Constants.API_KEY, CustomUtils.getInstance().getAppLanguage(getActivity()),ConfigurationFile.Constants.Content_Type,ConfigurationFile.Constants.Content_Type,"Bearer "+userData.getToken(),pageId)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(offersResponseResponse -> {
                        System.out.println("Offers CODE :"+offersResponseResponse.code());
                        CustomUtils.getInstance().cancelDialog();
                        //  binding.progressBar.setVisibility(View.GONE);
                        loading=false;
                        if (offersResponseResponse.code()==ConfigurationFile.Constants.SUCCESS_CODE) {
                            OffersResponse response=offersResponseResponse.body();
                            pos=offers.size();
                            offers.addAll(response.getData());
                            next=response.getLinks().getNext();
                            if (next!=null)
                                pageId=Integer.parseInt(next.substring(next.length()-1));
                            System.out.println("Offers CODE Size :"+offers.size());

                            setAdapter();
                        }else if (offersResponseResponse.code() == ConfigurationFile.Constants.UNSUBSCRIBE_CODE ||
                                offersResponseResponse.code() == ConfigurationFile.Constants.UNAUTHENTICATED_CODE){
                            CustomUtils.getInstance().endSession(getActivity());
                        }
                    }, throwable -> {
                        //  binding.progressBar.setVisibility(View.GONE);
                        CustomUtils.getInstance().cancelDialog();
                        System.out.println("Ex :"+throwable.getMessage());
                    });

        }else {
            Snackbar.make(binding.getRoot(),getResources().getString(R.string.internet_connection
            ),Snackbar.LENGTH_LONG).show();
        }
    }


    public void setAdapter(){

        if (offers.size()>0) {
            offersAdapter = new OffersAdapter(offers);
            binding.rvOffers.setAdapter(offersAdapter);
        }else {
            binding.rlNoAdv.setVisibility(View.VISIBLE);
        }
    }
}
