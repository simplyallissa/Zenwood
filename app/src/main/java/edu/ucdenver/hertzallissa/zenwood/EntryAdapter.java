package edu.ucdenver.hertzallissa.zenwood;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import edu.ucdenver.hertzallissa.zenwood.db.Entry;

public class EntryAdapter extends RecyclerView.Adapter<EntryAdapter.ListItemHolder> {

    private Context context;
    private HomeActivity homeActivity;
    private List<Entry> entryList;
    public EntryAdapter(Context context){
        this.context = context;
    }

    public EntryAdapter(HomeActivity homeActivity, ArrayList<Entry> entryList) {
        this.homeActivity = homeActivity;
        this.entryList = entryList;
    }

    public void setEntryList(List<Entry> entryList) {
        this.entryList = entryList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ListItemHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View listItem = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_layout, parent, false);


        return new ListItemHolder(listItem);
    }

    @Override
    public void onBindViewHolder(@NonNull ListItemHolder holder, int position) {
        Entry entry = entryList.get (position);

        holder.dateTextView.setText(String.valueOf(entry.getDate()));
        holder.firstLineTextView.setText(entry.getFirstLine());
        holder.lastUpdateTextView.setText(String.valueOf(entry.getLastUpdate()));
        holder.ratingTextView.setText(String.valueOf(entry.getRating()));

        holder.itemView.setTag(entry);

    }

    @Override
    public int getItemCount() {
        return entryList.size();
    }

    public class ListItemHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView dateTextView;

        private TextView firstLineTextView;

        private TextView lastUpdateTextView;

        private ImageView emojiImageView;

        private TextView ratingTextView;

        private ImageView editButton;

        public ListItemHolder(View itemView) {
            super(itemView);

            dateTextView = itemView.findViewById(R.id.dateTextView);
            firstLineTextView = itemView.findViewById(R.id.firstLineTextView);
            lastUpdateTextView = itemView.findViewById(R.id.lastUpdateTextView);
            emojiImageView = itemView.findViewById(R.id.emojiImageView);
            ratingTextView = itemView.findViewById(R.id.ratingTextView);
            editButton = itemView.findViewById(R.id.editImageView);

        }


        @Override
        public void onClick(View view) {
            // Handle item click if needed
            Entry entry = (Entry) itemView.getTag();
            Intent intent = new Intent(context, EditEntryActivity.class);
            intent.putExtra("entryId", entry.getId());
            context.startActivity(intent);

        }
    }
}
