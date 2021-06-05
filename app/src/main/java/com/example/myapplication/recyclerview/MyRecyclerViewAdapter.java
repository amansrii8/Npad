package com.example.myapplication.recyclerview;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.db.Notes;

import java.util.ArrayList;


public class MyRecyclerViewAdapter extends RecyclerView.Adapter<MyRecyclerViewAdapter.ViewHolder> {

    private LayoutInflater myInflator;
    private ArrayList<Notes> notes;
    private ItemClickListener onItemClickListener;

    public MyRecyclerViewAdapter(Context context, ArrayList<Notes> notes) {
        myInflator = LayoutInflater.from(context);
        this.notes = new ArrayList<>(notes);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = myInflator.inflate(R.layout.recycler_row_display_all_files, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Notes note = notes.get(position);
        holder.noteDescription.setText(note.getData());
        holder.noteTitle.setText(note.getNotesName());
    }

    @Override
    public int getItemCount() {
        return notes.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView noteTitle;
        private TextView noteDescription;

        public ViewHolder(View itemHolder) {
            super(itemHolder);
            noteTitle = itemHolder.findViewById(R.id.textview_file_title);
            noteDescription = itemHolder.findViewById(R.id.textview_file_description);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (onItemClickListener != null) {
                onItemClickListener.onItemClick(view, getAdapterPosition());
            }
        }

        Notes getItem(int id) {
            return notes.get(id);
        }

        // allows clicks events to be caught
        void setClickListener(ItemClickListener itemClickListener) {
            onItemClickListener = itemClickListener;
        }
    }

    // parent activity will implement this method to respond to click events
    public interface ItemClickListener {
        void onItemClick(View view, int position);
    }
}
