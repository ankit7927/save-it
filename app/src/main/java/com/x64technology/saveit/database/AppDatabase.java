package com.x64technology.saveit.database;


import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.x64technology.saveit.R;
import com.x64technology.saveit.models.FolderModel;
import com.x64technology.saveit.models.FolderNoteRef;
import com.x64technology.saveit.models.NoteModel;

@Database(entities = {NoteModel.class, FolderModel.class, FolderNoteRef.class }, version = 4, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {
    public abstract NotesDao notesDao();
    public abstract FolderDao folderDao();
    public abstract FolderNoteRefDao folderNoteRefDao();

    private static volatile AppDatabase instance;

    public static AppDatabase getInstance(final Context context) {
        if (instance==null) {
            synchronized (AppDatabase.class) {
                if (instance == null) {
                    instance = Room.databaseBuilder(context.getApplicationContext(), AppDatabase.class, context.getString(R.string.database_name))
                            .allowMainThreadQueries()
                            .fallbackToDestructiveMigration()
                            .build();
                }
            }
        } return instance;
    }
}
