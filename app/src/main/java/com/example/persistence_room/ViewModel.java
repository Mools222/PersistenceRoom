package com.example.persistence_room;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.util.Log;

import com.example.persistence_room.database.AppDatabase;
import com.example.persistence_room.database.User;

import java.util.List;

public class ViewModel extends AndroidViewModel {

    // Constant for logging
    private static final String TAG = ViewModel.class.getSimpleName();

    private LiveData<List<User>> list;

    public ViewModel(Application application) {
        super(application);
        AppDatabase database = AppDatabase.getInstance(this.getApplication());
        Log.d(TAG, "Actively retrieving the tasks from the DataBase");
        list = database.userDao().getAll();
    }

    public LiveData<List<User>> getList() {
        return list;
    }
}