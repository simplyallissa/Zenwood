package edu.ucdenver.hertzallissa.zenwood;

import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

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
        getSupportActionBar().setTitle(null);

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
                if (selectedEmoji.isEmpty()){
                    showAlertDialog("You must select an emoji.");
                    return;
                }
                int rating;
                try {
                    rating = Integer.parseInt(binding.ratingEditText.getText().toString());
                } catch (NumberFormatException e){
                    Log.e("AddEntryActivity", "Error parsing rating: " + e.getMessage());
                    rating = 0;
                }
                if (rating < 0 || rating > 10) {
                    showAlertDialog("Your rating must be a number 1-10.");
                    return;
                }
                String ratingText = binding.ratingEditText.getText().toString();
                if (ratingText.isEmpty()) {
                    showAlertDialog("You must enter a rating.");
                    return;
                }
                String firstLine = binding.entryInputEditText.getText().toString();
                if (firstLine.isEmpty()) {
                    showAlertDialog("You must enter something that you are grateful for.");
                    return;
                }
                Date currentDate = new Date();
                Date lastUpdate = new Date();
                addEntryToDatabase(currentDate, lastUpdate, firstLine, emoji, rating);
            }
        });
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
