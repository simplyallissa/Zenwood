package edu.ucdenver.hertzallissa.zenwood;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import edu.ucdenver.hertzallissa.zenwood.db.AppDatabase;
import edu.ucdenver.hertzallissa.zenwood.db.Entry;
import edu.ucdenver.hertzallissa.zenwood.db.EntryDao;
import edu.ucdenver.hertzallissa.zenwood.databinding.ActivityAddEntryBinding;

public class AddEntryActivity extends AppCompatActivity {

    private ActivityAddEntryBinding binding;
    private EntryDao entryDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAddEntryBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        Button addButton = findViewById(R.id.addButton);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int date = 12;
                int lastUpdate = 13;
                String emoji = "emoji";
                int rating;
                try {
                    rating = Integer.parseInt(binding.ratingEditText.getText().toString());
                } catch (NumberFormatException e){
                    Log.e("AddEntryActivity", "Error parsing rating: " + e.getMessage());
                    rating = 0;
                }
                String firstLine = binding.entryInputEditText.getText().toString();
                addEntryToDatabase(date, lastUpdate, firstLine, emoji, rating);
            }
        });
    }

    private void addEntryToDatabase(int date, int lastUpdate, String firstLine, String emoji, int rating) {
        AppDatabase db = AppDatabase.getDbInstance(this.getApplicationContext());

        Entry entry = new Entry(date, lastUpdate, firstLine, emoji, rating);
        db.entryDao().insertEntry(entry);
        Log.d("AddEntryActivity", "Entry added to database: " + entry.getId());

        finish();

    }
}
