package edu.ucdenver.hertzallissa.zenwood;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Date;
import java.util.List;

import edu.ucdenver.hertzallissa.zenwood.db.AppDatabase;
import edu.ucdenver.hertzallissa.zenwood.db.Entry;
import edu.ucdenver.hertzallissa.zenwood.databinding.ActivityAddEntryBinding;

public class AddEntryActivity extends AppCompatActivity {

    private ActivityAddEntryBinding binding;
    private EntryAdapter entryAdapter;
    private String selectedEmoji = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAddEntryBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setSupportActionBar(binding.addToolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        entryAdapter = new EntryAdapter(this);

        binding.emojiImageView1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onEmojiSelected(R.drawable.em_complicated);
            }
        });

        binding.emojiImageView2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onEmojiSelected(R.drawable.em_good);
            }
        });

        binding.emojiImageView3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onEmojiSelected(R.drawable.em_moderate);
            }
        });

        binding.emojiImageView4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onEmojiSelected(R.drawable.em_poor);
            }
        });

        binding.emojiImageView5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onEmojiSelected(R.drawable.em_shocking);
            }
        });


        Button addButton = findViewById(R.id.addButton);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String emoji = selectedEmoji;
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
    // Method to handle emoji selection
    private void onEmojiSelected(int emojiResId) {
        // Set the selected emoji
        selectedEmoji = getEmojiStringFromResource(emojiResId);

        // You may also highlight the selected emoji or provide visual feedback to the user if needed
        // For example, change the background color of the selected ImageView

        // Reset the background color for all ImageViews (unselect all)
        resetEmojiImageViewsBackground();

        // Set a background color for the selected ImageView (to indicate selection)
        ImageView selectedImageView = getImageViewByEmojiResource(emojiResId);
        if (selectedImageView != null) {
            selectedImageView.setBackgroundColor(getResources().getColor(R.color.selectedEmojiBackground));
        }

        if (emojiResId == R.drawable.em_complicated) {
            // Handle the case for em_complicated
        } else if (emojiResId == R.drawable.em_good) {
            // Handle the case for em_good
        } else if (emojiResId == R.drawable.em_moderate) {
            // Handle the case for em_moderate
        } else if (emojiResId == R.drawable.em_poor) {
            // Handle the case for em_poor
        } else if (emojiResId == R.drawable.em_shocking) {
            // Handle the case for em_shocking
        }
        // Add more else-if blocks as needed
    }

    // Method to reset the background color of all emoji ImageViews
    private void resetEmojiImageViewsBackground() {
        binding.emojiImageView1.setBackgroundColor(Color.TRANSPARENT);
        binding.emojiImageView2.setBackgroundColor(Color.TRANSPARENT);
        binding.emojiImageView3.setBackgroundColor(Color.TRANSPARENT);
        binding.emojiImageView4.setBackgroundColor(Color.TRANSPARENT);
        binding.emojiImageView5.setBackgroundColor(Color.TRANSPARENT);
    }

    private ImageView getImageViewByEmojiResource(int emojiResId) {
        ImageView imageView = null;
        if (emojiResId == R.drawable.em_complicated) {
            imageView = binding.emojiImageView1;
        } else if (emojiResId == R.drawable.em_good) {
            imageView = binding.emojiImageView2;
        } else if (emojiResId == R.drawable.em_moderate) {
            imageView = binding.emojiImageView3;
        } else if (emojiResId == R.drawable.em_poor) {
            imageView = binding.emojiImageView4;
        } else if (emojiResId == R.drawable.em_shocking) {
            imageView = binding.emojiImageView5;
        }
        return imageView;
    }


    private String getEmojiStringFromResource(int resId) {
        if (resId == R.drawable.em_complicated) {
            return "em_complicated";
        } else if (resId == R.drawable.em_good) {
            return "em_good";
        } else if (resId == R.drawable.em_moderate) {
            return "em_moderate";
        } else if (resId == R.drawable.em_poor) {
            return "em_poor";
        } else if (resId == R.drawable.em_shocking) {
            return "em_shocking";
        } else {
            return ""; // Handle the default case or throw an exception if needed
        }
    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == android.R.id.home || id == R.id.action_back) {
            onBackPressed();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void addEntryToDatabase(Date date, Date lastUpdate, String firstLine, String emoji, int rating) {
        AppDatabase db = AppDatabase.getDbInstance(this.getApplicationContext());

        long dateTimpestamp = date.getTime();
        Entry entry = new Entry(dateTimpestamp, dateTimpestamp, firstLine, selectedEmoji, rating);
        entry.setDate(formatDate(dateTimpestamp));
        entry.setLastUpdate(formatDate(lastUpdate.getTime()));
        long entryId = db.entryDao().insertEntry(entry);
        // This is to log the entry id after it is added
        List<Entry> entryList = db.entryDao().getAllEntries();
        Log.d("AddEntryActivity", "Number of entries after insertion: " + entryList.size());

        entryAdapter.setEntryList(entryList);
        entryAdapter.notifyDataSetChanged();

        Log.d("AddEntryActivity", "Entry added to database: " + entryId);

        finish();
    }

    private long formatDate(long timestamp) {
        return timestamp;
    }

}
