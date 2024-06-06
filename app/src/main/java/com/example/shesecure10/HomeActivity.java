package com.example.shesecure10;

import android.Manifest;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.shesecure10.databinding.ActivityMapsBinding;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class HomeActivity extends AppCompatActivity {

    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1;
    private GoogleMap mMap;
    private ActivityMapsBinding binding;
    private FusedLocationProviderClient fusedLocationClient;

    LatLng currentLocation;


    private FirebaseAuth mAuth;
    private static final int PERMISSION_REQUEST_CODE = 1;

    private String phoneNumber,message;




    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);


        ImageView settingsBar = findViewById(R.id.imageView15);

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

        ImageView sosimage = findViewById(R.id.sosimage);
        sosimage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                getCurrentLocation();

            }
        });


    }

    private void sendSMS(String phoneNumber, String message)
    {
        //Toast.makeText(HomeActivity.this, phoneNumber, Toast.LENGTH_SHORT).show();

        SmsManager smsManager = SmsManager.getDefault();
        smsManager.sendTextMessage(phoneNumber, null, message, null, null);
        Toast.makeText(HomeActivity.this, "message sent successfully", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                sendSMS(phoneNumber,message);
                Toast.makeText(HomeActivity.this, "Message sent successfully", Toast.LENGTH_SHORT).show();
            }
            else {
                // Permission denied, show a message to the user
            }
        }
    }

    private void getCurrentLocation()
    {
        // Check for location permissions
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // Request location permission if not granted
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, PERMISSION_REQUEST_CODE);
        } else
        {
            // Permission has already been granted, get current location
            fusedLocationClient.getLastLocation()
                    .addOnSuccessListener(this, new OnSuccessListener<Location>() {
                        @Override
                        public void onSuccess(Location location) {
                            if (location != null)
                            {

                                Toast.makeText(HomeActivity.this,"done", Toast.LENGTH_SHORT).show();
                                // Location retrieved successfully
                                currentLocation = new LatLng(location.getLatitude(), location.getLongitude());

                                // Now that currentLocation is set, you can use it to send SOS message or perform other actions
                                sendMessage();
                             //   Toast.makeText(HomeActivity.this, "message sent successfully", Toast.LENGTH_SHORT).show();
                            } else {
                                // Handle the case where location is null
                                Toast.makeText(HomeActivity.this, "Unable to get current location", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        }
    }

    public static String removeCharacters(String str, int n) {
        // Check if the string length is greater than the number of characters to be removed
        if (str.length() > n) {
            return str.substring(n, str.length() - 1);
        }
        // Return an empty string if the string is too short
        return "";
    }

    private void sendMessage() {
        // Use currentLocation to compose SOS message and send it
        mAuth = FirebaseAuth.getInstance();

        FirebaseUser currentUser = mAuth.getCurrentUser();
        DocumentReference docRef = FirebaseFirestore.getInstance().collection("emergencyemail").document(currentUser.getEmail());
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot doc = task.getResult();
                    if (doc.exists()) {
                        String phonenumberr= doc.getData().toString();


                        phoneNumber =phonenumberr.substring(0,0)+phonenumberr.substring(0+1);



                        Toast.makeText(HomeActivity.this, ""+currentLocation.longitude+" "+currentLocation.latitude, Toast.LENGTH_SHORT).show();

                        message = "Help! I am in an emergency.\n" +
                                "\n" +
                                "Location: { " + currentLocation.longitude + " " + currentLocation.latitude + " }" +
                                "\n" +
                                "Details:\n" +
                                "\n" +
                                "I am in immediate danger and need assistance. Please contact the authorities and come to my location as soon as possible.";

                        if (ContextCompat.checkSelfPermission(HomeActivity.this, Manifest.permission.SEND_SMS) != PackageManager.PERMISSION_GRANTED) {
                            // Request SMS permission if not granted
                            ActivityCompat.requestPermissions(HomeActivity.this, new String[]{Manifest.permission.SEND_SMS}, PERMISSION_REQUEST_CODE);
                        } else {
                            // Permission granted, send SOS message
                            sendSMS(phoneNumber, message);
                           // Toast.makeText(HomeActivity.this, "message sent successfully", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            }
        });
    }
}

//
//
//
// package com.example.shesecure10;
//
//import android.Manifest;
//import androidx.annotation.NonNull;
//import androidx.appcompat.app.AppCompatActivity;
//import androidx.cardview.widget.CardView;
//import androidx.core.app.ActivityCompat;
//import androidx.core.content.ContextCompat;
//
//import android.content.Intent;
//import android.content.pm.PackageManager;
//import android.os.Bundle;
//import android.provider.ContactsContract;
//import android.telephony.SmsManager;
//import android.view.View;
//import android.widget.ImageView;
//import android.widget.Toast;
//
//import com.google.android.gms.tasks.OnCompleteListener;
//import com.google.android.gms.tasks.Task;
//import com.google.firebase.auth.FirebaseAuth;
//import com.google.firebase.auth.FirebaseUser;
//import com.google.firebase.firestore.DocumentReference;
//import com.google.firebase.firestore.DocumentSnapshot;
//import com.google.firebase.firestore.FirebaseFirestore;
//
//public class HomeActivity extends AppCompatActivity {
//    private FirebaseAuth mAuth;
//    private static final int PERMISSION_REQUEST_CODE = 1;
//
//    private String phoneNumber,message;
//
//
//
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_home);
//
//        ImageView settingsBar = findViewById(R.id.imageView15);
//        ImageView sosimage=findViewById(R.id.sosimage);
//
//        CardView high_danger =findViewById(R.id.highdanger);
//        high_danger.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                startActivity(new Intent(HomeActivity.this,MapActivity.class));
//            }
//        });
//
//
//        CardView safety_route =findViewById(R.id.routeplanner);
//        safety_route.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                startActivity(new Intent(HomeActivity.this,RouteActivity.class));
//            }
//        });
//
//        ImageView mapgg =findViewById(R.id.imageView16);
//        mapgg.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                startActivity(new Intent(HomeActivity.this,MapsActivity.class));
//            }
//        });
//
//
//        // Set OnClickListener for the settings bar ImageView
//        settingsBar.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                // Define an intent to open the SettingsActivity
//                Intent intent = new Intent(HomeActivity.this, SettingsActivity.class);
//
//                // Start the SettingsActivity
//                startActivity(intent);
//            }
//        });
//
//        CardView virtual_comp=findViewById(R.id.virtualcompanion);
//        virtual_comp.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                    startActivity(new Intent(HomeActivity.this,VirtualActivity.class));
//            }
//        });
//
//        // Set OnClickListener for the self-defense tutorial CardView
//        CardView self_defense = findViewById(R.id.selfdefense);
//        self_defense.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                startActivity(new Intent(HomeActivity.this, TutorialActivitynew.class));
//            }
//        });
//
//        sosimage.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                mAuth = FirebaseAuth.getInstance();
//
//                FirebaseUser currentUser = mAuth.getCurrentUser();
//                DocumentReference docref= FirebaseFirestore.getInstance().collection("emergencyemail").document(currentUser.getEmail());
//                docref.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
//                    @Override
//                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
//                        if(task.isSuccessful())
//                        {
//                            DocumentSnapshot doc=task.getResult();
//                            if(doc.exists())
//                            {
//                                 phoneNumber = doc.getData().toString();
//                               // Toast.makeText(HomeActivity.this, "Message sent successfully", Toast.LENGTH_SHORT).show();
//                                 message="sent";
//                                if (ContextCompat.checkSelfPermission(HomeActivity.this,
//                                        Manifest.permission.SEND_SMS) != PackageManager.PERMISSION_GRANTED) {
//                                    ActivityCompat.requestPermissions(HomeActivity.this,
//                                            new String[]{Manifest.permission.SEND_SMS}, PERMISSION_REQUEST_CODE);
//                                } else {
//                                    sendSMS(phoneNumber, message);
//                                    Toast.makeText(HomeActivity.this, "Message sent successfully", Toast.LENGTH_SHORT).show();
//                                }
//                            }
//                        }
//                    }
//                });
//            }
//        });
//
//
//
//
//
//    }
//
//    private void sendSMS(String phoneNumber, String message) {
//        SmsManager smsManager = SmsManager.getDefault();
//        smsManager.sendTextMessage(phoneNumber, null, message, null, null);
//    }
//    @Override
//    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
//        if (requestCode == PERMISSION_REQUEST_CODE) {
//            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//
//                sendSMS(phoneNumber,message);
//                Toast.makeText(HomeActivity.this, "Message sent successfully", Toast.LENGTH_SHORT).show();
//            } else {
//                // Permission denied, show a message to the user
//            }
//        }
//    }
//}