package com.example.chat;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class MessageActivity extends AppCompatActivity {

    private static final String TAG = "MACO";
    private FirebaseAuth mAuth;
    private FirebaseFirestore db;

    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;

    private Button btnSend;
    private EditText etSendText;

    private ArrayList<Message> listOfMessages = new ArrayList<>();
    private String currentUserId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);

        mAuth = FirebaseAuth.getInstance();
        currentUserId = mAuth.getCurrentUser().getUid();
        db = FirebaseFirestore.getInstance();

        btnSend = findViewById(R.id.btn_send);
        etSendText = findViewById(R.id.et_send_text);

        recyclerView = findViewById(R.id.rv_message);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        getMessagesFromFirestore();

        mAdapter = new MessageAdapter(listOfMessages, currentUserId);
        recyclerView.setAdapter(mAdapter);

        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String message = etSendText.getText().toString().trim();
                String sender = mAuth.getCurrentUser().getUid();
                etSendText.setText("");
                Message newMessage = new Message(message, sender);

                db.collection("rooms")
                        .document("room1")
                        .collection("messages")
                        .add(newMessage)
                        .addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                            @Override
                            public void onComplete(@NonNull Task<DocumentReference> task) {
                                listOfMessages.add(newMessage);
                                mAdapter.notifyDataSetChanged();
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(MessageActivity.this, "something went wrong: " + e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }

    //REAL TIME SNAPSHOT UPDATE
    /*@Override
    protected void onStart() {
        super.onStart();
        db.collection("rooms")
                .document("room1")
                .collection("messages")
                .addSnapshotListener(this, new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                        if (error != null) {
                            Log.w(TAG, "Listen failed.", error);
                            return;
                        }
                        for (QueryDocumentSnapshot document : value){
                            Message message = document.toObject(Message.class);
                            listOfMessages.add(message);
                        }
                        mAdapter.notifyDataSetChanged();
                    }
                });
    }*/

    private void getMessagesFromFirestore() {
        db.collection("rooms")
                .document("room1")
                .collection("messages")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()){
                    for (QueryDocumentSnapshot document : task.getResult()){
                        Message message = document.toObject(Message.class);
                        listOfMessages.add(message);
                    }
                    mAdapter.notifyDataSetChanged();
                }

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(MessageActivity.this, "something went wrong: " + e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();

            }
        });
    }
}