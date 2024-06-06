package com.example.shesecure10;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

public class TutorialActivitynew extends AppCompatActivity {

    private CardView immediateTipsCardView;
    private CardView videosCardView;
    private CardView articlesCardView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tutorial);

        immediateTipsCardView = findViewById(R.id.immediateTipsCard);
        videosCardView = findViewById(R.id.videosCard);
        articlesCardView = findViewById(R.id.articlesCard);

        immediateTipsCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Start ImmediatetipsActivity when the card is clicked
                startActivity(new Intent(TutorialActivitynew.this, Immediatetipss.class));
            }
        });

        videosCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Start VideosActivity when the card is clicked
                startActivity(new Intent(TutorialActivitynew.this, VideosActivity.class));
            }
        });

        articlesCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Start SelfDefenseTipsActivity when the card is clicked
                startActivity(new Intent(TutorialActivitynew.this, SelfDefenseTipsActivity.class));
            }
        });
    }
}
