package com.example.chat;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class AddFriend extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private FirebaseFirestore db;

    private EditText etSearch;
    private Button btnSearch;

    private RecyclerView rvListSearch;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;

    private ArrayList<User> friends = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_friend);

        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

        etSearch = findViewById(R.id.et_search);
        btnSearch = findViewById(R.id.btn_search);

        initRecyclerView();

        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                friends.clear();
                String searchName = etSearch.getText().toString();

                db.collection("users").whereEqualTo("name", searchName).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()){
                            for (QueryDocumentSnapshot document : task.getResult()){
                                User friend = document.toObject(User.class);
                                friends.add(friend);
                            }
                            mAdapter.notifyDataSetChanged();
                        }
                    }
                });
            }
        });

    }
    private void initRecyclerView() {
        rvListSearch = findViewById(R.id.rv_list_search);
        rvListSearch.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        rvListSearch.setLayoutManager(layoutManager);
        mAdapter = new SearchAdapter(friends);
        rvListSearch.setAdapter(mAdapter);
    }
}