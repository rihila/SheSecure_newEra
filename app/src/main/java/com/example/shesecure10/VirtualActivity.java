package com.example.shesecure10;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class VirtualActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    EditText messageEditText;
    ImageButton sendButton;
    List<Message> messageList;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_virtual);

        messageList=new ArrayList<>();

        recyclerView = findViewById(R.id.recycler_view);

        messageEditText = findViewById(R.id.message_edit_text);

        sendButton = findViewById(R.id.send_button);

        sendButton.setOnClickListener((v) ->
        {
            String question=messageEditText.getText().toString().trim();
            Toast.makeText(this,question,Toast.LENGTH_LONG).show();


        });

    }
}

