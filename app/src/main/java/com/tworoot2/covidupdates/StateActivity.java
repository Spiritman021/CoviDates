package com.tworoot2.covidupdates;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class StateActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

   // TextView confirmedC, activeC, deathC, recoveredC, dateD, timeT;
    TextView dateD;
    Spinner spinner;
    RecyclerView recV;
    RequestQueue requestQueue;
    private ArrayList<Model> stateWiseModelArrayList;
    private myAdapter stateWiseAdapter;

    ProgressDialog progress;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_state);
        // on create

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

//        confirmedC = (TextView) findViewById(R.id.confirmedC);
//        activeC = (TextView) findViewById(R.id.activeC);
//        deathC = (TextView) findViewById(R.id.deathC);
//        recoveredC = (TextView) findViewById(R.id.recoveredC);
        dateD = (TextView) findViewById(R.id.dateD);
//        timeT = (TextView) findViewById(R.id.timeT);
        spinner = (Spinner) findViewById(R.id.spinner);

        spinner.setOnItemSelectedListener(this);
        spinnerAction();

//        ProgressBar progressBar = new ProgressBar();
//        progressBar.ShowDialog(StateActivity.this);

        //  ShowDialog(this);



        fetchData1();


        recV = (RecyclerView) findViewById(R.id.recV);
        recV.setHasFixedSize(true);
        recV.setLayoutManager(new LinearLayoutManager(this));


        stateWiseModelArrayList = new ArrayList<>();
        stateWiseAdapter = new myAdapter(StateActivity.this, stateWiseModelArrayList);
        recV.setAdapter(stateWiseAdapter);

        //progress bar
        progress = new ProgressDialog(this);
        progress.show();
        progress.setContentView(R.layout.progress_dialog);
        progress.setCanceledOnTouchOutside(false);
        progress.getWindow().setBackgroundDrawableResource(android.R.color.transparent);



        // on create end
    }

    private void spinnerAction() {
        // Spinner click listener


        // Spinner Drop down elements
        List<String> categories = new ArrayList<String>();
        categories.add("Sort by State Name");
        categories.add("Sort by Active Cases");
        categories.add("Sort by Confirmed Cases");
        categories.add("Sort by Recovered Cases");
        categories.add("Sort by Deaths");

        // Creating adapter for spinner
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, categories);

        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        spinner.setAdapter(dataAdapter);
    }

    private void fetchData1() {

        RequestQueue requestQueue = Volley.newRequestQueue(this);

        String url = "https://api.covid19india.org/data.json";
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onResponse(JSONObject response) {

                JSONArray latestData = null; // json array hai ye, jisme data store hoga // this will  show latest data
                JSONArray olderData = null; // this will  show previous day data

                try {
                    latestData = response.getJSONArray("statewise"); //  yaha pe statewise wala pura json array aake store ho jayega
                    JSONObject latestD = latestData.getJSONObject(0);

                    olderData = response.getJSONArray("cases_time_series");
                    JSONObject olderD = olderData.getJSONObject((olderData.length() - 1));


                    //JSONArray jsonArray = response.getJSONArray("statewise");
                    stateWiseModelArrayList.clear();

                    for (int i = 1; i < latestData.length(); i++) {

                        
                        DecimalFormat formatter = new DecimalFormat("#,###,###");


                        JSONObject stateWise = latestData.getJSONObject(i);

                        if (i==31){
                            continue;
                        }



                        String stateName = stateWise.getString("state");

                        String confirmedS = stateWise.getString("confirmed");
                        String recoveredS = stateWise.getString("recovered");
                        String deathS = stateWise.getString("deaths");
                        String activeS = stateWise.getString("active");



//                        String confirmedS = String.valueOf(formatter.format(Integer.parseInt(stateWise.getString("confirmed"))));
//                        String recoveredS = String.valueOf(formatter.format(Integer.parseInt(stateWise.getString("recovered"))));
//                        String deathS = String.valueOf(formatter.format(Integer.parseInt(stateWise.getString("deaths"))));
//                        String activeS = String.valueOf(formatter.format(Integer.parseInt(stateWise.getString("active"))));


                        String date = stateWise.getString("lastupdatedtime");
                        Model stateC = new Model(stateName, confirmedS, recoveredS, activeS, deathS, date);

                        stateWiseModelArrayList.add(stateC);
                        stateWiseAdapter.notifyDataSetChanged();

                        Handler delay = new Handler();

                        delay.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                if (stateWiseAdapter.getItemCount() != 0){
                                    progress.cancel();
                                }
                            }
                        },1500);


                    }


                    //this will show previous day data
