package com.tworoot2.covidupdates;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
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
import com.android.volley.toolbox.Volley;
import com.tworoot2.covidupdates.Adapters.DistrictAdapter;
import com.tworoot2.covidupdates.Models.DistrictModel;
import com.tworoot2.covidupdates.Models.Model;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class DistrictActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    TextView confirmedC, activeC, deathC, recoveredC, dateD, timeT, stateName;
    public String state;
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


        spinner = (Spinner) findViewById(R.id.spinner);

        spinner.setOnItemSelectedListener(this);
        spinnerAction();


        dateD = (TextView) findViewById(R.id.dateD);
        dateD.setText(getIntent().getStringExtra("date"));
        state = getIntent().getStringExtra("name");
        setTitle(state);

        districtModelArrayList = new ArrayList<>();

        recV = (RecyclerView) findViewById(R.id.recV);
        recV.setHasFixedSize(true);
        recV.setLayoutManager(new LinearLayoutManager(this));

        districtAdapter = new DistrictAdapter(DistrictActivity.this, districtModelArrayList);

        fetchData();

        recV.setAdapter(districtAdapter);


    }

    private void fetchData() {


        try {
            String dist = getIntent().getStringExtra("district");

            JSONObject jsonObject = null;
            jsonObject = new JSONObject(dist);


            for (int i = 0; i < jsonObject.length(); i++) {

                String districtCode = String.valueOf(jsonObject.names().get(i));

                String districtName = districtCode;

                JSONObject districtObject1 = jsonObject.getJSONObject(districtCode).getJSONObject("total");

//                String dT = (dateTime.getString("last_updated"));
//                String dateT = dT.substring(8, 10) + "/" + dT.substring(5, 7) + "/" + dT.substring(0, 4);
//                String timeT = dT.substring(11, 19);

                String vaccinated1 = districtObject1.getString("vaccinated1");
                String vaccinated2 = districtObject1.getString("vaccinated2");
                String totalDoses = String.valueOf((Integer.valueOf(vaccinated1)) + (Integer.valueOf(vaccinated2)));

                String confirmedS = districtObject1.getString("confirmed");
                String recoveredS = districtObject1.getString("recovered");
                String deathS = districtObject1.getString("deceased");
                String activeS = String.valueOf((Integer.valueOf(confirmedS)) -
                        (Integer.valueOf(recoveredS) + Integer.valueOf(deathS)));

//                String date = dateT + " at " + timeT;
                String date = " at ";


                DistrictModel stateC = new DistrictModel(districtName, confirmedS, recoveredS, activeS, deathS, date);

                districtModelArrayList.add(stateC);

                districtAdapter = new DistrictAdapter(DistrictActivity.this, districtModelArrayList);

                districtAdapter.notifyDataSetChanged();


            }


        } catch (JSONException e) {
            e.printStackTrace();
            Toast.makeText(this, "Error" + e, Toast.LENGTH_SHORT).show();
        }

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


    public void sortData1() {
        Collections.sort(districtModelArrayList, new Comparator<DistrictModel>() {
            @Override
            public int compare(DistrictModel o1, DistrictModel o2) {
                int c1, c2;

                try {
                    c1 = Integer.parseInt(o1.getConfirmedC());
                    c2 = Integer.parseInt(o2.getConfirmedC());
                    return Integer.compare(c2, c1);
                } catch (Exception e) {
                    return o1.getConfirmedC().compareTo(o2.getConfirmedC());
                }
            }
        });
        districtAdapter.notifyDataSetChanged();
    }

    public void sortData2() {
        Collections.sort(districtModelArrayList, new Comparator<DistrictModel>() {
            @Override
            public int compare(DistrictModel o1, DistrictModel o2) {
                int c1, c2;

                try {
                    c1 = Integer.parseInt(o1.getActiveC());
                    c2 = Integer.parseInt(o2.getActiveC());
                    return Integer.compare(c2, c1);
                } catch (Exception e) {
                    return o1.getConfirmedC().compareTo(o2.getConfirmedC());
                }
            }
        });
        districtAdapter.notifyDataSetChanged();
    }

    public void sortData3() {
        Collections.sort(districtModelArrayList, new Comparator<DistrictModel>() {
            @Override
            public int compare(DistrictModel o1, DistrictModel o2) {
                int c1, c2;

                try {
                    c1 = Integer.parseInt(o1.getRecoveredC());
                    c2 = Integer.parseInt(o2.getRecoveredC());
                    return Integer.compare(c2, c1);
                } catch (Exception e) {
                    return o1.getConfirmedC().compareTo(o2.getConfirmedC());
                }
            }
        });
        districtAdapter.notifyDataSetChanged();
    }

    public void sortData4() {
        Collections.sort(districtModelArrayList, new Comparator<DistrictModel>() {
            @Override
            public int compare(DistrictModel o1, DistrictModel o2) {
                int c1, c2;

                try {
                    c1 = Integer.parseInt(o1.getDeathC());
                    c2 = Integer.parseInt(o2.getDeathC());
                    return Integer.compare(c2, c1);
                } catch (Exception e) {
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

        if (adapterView.getPositionForView(view) == 0) {
            fetchData();
        }
        if (adapterView.getPositionForView(view) == 1) {
            sortData2();
        }
        if (adapterView.getPositionForView(view) == 2) {
            sortData1();
        }
        if (adapterView.getPositionForView(view) == 3) {
            sortData3();
        }
        if (adapterView.getPositionForView(view) == 4) {
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