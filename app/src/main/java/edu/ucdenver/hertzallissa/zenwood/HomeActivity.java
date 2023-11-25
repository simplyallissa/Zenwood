package edu.ucdenver.hertzallissa.zenwood;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import edu.ucdenver.hertzallissa.zenwood.db.AppDatabase;
import edu.ucdenver.hertzallissa.zenwood.databinding.ActivityHomeBinding;
import edu.ucdenver.hertzallissa.zenwood.db.Entry;

public class HomeActivity extends AppCompatActivity {

    private ActivityHomeBinding binding;

    private EntryAdapter entryAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityHomeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        entryAdapter = new EntryAdapter(this);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        binding.content.recyclerView.setLayoutManager(layoutManager);
        binding.content.recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        binding.content.recyclerView.setAdapter(entryAdapter);

        loadEntryList();
    }

    private void loadEntryList() {
        AppDatabase db = AppDatabase.getDbInstance(this.getApplicationContext());
        List<Entry> entryList = db.entryDao().getAllEntries();

        Log.d("HomeActivity", "Number of entries: " + entryList.size());

        entryAdapter.setEntryList(entryList);
        entryAdapter.notifyDataSetChanged();

    }

    public void goToAddEntryScreen(View view){
        Intent intent = new Intent(HomeActivity.this, AddEntryActivity.class);
        startActivity(intent);
    }


//    public boolean onOptionsItemSelected(MenuItem item) {
//        int id = item.getItemId();
//
//        if(id == R.id.) {
//
//        }
//        return super.onOptionsItemSelected(item);
//    }
    @Override
    protected void onResume(){
        super.onResume();
        loadEntryList();

    }

}
