package com.example.bicyclefinder;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.Arrays;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "lukind";
    private FirebaseAuth mAuth;
    private Object AuthResult;
    private TextView messageView;
    private ProgressBar progressBar;
    private View view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mAuth = FirebaseAuth.getInstance();
        messageView = findViewById(R.id.mainMessageTextView);
        progressBar = findViewById(R.id.mainProgressbar);
    }

    public void onStart() {
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        TextView messageView = findViewById(R.id.mainMessageTextView);
        if (currentUser == null) {
            messageView.setText("Ingen er logget ind");
        } else {
            messageView.setText(currentUser.getEmail());
            Intent intent = new Intent(getBaseContext(), AfterUserLoggedIn.class);
            FirebaseUser user = mAuth.getCurrentUser();
            intent.putExtra("UserloggedIn", user.getEmail());
            startActivity(intent);
        }
    }

    protected void onResume() {
        super.onResume();
        messageView.setText("");
        EditText emailView = findViewById(R.id.mainEmailEditText);
        EditText passwordView = findViewById(R.id.mainPasswordEditText);
        emailView.setText("");
        passwordView.setText("");
    }

    public void loginClicked(View view) {
        EditText emailView = findViewById(R.id.mainEmailEditText);
        EditText passwordView = findViewById(R.id.mainPasswordEditText);
        String email = emailView.getText().toString().trim();
        String password = passwordView.getText().toString().trim();
        progressBar.setVisibility(View.VISIBLE);

        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Log.d(TAG, "signInWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            messageView.setText("Velkommen" + " " + user.getEmail());
                            Intent intent = new Intent(getBaseContext(), AfterUserLoggedIn.class);
                            //String username = user.getEmail();
                            intent.putExtra("UserloggedIn", user.getEmail());
                            startActivity(intent);

                        } else {
                            Log.w(TAG, "signInWithEmail:failure", task.getException());
                            Toast.makeText(getBaseContext(), "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                            messageView.setText("Forkert brugernavn eller adgangskode");

                        }
                        progressBar.setVisibility(View.INVISIBLE);
                    }
                });
    }

    public void signUpClicked(View view) {
        Intent intent = new Intent(this, SignUpActivity.class);
        startActivity(intent);
    }
}