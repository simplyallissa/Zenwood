package edu.ucdenver.hertzallissa.zenwood;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import edu.ucdenver.hertzallissa.zenwood.db.AppDatabase;
import edu.ucdenver.hertzallissa.zenwood.db.Entry;
import edu.ucdenver.hertzallissa.zenwood.db.EntryDao;
import edu.ucdenver.hertzallissa.zenwood.databinding.ActivityAddEntryBinding;

public class AddEntryActivity extends AppCompatActivity {

    private ActivityAddEntryBinding binding;
    private EntryAdapter entryAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAddEntryBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        entryAdapter = new EntryAdapter(this);


        Button addButton = findViewById(R.id.addButton);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String emoji = "emoji";
                int rating;
                try {
                    rating = Integer.parseInt(binding.ratingEditText.getText().toString());
                } catch (NumberFormatException e){
                    Log.e("AddEntryActivity", "Error parsing rating: " + e.getMessage());
                    rating = 0;
                }
                String firstLine = binding.entryInputEditText.getText().toString();
                Date currentDate = new Date();
                Date lastUpdate = new Date();
                addEntryToDatabase(currentDate, lastUpdate, firstLine, emoji, rating);
            }
        });
    }

    private void addEntryToDatabase(Date date, Date lastUpdate, String firstLine, String emoji, int rating) {
        AppDatabase db = AppDatabase.getDbInstance(this.getApplicationContext());

        Entry entry = new Entry(firstLine, emoji, rating);
        entry.setDate(formatDate(date));
        entry.setLastUpdate(formatDate(lastUpdate));
        long entryId = db.entryDao().insertEntry(entry);
        // This is to log the entry id after it is added
        List<Entry> entryList = db.entryDao().getAllEntries();
        Log.d("AddEntryActivity", "Number of entries after insertion: " + entryList.size());

        entryAdapter.setEntryList(entryList);
        entryAdapter.notifyDataSetChanged();

        Log.d("AddEntryActivity", "Entry added to database: " + entryId);

        finish();
    }

    private String formatDate(Date date) {
        SimpleDateFormat outputFormat = new SimpleDateFormat("MM/dd/yy");
        return outputFormat.format(date);
    }

}
