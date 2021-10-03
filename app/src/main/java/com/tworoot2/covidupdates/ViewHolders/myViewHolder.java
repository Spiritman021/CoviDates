package com.tworoot2.covidupdates.ViewHolders;

import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.tworoot2.covidupdates.R;

public class myViewHolder extends RecyclerView.ViewHolder {
    public TextView stateName;
    public TextView confirmedC;
    public TextView recoveredC;
    public TextView activeC;
    public TextView deathC;
    public TextView dateD;
    public TextView dose1;
    public TextView dose2;
    public TextView totalDose;



    public myViewHolder(View itemView){
        super(itemView);
        totalDose = (TextView)itemView.findViewById(R.id.totalDose);
        stateName = (TextView)itemView.findViewById(R.id.stateName);
        confirmedC = (TextView)itemView.findViewById(R.id.confirmedC);
        recoveredC = (TextView)itemView.findViewById(R.id.recoveredC);
        activeC = (TextView)itemView.findViewById(R.id.activeC);
        deathC = (TextView)itemView.findViewById(R.id.deathC);
        dateD = (TextView)itemView.findViewById(R.id.dateD);
        dose1 = (TextView)itemView.findViewById(R.id.dose1);
        dose2 = (TextView)itemView.findViewById(R.id.dose2);

    }


}
