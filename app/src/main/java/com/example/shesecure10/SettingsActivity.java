package com.example.shesecure10;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class SettingsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        // Set OnClickListener for the "Add Emails" option
        findViewById(R.id.addEmailOption).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Launch the AddEmergencyEmailActivity
                startActivity(new Intent(SettingsActivity.this, AddEmergencyEmailActivity.class));
            }
        });

        // Set OnClickListener for the "Show Emergency Emails" option
        findViewById(R.id.showEmergencyEmailOption).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Call a method to retrieve and show emergency emails
                showEmergencyEmails();
            }
        });
    }

    private void showEmergencyEmails() {
        // Retrieve the current logged-in user's username from shared preferences
        SharedPreferences sharedPreferences = getSharedPreferences("shared_prefs", Context.MODE_PRIVATE);
        String currentUser = sharedPreferences.getString("username", "");

        Database db = new Database(getApplicationContext(), "SheSecure", null, 1);

        // Log the current user's username
        Log.d("SettingsActivity", "Current User: " + currentUser);

        // Check if the current user is logged in
        if (!currentUser.isEmpty()) {
            // Retrieve emergency emails associated with the current user from the database
         //   SOSDatabaseHelper dbHelper = new SOSDatabaseHelper(this);
          //  SOSDatabaseHelper dbHelper= new SOSDatabaseHelper(getApplicationContext(), "sos_database", null, 1);
            List<String> emergencyEmails = new ArrayList<>();

            // Display the retrieved emergency emails
            if (!emergencyEmails.isEmpty()) {
                // Construct a message containing all the emergency emails
                StringBuilder message = new StringBuilder("Emergency Emails:\n");
                for (String email : emergencyEmails) {
                    message.append("- ").append(email).append("\n");
                }

                // Show the message (You can adjust this to display the emails in a list, dialog, etc.)
                Toast.makeText(this, message.toString(), Toast.LENGTH_LONG).show();
            } else {
                // If no emergency emails found, show a message indicating so
                Toast.makeText(this, "No emergency emails found.", Toast.LENGTH_SHORT).show();
            }
        } else {
            // If no user is logged in, show a message indicating so
            Toast.makeText(this, "No user logged in.", Toast.LENGTH_SHORT).show();
        }
    }

}
