package com.example.shesecure10;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.shesecure10.databinding.ActivityRegisterBinding;
import com.example.shesecure10.models.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class RegisterActivity extends AppCompatActivity {

    ActivityRegisterBinding binding;
    private FirebaseAuth auth;


    FirebaseDatabase database;



    EditText edUsername,edEmail,edPassword,edConfirm;
    Button btn;
    CheckBox chk;
    TextView tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding=ActivityRegisterBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        auth=FirebaseAuth.getInstance();
        database=FirebaseDatabase.getInstance();

        btn=findViewById(R.id.button3reg);


        edUsername=findViewById(R.id.editTextTextreg3);
        edEmail=findViewById(R.id.editTextTextEmailAddressreg);
        edPassword=findViewById(R.id.editTextTextPassword2reg3);
        edConfirm=findViewById(R.id.editTextTextPassword22reg3);

        btn=findViewById(R.id.button3reg);

        chk=findViewById(R.id.checkBox2reg);

        tv=findViewById(R.id.textView8reg);

        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(RegisterActivity.this,LoginActivity.class));
            }
        });
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = edUsername.getText().toString();
                String password = edPassword.getText().toString();
                String email = edEmail.getText().toString();
                String confirm = edConfirm.getText().toString();
                Toast.makeText(getApplicationContext(), "Please fill All details", Toast.LENGTH_SHORT).show();

              

                if (username.length() == 0 || password.length() == 0 || email.length() == 0 || confirm.length() == 0 ) {
                    Toast.makeText(getApplicationContext(), "Please fill All details", Toast.LENGTH_SHORT).show();
                } else if (password.compareTo(confirm)!=0) {
                    Toast.makeText(getApplicationContext(), "Password and Confirm Password didn't match", Toast.LENGTH_SHORT).show();
                } else {

                   registeruser(username,password,email,confirm);


                }
            }

        });


    }

    private void registeruser(String username, String password, String email, String confirm) {
        auth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(RegisterActivity.this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful())
                {
                    Toast.makeText(getApplicationContext(), "Registration Successful", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(RegisterActivity.this,LoginActivity.class));
                }
                else
                {
                    Toast.makeText(getApplicationContext(), "Registration Failed!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}