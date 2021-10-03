package com.tworoot2.covidupdates.ViewHolders;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.tworoot2.covidupdates.R;

public class DistrictViewHolder extends RecyclerView.ViewHolder {

    public TextView districtName;
    public TextView confirmedC;
    public TextView recoveredC;
    public TextView activeC;
    public TextView deathC;
    public TextView dateD;

    public DistrictViewHolder(@NonNull View itemView) {
        super(itemView);
        districtName = (TextView)itemView.findViewById(R.id.districtName);
        confirmedC = (TextView)itemView.findViewById(R.id.confirmedC);
        recoveredC = (TextView)itemView.findViewById(R.id.recoveredC);
        activeC = (TextView)itemView.findViewById(R.id.activeC);
        deathC = (TextView)itemView.findViewById(R.id.deathC);
        dateD = (TextView)itemView.findViewById(R.id.dateD);
    }
}
