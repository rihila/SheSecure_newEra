package com.example.shesecure10;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class AddEmergencyEmailActivity extends AppCompatActivity {

    private EditText emailEditText;
    private Button addButton;
    private FirebaseAuth mAuth;

   // private SOSDatabaseHelper sosDatabaseHelper;


    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_emergency_email);

        emailEditText = findViewById(R.id.emailEditText);
        addButton = findViewById(R.id.addButton);
        mAuth = FirebaseAuth.getInstance();

        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null) {

            String uid = currentUser.getUid();
            String useremail = currentUser.getEmail();



        }

       // sosDatabaseHelper = new SOSDatabaseHelper(this);

      //  sosDatabaseHelper= new SOSDatabaseHelper(getApplicationContext(), "sos_database", null, 1);

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = emailEditText.getText().toString().trim();
                FirebaseFirestore db=FirebaseFirestore.getInstance();
                if (!email.isEmpty()) {
                    // Get username of logged-in user (You need to implement this part)
                    String loggedInUsername = "username"; // Replace this with actual username

                    Map<String, Object> emergencymail=new HashMap<>();
                    emergencymail.put("emergencymail", email);
                    db.collection("emergencyemail").document(currentUser.getEmail()).set(emergencymail).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful())
                            {
                                Toast.makeText(AddEmergencyEmailActivity.this, "Email added successfully", Toast.LENGTH_SHORT).show();
                            }
                            else
                            {
                                Toast.makeText(AddEmergencyEmailActivity.this, "Email not added", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });




                } else {
                    Toast.makeText(AddEmergencyEmailActivity.this, "Please enter an email address", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
