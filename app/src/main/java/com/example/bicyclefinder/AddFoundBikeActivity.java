package com.example.bicyclefinder;

        import androidx.appcompat.app.AppCompatActivity;

        import android.content.Intent;
        import android.os.Bundle;
        import android.view.View;
        import android.widget.AdapterView;
        import android.widget.EditText;
        import android.widget.ProgressBar;
        import android.widget.Spinner;
        import android.widget.TextView;
        import android.widget.Toast;

        import com.google.firebase.auth.FirebaseAuth;

        import retrofit2.Call;
        import retrofit2.Callback;
        import retrofit2.Response;

public class AddFoundBikeActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener  {
    private ProgressBar progressBar;
    private TextView messageView;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_found_bike);
        progressBar = findViewById(R.id.addBikeProgressbar);
        messageView = findViewById(R.id.singleBikeMessageTextView);
        mAuth = FirebaseAuth.getInstance();
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
        EditText dateField = findViewById(R.id.singleBikeDateEditText);
        EditText missingFoundField = findViewById(R.id.singleBikeMissingFoundEditText);
        // Spinner missingFoundField = findViewById(R.id.singleBikeMissingFoundEditText);

        String frameNumber = frameField.getText().toString().trim();
        String brand = brandField.getText().toString().trim();
        String color = colorField.getText().toString().trim();
        String place = placeField.getText().toString().trim();
        String date = dateField.getText().toString().trim();
        String selectedType = (String) typeField.getSelectedItem();
        String selectedMissingFound = "found";

        //Spinner selectedType = (Spinner) typeField.getSelectedItem();
        //Spinner selectedMissingFound = (Spinner) missingFoundField.getSelectedItem();

        BikeFinderService bikeFinderService = ApiUtils.getBikeFinderService();
        Bike bike = new Bike(frameNumber, selectedType, brand, color, place, date, selectedMissingFound);


        Call<Bike> saveBikeCall = bikeFinderService.saveBikeBody(bike);
        progressBar.setVisibility(View.VISIBLE);
        saveBikeCall.enqueue(new Callback<Bike>() {
            @Override
            public void onResponse(Call<Bike> call, Response<Bike> response) {
                progressBar.setVisibility(View.INVISIBLE);
                if(response.isSuccessful()) {
                    Bike newBike = response.body();
                    Toast.makeText(getBaseContext(),  "Cykel tilføjet", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(getBaseContext(), AfterUserLoggedIn.class);
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

    public void backButtonClicked (View view) {
        finish();
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String text = parent.getItemAtPosition(position).toString();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
