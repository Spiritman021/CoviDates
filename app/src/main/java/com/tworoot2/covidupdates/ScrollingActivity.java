package com.tworoot2.covidupdates;

import static android.content.ContentValues.TAG;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.tworoot2.covidupdates.About.AboutMe;
import com.tworoot2.covidupdates.databinding.ActivityScrollingBinding;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Iterator;
import java.util.Locale;


public class ScrollingActivity extends AppCompatActivity {

    private ActivityScrollingBinding binding;
    TextView confirmedC, activeC, deathC, recoveredC, dateD, timeT, dose1, dose2, totalDose;
    Button statewise;
    FirebaseAnalytics firebaseAnalytics;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
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

        firebaseAnalytics = FirebaseAnalytics.getInstance(this);

        FloatingActionButton fab = binding.fab;
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Snackbar.make(view, "Hello my self Vishal Anand", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
                Intent intent = new Intent();
                intent.setClass(ScrollingActivity.this, AboutMe.class);
                startActivity(intent);
            }
        });
        // on create


        confirmedC = (TextView) findViewById(R.id.confirmedC);
        activeC = (TextView) findViewById(R.id.activeC);
        deathC = (TextView) findViewById(R.id.deathC);
        recoveredC = (TextView) findViewById(R.id.recoveredC);
        dateD = (TextView) findViewById(R.id.dateD);
        dose1 = (TextView) findViewById(R.id.dose1);
        dose2 = (TextView) findViewById(R.id.dose2);
        totalDose = (TextView) findViewById(R.id.totalDose);
        statewise = (Button) findViewById(R.id.statewise);
        getStateWiseData();

        CheckForUpdate();

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

