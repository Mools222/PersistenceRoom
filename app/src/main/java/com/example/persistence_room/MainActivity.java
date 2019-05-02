package com.example.persistence_room;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;

import com.example.persistence_room.database.AppDatabase;
import com.example.persistence_room.database.User;

import java.util.List;

/**
 * Tutorials:
 * https://classroom.udacity.com/courses/ud851 (lesson 8)
 * https://developer.android.com/training/data-storage/room/index.html#java
 * <p>
 * Requirements:
 * 1. Add the dependencies in the build.gradle file
 * implementation "android.arch.persistence.room:runtime:1.1.1"
 * annotationProcessor "android.arch.persistence.room:compiler:1.1.1"
 * implementation "android.arch.lifecycle:extensions:1.1.1"
 * annotationProcessor "android.arch.lifecycle:compiler:1.1.1"
 */

public class MainActivity extends AppCompatActivity {
    private AppDatabase appDatabase;
    private MyAdapter myAdapter;
    private EditText editText1;
    private EditText editText2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editText1 = findViewById(R.id.et1);
        editText2 = findViewById(R.id.et2);

        appDatabase = AppDatabase.getInstance(getApplicationContext());

        RecyclerView recyclerView = findViewById(R.id.rv);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        myAdapter = new MyAdapter();
        recyclerView.setAdapter(myAdapter);

        setupViewModel();
    }

    private void setupViewModel() {
        ViewModel viewModel = ViewModelProviders.of(this).get(ViewModel.class);
        viewModel.getList().observe(this, new Observer<List<User>>() {
            @Override
            public void onChanged(@Nullable List<User> list) {
                myAdapter.changeData(list);
            }
        });
    }

    public void write(View view) {
        final User user = new User(editText1.getText().toString(), editText2.getText().toString());

        AppExecutors.getInstance().diskIO().execute(new Runnable() {
            @Override
            public void run() {
                appDatabase.userDao().insertAll(user);
            }
        });
    }

    public void delete(View view) {
        AppExecutors.getInstance().diskIO().execute(new Runnable() {
            @Override
            public void run() {
                appDatabase.userDao().deleteAll();
            }
        });
    }
}
