package edu.ucdenver.hertzallissa.zenwood;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;



public class EntryAdapter extends RecyclerView.Adapter<EntryAdapter.ListItemHolder> {
    @NonNull
    @Override
    public ListItemHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View listItem = LayoutInflater.from((parent.getContext()))
                .inflate(R.layout.list_layout, parent, false);

        return new ListItemHolder(listItem);
    }

    @Override
    public void onBindViewHolder(@NonNull ListItemHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class ListItemHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public ListItemHolder (View itemView){
            super (itemView);

        }

        @Override
        public void onClick(View view) {

        }
    }
}
