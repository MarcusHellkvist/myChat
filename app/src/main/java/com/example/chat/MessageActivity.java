package com.example.chat;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.ArrayList;

public class MessageActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;

    private Button btnSend;
    private EditText etSendText;

    private ArrayList<Message> listOfMessages = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);

        btnSend = findViewById(R.id.btn_send);
        etSendText = findViewById(R.id.et_send_text);

        recyclerView = findViewById(R.id.rv_message);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        Message mess1 = new Message("First message!");
        Message mess2 = new Message("Wow");
        Message mess3 = new Message("Det funkar ju!");
        Message mess4 = new Message("Vad gör du imorgon?");
        Message mess5 = new Message("Ingenting");
        Message mess6 = new Message("Vill du hänga?");

        listOfMessages.add(mess1);
        listOfMessages.add(mess2);
        listOfMessages.add(mess3);
        listOfMessages.add(mess4);
        listOfMessages.add(mess5);
        listOfMessages.add(mess6);

        mAdapter = new MessageAdapter(listOfMessages);
        recyclerView.setAdapter(mAdapter);

        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String message = etSendText.getText().toString().trim();
                Message newMessage = new Message(message);
                listOfMessages.add(newMessage);
                etSendText.setText("");
                mAdapter.notifyDataSetChanged();
            }
        });
    }
}