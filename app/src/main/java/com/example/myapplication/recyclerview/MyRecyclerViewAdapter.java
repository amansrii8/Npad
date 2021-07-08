package com.example.myapplication.recyclerview;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.activities.HomePageActivity;
import com.example.myapplication.db.Notes;

import java.util.ArrayList;
import java.util.List;


public class MyRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private LayoutInflater myInflator;
    private ArrayList<Object> noteObjects;
    private ItemClickListener onItemClickListener;
    private ItemLongClickListener onItemLongClickListener;

    public static final int TYPE_HEADER = 0;
    public static final int TYPE_ITEM = 1;

    public MyRecyclerViewAdapter(Context context, List<Object> notes) {
        myInflator = LayoutInflater.from(context);
        noteObjects = new ArrayList<>(notes);
        onItemClickListener = (HomePageActivity)context;
        onItemLongClickListener = (HomePageActivity)context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        if (viewType == TYPE_HEADER) {
            return new HeaderViewHolder(myInflator.inflate(R.layout.recycler_header,
                    parent, false));
        } else {
            return new ItemViewHolder(myInflator.inflate(R.layout.recycler_row_display_all_files,
                    parent, false));
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        if (getItemViewType(position) == TYPE_HEADER) {
            ((HeaderViewHolder)holder).noteHeader.setText(String.valueOf((noteObjects.get(position))));
            ((HeaderViewHolder)holder).position = position;
        } else {
             Notes note = (Notes) noteObjects.get(position);
            ((ItemViewHolder)holder).noteDescription.setText(note.getData());
            ((ItemViewHolder)holder).noteTitle.setText(note.getNotesName());
            ((ItemViewHolder)holder).position = position;
        }
    }

    @Override
    public int getItemCount() {
        Log.d("Size of List", "" + noteObjects.size());
        return noteObjects.size();
    }

    @Override
    public int getItemViewType(int position) {
       if (noteObjects.get(position) instanceof Notes) {
           return TYPE_ITEM;
       } else {
           return TYPE_HEADER;
       }
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener,
            View.OnLongClickListener {

        private TextView noteTitle;
        private TextView noteDescription;
        private int position;

        public ItemViewHolder(View itemHolder) {
            super(itemHolder);
            noteTitle = itemHolder.findViewById(R.id.textview_file_title);
            noteDescription = itemHolder.findViewById(R.id.textview_file_description);
            itemView.setOnClickListener(this);
            itemView.setOnLongClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (onItemClickListener != null) {
                onItemClickListener.onItemClick(view, position);
            }
        }

        @Override
        public boolean onLongClick(View v) {
            if (onItemLongClickListener != null) {
                onItemLongClickListener.onItemLongClick(v,
                        ((Notes)(noteObjects).get(position)).getNotesID());
            }

            return true;
        }
    }

    public class HeaderViewHolder extends RecyclerView.ViewHolder {

        private TextView noteHeader;
        private int position;

        public HeaderViewHolder(View itemHolder) {
            super(itemHolder);
            noteHeader = itemHolder.findViewById(R.id.textView_recycler_header);
        }
    }

    // parent activity will implement this method to respond to click events
    public interface ItemClickListener {
        void onItemClick(View view, int position);
    }

    public interface ItemLongClickListener {
        void onItemLongClick(View view, String noteId);
    }



}
