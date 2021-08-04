package com.tworoot2.covidupdates;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.tworoot2.covidupdates.databinding.ActivityScrollingBinding;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;


public class ScrollingActivity extends AppCompatActivity {

    private ActivityScrollingBinding binding;
    TextView confirmedC, activeC, deathC, recoveredC, dateD, timeT;
    Button statewise;

    ProgressDialog progress;
    Handler delay;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityScrollingBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Toolbar toolbar = binding.toolbar;
        setSupportActionBar(toolbar);
        CollapsingToolbarLayout toolBarLayout = binding.toolbarLayout;
        toolBarLayout.setTitle(getTitle());

        FloatingActionButton fab = binding.fab;
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Hello my self Vishal Anand", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();

            }
        });
        // on create


        confirmedC = (TextView) findViewById(R.id.confirmedC);
        activeC = (TextView) findViewById(R.id.activeC);
        deathC = (TextView) findViewById(R.id.deathC);
        recoveredC = (TextView) findViewById(R.id.recoveredC);
        dateD = (TextView) findViewById(R.id.dateD);
        statewise = (Button) findViewById(R.id.statewise);
        getStateWiseData();


        //progress bar
        progress = new ProgressDialog(this);
        progress.show();
        progress.setContentView(R.layout.progress_dialog);
        progress.setCanceledOnTouchOutside(false);
        progress.getWindow().setBackgroundDrawableResource(android.R.color.transparent);


        //  ShowDialog(this);


        fetchData1();







        // on create end
    }

    private void getStateWiseData() {
        statewise.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(ScrollingActivity.this, StateActivity.class);
                startActivity(intent);
            }
        });
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


                    //this will show previous day data
                    int active = Integer.parseInt(olderD.getString("totalconfirmed")) - (
                            Integer.parseInt(olderD.getString("totaldeceased"))
                                    + Integer.parseInt(olderD.getString("totalrecovered"))

                    );


                    String death = olderD.getString("totaldeceased");
                    String recovered = olderD.getString("totalrecovered");
                    String confirmed = olderD.getString("totalconfirmed");
                    String date = latestD.getString("lastupdatedtime");

                    DecimalFormat formatter = new DecimalFormat("#,###,###");

                    confirmedC.setText(formatter.format(Integer.parseInt(confirmed)));
                    activeC.setText(formatter.format(Integer.parseInt(String.valueOf(active))));
                    deathC.setText(formatter.format(Integer.parseInt(death)));
                    recoveredC.setText(formatter.format(Integer.parseInt(recovered)));
                    dateD.setText(date.substring(0, 10));

                    Handler delay = new Handler();

                    delay.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            if (dateD.getText() != null){
                                progress.cancel();
                            }
                        }
                    },1500);


                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(ScrollingActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });


        requestQueue.add(jsonObjectRequest);

    }
//    public void ShowDialog(Context context) {
//        //setting up progress dialog
//        progressDialog = new ProgressDialog(context);
//        progressDialog.show();
//        progressDialog.setContentView(R.layout.progress_dialog);
//        progressDialog.setCanceledOnTouchOutside(false);
//        progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
//    }
//    public void DismissDialog() {
//        progressDialog.dismiss();
//    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_scrolling, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


}