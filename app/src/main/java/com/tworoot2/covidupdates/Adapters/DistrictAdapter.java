package com.tworoot2.covidupdates.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.tworoot2.covidupdates.Models.DistrictModel;
import com.tworoot2.covidupdates.ViewHolders.DistrictViewHolder;
import com.tworoot2.covidupdates.R;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class DistrictAdapter extends RecyclerView.Adapter<DistrictViewHolder> {

    Context context;
    private ArrayList<DistrictModel> arrayList;
    private DistrictViewHolder holder;

    public DistrictAdapter(Context context, ArrayList<DistrictModel> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
    }

    @NonNull
    @Override
    public DistrictViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.district,parent,false);

        return new DistrictViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DistrictViewHolder holder, int position) {
        DistrictModel currentItem = arrayList.get(position);
        DecimalFormat formatter = new DecimalFormat("#,###,###");

        String confirmed = String.valueOf(formatter.format(Integer.parseInt(currentItem.getConfirmedC())));
        String recovered = String.valueOf(formatter.format(Integer.parseInt(currentItem.getRecoveredC())));
        String death = String.valueOf(formatter.format(Integer.parseInt(currentItem.getDeathC())));
        String active = String.valueOf(formatter.format(Integer.parseInt(currentItem.getActiveC())));
        String date = currentItem.getDateD();
        String name = currentItem.getDistrictName();



        holder.districtName.setText(name);
        holder.confirmedC.setText(confirmed);
        holder.recoveredC.setText(recovered);
        holder.deathC.setText(death);
        holder.activeC.setText(active);
        holder.dateD.setText(date);
    }

    @Override
    public int getItemCount() {
        return arrayList == null ? 0 : arrayList.size();
    }
}
