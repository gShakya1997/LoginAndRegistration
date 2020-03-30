package com.example.loginandregistration;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.loginandregistration.activities.LoginActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class RegisterActivity extends AppCompatActivity {
    private TextInputLayout etStoreName, etStoreEmail, etStorePassword, etStroreCPassword;
    private Button btnGoToLogin, btnRegister;
    private ProgressBar progressBar;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initialize();
        firebaseAuth = FirebaseAuth.getInstance();

        btnGoToLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                finish();
            }
        });

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String storeEmail = etStoreEmail.getEditText().getText().toString().trim();
                String storePassword = etStorePassword.getEditText().getText().toString().trim();

                if (TextUtils.isEmpty(storeEmail)) {
                    etStoreEmail.setError("Enter your email address");
                    return;
                }

                if (TextUtils.isEmpty(storePassword)) {
                    etStorePassword.setError("Enter your password");
                    return;
                }

                progressBar.setVisibility(View.VISIBLE);

                firebaseAuth.createUserWithEmailAndPassword(storeEmail, storePassword)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    Toast.makeText(RegisterActivity.this,
                                            "User Created", Toast.LENGTH_SHORT).show();
                                    startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                                } else {
                                    Toast.makeText(RegisterActivity.this,
                                            "Error " + task.getException().getMessage(),
                                            Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }
        });
    }

    private void initialize() {
        etStoreName = findViewById(R.id.etStoreName);
        etStoreEmail = findViewById(R.id.etStoreEmail);
        etStorePassword = findViewById(R.id.etStorePassword);
        etStroreCPassword = findViewById(R.id.etStoreCPassword);
        btnGoToLogin = findViewById(R.id.btnGoToLogin);
        btnRegister = findViewById(R.id.btnRegister);
        progressBar = findViewById(R.id.progressBar);
    }
}
