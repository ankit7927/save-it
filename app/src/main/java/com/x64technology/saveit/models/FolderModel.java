package com.x64technology.saveit.models;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import com.x64technology.saveit.utilities.Constants;

import java.io.Serializable;


@Entity(tableName = Constants.FOLDER_TABLE_NAME)
public class FolderModel implements Serializable {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = Constants.FOLDER_ID)
    public int folderId;

    @ColumnInfo(name = Constants.FOLDER_NAME)
    public String name;

    @ColumnInfo(name = Constants.FOLDER_COLOR)
    public int color;

    public FolderModel() {}

    @Ignore
    public FolderModel(String name, int color) {
        this.name = name;
        this.color = color;
    }

    @Ignore
    public FolderModel(int id, String name) {
        this.folderId = id;
        this.name = name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setColor(int color) {
        this.color = color;
    }
}
