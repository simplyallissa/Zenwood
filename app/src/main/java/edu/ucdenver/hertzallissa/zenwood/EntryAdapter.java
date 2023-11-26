package edu.ucdenver.hertzallissa.zenwood;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import edu.ucdenver.hertzallissa.zenwood.db.AppDatabase;
import edu.ucdenver.hertzallissa.zenwood.db.Entry;

public class EntryAdapter extends RecyclerView.Adapter<EntryAdapter.ListItemHolder> {

    private Context context;
    private HomeActivity homeActivity;
    private List<Entry> entryList;
    public EntryAdapter(Context context){
        this.context = context;
        this.entryList = new ArrayList<>();
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

        return new ListItemHolder(listItem, parent.getContext());
    }

    @Override
    public void onBindViewHolder(@NonNull ListItemHolder holder, int position) {
        Entry entry = entryList.get (position);

        int emojiResId = getEmojiResourceId(entry.getEmoji());

        holder.dateTextView.setText(formatDate(entry.getDate()));
        holder.firstLineTextView.setText(entry.getFirstLine());
        holder.lastUpdateTextView.setText(formatDate(entry.getLastUpdate()));
        holder.ratingTextView.setText(String.valueOf(entry.getRating()));

        holder.updateEmoji(emojiResId);
        holder.itemView.setTag(entry);

        if (homeActivity != null) {
            homeActivity.updateNoEntriesTextViewVisibility(entryList);
        }
    }

    private int getEmojiResourceId(String emoji) {
        switch (emoji) {
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

    @Override
    public int getItemCount() {
        return entryList.size();
    }

    public void removeFromEntryList(Entry entry) {
        entryList.remove(entry);
        notifyDataSetChanged();
    }

    private String formatDate(long timestamp) {
        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yy");
        return sdf.format(new Date(timestamp));
    }

    public class ListItemHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private Context context;
        private TextView dateTextView;
        private TextView firstLineTextView;
        private TextView lastUpdateTextView;
        private ImageView emojiImageView;
        private TextView ratingTextView;
        private ImageView editButton;
        private ImageView deleteButton;

        public void updateEmoji(int emojiResId) {
            emojiImageView.setImageResource(emojiResId);
        }

        public ListItemHolder(View itemView, Context context) {
            super(itemView);

            dateTextView = itemView.findViewById(R.id.dateTextView);
            firstLineTextView = itemView.findViewById(R.id.firstLineTextView);
            lastUpdateTextView = itemView.findViewById(R.id.lastUpdateTextView);
            emojiImageView = itemView.findViewById(R.id.emojiImageView);
            ratingTextView = itemView.findViewById(R.id.ratingTextView);
            editButton = itemView.findViewById(R.id.editImageView);

            itemView.setOnClickListener(this);

            deleteButton = itemView.findViewById(R.id.deleteImageView);
            deleteButton.setOnClickListener(this);

            editButton = itemView.findViewById(R.id.editImageView);
            editButton.setOnClickListener(this);

            this.context = context;

        }


        @Override
        public void onClick(View view) {
            int id = view.getId();

            if (id == R.id.editImageView) {
                Entry entry = (Entry) itemView.getTag();
                Intent intent = new Intent(context, EditEntryActivity.class);
                intent.putExtra("entryId", entry.getId());
                context.startActivity(intent);
            } else if (id == R.id.deleteImageView) {
                Entry entry = (Entry) itemView.getTag();
                deleteEntry(entry);
            }
        }

        private void deleteEntry(Entry entry) {
            int position = getAbsoluteAdapterPosition();
            if (position != RecyclerView.NO_POSITION) {

                entryList.remove(position);
                notifyItemRemoved(position);

                if (homeActivity != null) {
                    homeActivity.removeFromEntryList(entry);
                    homeActivity.updateNoEntriesTextViewVisibility(entryList);
                }

                AppDatabase db = AppDatabase.getDbInstance(context);
                AsyncTask.execute(() -> {
                    db.entryDao().deleteEntry(entry);
                });
            }
        }
    }
}
