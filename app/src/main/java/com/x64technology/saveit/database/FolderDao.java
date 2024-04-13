package com.x64technology.saveit.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.x64technology.saveit.models.FolderModel;

import java.util.List;

@Dao
public interface FolderDao {

    @Insert
    void insertFolder(FolderModel folderModel);

    @Delete
    void deleteFolder(FolderModel folderModel);

    @Update
    void updateFolder(FolderModel folderModel);

    @Query("SELECT * FROM folders")
    LiveData<List<FolderModel>> getAllFolders();

    @Query("SELECT * FROM folders")
    List<FolderModel> getFolders();

    @Query("SELECT * FROM folders WHERE folder_name LIKE '%' || :query || '%'")
    List<FolderModel> searchFolder(String query);
}
