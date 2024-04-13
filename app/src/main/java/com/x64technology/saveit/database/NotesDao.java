package com.x64technology.saveit.database;


import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.x64technology.saveit.models.NoteModel;

import java.util.List;

@Dao
public interface NotesDao {

    @Insert
    public void insertNote(NoteModel noteModel);

    @Delete
    public void deleteNote(NoteModel noteModel);

    @Update
    public void updateNote(NoteModel noteModel);

    @Query("SELECT * FROM notes")
    public LiveData<List<NoteModel>> getAllNotes();

    @Query("SELECT * FROM notes WHERE note_id IN (:noteIds)")
    List<NoteModel> getNotesByIds(List<Integer> noteIds);


    @Query("SELECT * FROM notes WHERE title LIKE '%' || :query || '%' OR content LIKE '%' || :query || '%'")
    List<NoteModel> searchNote(String query);

    @Query("DELETE FROM notes WHERE note_id IN (:noteIds)")
    void deleteNotes(List<Integer> noteIds);
}
