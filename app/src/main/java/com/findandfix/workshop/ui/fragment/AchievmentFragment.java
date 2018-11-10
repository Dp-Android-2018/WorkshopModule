package com.findandfix.workshop.ui.fragment;

import android.content.Intent;
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

import com.findandfix.workshop.R;
import com.findandfix.workshop.databinding.FragmentAchievmentLayoutBinding;
import com.findandfix.workshop.model.global.AchievmentObj;
import com.findandfix.workshop.model.global.UserData;
import com.findandfix.workshop.model.response.AchievmentResponse;
import com.findandfix.workshop.network.ApiClient;
import com.findandfix.workshop.network.EndPoints;
import com.findandfix.workshop.ui.activity.AddAchievmentTitleActivity;
import com.findandfix.workshop.ui.adapter.AchievmentAdapter;
import com.findandfix.workshop.utils.ConfigurationFile;
import com.findandfix.workshop.utils.CustomUtils;
import com.findandfix.workshop.utils.NetWorkConnection;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class AchievmentFragment extends Fragment {
    private FragmentAchievmentLayoutBinding binding;
    private UserData userData;
    private List<AchievmentObj> achievmentObjs;
    private boolean loading=false;
    private String next=null;
    private int pageId=0;
    private int pos=0;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding= DataBindingUtil.inflate(inflater, R.layout.fragment_achievment_layout,container,false);
        View view=binding.getRoot();
        if (CustomUtils.getInstance().getAppLanguage(getActivity()).equals("ar")){
            binding.rlParent.setLayoutDirection(View.LAYOUT_DIRECTION_RTL);
        }
        achievmentObjs=new ArrayList<>();
        initializeRecycler();
        getUserData();
        loading=true;
        getAchievments();
        return view;
    }

    public void initializeRecycler(){

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        binding.rvAchievments.setLayoutManager(mLayoutManager);
        binding.rvAchievments.setItemAnimator(new DefaultItemAnimator());
        binding.rvAchievments.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if ( ((LinearLayoutManager) binding.rvAchievments.getLayoutManager()).findLastVisibleItemPosition()==achievmentObjs.size()-1) {
                    if (next!=null && loading ==false) {
                        loading=true;
                        System.out.println("Load More Data Success ");
                        getAchievments();
                    }

                }
            }
        });


        binding.fab.setOnClickListener(v -> {
            Intent i=new Intent(getActivity(), AddAchievmentTitleActivity.class);
            getActivity().startActivity(i);
        });
      /*  binding.rvAchievments.addOnScrollListener(new EndlessRecyclerOnScrollListener() {
            @Override
            public void onLoadMore() {
                System.out.println("Load More Data ");
            }
        });

        ((LinearLayoutManager) binding.rvAchievments.getLayoutManager()).findLastVisibleItemPosition()*/
    }
    public void getUserData(){
        userData= CustomUtils.getInstance().getSaveUserObject(getActivity());
    }


    public void getAchievments() {
        if (NetWorkConnection.isConnectingToInternet(getActivity())) {
            //binding.progressBar.setVisibility(View.VISIBLE);
            CustomUtils.getInstance().showProgressDialog(getActivity());
            System.out.println("Offers Token :" + userData.getToken());
            ApiClient.getClient().create(EndPoints.class).getAchievments(ConfigurationFile.Constants.API_KEY, CustomUtils.getInstance().getAppLanguage(getActivity()), ConfigurationFile.Constants.Content_Type, ConfigurationFile.Constants.Content_Type, "Bearer " + userData.getToken(),pageId)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(achievmentResponseResponse -> {
                        System.out.println("Offers CODE :" + achievmentResponseResponse.code());
                        CustomUtils.getInstance().cancelDialog();
                       // binding.progressBar.setVisibility(View.GONE);
                        loading=false;
                        if (achievmentResponseResponse.code() == ConfigurationFile.Constants.SUCCESS_CODE) {
                            AchievmentResponse response = achievmentResponseResponse.body();
                            pos=achievmentObjs.size();
                            achievmentObjs.addAll(response.getData());
                            next=response.getLinks().getNext();
                            if (next!=null)
                                pageId=Integer.parseInt(next.substring(next.length()-1));

                            System.out.println("Load More Data List Size:"+achievmentObjs.size());
                            System.out.println("Load More Data next:"+next);
                            System.out.println("Load More Data Page Id : "+pageId);
                           setAdapter();
                        }else if (achievmentResponseResponse.code() == ConfigurationFile.Constants.UNAUTHENTICATED_CODE ||
                                achievmentResponseResponse.code() == ConfigurationFile.Constants.UNSUBSCRIBE_CODE)
                            CustomUtils.getInstance().logout(getActivity());
                        else if (achievmentResponseResponse.code() == ConfigurationFile.Constants.CANT_COMPLETE_REQUEST_CODE)
                            Snackbar.make(getActivity().findViewById(R.id.drawer), getActivity().getResources().getString(R.string.cant_complete_your_request), Snackbar.LENGTH_LONG).show();
                    }, throwable -> {
                        CustomUtils.getInstance().cancelDialog();
                      //  binding.progressBar.setVisibility(View.GONE);
                        loading=false;
                        System.out.println("Ex :" + throwable.getMessage());
                    });

        } else {
            Snackbar.make(getActivity().findViewById(R.id.drawer), getActivity().getResources().getString(R.string.internet_connection), Snackbar.LENGTH_LONG).show();
        }
    }


    public void setAdapter(){
      //  achievmentObjs.clear();
        if (achievmentObjs.size()>0) {

            binding.rlNoItems.setVisibility(View.GONE);
            binding.rvAchievments.setVisibility(View.VISIBLE);

            AchievmentAdapter achievmentAdapter = new AchievmentAdapter(achievmentObjs);
            binding.rvAchievments.setAdapter(achievmentAdapter);
            binding.rvAchievments.scrollToPosition(pos);
        }else {
            binding.rlNoItems.setVisibility(View.VISIBLE);
            binding.rvAchievments.setVisibility(View.GONE);

        }

    }
}
