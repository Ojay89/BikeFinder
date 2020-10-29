package com.example.bicyclefinder;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.android.material.bottomappbar.BottomAppBar;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;

public class AfterUserLoggedIn extends AppCompatActivity {

    private BottomAppBar mBottomAppBar;
    private FloatingActionButton floatingActionButton;
    private ProgressBar progressBar;
    private TextView floatingText1;
    private TextView floatingText2;
    private FirebaseAuth mAuth;
    TextView welcomeName;


    FloatingActionButton fab, fab1, fab2;
    boolean isFabOpen = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_after_user_logged_in);
        progressBar = findViewById(R.id.mainProgressbar);
        floatingText1 = findViewById(R.id.floatingActionButtonBikeFoundText);
        floatingText2 = findViewById(R.id.floatingActionButtonBikeWantedText);
        fab = findViewById(R.id.floatingActionButton);
        fab1 = findViewById(R.id.floatingActionButtonBikeWanted);
        fab2 = findViewById(R.id.floatingActionButtonBikeFound);
        FabAnimation.init(fab1);
        FabAnimation.init(fab2);
        mAuth = FirebaseAuth.getInstance();

        Intent intent = getIntent();
        intent.getStringExtra("UserloggedIn");
        welcomeName = findViewById(R.id.afterLoggedInTextTextView);
        welcomeName.setText("Hej, " + intent.getStringExtra( "UserloggedIn"));
        welcomeName.setEnabled(false);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                animateFab();

            }
        });

        fab1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                animateFab();
                Intent intent = new Intent(getBaseContext(), AddWantedBikeActivity.class);
                startActivity(intent);

            }
        });

        fab2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                animateFab();
                Intent intent = new Intent(getBaseContext(), AddFoundBikeActivity.class);
                startActivity(intent);
            }
        });

    }
    private void animateFab()
    {
        isFabOpen = FabAnimation.rotateFab(fab, !isFabOpen);
        floatingText1.setVisibility(View.VISIBLE);
        floatingText2.setVisibility(View.VISIBLE);
        if(isFabOpen)
        {
            FabAnimation.fabOpen(fab1);
            FabAnimation.fabOpen(fab2);
        }
        else
        {
            floatingText1.setVisibility(View.INVISIBLE);
            floatingText2.setVisibility(View.INVISIBLE);
            FabAnimation.fabClosed(fab1);
            FabAnimation.fabClosed(fab2);

        }


    }


    public void wanted_bikesClicked(MenuItem item) {
        progressBar.setVisibility(View.VISIBLE);
        Intent intent = new Intent(AfterUserLoggedIn.this, ShowAllMissingBikesActivity.class);
        startActivity(intent);
        progressBar.setVisibility(View.INVISIBLE);
    }


    public void found_bikesClicked(MenuItem item) {
        progressBar.setVisibility(View.VISIBLE);
        Intent intent = new Intent(AfterUserLoggedIn.this, ShowAllFoundBikesActivity.class);
        startActivity(intent);
        progressBar.setVisibility(View.INVISIBLE);
    }

    public void all_bikesClicked(MenuItem item) {
        progressBar.setVisibility(View.VISIBLE);
        Intent intent = new Intent(AfterUserLoggedIn.this, ShowAllBikesActivity.class);
        startActivity(intent);
        progressBar.setVisibility(View.INVISIBLE);
    }

    public void myBikesClicked(MenuItem item) {
        progressBar.setVisibility(View.VISIBLE);
        Intent intent = new Intent(AfterUserLoggedIn.this, ShowMyBikesActivity.class);
        startActivity(intent);
        progressBar.setVisibility(View.INVISIBLE);
    }

    public void logout_clicked(MenuItem item) {
        mAuth.signOut();
        finish();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.app_bar_menu, menu);
        return true;
    }
}


