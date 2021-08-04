package com.tworoot2.covidupdates;

import android.content.Context;
import android.content.Intent;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.toolbox.StringRequest;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class myAdapter extends RecyclerView.Adapter<myViewHolder> {

    Context context;
    private ArrayList<Model> arrayList;
    private myViewHolder holder;
    ProgressBar progressBar = new ProgressBar();

    public myAdapter(Context context, ArrayList<Model> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
    }

    @NonNull
    @Override
    public myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.state,parent,false);


        return new myViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull myViewHolder holder, int position) {

        Model currentItem = arrayList.get(position);
        DecimalFormat formatter = new DecimalFormat("#,###,###");

        String confirmed = String.valueOf(formatter.format(Integer.parseInt(currentItem.getConfirmedC())));
        String recovered = String.valueOf(formatter.format(Integer.parseInt(currentItem.getRecoveredC())));
        String death = String.valueOf(formatter.format(Integer.parseInt(currentItem.getDeathC())));
        String active = String.valueOf(formatter.format(Integer.parseInt(currentItem.getActiveC())));
        String date = currentItem.getDateD();
        String name = currentItem.getStateName();



        holder.stateName.setText(name);
        holder.confirmedC.setText(confirmed);
        holder.recoveredC.setText(recovered);
        holder.deathC.setText(death);
        holder.activeC.setText(active);
        holder.dateD.setText(date);








        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Intent intent = new Intent(context,SingleStateData.class);
                intent.putExtra("name",name);
                intent.putExtra("confirmed",confirmed);
                intent.putExtra("recovered",recovered);
                intent.putExtra("death",death);
                intent.putExtra("active",active);
                intent.putExtra("date",date);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);

            }
        });

        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {

                int position = holder.getAdapterPosition();
                Toast.makeText(context, "Item Clicked : " + (position+1), Toast.LENGTH_SHORT).show();

                return false;
            }
        });

    }

    @Override
    public int getItemCount() {


        return arrayList == null ? 0 : arrayList.size();
    }

}
