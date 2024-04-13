package com.x64technology.saveit.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.x64technology.saveit.R;
import com.x64technology.saveit.databinding.LayoutNoteBinding;
import com.x64technology.saveit.interfaces.NoteFragment;
import com.x64technology.saveit.models.NoteModel;
import com.x64technology.saveit.utilities.DateHelper;
import com.x64technology.saveit.utilities.NoteDiffCallBack;

import java.util.ArrayList;
import java.util.List;

public class NotesAdapter extends RecyclerView.Adapter<NotesAdapter.NotesViewHolder> {
    List<NoteModel> noteModelList = new ArrayList<>();
    Context context;
    NoteFragment noteFragment;
    public NotesAdapter(Context context, NoteFragment noteFragment) {
        this.context = context;
        this.noteFragment = noteFragment;
    }

    @NonNull
    @Override
    public NotesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_note, parent, false);
        return new NotesViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NotesViewHolder holder, int position) {
        NoteModel noteModel = noteModelList.get(position);

        String timeAgo = DateHelper.calcTimeAgo(noteModel.updated);

        holder.noteBinding.noteUpdated.setText(timeAgo);

        if (noteModel.title.isEmpty())
            holder.noteBinding.noteTitle.setVisibility(View.GONE);
        else
            holder.noteBinding.noteTitle.setText(noteModel.title);

        holder.noteBinding.noteContent.setText(noteModel.content);

        holder.itemView.setOnClickListener(v -> noteFragment.onNoteClick(noteModel));
    }

    @Override
    public int getItemCount() {
        return noteModelList.size();
    }

    public static class NotesViewHolder extends RecyclerView.ViewHolder {
        LayoutNoteBinding noteBinding;
        public NotesViewHolder(@NonNull View itemView) {
            super(itemView);
            noteBinding = LayoutNoteBinding.bind(itemView);
        }
    }

    public void setData(List<NoteModel> newNotes) {
        DiffUtil.Callback callback = new NoteDiffCallBack(noteModelList, newNotes);
        DiffUtil.DiffResult result = DiffUtil.calculateDiff(callback);

        noteModelList.clear();
        noteModelList.addAll(newNotes);

        result.dispatchUpdatesTo(this);
    }
}
