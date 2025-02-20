package com.example.bicyclefinder;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.os.Bundle;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddFoundBikeActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    private ProgressBar progressBar;
    private TextView messageView;
    private FirebaseAuth mAuth;
    //private Spinner spinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_found_bike);
        progressBar = findViewById(R.id.addBikeProgressbar);
        messageView = findViewById(R.id.singleBikeMessageTextView);
        mAuth = FirebaseAuth.getInstance();
        Spinner spinner = findViewById(R.id.singleBikeKindOfBicycleSpinner);
        ArrayAdapter adapter = ArrayAdapter.createFromResource(this, R.array.types_array, R.layout.spinner_dropdown_layout);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);

        /*Spinner spinnerType = findViewById(R.id.singleBikeKindOfBicycleSpinner);
        Spinner spinnerMissingFound = findViewById(R.id.singleBikeMissingFoundEditText);
        ArrayAdapter<CharSequence> adapterType = ArrayAdapter.createFromResource(this, R.array.types_array, android.R.layout.simple_spinner_item);
        ArrayAdapter<CharSequence> adapterMissingFound = ArrayAdapter.createFromResource(this, R.array.missingFound_array, android.R.layout.simple_spinner_item);
        adapterType.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        adapterMissingFound.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerType.setAdapter(adapterType);
        spinnerMissingFound.setAdapter(adapterMissingFound);
        spinnerType.setOnItemSelectedListener(this);
        spinnerMissingFound.setOnItemSelectedListener(this);*/

    }

    public void addBikeButtonClicked(View view) {
        EditText frameField = findViewById(R.id.singleBikeFrameNumberEditText);
        Spinner typeField = findViewById(R.id.singleBikeKindOfBicycleSpinner);
        //EditText typeField = findViewById(R.id.singleBikeKindOfBicycleEditText);
        EditText brandField = findViewById(R.id.singleBikeBrandEditText);
        EditText colorField = findViewById(R.id.singleBikeColorEditText);
        EditText placeField = findViewById(R.id.singleBikePlaceEditText);
        //EditText dateField = findViewById(R.id.singleBikeDateEditText);
        EditText missingFoundField = findViewById(R.id.singleBikeMissingFoundEditText);
        EditText nameField = findViewById(R.id.singleBikeNameEditText);
        EditText phoneField = findViewById(R.id.singleBikePhoneEditText);
        // Spinner missingFoundField = findViewById(R.id.singleBikeMissingFoundEditText);
        String frameNumber = frameField.getText().toString().trim();
        String brand = brandField.getText().toString().trim();
        String color = colorField.getText().toString().trim();
        String place = placeField.getText().toString().trim();
        String name = nameField.getText().toString().trim();
        String phone = phoneField.getText().toString().trim();
        String firebaseUserId = mAuth.getCurrentUser().getUid();
        //String date = dateField.getText().toString().trim();
        String selectedType = (String) typeField.getSelectedItem();
        String selectedMissingFound = "found";

        //Spinner selectedType = (Spinner) typeField.getSelectedItem();
        //Spinner selectedMissingFound = (Spinner) missingFoundField.getSelectedItem();

        int errorColor = ContextCompat.getColor(getApplicationContext(), R.color.error);
        ForegroundColorSpan foregroundColorSpan = new ForegroundColorSpan(errorColor);

        if (frameNumber.isEmpty()) {
            String errorString = "Udfyld stelnummer";
            SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder(errorString);
            spannableStringBuilder.setSpan(foregroundColorSpan, 0, errorString.length(), 0);
            frameField.setError(spannableStringBuilder);
        } else if (brand.isEmpty()) {
            String errorString = "Udfyld cykel mærke";
            SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder(errorString);
            spannableStringBuilder.setSpan(foregroundColorSpan, 0, errorString.length(), 0);
            brandField.setError(spannableStringBuilder);
        } else if (color.isEmpty()) {
            String errorString = "Udfyld farve";
            SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder(errorString);
            spannableStringBuilder.setSpan(foregroundColorSpan, 0, errorString.length(), 0);
            colorField.setError(spannableStringBuilder);

        } else if (place.isEmpty()) {
            String errorString = "Udfyld placering";
            SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder(errorString);
            spannableStringBuilder.setSpan(foregroundColorSpan, 0, errorString.length(), 0);
            placeField.setError(spannableStringBuilder);

        } else if (name.isEmpty()) {
            String errorString = "Udfyld navn";
            SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder(errorString);
            spannableStringBuilder.setSpan(foregroundColorSpan, 0, errorString.length(), 0);
            nameField.setError(spannableStringBuilder);

        } else if (phone.isEmpty()) {
            String errorString = "Udfyld telefon nummer";
            SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder(errorString);
            spannableStringBuilder.setSpan(foregroundColorSpan, 0, errorString.length(), 0);
            phoneField.setError(spannableStringBuilder);
        } else {

            Bike bike = new Bike(frameNumber, selectedType, brand, color, place, "", 100, name, phone, selectedMissingFound, firebaseUserId);

            BikeFinderService bikeFinderService = ApiUtils.getBikeFinderService();

            Call<Bike> saveBikeCall = bikeFinderService.saveBikeBody(bike);
            progressBar.setVisibility(View.VISIBLE);
            saveBikeCall.enqueue(new Callback<Bike>() {
                @Override
                public void onResponse(Call<Bike> call, Response<Bike> response) {
                    progressBar.setVisibility(View.INVISIBLE);
                    if (response.isSuccessful()) {
                        Bike newBike = response.body();
                        Toast.makeText(getBaseContext(), "Cykel tilføjet", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(getBaseContext(), AfterUserLoggedIn.class);
                        FirebaseUser user = mAuth.getCurrentUser();
                        intent.putExtra("UserloggedIn", user.getEmail());
                        startActivity(intent);
                        progressBar.setVisibility(View.VISIBLE);
                    } else {
                        String problem = "problem" + response.code() + " " + response.message();
                        messageView.setText("Problem Opstået");
                    }
                    progressBar.setVisibility(View.INVISIBLE);
                }

                @Override
                public void onFailure(Call<Bike> call, Throwable t) {
                    progressBar.setVisibility(View.INVISIBLE);
                    messageView.setText(t.getMessage());
                }
            });
        }
    }



        public void backButtonClicked (View view){
            finish();
        }

   /* @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String text = parent.getItemAtPosition(position).toString();
    }*/

        @Override
        public void onItemSelected (AdapterView < ? > adapterView, View view,int position, long id){
            String text = adapterView.getItemAtPosition(position).toString();
        }


        @Override
        public void onNothingSelected (AdapterView < ? > parent){
        }

    }


