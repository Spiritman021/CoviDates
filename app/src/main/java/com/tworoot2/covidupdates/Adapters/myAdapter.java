package com.tworoot2.covidupdates.Adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.tworoot2.covidupdates.Models.Model;
import com.tworoot2.covidupdates.ProgressBar;
import com.tworoot2.covidupdates.R;
import com.tworoot2.covidupdates.SingleStateData;
import com.tworoot2.covidupdates.ViewHolders.myViewHolder;

import org.json.JSONObject;

import java.text.DecimalFormat;
import java.util.ArrayList;

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
        String dose1 = String.valueOf(formatter.format(Integer.parseInt(currentItem.getDose1())));
        String dose2 = String.valueOf(formatter.format(Integer.parseInt(currentItem.getDose2())));
        String totalDose = String.valueOf(formatter.format(Integer.parseInt(currentItem.getTotalDose())));

        JSONObject dis = currentItem.getDistrictObject();


        if (currentItem.getStateName().equals("AN")) {
            holder.stateName.setText(context.getString(R.string.AN)) ;
        }
        else if (currentItem.getStateName().equals("AP")){
            holder.stateName.setText(context.getString(R.string.AP)) ;
        }
        else if (currentItem.getStateName().equals("AR")){
            holder.stateName.setText(context.getString(R.string.AR)) ;
        }
        else if (currentItem.getStateName().equals("AS")){
            holder.stateName.setText(context.getString(R.string.AS)) ;
        }
        else if (currentItem.getStateName().equals("BR")){
            holder.stateName.setText(context.getString(R.string.BR)) ;
        }
        else if (currentItem.getStateName().equals("CH")){
            holder.stateName.setText(context.getString(R.string.CH)) ;
        }
        else if (currentItem.getStateName().equals("CT")){
            holder.stateName.setText(context.getString(R.string.CT)) ;
        }
        else if (currentItem.getStateName().equals("DL")){
            holder.stateName.setText(context.getString(R.string.DL)) ;
        }
        else if (currentItem.getStateName().equals("DN")){
            holder.stateName.setText(context.getString(R.string.DN)) ;
        }
        else if (currentItem.getStateName().equals("GA")){
            holder.stateName.setText(context.getString(R.string.GA)) ;
        }
        else if (currentItem.getStateName().equals("GJ")){
            holder.stateName.setText(context.getString(R.string.GJ)) ;
        }
        else if (currentItem.getStateName().equals("HP")){
            holder.stateName.setText(context.getString(R.string.HP)) ;
        }
        else if (currentItem.getStateName().equals("HR")){
            holder.stateName.setText(context.getString(R.string.HR)) ;
        }
        else if (currentItem.getStateName().equals("JH")){
            holder.stateName.setText(context.getString(R.string.JH)) ;
        }
        else if (currentItem.getStateName().equals("JK")){
            holder.stateName.setText(context.getString(R.string.JK)) ;
        }
        else if (currentItem.getStateName().equals("KA")){
            holder.stateName.setText(context.getString(R.string.KA)) ;
        }
        else if (currentItem.getStateName().equals("KL")){
            holder.stateName.setText(context.getString(R.string.KL)) ;
        }
        else if (currentItem.getStateName().equals("LA")){
            holder.stateName.setText(context.getString(R.string.LA)) ;
        }
        else if (currentItem.getStateName().equals("LD")){
            holder.stateName.setText(context.getString(R.string.LD)) ;
        }
        else if (currentItem.getStateName().equals("MH")){
            holder.stateName.setText(context.getString(R.string.MH)) ;
        }
        else if (currentItem.getStateName().equals("ML")){
            holder.stateName.setText(context.getString(R.string.ML)) ;
        }
        else if (currentItem.getStateName().equals("MN")){
            holder.stateName.setText(context.getString(R.string.MN)) ;
        }
        else if (currentItem.getStateName().equals("MP")){
            holder.stateName.setText(context.getString(R.string.MP)) ;
        }
        else if (currentItem.getStateName().equals("MZ")){
            holder.stateName.setText(context.getString(R.string.MZ)) ;
        }
        else if (currentItem.getStateName().equals("NL")){
            holder.stateName.setText(context.getString(R.string.NL)) ;
        }
        else if (currentItem.getStateName().equals("OR")){
            holder.stateName.setText(context.getString(R.string.OR)) ;
        }
        else if (currentItem.getStateName().equals("PB")){
            holder.stateName.setText(context.getString(R.string.PB)) ;
        }
        else if (currentItem.getStateName().equals("PY")){
            holder.stateName.setText(context.getString(R.string.PY)) ;
        }
        else if (currentItem.getStateName().equals("RJ")){
            holder.stateName.setText(context.getString(R.string.RJ)) ;
        }
        else if (currentItem.getStateName().equals("SK")){
            holder.stateName.setText(context.getString(R.string.SK)) ;
        }
        else if (currentItem.getStateName().equals("TG")){
            holder.stateName.setText(context.getString(R.string.TG)) ;
        }
        else if (currentItem.getStateName().equals("TN")){
            holder.stateName.setText(context.getString(R.string.TN)) ;
        }
        else if (currentItem.getStateName().equals("TR")){
            holder.stateName.setText(context.getString(R.string.TR)) ;
        }
        else if (currentItem.getStateName().equals("TT")){
            holder.stateName.setText(context.getString(R.string.TT)) ;
        }
        else if (currentItem.getStateName().equals("UP")){
            holder.stateName.setText(context.getString(R.string.UP)) ;
        }
        else if (currentItem.getStateName().equals("UT")){
            holder.stateName.setText(context.getString(R.string.UT)) ;
        }
        else if (currentItem.getStateName().equals("WB")){
            holder.stateName.setText(context.getString(R.string.WB)) ;
        }
        else{
            holder.stateName.setText("Unknown State") ;
        }

        holder.confirmedC.setText(confirmed);
        holder.recoveredC.setText(recovered);
        holder.deathC.setText(death);
        holder.activeC.setText(active);
        holder.dateD.setText(date);
        holder.dose1.setText(dose1);
        holder.dose2.setText(dose2);
        holder.totalDose.setText(totalDose);








        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Intent intent = new Intent(context, SingleStateData.class);
                intent.putExtra("name",holder.stateName.getText());
                intent.putExtra("confirmed",confirmed);
                intent.putExtra("recovered",recovered);
                intent.putExtra("death",death);
                intent.putExtra("active",active);
                intent.putExtra("date",date);
                intent.putExtra("dose1",dose1);
                intent.putExtra("dose2",dose2);
                intent.putExtra("totalDose",totalDose);
                intent.putExtra("district",dis.toString());
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

    public void stateFullForm(Model currentItem){

    }

}
