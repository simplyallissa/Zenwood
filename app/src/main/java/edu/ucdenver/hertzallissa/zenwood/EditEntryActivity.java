package edu.ucdenver.hertzallissa.zenwood;

import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.util.Date;
import java.util.List;

import edu.ucdenver.hertzallissa.zenwood.databinding.ActivityEditEntryBinding;
import edu.ucdenver.hertzallissa.zenwood.db.AppDatabase;
import edu.ucdenver.hertzallissa.zenwood.db.Entry;

public class EditEntryActivity extends AppCompatActivity {

    private ActivityEditEntryBinding binding;
    private EntryAdapter entryAdapter;
    private String selectedEmoji = "";
    private long entryId = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityEditEntryBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setSupportActionBar(binding.editToolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle(null);

        entryAdapter = new EntryAdapter(this);

        entryId = getIntent().getLongExtra("ENTRY_ID", -1);
        Log.d("EditEntryActivity", "Entry ID received: " + entryId);

        String selectedEmoji = getIntent().getStringExtra("SELECTED_EMOJI");
        int selectedRating = getIntent().getIntExtra("SELECTED_RATING", -1);
        binding.rating2EditText.setText(String.valueOf(selectedRating));
        String selectedEntryText = getIntent().getStringExtra("SELECTED_ENTRY_TEXT");
        binding.entryInput2EditText.setText(String.valueOf(selectedEntryText));
        loadEntryDetails(entryId);
        onEmojiSelected(getEmojiResourceFromString(selectedEmoji));


        //This makes all of the emoji images clickable and when clicked passes in the resource id to onEmojiSelected
        binding.emojiImage2View1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onEmojiSelected(R.drawable.em_complicated);
            }
        });

        binding.emojiImage2View2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onEmojiSelected(R.drawable.em_good);
            }
        });

        binding.emojiImage2View3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onEmojiSelected(R.drawable.em_moderate);
            }
        });

        binding.emojiImage2View4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onEmojiSelected(R.drawable.em_poor);
            }
        });

        binding.emojiImage2View5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onEmojiSelected(R.drawable.em_shocking);
            }
        });

        Button editbutton = findViewById(R.id.updateButton);
        editbutton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                updateEntry();
            }
        });
    }

    private void loadEntryDetails(long entryId) {
        Log.d("EditEntryActivity", "Load Entry Details - Entry ID: " + entryId);
        // Load the entry details from the database using the entryId
        AppDatabase db = AppDatabase.getDbInstance(this.getApplicationContext());
        Entry entry = db.entryDao().getEntryById(entryId);
        Log.d("EditEntryActivity", "Load Entry Details - Entry ID: " + entryId);
        if (entry != null) {
            Log.d("EditEntryActivity", "Loaded Entry ID: " + entry.getId());
            selectedEmoji = entry.getEmoji();
            onEmojiSelected(getEmojiResourceFromString(selectedEmoji));
            binding.rating2EditText.setText(String.valueOf(entry.getRating()));
            binding.entryInput2EditText.setText(entry.getFirstLine());
        }
    }

    private void updateEntry() {
        Log.d("EditEntryActivity", "Update Entry method called");
        String emoji = selectedEmoji;
        if (selectedEmoji.isEmpty()) {
            showAlertDialog("You must select an emoji.");
            return;
        }
        int rating;
        try {
            rating = Integer.parseInt(binding.rating2EditText.getText().toString());
        } catch (NumberFormatException e) {
            Log.e("EditEntryActivity", "Error parsing rating: " + e.getMessage());
            showAlertDialog("Please enter a valid number.");
            return;
        }
        if (rating < 0 || rating > 10) {
            showAlertDialog("Your rating must be a number 1-10.");
            return;
        }
        String ratingText = binding.rating2EditText.getText().toString();
        if (ratingText.isEmpty()) {
            showAlertDialog("You must enter a rating.");
            return;
        }
        String firstLine = binding.entryInput2EditText.getText().toString();
        if (firstLine.isEmpty()) {
            showAlertDialog("You must enter something that you are grateful for.");
            return;
        }

        // Update the entry in the database
        AppDatabase db = AppDatabase.getDbInstance(this.getApplicationContext());
        Entry entry = db.entryDao().getEntryById(entryId);
        Log.d("EditEntryActivity", "Entry ID: " + entryId);
        if (entry != null) {
            entry.setEmoji(emoji);
            entry.setRating(rating);
            entry.setFirstLine(firstLine);
            entry.setLastUpdate(new Date().getTime());

            // Save the updated entry
            db.entryDao().updateEntry(entry);

            List<Entry> entryList = db.entryDao().getAllEntries();
            Log.d("AddEntryActivity", "Number of entries after insertion: " + entryList.size());

            entryAdapter.setEntryList(entryList);
            entryAdapter.notifyDataSetChanged();
            Log.d("EditEntryActivity", "Update Entry Clicked");

            finish();
        }
    }

    private int getEmojiResourceFromString(String emojiString) {
        switch (emojiString) {
            case "em_complicated":
                return R.drawable.em_complicated;
            case "em_good":
                return R.drawable.em_good;
            case "em_moderate":
                return R.drawable.em_moderate;
            case "em_poor":
                return R.drawable.em_poor;
            case "em_shocking":
                return R.drawable.em_shocking;
            default:
                return R.drawable.em_complicated;
        }
    }

    private void onEmojiSelected(int emojiResId) {
        selectedEmoji = getEmojiStringFromResource(emojiResId);

        resetEmojiImageViewsBackground();

        ImageView selectedImageView = getImageViewByEmojiResource(emojiResId);
        if (selectedImageView != null) {
            selectedImageView.setBackgroundColor(getResources().getColor(R.color.selectedEmojiBackground));
        }
    }

    private void resetEmojiImageViewsBackground() {
        binding.emojiImage2View1.setBackgroundColor(Color.TRANSPARENT);
        binding.emojiImage2View2.setBackgroundColor(Color.TRANSPARENT);
        binding.emojiImage2View3.setBackgroundColor(Color.TRANSPARENT);
        binding.emojiImage2View4.setBackgroundColor(Color.TRANSPARENT);
        binding.emojiImage2View5.setBackgroundColor(Color.TRANSPARENT);
    }

    private ImageView getImageViewByEmojiResource(int emojiResId) {
        if (emojiResId == R.drawable.em_complicated) {
            return binding.emojiImage2View1;
        } else if (emojiResId == R.drawable.em_good) {
            return binding.emojiImage2View2;
        } else if (emojiResId == R.drawable.em_moderate) {
            return binding.emojiImage2View3;
        } else if (emojiResId == R.drawable.em_poor) {
            return binding.emojiImage2View4;
        } else if (emojiResId == R.drawable.em_shocking) {
            return binding.emojiImage2View5;
        }
        return null;
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
            return "em_complicated";
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

    private void showAlertDialog(String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(message)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // You have to have this method even though we're not doing any handling here.
                    }
                });
        builder.create().show();
    }
}
