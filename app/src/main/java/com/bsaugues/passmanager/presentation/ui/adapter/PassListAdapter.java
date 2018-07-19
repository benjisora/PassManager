package com.bsaugues.passmanager.presentation.ui.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bsaugues.passmanager.R;
import com.bsaugues.passmanager.presentation.ui.activity.BaseActivity;
import com.bsaugues.passmanager.presentation.ui.view.viewdatawrapper.PassEntityDataWrapper;
import com.bsaugues.passmanager.presentation.ui.view.viewholder.PassViewHolder;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

public class PassListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private BaseActivity activity;
    private List<PassEntityDataWrapper> passList;

    @Inject
    public PassListAdapter(BaseActivity activity) {
        this.activity = activity;
        this.passList = new ArrayList<>();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(activity).inflate(R.layout.recyclerview_pass_item, parent, false);
        return new PassViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ((PassViewHolder) holder).bindPass(passList.get(position));
    }

    @Override
    public int getItemCount() {
        return passList.size();
    }

    public void setPassList(List<PassEntityDataWrapper> passList) {
        this.passList.clear();
        this.passList.addAll(passList);
        notifyDataSetChanged();
    }
}
