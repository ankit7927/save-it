package com.x64technology.saveit.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.x64technology.saveit.R;
import com.x64technology.saveit.adapters.NotesAdapter;
import com.x64technology.saveit.database.AppDatabase;
import com.x64technology.saveit.database.FolderNoteRefDao;
import com.x64technology.saveit.database.NotesDao;
import com.x64technology.saveit.databinding.ActivityFolderNotesBinding;
import com.x64technology.saveit.models.FolderModel;
import com.x64technology.saveit.models.NoteModel;
import com.x64technology.saveit.utilities.CustomAlertDialog;

import java.util.List;

public class FolderNotesActivity extends AppCompatActivity {

    ActivityFolderNotesBinding folderNotesBinding;
    Intent intent;
    NotesDao notesDao;
    FolderNoteRefDao folderNoteRefDao;
    NotesAdapter adapter;
    FolderModel folderModel;
    AlertDialog alertDialog, removeNotesDialog, deleteFolderDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        folderNotesBinding = ActivityFolderNotesBinding.inflate(getLayoutInflater());
        setContentView(folderNotesBinding.getRoot());

        iniVars();

        setCallbacks();
    }

    private void iniVars() {
        intent = getIntent();

        notesDao = AppDatabase.getInstance(this).notesDao();
        folderNoteRefDao = AppDatabase.getInstance(this).folderNoteRefDao();

        adapter = new NotesAdapter(this, noteModel -> {
            intent = new Intent(FolderNotesActivity.this, EditNoteActivity.class);
            intent.putExtra(getString(R.string.note_model), noteModel);
            startActivity(intent);
        });

        folderNotesBinding.folderNoteRecycler.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
        folderNotesBinding.folderNoteRecycler.setAdapter(adapter);

        if (intent.hasExtra(getString(R.string.folder_model))) {
            folderModel = (FolderModel) intent.getSerializableExtra(getString(R.string.folder_model));

            folderNotesBinding.toolbarFolderNote.setTitle(folderModel.name);

            List<Integer> notesInFolder = folderNoteRefDao.getNotesInFolder(folderModel.folderId);

            List<NoteModel> notesByIds = notesDao.getNotesByIds(notesInFolder);
            if (notesByIds.size()==0) {
                folderNotesBinding.folderNoteRecycler.setVisibility(View.GONE);
                folderNotesBinding.emptyFolderText.setVisibility(View.VISIBLE);
            } else {
                adapter.setData(notesByIds);
                folderNotesBinding.folderNoteRecycler.setVisibility(View.VISIBLE);
                folderNotesBinding.emptyFolderText.setVisibility(View.GONE);
            }

        }

        alertDialog = CustomAlertDialog.getFolderDialog(this, folderNotesBinding.getRoot(), folderModel);
        removeNotesDialog = CustomAlertDialog.removeNotesDialog(this, folderModel);
        deleteFolderDialog = CustomAlertDialog.deleteFolderDialog(this, folderModel);
    }

    private void setCallbacks() {
        folderNotesBinding.toolbarFolderNote.setNavigationOnClickListener(v -> getOnBackPressedDispatcher().onBackPressed());

        folderNotesBinding.toolbarFolderNote.setOnMenuItemClickListener(item -> {
            if (item.getItemId() == R.id.menu_remove_all)
                removeNotesDialog.show();
            else if (item.getItemId() == R.id.menu_delete_folder)
                deleteFolderDialog.show();
            return false;
        });
    }
}