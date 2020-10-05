package com.example.bicyclefinder;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import retrofit2.Retrofit;

public class SignUpActivity extends AppCompatActivity {

    private static final String TAG = "MyUsers";
    private FirebaseAuth mAuth;
    private TextView messageView;
    private Button registererButton;
    private EditText emailText, passwordText;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        mAuth = FirebaseAuth.getInstance();
        messageView = findViewById(R.id.mainMessageTextView);
        progressBar = findViewById(R.id.mainProgressbar);
        initializeUI();
        registererButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createClicked();
            }
        });

    }

    public void createClicked() {
        String email, password;
        email = emailText.getText().toString().trim();
        password = passwordText.getText().toString().trim();


        if ("".equals(email)) {
            messageView.setText("Email adresse eksisterer");
            return;
        }

        if ("".equals(password)) {
            messageView.setText("Skriv venligst adgangskode");
            return;
        }

        progressBar.setVisibility(View.VISIBLE);
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(getApplicationContext(), "Bruger oprettet", Toast.LENGTH_LONG).show();
                            Log.d(TAG, "CreateUserWithEmail:succes");
                            FirebaseUser user = mAuth.getCurrentUser();
                            Intent intent = new Intent(getBaseContext(), MainActivity.class);
                            startActivity(intent);
                            progressBar.setVisibility(View.VISIBLE);
                        } else {
                            try {
                                messageView.setText("Der er opstået et problem");
                                AuthResult result = task.getResult();
                            } catch (RuntimeException ex) {
                                messageView.setText(ex.getCause().getMessage());
                            }
                        }
                        progressBar.setVisibility(View.INVISIBLE);
                    }
                });

    }

    private void initializeUI() {
        emailText = findViewById(R.id.signUpEmailEditView);
        passwordText = findViewById(R.id.signUpPasswordEditView);
        registererButton = findViewById(R.id.signUpCreateButton);
    }

}