//        String url = "https://api.covid19india.org/data.json";
//        String url = "https://data.covid19india.org/data.json";
        String url = "https://data.covid19india.org/v4/min/timeseries.min.json";

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onResponse(JSONObject response) {


                try {

                    // Start from here

                    JSONObject totalCase = null; // Total case in india, data will store here


                    JSONObject tempData = response.getJSONObject("TT").getJSONObject("dates");
                    JSONObject tempData1 = response.getJSONObject("TT").getJSONObject("dates");

                    JSONObject lastElement = tempData.getJSONObject(String.valueOf(tempData.names().get(tempData.length() - 1)));


                    String key1 = String.valueOf(tempData.names().get(tempData.length() - 1));


                    String date1 = key1.substring(8,10)+ "/" + key1.substring(5,7) + "/" + key1.substring(0,4);



                    totalCase = lastElement.getJSONObject("total");

                    String confirmed = totalCase.getString("confirmed");
                    String death = totalCase.getString("deceased");
                    String recovered = totalCase.getString("recovered");
                    String doseOne = totalCase.getString("vaccinated1");
                    String doseTwo = totalCase.getString("vaccinated2");

                    String totalVaccine = String.valueOf(Integer.parseInt(doseOne)
                            + Integer.parseInt(doseTwo));

                    String active = String.valueOf(Integer.parseInt(totalCase.getString("confirmed")) - (
                            Integer.parseInt(totalCase.getString("deceased"))
                                    + Integer.parseInt(totalCase.getString("recovered"))
                    ));


                    // end


                    DecimalFormat formatter = new DecimalFormat("#,###,###");

                    confirmedC.setText(formatter.format(Integer.parseInt(confirmed)));
                    activeC.setText(formatter.format(Integer.parseInt(String.valueOf(active))));
                    deathC.setText(formatter.format(Integer.parseInt(death)));
                    recoveredC.setText(formatter.format(Integer.parseInt(recovered)));
                    dose1.setText(formatter.format(Integer.parseInt(doseOne)));
                    dose2.setText(formatter.format(Integer.parseInt(doseTwo)));
                    totalDose.setText(formatter.format(Integer.parseInt(totalVaccine)));
                    dateD.setText(date1);

                    Handler delay = new Handler();

                    delay.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            if (dateD.getText() != null) {
                                progress.cancel();
                            }
                        }
                    }, 1500);


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
        if (id == R.id.precautions) {

            Dialog dialog = new Dialog(ScrollingActivity.this);
            dialog.setContentView(R.layout.precautions);
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            dialog.setCanceledOnTouchOutside(true);
            dialog.setCancelable(true);
            dialog.show();

            return true;
        }

        if (id == R.id.about){
            Intent intent = new Intent();
            intent.setClass(ScrollingActivity.this, AboutMe.class);
            startActivity(intent);
        }

        if (id == R.id.helpline){
            Dialog dialog = new Dialog(ScrollingActivity.this);
            dialog.setContentView(R.layout.helpline);
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            dialog.setCanceledOnTouchOutside(true);
            dialog.setCancelable(true);
            dialog.findViewById(R.id.hmh).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent callIntent = new Intent(Intent.ACTION_DIAL);
                    callIntent.setData(Uri.parse("tel:" + "1075"));//change the number
                    startActivity(callIntent);
                }
            });
            dialog.findViewById(R.id.ch).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent callIntent = new Intent(Intent.ACTION_DIAL);
                    callIntent.setData(Uri.parse("tel:" + "1098"));//change the number
                    startActivity(callIntent);
                }
            });
            dialog.findViewById(R.id.mhh).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent callIntent = new Intent(Intent.ACTION_DIAL);
                    callIntent.setData(Uri.parse("tel:" + "08046110007"));//change the number
                    startActivity(callIntent);
                }
            });
            dialog.findViewById(R.id.sch).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent callIntent = new Intent(Intent.ACTION_DIAL);
                    callIntent.setData(Uri.parse("tel:" + "14567"));//change the number
                    startActivity(callIntent);
                }
            });
            dialog.findViewById(R.id.acch).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent callIntent = new Intent(Intent.ACTION_DIAL);
                    callIntent.setData(Uri.parse("tel:" + "14443"));//change the number
                    startActivity(callIntent);
                }
            });
            dialog.findViewById(R.id.mwh).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String url = "https://api.whatsapp.com/send/?phone=+919013151515&text=Namaste";
                    Intent intent1 = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                    startActivity(intent1);
                }
            });
            dialog.show();
        }

        if (id == R.id.app){

        }
        return super.onOptionsItemSelected(item);
    }


    private void CheckForUpdate() {
        try {
            String version = this.getPackageManager().getPackageInfo(getPackageName(), 0).versionName;

            firebaseDatabase = FirebaseDatabase.getInstance();
            databaseReference = firebaseDatabase.getReference().child("version").child("app").child("v1");
            databaseReference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    String versionName = (String) dataSnapshot.getValue();

                    if (!versionName.equals(version)) {


                        Dialog dialog = new Dialog(ScrollingActivity.this);
                        dialog.setContentView(R.layout.update_ui);
                        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                        dialog.setCanceledOnTouchOutside(false);
                        dialog.setCancelable(false);
                        dialog.findViewById(R.id.update).setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {

                                FirebaseDatabase database = FirebaseDatabase.getInstance();
                                DatabaseReference myRef = database.getReference().child("version").child("covidateLink");

                                myRef.addValueEventListener(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(DataSnapshot dataSnapshot1) {
                                        String url = (String) dataSnapshot1.getValue();
                                        Intent intent1 = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                                        startActivity(intent1);
                                    }

                                    @Override
                                    public void onCancelled(DatabaseError error1) {
                                        // Failed to read value
                                        Log.w(TAG, "Failed to read value.", error1.toException());
                                    }
                                });


                            }
                        });
                        dialog.findViewById(R.id.cancel).setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                dialog.dismiss();
                                Toast.makeText(ScrollingActivity.this, "Please update your app as soon as possible, you are loosing lots of thing without this update", Toast.LENGTH_LONG).show();
                            }
                        });
                        dialog.show();
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                    Log.w(TAG, "Failed to read value.", databaseError.toException());
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }



}