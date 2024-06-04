package com.example.shesecure10;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class AddEmergencyEmailActivity extends AppCompatActivity {

    private EditText emailEditText;
    private Button addButton;

   // private SOSDatabaseHelper sosDatabaseHelper;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_emergency_email);

        emailEditText = findViewById(R.id.emailEditText);
        addButton = findViewById(R.id.addButton);

       // sosDatabaseHelper = new SOSDatabaseHelper(this);

      //  sosDatabaseHelper= new SOSDatabaseHelper(getApplicationContext(), "sos_database", null, 1);

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = emailEditText.getText().toString().trim();
                if (!email.isEmpty()) {
                    // Get username of logged-in user (You need to implement this part)
                    String loggedInUsername = "username"; // Replace this with actual username

                    // Add emergency email to database
                   // sosDatabaseHelper.addEmergencyEmail(loggedInUsername, email);
                    Toast.makeText(AddEmergencyEmailActivity.this, "Email added successfully", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(AddEmergencyEmailActivity.this, "Please enter an email address", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
