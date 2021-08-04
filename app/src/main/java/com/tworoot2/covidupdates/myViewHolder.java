package com.tworoot2.covidupdates;

import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

public class myViewHolder extends RecyclerView.ViewHolder {
    TextView stateName;
    TextView confirmedC;
    TextView recoveredC;
    TextView activeC;
    TextView deathC;
    TextView dateD;



    public myViewHolder(View itemView){
        super(itemView);
        stateName = (TextView)itemView.findViewById(R.id.stateName);
        confirmedC = (TextView)itemView.findViewById(R.id.confirmedC);
        recoveredC = (TextView)itemView.findViewById(R.id.recoveredC);
        activeC = (TextView)itemView.findViewById(R.id.activeC);
        deathC = (TextView)itemView.findViewById(R.id.deathC);
        dateD = (TextView)itemView.findViewById(R.id.dateD);

    }


}
