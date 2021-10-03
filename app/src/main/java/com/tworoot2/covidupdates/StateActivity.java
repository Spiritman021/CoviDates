package com.tworoot2.covidupdates;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
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
import com.tworoot2.covidupdates.Adapters.myAdapter;
import com.tworoot2.covidupdates.Models.Model;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class StateActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {


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

        spinner = (Spinner) findViewById(R.id.spinner);

        spinner.setOnItemSelectedListener(this);
        spinnerAction();


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

        String url = "https://data.covid19india.org/v4/min/data.min.json";


        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onResponse(JSONObject response) {


                try {

                    stateWiseModelArrayList.clear();

                    for (int i = 0; i < response.length(); i++) {

                        if (response.names().get(i).equals("TT")){
                            continue;
                        }


                        String stateCode = String.valueOf(response.names().get(i));

                        String stateName = stateCode;


                        JSONObject districtObject1 = response.getJSONObject(stateCode).getJSONObject("districts");


                        JSONObject everything = response.getJSONObject(stateCode).getJSONObject("total");
                        JSONObject dateTime = response.getJSONObject(stateCode).getJSONObject("meta");

                        String dT = (dateTime.getString("last_updated"));
                        String dateT = dT.substring(8, 10) + "/" + dT.substring(5, 7) + "/" + dT.substring(0, 4);
                        String timeT = dT.substring(11, 19);

                        String vaccinated1 = everything.getString("vaccinated1");
                        String vaccinated2 = everything.getString("vaccinated2");
                        String totalDoses = String.valueOf((Integer.valueOf(vaccinated1)) + (Integer.valueOf(vaccinated2)));

                        String confirmedS = everything.getString("confirmed");
                        String recoveredS = everything.getString("recovered");
                        String deathS = everything.getString("deceased");
                        String activeS = String.valueOf((Integer.valueOf(confirmedS)) -
                                (Integer.valueOf(recoveredS) + Integer.valueOf(deathS)));

                        String date = dateT + " at " + timeT;
                        Model stateC = new Model(stateName, confirmedS, recoveredS, activeS, deathS, date,
                                vaccinated1, vaccinated2, totalDoses, districtObject1);

                        stateWiseModelArrayList.add(stateC);
                        stateWiseAdapter.notifyDataSetChanged();

                        Handler delay = new Handler();

                        delay.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                if (stateWiseAdapter.getItemCount() != 0) {
                                    progress.cancel();
                                }
                            }
                        }, 1500);


                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(StateActivity.this, "Error" + e, Toast.LENGTH_SHORT).show();
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
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }


    public void sortData1() {
        Collections.sort(stateWiseModelArrayList, new Comparator<Model>() {
            @Override
            public int compare(Model o1, Model o2) {

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

        stateWiseAdapter.notifyDataSetChanged();


    }

    public void sortData2() {
        Collections.sort(stateWiseModelArrayList, new Comparator<Model>() {
            @Override
            public int compare(Model o1, Model o2) {

                int c1, c2;

                try {
                    c1 = Integer.parseInt(o1.getActiveC());
                    c2 = Integer.parseInt(o2.getActiveC());
                    return Integer.compare(c2, c1);
                } catch (Exception e) {
                    return o1.getActiveC().compareTo(o2.getActiveC());
                }

            }
        });

        stateWiseAdapter.notifyDataSetChanged();


    }

    public void sortData3() {
        Collections.sort(stateWiseModelArrayList, new Comparator<Model>() {
            @Override
            public int compare(Model o1, Model o2) {

                int c1, c2;

                try {
                    c1 = Integer.parseInt(o1.getRecoveredC());
                    c2 = Integer.parseInt(o2.getRecoveredC());
                    return Integer.compare(c2, c1);
                } catch (Exception e) {
                    return o1.getRecoveredC().compareTo(o2.getRecoveredC());
                }

            }
        });

        stateWiseAdapter.notifyDataSetChanged();


    }

    public void sortData4() {
        Collections.sort(stateWiseModelArrayList, new Comparator<Model>() {
            @Override
            public int compare(Model o1, Model o2) {

                int c1, c2;

                try {
                    c1 = Integer.parseInt(o1.getDeathC());
                    c2 = Integer.parseInt(o2.getDeathC());
                    return Integer.compare(c2, c1);
                } catch (Exception e) {
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

        if (adapterView.getPositionForView(view) == 0) {
            fetchData1();
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


}