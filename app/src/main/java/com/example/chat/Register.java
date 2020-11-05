package com.example.chat;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.Random;

import static java.lang.Integer.parseInt;

public class Register extends AppCompatActivity {

    private static final String TAG = "MACO";

    private FirebaseAuth mAuth;
    private FirebaseFirestore db;
    private CollectionReference userRef;
    
    private EditText etRegName, etRegPhone, etRegEmail, etRegPassword, etRegPasswordConf;
    private Button btnRegisterAccount;

    private Random rNumber = new Random();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
        userRef = db.collection("users");

        etRegName = findViewById(R.id.et_reg_name);
        etRegPhone = findViewById(R.id.et_reg_phone);
        etRegEmail = findViewById(R.id.et_reg_email);
        etRegPassword = findViewById(R.id.et_reg_password);
        etRegPasswordConf = findViewById(R.id.et_reg_password_conf);
        btnRegisterAccount = findViewById(R.id.btn_register_account);

        
        btnRegisterAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String name = etRegName.getText().toString().trim();
                String phone = etRegPhone.getText().toString().trim();
                String email = etRegEmail.getText().toString().trim();
                String password = etRegPassword.getText().toString().trim();
                String confirmPassword = etRegPasswordConf.getText().toString().trim();

                if (name.isEmpty() || phone.isEmpty() || email.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()){
                    Toast.makeText(Register.this, "fields can't be empty.", Toast.LENGTH_SHORT).show();
                } else {
                    if (password.contentEquals(confirmPassword)){

                        //CREATE ID
                        //createRandomUniqueId();

                        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()){
                                    Toast.makeText(Register.this, "new account created!", Toast.LENGTH_SHORT).show();

                                    String uid = mAuth.getCurrentUser().getUid();
                                    User user = new User(uid, name, phone, email);

                                    db.collection("users").document(uid).set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void aVoid) {
                                            Toast.makeText(Register.this, "user successfully created", Toast.LENGTH_SHORT).show();
                                            Intent intent = new Intent(Register.this, MainActivity.class);
                                            startActivity(intent);
                                        }
                                    })
                                    .addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            Toast.makeText(Register.this, "something went wrong " + e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                                        }
                                    });
                                } else {
                                    Toast.makeText(Register.this, "something went wrong: " + task.getException().getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                    } else {
                        Toast.makeText(Register.this, "password does not match.", Toast.LENGTH_SHORT).show();
                    }
                }

            }
        });
        
    }

    private void createRandomUniqueId() {

        int rUserId = rNumber.nextInt(10000);
        Log.d(TAG, "createRandomId: " + rUserId);

        userRef.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()){
                    for (QueryDocumentSnapshot document : task.getResult()){
                        int userId = Integer.parseInt(document.get("randomUserId").toString());

                    }
                } else {
                    Toast.makeText(Register.this, "something went wrong: " + task.getException().getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}