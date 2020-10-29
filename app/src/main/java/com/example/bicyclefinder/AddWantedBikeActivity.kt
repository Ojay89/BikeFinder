package com.example.bicyclefinder

import android.content.Intent
import android.os.Bundle
import android.text.SpannableStringBuilder
import android.text.style.ForegroundColorSpan
import android.view.View
import android.widget.*
import android.widget.AdapterView.OnItemSelectedListener
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.google.firebase.auth.FirebaseAuth
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AddWantedBikeActivity : AppCompatActivity(), OnItemSelectedListener {
    private var progressBar: ProgressBar? = null
    private var messageView: TextView? = null
    private var mAuth: FirebaseAuth? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_bike)
        mAuth = FirebaseAuth.getInstance()
        progressBar = findViewById(R.id.addBikeProgressbar)
        messageView = findViewById(R.id.singleBikeMessageTextView)
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
        val spinner = findViewById<Spinner>(R.id.singleBikeKindOfBicycleSpinner)
        val adapter: ArrayAdapter<*> = ArrayAdapter.createFromResource(this, R.array.types_array, R.layout.spinner_dropdown_layout)
        spinner.adapter = adapter
        spinner.onItemSelectedListener = this
    }

    fun addBikeButtonClicked(view: View?) {
        val frameField = findViewById<EditText>(R.id.singleBikeFrameNumberEditText)
        val typeField = findViewById<Spinner>(R.id.singleBikeKindOfBicycleSpinner)
        //EditText typeField = findViewById(R.id.singleBikeKindOfBicycleEditText);
        val brandField = findViewById<EditText>(R.id.singleBikeBrandEditText)
        val colorField = findViewById<EditText>(R.id.singleBikeColorEditText)
        val placeField = findViewById<EditText>(R.id.singleBikePlaceEditText)
        val dateField = findViewById<EditText>(R.id.singleBikeDateEditText)
        val nameField = findViewById<EditText>(R.id.singleBikeNameEditText)
        val phoneField = findViewById<EditText>(R.id.singleBikePhoneEditText)
        //EditText missingFoundField = findViewById(R.id.singleBikeMissingFoundEditText);
        //EditText missingFoundField = findViewById(R.id.singleBikeMissingFoundEditText);
        val frameNumber = frameField.text.toString().trim { it <= ' ' }
        val brand = brandField.text.toString().trim { it <= ' ' }
        val color = colorField.text.toString().trim { it <= ' ' }
        val place = placeField.text.toString().trim { it <= ' ' }
        //        String date = dateField.getText().toString().trim();
        val name = nameField.text.toString().trim { it <= ' ' }
        val phone = phoneField.text.toString().trim { it <= ' ' }
        val firebaseUserId = mAuth!!.currentUser!!.uid
        val selectedType = typeField.selectedItem as String
        val selectedMissingFound = "missing"

        //Spinner selectedType = (Spinner) typeField.getSelectedItem();
        //Spinner selectedMissingFound = (Spinner) missingFoundField.getSelectedItem();
        val errorColor = ContextCompat.getColor(applicationContext, R.color.error)
        val foregroundColorSpan = ForegroundColorSpan(errorColor)
        if (frameNumber.isEmpty()) {
            val errorString = "Udfyld stelnummer"
            val spannableStringBuilder = SpannableStringBuilder(errorString)
            spannableStringBuilder.setSpan(foregroundColorSpan, 0, errorString.length, 0)
            frameField.error = spannableStringBuilder
        } else if (brand.isEmpty()) {
            val errorString = "Udfyld cykel mærke"
            val spannableStringBuilder = SpannableStringBuilder(errorString)
            spannableStringBuilder.setSpan(foregroundColorSpan, 0, errorString.length, 0)
            brandField.error = spannableStringBuilder
        } else if (color.isEmpty()) {
            val errorString = "Udfyld farve"
            val spannableStringBuilder = SpannableStringBuilder(errorString)
            spannableStringBuilder.setSpan(foregroundColorSpan, 0, errorString.length, 0)
            colorField.error = spannableStringBuilder
        } else if (place.isEmpty()) {
            val errorString = "Udfyld placering"
            val spannableStringBuilder = SpannableStringBuilder(errorString)
            spannableStringBuilder.setSpan(foregroundColorSpan, 0, errorString.length, 0)
            placeField.error = spannableStringBuilder
        } else if (name.isEmpty()) {
            val errorString = "Udfyld navn"
            val spannableStringBuilder = SpannableStringBuilder(errorString)
            spannableStringBuilder.setSpan(foregroundColorSpan, 0, errorString.length, 0)
            nameField.error = spannableStringBuilder
        } else if (phone.isEmpty()) {
            val errorString = "Udfyld telefon nummer"
            val spannableStringBuilder = SpannableStringBuilder(errorString)
            spannableStringBuilder.setSpan(foregroundColorSpan, 0, errorString.length, 0)
            phoneField.error = spannableStringBuilder
        } else {
            val bike = Bike(frameNumber, selectedType, brand, color, place, "", 100, name, phone, selectedMissingFound, firebaseUserId)
            val bikeFinderService = ApiUtils.getBikeFinderService()
            val saveBikeCall = bikeFinderService.saveBikeBody(bike)
            progressBar!!.visibility = View.VISIBLE
            saveBikeCall.enqueue(object : Callback<Bike?> {
                override fun onResponse(call: Call<Bike?>, response: Response<Bike?>) {
                    progressBar!!.visibility = View.INVISIBLE
                    if (response.isSuccessful) {
                        val newBike = response.body()
                        Toast.makeText(baseContext, "Cykel tilføjet", Toast.LENGTH_SHORT).show()
                        val intent = Intent(baseContext, AfterUserLoggedIn::class.java)
                        val user = mAuth!!.currentUser
                        intent.putExtra("UserloggedIn", user!!.email)
                        startActivity(intent)
                        progressBar!!.visibility = View.VISIBLE
                    } else {
                        val problem = "problem" + response.code() + " " + response.message()
                        messageView!!.text = "Problem Opstået"
                    }
                    progressBar!!.visibility = View.INVISIBLE
                }

                override fun onFailure(call: Call<Bike?>, t: Throwable) {
                    progressBar!!.visibility = View.INVISIBLE
                    messageView!!.text = t.message
                }
            })
        }
    }

    fun backButtonClicked(view: View?) {
        finish()
    }

    override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
        val text = parent.getItemAtPosition(position).toString()
    }

    override fun onNothingSelected(parent: AdapterView<*>?) {}
}