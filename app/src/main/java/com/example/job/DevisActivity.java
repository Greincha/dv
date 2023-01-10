package com.example.job;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class DevisActivity extends AppCompatActivity {


    private Button buttonnouveaudevis;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_devis);

        this.buttonnouveaudevis = findViewById(R.id.buttonnouveaudevis);

        buttonnouveaudevis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)  {
                Intent otherActivity = new Intent(getApplicationContext(), NouveauDevisActivity.class);
                startActivity(otherActivity);
                finish();

            }
        });
    }
}