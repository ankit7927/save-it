package com.x64technology.saveit.models;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import com.x64technology.saveit.utilities.Constants;

import java.io.Serializable;
import java.util.Objects;

@Entity(tableName = Constants.NOTES_TABLE_NAME)
public class NoteModel implements Serializable {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = Constants.NOTE_ID)
    public int noteId;

    @ColumnInfo(name = Constants.NOTE_TITLE)
    public String title;

    @ColumnInfo(name = Constants.NOTE_CONTENT)
    public String  content;

    @ColumnInfo(name = Constants.NOTE_CREATED)
    public String created;

    @ColumnInfo(name = Constants.NOTE_UPDATED)
    public String updated;

    public NoteModel() {}

    @Ignore
    public NoteModel(String title, String content, String created, String updated) {
        this.title = title;
        this.content = content;
        this.created = created;
        this.updated = updated;
    }

    @Ignore
    public NoteModel(int id, String title, String content, String created, String updated) {
        this.noteId = id;
        this.title = title;
        this.content = content;
        this.created = created;
        this.updated = updated;
    }

    public void setId(int id) {
        this.noteId = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setCreated(String created) {
        this.created = created;
    }

    public void setUpdated(String updated) {
        this.updated = updated;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        NoteModel noteModel = (NoteModel) o;
        return noteId == noteModel.noteId && Objects.equals(title, noteModel.title) && Objects.equals(content, noteModel.content) && Objects.equals(created, noteModel.created) && Objects.equals(updated, noteModel.updated);
    }
}
