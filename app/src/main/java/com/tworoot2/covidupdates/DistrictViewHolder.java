package com.tworoot2.covidupdates;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class DistrictViewHolder extends RecyclerView.ViewHolder {

    TextView districtName;
    TextView confirmedC;
    TextView recoveredC;
    TextView activeC;
    TextView deathC;
    TextView dateD;

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
