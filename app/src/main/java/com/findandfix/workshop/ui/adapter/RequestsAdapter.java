package com.findandfix.workshop.ui.adapter;

import android.databinding.DataBindingUtil;
import android.databinding.ObservableList;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.findandfix.workshop.R;
import com.findandfix.workshop.databinding.ItemCompletedRequestLayoutBinding;
import com.findandfix.workshop.databinding.ItemCurrentRequestsLayoutBinding;
import com.findandfix.workshop.databinding.ItemInProgressRequestsLayoutBinding;
import com.findandfix.workshop.databinding.ItemMoreCompletedRequestLayoutBinding;
import com.findandfix.workshop.databinding.ItemMoreSentOffersRequestsLayoutBinding;
import com.findandfix.workshop.databinding.ItemSentOffersRequestsLayoutBinding;
import com.findandfix.workshop.model.global.RequestData;
import com.findandfix.workshop.ui.holder.CompletedRequestHolder;
import com.findandfix.workshop.ui.holder.InProgressRequestHolder;
import com.findandfix.workshop.ui.holder.MoreCompletedRequestHolder;
import com.findandfix.workshop.ui.holder.MoreSentOffersRequestHolder;
import com.findandfix.workshop.ui.holder.PendingRequestHolder;
import com.findandfix.workshop.ui.holder.RequestsHolder;

import java.util.List;

/**
 * Created by DELL on 27/03/2018.
 */

public class RequestsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<RequestData> requests;
    private int checker;
    public RequestsAdapter(ObservableList<RequestData>requests, int checker) {
        this.requests=requests;
        this.checker=checker;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (checker==1) {
            ItemCurrentRequestsLayoutBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.item_current_requests_layout, parent, false);
            return new RequestsHolder(binding);
        }else if(checker==2){
            ItemSentOffersRequestsLayoutBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.item_sent_offers_requests_layout,parent,false);
            return new PendingRequestHolder(binding);
        }else if(checker==3){
            ItemInProgressRequestsLayoutBinding binding= DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),R.layout.item_in_progress_requests_layout,parent,false);
            return new InProgressRequestHolder(binding);
        }else if(checker==4){
            ItemCompletedRequestLayoutBinding binding= DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),R.layout.item_completed_request_layout,parent,false);
            return new CompletedRequestHolder(binding);
        }else if(checker==5){
            ItemMoreCompletedRequestLayoutBinding binding= DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),R.layout.item_more_completed_request_layout,parent,false);
            return new MoreCompletedRequestHolder(binding);
        }else if(checker==6){
            ItemMoreSentOffersRequestsLayoutBinding binding= DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),R.layout.item_more_sent_offers_requests_layout,parent,false);
            return new MoreSentOffersRequestHolder(binding);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if(checker==1){
            RequestsHolder requestsHolder=(RequestsHolder)holder;
            requestsHolder.BindRequest(requests.get(position));
        } else if(checker==2){
            PendingRequestHolder pendingRequestHolder=(PendingRequestHolder)holder;
            pendingRequestHolder.BindRequest(requests.get(position));
        } else if(checker==3){
            InProgressRequestHolder inProgressRequestHolder=(InProgressRequestHolder)holder;
            inProgressRequestHolder.BindRequest(requests.get(position));
        }else if(checker==4){
            CompletedRequestHolder completedRequestHolder=(CompletedRequestHolder)holder;
            completedRequestHolder.BindRequest(requests.get(position));
        }else if(checker==5){
            MoreCompletedRequestHolder completedRequestHolder=(MoreCompletedRequestHolder)holder;
            completedRequestHolder.BindRequest(requests.get(position));
        }else if(checker==6){
            MoreSentOffersRequestHolder completedRequestHolder=(MoreSentOffersRequestHolder)holder;
            completedRequestHolder.BindRequest(requests.get(position));
        }

    }

    @Override
    public int getItemCount() {
        System.out.println("Requests Binding:"+requests.size());
        return requests.size();
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }
}
