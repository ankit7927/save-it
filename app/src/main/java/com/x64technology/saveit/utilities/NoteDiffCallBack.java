package com.x64technology.saveit.utilities;

import androidx.recyclerview.widget.DiffUtil;

import com.x64technology.saveit.models.NoteModel;

import java.util.List;

public class NoteDiffCallBack extends DiffUtil.Callback {
    List<NoteModel> oldNotes;
    List<NoteModel> newNotes;

    public NoteDiffCallBack(List<NoteModel> oldNotes, List<NoteModel> newNotes) {
        this.oldNotes = oldNotes;
        this.newNotes = newNotes;
    }

    @Override
    public int getOldListSize() {
        return oldNotes.size();
    }

    @Override
    public int getNewListSize() {
        return newNotes.size();
    }

    @Override
    public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
        return oldNotes.get(oldItemPosition).noteId == newNotes.get(newItemPosition).noteId;
    }

    @Override
    public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
        return oldNotes.get(oldItemPosition).equals(newNotes.get(newItemPosition));
    }
}
