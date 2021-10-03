package com.tworoot2.covidupdates.About;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.tworoot2.covidupdates.R;

public class AboutMe extends AppCompatActivity {

    ImageView imageView, linkedin, github, instagram;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_me);

        setTitle("About");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        imageView = (ImageView) findViewById(R.id.imageView);
        linkedin = (ImageView) findViewById(R.id.imageView1);
        github = (ImageView) findViewById(R.id.imageView2);
        instagram = (ImageView) findViewById(R.id.imageView3);

        linkedin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://in.linkedin.com/in/spiritman021"));
                startActivity(intent);
            }
        });

        github.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://github.com/Spiritman021"));
                startActivity(intent);
            }
        });

        instagram.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_VIEW,
                        Uri.parse("https://www.instagram.com/invites/contact/?i=8etcck4tyyxf&utm_content=5vbxq6h"));
                startActivity(intent);
            }
        });

        Glide.with(this)
                .load("https://avatars.githubusercontent.com/u/66544968?s=400&u=85c3369468884fd248d214079e1efdf0c90c4b17&v=4")
                .into(imageView);

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