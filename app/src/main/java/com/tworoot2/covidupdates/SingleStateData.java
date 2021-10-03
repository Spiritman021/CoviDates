package com.tworoot2.covidupdates;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.material.snackbar.Snackbar;

public class SingleStateData extends AppCompatActivity {

    TextView confirmedC, activeC, deathC, recoveredC, dateD, timeT, stateName, dose1, dose2, totalDose;
    Button getDistrict;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_state_data);
        confirmedC = (TextView) findViewById(R.id.confirmedC);
        activeC = (TextView) findViewById(R.id.activeC);
        deathC = (TextView) findViewById(R.id.deathC);
        recoveredC = (TextView) findViewById(R.id.recoveredC);
        dateD = (TextView) findViewById(R.id.dateD);
        dose1 = (TextView) findViewById(R.id.dose1);
        dose2 = (TextView) findViewById(R.id.dose2);
        totalDose = (TextView) findViewById(R.id.totalDose);
        stateName = (TextView) findViewById(R.id.stateName);
        getDistrict = (Button) findViewById(R.id.getDistrict);
        getDistrict.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent intent = new Intent(SingleStateData.this,DistrictActivity.class);
//                intent.putExtra("name",getIntent().getStringExtra("name"));
//                intent.putExtra("date",dateD.getText().toString());
//                intent.putExtra("district",getIntent().getStringExtra("district"));
//                startActivity(intent);

                Snackbar.make(v, "Not available yet, please wait for the next update.", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);


        confirmedC.setText(getIntent().getStringExtra("confirmed"));
        totalDose.setText(getIntent().getStringExtra("totalDose"));
        dose1.setText(getIntent().getStringExtra("dose1"));
        dose2.setText(getIntent().getStringExtra("dose2"));
        activeC.setText(getIntent().getStringExtra("active"));
        deathC.setText(getIntent().getStringExtra("death"));
        recoveredC.setText(getIntent().getStringExtra("recovered"));
        dateD.setText(getIntent().getStringExtra("date"));
        stateName.setText(getIntent().getStringExtra("name"));
        stateName.setEllipsize(TextUtils.TruncateAt.MARQUEE);
        stateName.setMarqueeRepeatLimit(-1);
        stateName.setSingleLine(true);
        stateName.setSelected(true);

        setTitle(getIntent().getStringExtra("name"));


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
}