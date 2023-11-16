package edu.ucdenver.hertzallissa.zenwood;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ConstraintLayout welcomeScreen = findViewById(R.id.welcome_screen);

        welcomeScreen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Create an Intent to start the HomeActivity
                Intent intent = new Intent(MainActivity.this, HomeActivity.class);

                // Start the HomeActivity
                startActivity(intent);
            }
        });

    }

}