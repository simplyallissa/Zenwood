package edu.ucdenver.hertzallissa.zenwood;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import edu.ucdenver.hertzallissa.zenwood.databinding.ActivityAddEntryBinding;
import edu.ucdenver.hertzallissa.zenwood.databinding.ActivityEditEntryBinding;
import edu.ucdenver.hertzallissa.zenwood.db.Entry;

public class EditEntryActivity extends AppCompatActivity {

    private ActivityEditEntryBinding binding;

    private Entry entry;

    public EditEntryActivity(Entry entry) {this.entry = entry;}

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityEditEntryBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

    }
}
