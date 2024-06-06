package com.example.shesecure10;

import android.Manifest;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class HomeActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private static final int PERMISSION_REQUEST_CODE = 1;

    private String phoneNumber,message;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        ImageView settingsBar = findViewById(R.id.imageView15);
        ImageView sosimage=findViewById(R.id.sosimage);

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

        ImageView mapgg =findViewById(R.id.imageView16);
        mapgg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(HomeActivity.this,MapsActivity.class));
            }
        });


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

        sosimage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mAuth = FirebaseAuth.getInstance();

                FirebaseUser currentUser = mAuth.getCurrentUser();
                DocumentReference docref= FirebaseFirestore.getInstance().collection("emergencyemail").document(currentUser.getEmail());
                docref.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if(task.isSuccessful())
                        {
                            DocumentSnapshot doc=task.getResult();
                            if(doc.exists())
                            {
                                 phoneNumber = doc.getData().toString();
                               // Toast.makeText(HomeActivity.this, "Message sent successfully", Toast.LENGTH_SHORT).show();
                                 message="sent";
                                if (ContextCompat.checkSelfPermission(HomeActivity.this,
                                        Manifest.permission.SEND_SMS) != PackageManager.PERMISSION_GRANTED) {
                                    ActivityCompat.requestPermissions(HomeActivity.this,
                                            new String[]{Manifest.permission.SEND_SMS}, PERMISSION_REQUEST_CODE);
                                } else {
                                    sendSMS(phoneNumber, message);
                                    Toast.makeText(HomeActivity.this, "Message sent successfully", Toast.LENGTH_SHORT).show();
                                }
                            }
                        }
                    }
                });
            }
        });

      



    }

    private void sendSMS(String phoneNumber, String message) {
        SmsManager smsManager = SmsManager.getDefault();
        smsManager.sendTextMessage(phoneNumber, null, message, null, null);
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                sendSMS(phoneNumber,message);
                Toast.makeText(HomeActivity.this, "Message sent successfully", Toast.LENGTH_SHORT).show();
            } else {
                // Permission denied, show a message to the user
            }
        }
    }
}