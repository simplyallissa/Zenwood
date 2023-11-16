package edu.ucdenver.hertzallissa.zenwood;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

public class HomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

    }

    public void goToAddEntryScreen(View view){
        Intent intent = new Intent(HomeActivity.this, AddEntryActivity.class);
        startActivity(intent);
    }
}
