package edu.ucdenver.hertzallissa.zenwood;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import edu.ucdenver.hertzallissa.zenwood.databinding.ActivityAddEntryBinding;
import edu.ucdenver.hertzallissa.zenwood.databinding.ActivityEditEntryBinding;
import edu.ucdenver.hertzallissa.zenwood.db.AppDatabase;
import edu.ucdenver.hertzallissa.zenwood.db.Entry;
import edu.ucdenver.hertzallissa.zenwood.db.EntryDao;

public class EditEntryActivity extends AppCompatActivity {

    private ActivityEditEntryBinding binding;

    private EntryDao entryDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityEditEntryBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Button editbutton = findViewById(R.id.updateButton);
        editbutton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                //TODO: Need some logic here to either get user input
                //OR just use what is in the database if there is no user entry.
//                editExistingEntry();
            }
        });
    }
    private void editExistingEntry() {
        AppDatabase db = AppDatabase.getDbInstance(this.getApplicationContext());

        //TODO: retrieve the current entry. This is similar to what we did with the phone book hw4 assignment where you will need to get it by ID.
        finish();
    }
}
