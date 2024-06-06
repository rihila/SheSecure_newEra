package com.example.shesecure10;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

public class SelfDefenseTipsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_self_defense_tips);

        // CardView
        CardView cardView = findViewById(R.id.cardView);
        cardView.setOnClickListener(v -> openUrl("https://www.healthline.com/health/womens-health/self-defense-tips-escape"));

        // CardView 1
        CardView cardView1 = findViewById(R.id.cardView1);
        cardView1.setOnClickListener(v -> openUrl("https://brightside.me/articles/7-self-defense-techniques-for-women-recommended-by-a-professional-441310/"));

        // CardView 2
        CardView cardView2 = findViewById(R.id.cardView2);
        cardView2.setOnClickListener(v -> openUrl("https://www.girlswhofight.co/post/ten-self-defense-strategies-women-need-to-know"));

        // CardView 3
        CardView cardView3 = findViewById(R.id.cardView3);
        cardView3.setOnClickListener(v -> openUrl("https://byshree.com/blogs/news/fight-back-10-self-defense-techniques-every-woman-should-know"));

        // CardView 4
        CardView cardView4 = findViewById(R.id.cardView4);
        cardView4.setOnClickListener(v -> openUrl("https://journeywoman.com/solo-travel-advice/expert-self-defense-tips-for-women-travellers/"));

        // CardView 5
        CardView cardView5 = findViewById(R.id.cardView5);
        cardView5.setOnClickListener(v -> openUrl("https://eastwestmartialarts.org/self-defence-for-women-strategies/"));

        // CardView 6
        CardView cardView6 = findViewById(R.id.cardView6);
        cardView6.setOnClickListener(v -> openUrl("https://online.maryville.edu/online-masters-degrees/health-administration/self-defense-tips/"));

        // CardView 7
        CardView cardView7 = findViewById(R.id.cardView7);
        cardView7.setOnClickListener(v -> openUrl("https://sheroes.com/articles/self-defence-for-women-15-effective-techniques/NzEzMw=="));

        // CardView 8
        CardView cardView8 = findViewById(R.id.cardView8);
        cardView8.setOnClickListener(v -> openUrl("https://www.rediff.com/getahead/slide-show/slide-show-1-specials-10-self-defense-tips-every-woman-should-know/20131021.htm"));

        // CardView 9
        CardView cardView9 = findViewById(R.id.cardView9);
        cardView9.setOnClickListener(v -> openUrl("https://pubmed.ncbi.nlm.nih.gov/1583240/"));
    }

    private void openUrl(String url) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse(url));
        startActivity(intent);
    }
}
