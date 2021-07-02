package com.example.myapplication.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.example.myapplication.R;
import com.example.myapplication.db.Notes;
import com.example.myapplication.recyclerview.MyRecyclerViewAdapter;
import com.example.myapplication.viewmodel.HomePageActivityViewModel;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;
import io.realm.Sort;

public class HomePageActivity extends AppCompatActivity implements MyRecyclerViewAdapter.ItemClickListener {

    private Button buttonStart;
    private RecyclerView recyclerViewDisplayAllNotes;
    private MyRecyclerViewAdapter adapter;
    private Realm realmDB;
    public static final String MY_PREFS_NAME = "MyPrefsFile";
    private HomePageActivityViewModel homePageActivityViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.homepage);

        Toolbar myToolBar = findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolBar);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(R.string.app_motto);
        }

        buttonStart = findViewById(R.id.button_startnotes);
        recyclerViewDisplayAllNotes = findViewById(R.id.recyclerview_display_all_files);
        Realm.init(this);
        realmDB=Realm.getDefaultInstance();

        homePageActivityViewModel = ViewModelProviders.of(HomePageActivity.this)
                .get(HomePageActivityViewModel.class);

        homePageActivityViewModel.init();

        homePageActivityViewModel.getNotes().observe(this, new Observer<List<Notes>>() {
            @Override
            public void onChanged(List<Notes> notes) {
                adapter.notifyDataSetChanged();
            }
        });

        SharedPreferences.Editor sharedPreferences = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE).edit();
        SharedPreferences sharedPreferences1 = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);

        if (sharedPreferences1.getString(getString(R.string.sharedpreference_key_layout), "").equals("")
        || sharedPreferences1.getString(getString(R.string.sharedpreference_key_layout),
                "").equals(getString(R.string.sharedpreference_key_linear))) {
            recyclerViewDisplayAllNotes.setLayoutManager(new LinearLayoutManager(this));
            sharedPreferences.putString(getString(R.string.sharedpreference_key_layout),
                    getString(R.string.sharedpreference_key_linear));
        } else if (sharedPreferences1.getString(getString(R.string.sharedpreference_key_layout),
                "").equals(getString(R.string.sharedprefernce_key_staggered))) {
            recyclerViewDisplayAllNotes.setLayoutManager(new StaggeredGridLayoutManager(2,
                    StaggeredGridLayoutManager.VERTICAL));
            sharedPreferences.putString(getString(R.string.sharedpreference_key_layout),
                    getString(R.string.sharedprefernce_key_staggered));
        }

        sharedPreferences.apply();

        adapter = new MyRecyclerViewAdapter(this,
                homePageActivityViewModel.getNotes().getValue());
        recyclerViewDisplayAllNotes.setAdapter(adapter);


        buttonStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomePageActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.homepage_menu, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch(item.getItemId()) {
            case R.id.action_find:
                break;
            case R.id.action_change_layout:
                checkRecyclerLayoutType();
                break;

        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onItemClick(View view, int position) {
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra(getString(R.string.note_id), getPreviouslyStoredNotes().get(position).getNotesID());
        startActivity(intent);
        finish();
    }

    private ArrayList<Notes> getPreviouslyStoredNotes() {
        RealmResults<Notes> notesData =realmDB.where(Notes.class).findAll().sort("timeOfModification", Sort.DESCENDING);
        return new ArrayList<>(notesData);
    }

    private void checkRecyclerLayoutType() {
        if (recyclerViewDisplayAllNotes.getLayoutManager() instanceof StaggeredGridLayoutManager) {
            recyclerViewDisplayAllNotes.setLayoutManager(new LinearLayoutManager(this));
        } else {
            recyclerViewDisplayAllNotes.setLayoutManager(new StaggeredGridLayoutManager(2,
                    StaggeredGridLayoutManager.VERTICAL));
        }
    }
}
