package edu.ucdenver.hertzallissa.zenwood;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import edu.ucdenver.hertzallissa.zenwood.databinding.ActivityAddEntryBinding;
import edu.ucdenver.hertzallissa.zenwood.databinding.ActivityEditEntryBinding;

public class EditEntryActivity extends AppCompatActivity {

    private ActivityEditEntryBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityEditEntryBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

    }
}
