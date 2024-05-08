package com.example.shesecure10;

import static com.google.common.util.concurrent.MoreExecutors.directExecutor;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.ai.client.generativeai.GenerativeModel;
import com.google.ai.client.generativeai.java.GenerativeModelFutures;
import com.google.ai.client.generativeai.type.Content;
import com.google.ai.client.generativeai.type.GenerateContentResponse;
import com.google.common.util.concurrent.FutureCallback;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class VirtualActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    EditText messageEditText;
    ImageButton sendButton;
    List<Message> messageList;
    MessageAdapter messageAdapter;
    public static final MediaType JSON = MediaType.get("application/json");
    OkHttpClient client = new OkHttpClient();




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_virtual);

        messageList=new ArrayList<>();

        recyclerView = findViewById(R.id.recycler_view);

        messageEditText = findViewById(R.id.message_edit_text);

        sendButton = findViewById(R.id.send_button);



        messageAdapter=new MessageAdapter(messageList);

        recyclerView.setAdapter(messageAdapter);

        LinearLayoutManager llm=new LinearLayoutManager(this);

        llm.setStackFromEnd(true);

        recyclerView.setLayoutManager(llm);

        sendButton.setOnClickListener((v) ->
        {

            String question=messageEditText.getText().toString().trim();
            addToChat(question,Message.SENT_BY_ME);
            messageEditText.setText("");
            callgemini(question);

        });



    }

    public void callgemini(String question)
    {

        messageList.add(new Message("Typing...",Message.SENT_BY_BOT));

        GenerativeModel gm = new GenerativeModel(/* modelName */ "gemini-pro", "AIzaSyC9TdV3w6aGAbJ6Gp2t5Da3K5Y2p0IToi4");
        GenerativeModelFutures model = GenerativeModelFutures.from(gm);

        Content content = new Content.Builder()
                .addText(question)
                .build();
                ListenableFuture <GenerateContentResponse> response = model.generateContent(content);




           Futures.addCallback(response, new FutureCallback<GenerateContentResponse>() {

                @Override
                public void onSuccess(GenerateContentResponse result) {

                    String resultText = result.getText();
                    addResponse(resultText.trim());
                }

                @Override
                public void onFailure(Throwable t) {

                    t.printStackTrace();
                    addResponse(t.toString());
                }
            },directExecutor());



    }

    void addToChat(String message,String sentBy)
    {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                messageList.add(new Message(message,sentBy));
                messageAdapter.notifyDataSetChanged();
                recyclerView.smoothScrollToPosition(messageAdapter.getItemCount());
            }
        });

    }

    void addResponse(String response)
    {
        messageList.remove(messageList.size()-1);
        addToChat(response,Message.SENT_BY_BOT);
    }

    void callAPI(String question)
    {

        JSONObject jsonBODY =new JSONObject();
        try {


            JSONArray messagesArray = new JSONArray();
            JSONObject messageObject = new JSONObject();
            messageObject.put("role", "user");
            messageObject.put("content", question);
            messagesArray.put(messageObject);
            jsonBODY.put("model","gpt-3.5-turbo");
            jsonBODY.put("messages", messagesArray);
            jsonBODY.put("max_tokens", 4000);
            jsonBODY.put("temperature",0);
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }

        RequestBody body=RequestBody.create(jsonBODY.toString(),JSON);
        Request request=new Request.Builder()
                .url("https://api.openai.com/v1/chat/completions")
                .header("Authorization","Bearer sk-proj-kaCOULsZg2fb5i3bGQxpT3BlbkFJAOTjyCHE5k8T5jvr3oXS")
                .post( body)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                addResponse("Failed to load the response due to "+e.getMessage());
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {

                if (response.isSuccessful())
                {
                    JSONObject jsonObject= null;
                    try {


                        assert response.body() != null;
                        jsonObject = new JSONObject(response.body().string());
                        JSONArray jsonArray = jsonObject.getJSONArray("choices");
                        String result = jsonArray.getJSONObject(0).getJSONObject("message").getString("content");
                        addResponse(result.trim());
                    } catch (JSONException e) {
                        throw new RuntimeException(e);
                    }

                }
                else
                {
                    addResponse("Failed to load the response due to "+response.body().string());
                }
            }
        });
    }
}

