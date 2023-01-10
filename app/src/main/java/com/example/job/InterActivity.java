package com.example.job;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class InterActivity extends AppCompatActivity {

    private Button buttonnouveauinter;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inter);


        this.buttonnouveauinter = findViewById(R.id.buttonnouveaubi);

        buttonnouveauinter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)  {
                Intent otherActivity = new Intent(getApplicationContext(), NouveauBIActivity.class);
                startActivity(otherActivity);
                finish();

            }
        });
    }
}