package com.example.shesecure10;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class TutorialActivitynew extends AppCompatActivity {

    private CardView immediateTipsCardView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tutorial_activitynew);
        immediateTipsCardView = findViewById(R.id.immediateTipsCard);

        immediateTipsCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Start ImmediatetipsActivity when the card is clicked
                startActivity(new Intent(TutorialActivitynew.this, Immediatetipss.class));
            }
        });
    }
}