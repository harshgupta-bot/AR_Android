package com.example.historyandculture;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class ChatbotActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private EditText userInput;
    private Button sendBtn;
    private ChatAdapter adapter;
    private ArrayList<Message> messageList;

    private final OkHttpClient client = new OkHttpClient();
    private static final MediaType JSON = MediaType.get("application/json; charset=utf-8");
    private static final String GEMINI_API_KEY = "AIzaSyAZXsPlu18MC7JZq4YzDbdOdwN2caV5LdQ";
    private static final String MODEL = "gemini-1.5-flash";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chatbot);

        recyclerView = findViewById(R.id.chatRecyclerView);
        userInput = findViewById(R.id.userInput);
        sendBtn = findViewById(R.id.sendBtn);

        messageList = new ArrayList<>();
        adapter = new ChatAdapter(messageList);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        sendBtn.setOnClickListener(v -> {
            String input = userInput.getText().toString().trim();
            if (!input.isEmpty()) {
                addMessage(input, true); // user message
                userInput.setText("");
                getAIResponse(input);
            }
        });
    }

    private void addMessage(String content, boolean isUser) {
        runOnUiThread(() -> {
            messageList.add(new Message(content, isUser));
            adapter.notifyItemInserted(messageList.size() - 1);
            recyclerView.scrollToPosition(messageList.size() - 1);
        });
    }

    private void getAIResponse(String prompt) {
        try {
            String url = "https://generativelanguage.googleapis.com/v1beta/models/"
                    + MODEL + ":generateContent?key=" + GEMINI_API_KEY;

            // Request JSON body
            JSONObject bodyJson = new JSONObject();
            JSONArray parts = new JSONArray()
                    .put(new JSONObject().put("text", prompt));
            JSONObject userMsg = new JSONObject()
                    .put("role", "user")
                    .put("parts", parts);

            JSONArray contents = new JSONArray().put(userMsg);
            bodyJson.put("contents", contents);

            RequestBody body = RequestBody.create(bodyJson.toString(), JSON);
            Request request = new Request.Builder().url(url).post(body).build();

            client.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    addMessage("Error: " + e.getMessage(), false);
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    String reply;
                    try {
                        String raw = response.body().string();
                        JSONObject root = new JSONObject(raw);
                        JSONArray candidates = root.optJSONArray("candidates");
                        if (candidates != null && candidates.length() > 0) {
                            JSONObject content = candidates.getJSONObject(0).getJSONObject("content");
                            JSONArray p = content.getJSONArray("parts");
                            reply = p.getJSONObject(0).optString("text", "(empty)");
                        } else {
                            reply = "(no response)";
                        }
                    } catch (Exception ex) {
                        reply = "(parse error)";
                    }
                    addMessage("AI: " + reply, false);
                }
            });

        } catch (Exception e) {
            addMessage("Error: " + e.getMessage(), false);
        }
    }
}
