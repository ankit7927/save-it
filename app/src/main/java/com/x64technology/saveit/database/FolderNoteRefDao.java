package com.x64technology.saveit.database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.x64technology.saveit.models.FolderNoteRef;

import java.util.List;

@Dao
public interface FolderNoteRefDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    public void insertRef(FolderNoteRef folderNoteRef);

    @Delete
    public void deleteRef(FolderNoteRef folderNoteRef);

    @Query("SELECT noteId FROM folder_note_ref WHERE folderId = :folderId")
    List<Integer> getNotesInFolder(int folderId);

    @Query("SELECT folderId FROM folder_note_ref WHERE noteId = :noteId")
    List<Integer> getFoldersInNote(int noteId);

    @Query("DELETE FROM folder_note_ref WHERE folderId = :folderId")
    void removeNotesFromFolder(int folderId);
}
