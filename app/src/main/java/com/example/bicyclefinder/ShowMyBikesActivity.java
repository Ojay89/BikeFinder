package com.example.bicyclefinder;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ShowMyBikesActivity extends AppCompatActivity {
    private static final String LOG_TAG = "FoundCycles";
    private TextView messageView;
    private ProgressBar progressBar;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_my_bikes);
        messageView = findViewById(R.id.mainMessageTextView);
        progressBar = findViewById(R.id.mainProgressbar);
        mAuth = FirebaseAuth.getInstance();
    }

    @Override
    protected void onStart() {
        super.onStart();
        getAndShowMyBikes();
    }

    @Override
    public void onBackPressed()
    {
        Intent intent = new Intent(getBaseContext(), AfterUserLoggedIn.class);
        FirebaseUser user = mAuth.getCurrentUser();
        intent.putExtra("UserloggedIn", user.getEmail());
        startActivity(intent);
    }

    private void getAndShowMyBikes() {
        BikeFinderService bikeFinderService = ApiUtils.getBikeFinderService();
        String firebaseId = mAuth.getCurrentUser().getUid();
        Call<List<Bike>> getMyBikes = bikeFinderService.getFirebaseUserId(firebaseId);
        Log.d("Minbruger", firebaseId);
        messageView.setText("");
        progressBar.setVisibility(View.VISIBLE);

        getMyBikes.enqueue(new Callback<List<Bike>>() {

            @Override
            public void onResponse(Call<List<Bike>> call, Response<List<Bike>> response) {
                Log.d(LOG_TAG, response.raw().toString());
                progressBar.setVisibility(View.INVISIBLE);
                if (response.isSuccessful()) {
                    List<Bike> allBikes = response.body();
                    Log.d(LOG_TAG, allBikes.toString());
                    populateRecyclerView(allBikes);
                } else {
                    String message = "Problem " + response.code() + " " + response.message();
                    Log.d(LOG_TAG, message);
                    messageView.setText(message);
                }
            }


            @Override
            public void onFailure(Call<List<Bike>> call, Throwable t) {
                progressBar.setVisibility(View.INVISIBLE);
                Log.e(LOG_TAG, t.getMessage());
                messageView.setText(t.getMessage());
            }
        });
    }


    private void populateRecyclerView(List<Bike> allBikes) {
        RecyclerView recyclerView = findViewById(R.id.userLoggedInRecyclerView);
        Log.d(LOG_TAG, "FindBikes" + allBikes.toString());
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        RecyclerViewAdapter<Bike> adapter = new RecyclerViewAdapter<>(allBikes);
        recyclerView.setAdapter(adapter);
        ((RecyclerViewAdapter<Bike>) adapter).setOnItemClickListener((view, position, item) -> {
            Bike bike = (Bike) item;
            Log.d(LOG_TAG, item.toString());
            Intent intent = new Intent(ShowMyBikesActivity.this, MySingleBikeActivity.class);
            intent.putExtra(SingleBikeActivity.BIKE, bike);
            Log.d(LOG_TAG, "putExtra " + bike.toString());
            startActivity(intent);
        });
    }

}

