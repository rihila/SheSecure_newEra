package com.example.shesecure10;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {

    EditText edEmail, edPassword;
    Button login, register;
    CheckBox remember;

    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        edEmail= findViewById(R.id.editTextTextEmailAddress);
        edPassword = findViewById(R.id.editTextTextPassword);

        login = findViewById(R.id.button);
        register = findViewById(R.id.button3);

        remember = findViewById(R.id.checkBox2);

        auth=FirebaseAuth.getInstance();

       /* login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = edUsername.getText().toString();
                String password = edPassword.getText().toString();

                Database db=new Database(getApplicationContext(),"SheSecure",null,1);


                if (username.length() == 0 || password.length() == 0) {
                    Toast.makeText(getApplicationContext(), "Please fill All details", Toast.LENGTH_SHORT).show();
                } else {
                    if(db.login(username,password)==1) {
                        Toast.makeText(getApplicationContext(), "Login Success", Toast.LENGTH_SHORT).show();

                        SharedPreferences sharedPreferences=getSharedPreferences("shared_prefs", Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor= sharedPreferences.edit();
                        editor.putString("username",username);
                        editor.apply();

                        startActivity(new Intent(LoginActivity.this,HomeActivity.class));
                    }
                    else
                        Toast.makeText(getApplicationContext(), "Invalid Username and Password", Toast.LENGTH_SHORT).show();
                }
            }
        });*/

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String useremail = edEmail.getText().toString();
                String password = edPassword.getText().toString();
                if (useremail.length() == 0 || password.length() == 0) {
                    Toast.makeText(getApplicationContext(), "Please fill All details", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    loginuser(useremail,password);
                }
            }
        });

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
            }
        });

    }

    private void loginuser(String useremail, String password) {
        auth.signInWithEmailAndPassword(useremail,password).addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful())
                {
                    Toast.makeText(getApplicationContext(), "Login Success", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(LoginActivity.this,HomeActivity.class));
                }
                else
                {
                    Toast.makeText(getApplicationContext(), "Login Failed!", Toast.LENGTH_SHORT).show();
                }

            }
        }
        );
    }
}

