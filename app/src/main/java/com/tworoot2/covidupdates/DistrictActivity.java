package com.tworoot2.covidupdates;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NavUtils;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
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
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class DistrictActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    TextView confirmedC, activeC, deathC, recoveredC, dateD, timeT,stateName;
    public String state ;
    ArrayList<DistrictModel> districtModelArrayList;
    RecyclerView recV;
    DistrictAdapter districtAdapter;
    Spinner spinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_district);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        fetchData();

        spinner = (Spinner) findViewById(R.id.spinner);

        spinner.setOnItemSelectedListener(this);
        spinnerAction();

        dateD = (TextView) findViewById(R.id.dateD);
        dateD.setText(getIntent().getStringExtra("date"));
        state = getIntent().getStringExtra("name");
        setTitle(state);
        recV = (RecyclerView) findViewById(R.id.recV);
        recV.setHasFixedSize(true);
        recV.setLayoutManager(new LinearLayoutManager(this));

        districtModelArrayList = new ArrayList<>();
        districtAdapter = new DistrictAdapter(DistrictActivity.this, districtModelArrayList);
        recV.setAdapter(districtAdapter);


    }

    private void fetchData(){
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        String url = "https://api.covid19india.org/v2/state_district_wise.json";
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(
                Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {

                JSONObject latestDistrictData = null;
               // JSONObject olderData = null;
                JSONObject jsonObjectState = null ;
                JSONArray jsonArrayDistrict;

                try {

                    int corona = 0;
                    districtModelArrayList.clear();

                    for (int i = 1; i < response.length(); i++){
                        jsonObjectState = response.getJSONObject(i);

                        if (state.toLowerCase().equals(jsonObjectState.getString("state").toLowerCase())){

                            jsonArrayDistrict = jsonObjectState.getJSONArray("districtData");

                            for (int j = 0; j < jsonArrayDistrict.length();j++){

                                latestDistrictData = jsonArrayDistrict.getJSONObject(j);


                                String districtName = latestDistrictData.getString("district");
                                String confirmedS = latestDistrictData.getString("confirmed");
                                String recoveredS = latestDistrictData.getString("recovered");
                                String deathS = latestDistrictData.getString("deceased");
                                String activeS = latestDistrictData.getString("active");
                               // String date = latestDistrictData.getString("migratedother");
                                String date =  getIntent().getStringExtra("date").substring(0,10);
                                DistrictModel stateC = new DistrictModel(districtName, confirmedS, recoveredS, activeS, deathS, date);

                                districtModelArrayList.add(stateC);


                            }
                            corona = 1;
                        }
                        if (corona == 1){
                            break;
                        }
                    }

                    districtAdapter.notifyDataSetChanged();
                }
                catch (Exception e){
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });
        requestQueue.add(jsonArrayRequest);
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


    public void sortData1(){
        Collections.sort(districtModelArrayList, new Comparator<DistrictModel>() {
            @Override
            public int compare(DistrictModel o1, DistrictModel o2) {
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
        districtAdapter.notifyDataSetChanged();
    }

    public void sortData2(){
        Collections.sort(districtModelArrayList, new Comparator<DistrictModel>() {
            @Override
            public int compare(DistrictModel o1, DistrictModel o2) {
                int c1, c2;

                try {
                    c1 = Integer.parseInt(o1.getActiveC());
                    c2 = Integer.parseInt(o2.getActiveC());
                    return Integer.compare(c2,c1);
                }
                catch (Exception e)    {
                    return o1.getConfirmedC().compareTo(o2.getConfirmedC());
                }
            }
        });
        districtAdapter.notifyDataSetChanged();
    }

    public void sortData3(){
        Collections.sort(districtModelArrayList, new Comparator<DistrictModel>() {
            @Override
            public int compare(DistrictModel o1, DistrictModel o2) {
                int c1, c2;

                try {
                    c1 = Integer.parseInt(o1.getRecoveredC());
                    c2 = Integer.parseInt(o2.getRecoveredC());
                    return Integer.compare(c2,c1);
                }
                catch (Exception e)    {
                    return o1.getConfirmedC().compareTo(o2.getConfirmedC());
                }
            }
        });
        districtAdapter.notifyDataSetChanged();
    }

    public void sortData4(){
        Collections.sort(districtModelArrayList, new Comparator<DistrictModel>() {
            @Override
            public int compare(DistrictModel o1, DistrictModel o2) {
                int c1, c2;

                try {
                    c1 = Integer.parseInt(o1.getDeathC());
                    c2 = Integer.parseInt(o2.getDeathC());
                    return Integer.compare(c2,c1);
                }
                catch (Exception e)    {
                    return o1.getConfirmedC().compareTo(o2.getConfirmedC());
                }
            }
        });
        districtAdapter.notifyDataSetChanged();
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

//
//    @Override
//    public void onBackPressed() {
//        super.onBackPressed();
//    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        String item = adapterView.getItemAtPosition(i).toString();
        Toast.makeText(adapterView.getContext(), "Selected: " + item, Toast.LENGTH_LONG).show();

        if (adapterView.getPositionForView(view) == 0){
            fetchData();
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

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
}