//                    int active = Integer.parseInt(olderD.getString("totalconfirmed")) - (
//                            Integer.parseInt(olderD.getString("totaldeceased"))
//                                    + Integer.parseInt(olderD.getString("totalrecovered"))
//
//                    );
//
//
//                    String death = olderD.getString("totaldeceased");
//                    String recovered = olderD.getString("totalrecovered");
//                    String confirmed = olderD.getString("totalconfirmed");
                    String date = latestD.getString("lastupdatedtime");
//
//                    DecimalFormat formatter = new DecimalFormat("#,###,###");
//
//                    confirmedC.setText(formatter.format(Integer.parseInt(confirmed)));
//                    activeC.setText(formatter.format(Integer.parseInt(String.valueOf(active))));
//                    deathC.setText(formatter.format(Integer.parseInt(death)));
//                    recoveredC.setText(formatter.format(Integer.parseInt(recovered)));
                    dateD.setText(date.substring(0, 10));



                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(StateActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });


        requestQueue.add(jsonObjectRequest);

    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_scrolling, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }


    public void sortData1(){
        Collections.sort(stateWiseModelArrayList, new Comparator<Model>() {
            @Override
            public int compare(Model o1, Model o2) {

                int c1, c2;

                try {
                    c1 = Integer.parseInt(o1.getConfirmedC());
                    c2 = Integer.parseInt(o2.getConfirmedC());
                    return Integer.compare(c2,c1);
                }
                catch (Exception e)    {
                    return o1.getConfirmedC().compareTo(o2.getConfirmedC());
                }

            }
        });

        stateWiseAdapter.notifyDataSetChanged();


    }

    public void sortData2(){
        Collections.sort(stateWiseModelArrayList, new Comparator<Model>() {
            @Override
            public int compare(Model o1, Model o2) {

                int c1, c2;

                try {
                    c1 = Integer.parseInt(o1.getActiveC());
                    c2 = Integer.parseInt(o2.getActiveC());
                    return Integer.compare(c2,c1);
                }
                catch (Exception e)    {
                    return o1.getActiveC().compareTo(o2.getActiveC());
                }

            }
        });

        stateWiseAdapter.notifyDataSetChanged();


    }

    public void sortData3(){
        Collections.sort(stateWiseModelArrayList, new Comparator<Model>() {
            @Override
            public int compare(Model o1, Model o2) {

                int c1, c2;

                try {
                    c1 = Integer.parseInt(o1.getRecoveredC());
                    c2 = Integer.parseInt(o2.getRecoveredC());
                    return Integer.compare(c2,c1);
                }
                catch (Exception e)    {
                    return o1.getRecoveredC().compareTo(o2.getRecoveredC());
                }

            }
        });

        stateWiseAdapter.notifyDataSetChanged();


    }

    public void sortData4(){
        Collections.sort(stateWiseModelArrayList, new Comparator<Model>() {
            @Override
            public int compare(Model o1, Model o2) {

                int c1, c2;

                try {
                    c1 = Integer.parseInt(o1.getDeathC());
                    c2 = Integer.parseInt(o2.getDeathC());
                    return Integer.compare(c2,c1);
                }
                catch (Exception e)    {
                    return o1.getDeathC().compareTo(o2.getDeathC());
                }

            }
        });

        stateWiseAdapter.notifyDataSetChanged();


    }



    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        String item = adapterView.getItemAtPosition(i).toString();
        Toast.makeText(adapterView.getContext(), "Selected: " + item, Toast.LENGTH_LONG).show();

        if (adapterView.getPositionForView(view) == 0){
            fetchData1();
        }
        if (adapterView.getPositionForView(view) == 1){
            sortData2();
        }
        if (adapterView.getPositionForView(view) == 2){
            sortData1();
        }
        if (adapterView.getPositionForView(view) == 3){
            sortData3();
        }
        if (adapterView.getPositionForView(view) == 4){
            sortData4();
        }

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }


}