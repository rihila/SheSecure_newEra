package com.example.shesecure10;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class HomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        CardView virtual_comp=findViewById(R.id.virtualcompanion);
        virtual_comp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    startActivity(new Intent(HomeActivity.this,VirtualActivity.class));
            }
        });

        CardView high_danger =findViewById(R.id.highdanger);
        high_danger.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(HomeActivity.this,MapActivity.class));
            }
        });


        CardView safety_route =findViewById(R.id.routeplanner);
        safety_route.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(HomeActivity.this,RouteActivity.class));
            }
        });

        ImageView mapgg =findViewById(R.id.ShowMap);
        mapgg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(HomeActivity.this,MapsActivity.class));
            }
        });





    }
}