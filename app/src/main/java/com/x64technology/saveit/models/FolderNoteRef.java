package com.x64technology.saveit.models;

import androidx.room.Entity;
import androidx.room.Ignore;

import com.x64technology.saveit.utilities.Constants;

@Entity(tableName = Constants.FOLDER_NOTE_REF_TABLE_NAME, primaryKeys = {"folderId", "noteId"})
public class FolderNoteRef {
    public int folderId;
    public int noteId;

    public FolderNoteRef() {
    }

    @Ignore
    public FolderNoteRef(int folderId, int noteId) {
        this.folderId = folderId;
        this.noteId = noteId;
    }
}
