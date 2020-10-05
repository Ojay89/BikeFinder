package com.example.bicyclefinder;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SingleBikeActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    public static final String BIKE = "BIKE";
    private static final String LOG_TAG = "MYBIKES";
    private Bike originalBike;
    private TextView messageView;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_bike);
        messageView = findViewById(R.id.singleBikeMessageTextView);

        Intent intent = getIntent();
        originalBike = (Bike) intent.getSerializableExtra(BIKE);

        Log.d(LOG_TAG, originalBike.toString());
        TextView headingView = findViewById(R.id.singleBikeHeadingTextview);
        headingView.setText("Bike Brand=" + originalBike.getBrand());

        EditText frameNumberView = findViewById(R.id.singleBikeFrameNumberEditText);
        frameNumberView.setText(originalBike.getFrameNumber());

        /*Spinner kindOfBicycleView = findViewById(R.id.singleBikeKindOfBicycleSpinner);
        ArrayAdapter<CharSequence> kindOfAdapter = ArrayAdapter.createFromResource(this, R.array.Types, android.R.layout.simple_spinner_item);
        kindOfAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        kindOfBicycleView.setAdapter(kindOfAdapter);
        kindOfBicycleView.setOnItemSelectedListener(this);*/

        EditText brandView = findViewById(R.id.singleBikeBrandEditText);
        brandView.setText(originalBike.getBrand());

        EditText colorView = findViewById(R.id.singleBikeColorEditText);
        colorView.setText(originalBike.getColors());

        EditText placeView = findViewById(R.id.singleBikePlaceEditText);
        placeView.setText(originalBike.getPlace());

        EditText dateView = findViewById(R.id.singleBikeDateEditText);
        dateView.setText(originalBike.getDate());

        /*Spinner missingFound = findViewById(R.id.singleBikeMissingFoundEditText);
        ArrayAdapter<CharSequence> missingFoundAdapter = ArrayAdapter.createFromResource(this, R.array.MissingFound, android.R.layout.simple_spinner_item);
        missingFoundAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        kindOfBicycleView.setAdapter(missingFoundAdapter);
        kindOfBicycleView.setOnItemSelectedListener(this);*/


    }

    public void backButtonClicked(View view) {
        Log.d(LOG_TAG, "backButtonClicked");
        finish();
    }

    public void deleteBookButtonClicked(View view) {
        BikeFinderService bikeFinderService = ApiUtils.getBikeFinderService();
        int bikeId = originalBike.getId();
        Call<Bike> deleteBikeCall = bikeFinderService.deleteBike(bikeId);
        messageView.setText("Cykel Slettet");

        deleteBikeCall.enqueue(new Callback<Bike>() {
            @Override
            public void onResponse(Call<Bike> call, Response<Bike> response) {
                if (response.isSuccessful()) {
                    String message = "Cykel Slettet, id: " + originalBike.getId();
                    Toast.makeText(getBaseContext(), message, Toast.LENGTH_SHORT).show();
                    Log.d(LOG_TAG, message);
                } else {
                    String problem = call.request().url() + "\n" + response.code() + " " + response.message();
                    messageView.setText(problem);
                    Log.e(LOG_TAG, problem);
                }
            }

            @Override
            public void onFailure(Call<Bike> call, Throwable t) {
                Log.e(LOG_TAG, "Problem: " + t.getMessage());
            }
        });
    }

    public void updateButtonClicked(View view) {
        Log.d(LOG_TAG, "anotherButtonClicked");
        Toast.makeText(this, "anotherButtonClicked", Toast.LENGTH_SHORT).show();

        EditText frameNumberField = findViewById(R.id.singleBikeFrameNumberEditText);
        Spinner typeField = findViewById(R.id.singleBikeKindOfBicycleSpinner);
        //EditText typeField = findViewById(R.id.singleBikeKindOfBicycleEditText);
        EditText brandField = findViewById(R.id.singleBikeBrandEditText);
        EditText colorField = findViewById(R.id.singleBikeColorEditText);
        EditText placeField = findViewById(R.id.singleBikePlaceEditText);
        EditText dateField = findViewById(R.id.singleBikeDateEditText);
        EditText missingFoundField = findViewById(R.id.singleBikeMissingFoundEditText);
        //Spinner missingFoundField = findViewById(R.id.singleBikeMissingFoundEditText);


        String frameNumber = frameNumberField.getText().toString().trim();
        String brand = brandField.getText().toString().trim();
        String color = colorField.getText().toString().trim();
        String place = placeField.getText().toString().trim();
        String date = dateField.getText().toString().trim();
        String selectedType = (String) typeField.getSelectedItem();
        String missingFound = missingFoundField.getText().toString().trim();
        //Spinner type = (Spinner) typeField.getSelectedItem();
        //Spinner missingFound = (Spinner) missingFoundField.getSelectedItem();


        Bike bikeToUpdate = new Bike(frameNumber, selectedType, brand, color, place, date, missingFound);
        Log.d(LOG_TAG, "Update " + bikeToUpdate);

        BikeFinderService bikeFinderService = ApiUtils.getBikeFinderService();
        Call<Bike> callUpdate = bikeFinderService.updateBike(originalBike.getId(), bikeToUpdate);
        callUpdate.enqueue(new Callback<Bike>() {
            @Override
            public void onResponse(Call<Bike> call, Response<Bike> response) {
                if (response.isSuccessful()) {
                    Log.d(LOG_TAG, response.body().toString());
                    messageView.setText("Updated " + response.body().toString());
                } else {
                    messageView.setText("Problem: " + response.code() + " " + response.message());
                }
            }

            @Override
            public void onFailure(Call<Bike> call, Throwable t) {
                messageView.setText("Problem: " + t.getMessage());
            }
        });
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String text = parent.getItemAtPosition(position).toString();
        Toast.makeText(parent.getContext(), text, Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

}
