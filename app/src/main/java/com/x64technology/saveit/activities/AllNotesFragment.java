package com.x64technology.saveit.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.x64technology.saveit.R;
import com.x64technology.saveit.adapters.NotesAdapter;
import com.x64technology.saveit.database.AppDatabase;
import com.x64technology.saveit.database.NotesDao;
import com.x64technology.saveit.databinding.FragmentAllNotesBinding;
import com.x64technology.saveit.interfaces.NoteFragment;
import com.x64technology.saveit.models.NoteModel;

public class AllNotesFragment extends Fragment implements NoteFragment {
    FragmentAllNotesBinding allNotesBinding;
    NotesDao notesDao;
    NotesAdapter adapter;
    Intent intent;

    public AllNotesFragment() {}


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_all_notes, container, false);
        allNotesBinding = FragmentAllNotesBinding.bind(root);

        notesDao = AppDatabase.getInstance(getActivity()).notesDao();

        adapter = new NotesAdapter(getActivity(), this);

        allNotesBinding.notesRecycler.setLayoutManager(new StaggeredGridLayoutManager(2, RecyclerView.VERTICAL));
        allNotesBinding.notesRecycler.setAdapter(adapter);


        notesDao.getAllNotes().observe(getViewLifecycleOwner(), noteModels -> {
            adapter.setData(noteModels);
            if (noteModels.isEmpty()){
                allNotesBinding.notesRecycler.setVisibility(View.INVISIBLE);
                allNotesBinding.noNoteText.setVisibility(View.VISIBLE);
            } else {
                allNotesBinding.noNoteText.setVisibility(View.INVISIBLE);
                allNotesBinding.notesRecycler.setVisibility(View.VISIBLE);
            }
        });

        return root;
    }

    @Override
    public void onNoteClick(NoteModel noteModel) {
        intent = new Intent(getActivity(), EditNoteActivity.class);
        intent.putExtra(getString(R.string.note_model), noteModel);
        startActivity(intent);
    }
}