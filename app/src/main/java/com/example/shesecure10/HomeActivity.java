package com.example.shesecure10;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class HomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        ImageView settingsBar = findViewById(R.id.imageView15);

        // Set OnClickListener for the settings bar ImageView
        settingsBar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Define an intent to open the SettingsActivity
                Intent intent = new Intent(HomeActivity.this, SettingsActivity.class);

                // Start the SettingsActivity
                startActivity(intent);
            }
        });

        CardView virtual_comp=findViewById(R.id.virtualcompanion);
        virtual_comp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    startActivity(new Intent(HomeActivity.this,VirtualActivity.class));
            }
        });

        // Set OnClickListener for the self-defense tutorial CardView
        CardView self_defense = findViewById(R.id.selfdefense);
        self_defense.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(HomeActivity.this, TutorialActivitynew.class));
            }
        });



    }
}