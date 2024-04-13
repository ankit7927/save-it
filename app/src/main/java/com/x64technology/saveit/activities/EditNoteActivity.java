package com.x64technology.saveit.activities;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.widget.EditText;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.snackbar.Snackbar;
import com.x64technology.saveit.R;
import com.x64technology.saveit.database.AppDatabase;
import com.x64technology.saveit.database.FolderDao;
import com.x64technology.saveit.database.FolderNoteRefDao;
import com.x64technology.saveit.database.NotesDao;
import com.x64technology.saveit.databinding.ActivityEditNoteBinding;
import com.x64technology.saveit.models.FolderModel;
import com.x64technology.saveit.models.FolderNoteRef;
import com.x64technology.saveit.models.NoteModel;
import com.x64technology.saveit.utilities.DateHelper;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;

import java.util.List;

public class EditNoteActivity extends AppCompatActivity {
    ActivityEditNoteBinding editNoteBinding;
    NotesDao notesDao;
    FolderNoteRefDao folderNoteRefDao;
    FolderDao folderDao;
    AlertDialog deleteDialog, addFolderDialog;
    NoteModel noteModel;
    Intent intent;
    Snackbar snackbar;
    EditText content;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        editNoteBinding = ActivityEditNoteBinding.inflate(getLayoutInflater());
        setContentView(editNoteBinding.getRoot());

        initVars();

        setCallbacks();

        folderDialog();
    }

    private void initVars() {
        intent = getIntent();
        if (intent.hasExtra(getString(R.string.note_model))) {
            noteModel = (NoteModel) intent.getSerializableExtra(getString(R.string.note_model));

            if (noteModel != null) {
                editNoteBinding.titleInp.setText(noteModel.title);
                editNoteBinding.contentInp.setText(noteModel.content);
            }
        }
        notesDao = AppDatabase.getInstance(this).notesDao();
        folderNoteRefDao = AppDatabase.getInstance(this).folderNoteRefDao();
        folderDao = AppDatabase.getInstance(this).folderDao();

        content = editNoteBinding.contentInp;

        snackbar = Snackbar.make(this, editNoteBinding.getRoot(), "Saved", Snackbar.LENGTH_SHORT);
    }

    private void setCallbacks() {
        deleteDialog = new MaterialAlertDialogBuilder(this)
                .setTitle(getString(R.string.title_delete_note))
                .setMessage(getString(R.string.message_delete_note))
                .setPositiveButton("Yes", (dialog, which) -> {
                    if (noteModel != null) notesDao.deleteNote(noteModel);
                    dialog.dismiss();
                    finish();
                }).setNegativeButton("No", (dialog, which) -> dialog.dismiss())
                .create();




        editNoteBinding.editToolbar.setNavigationOnClickListener(v -> getOnBackPressedDispatcher().onBackPressed());
        editNoteBinding.editToolbar.setOnMenuItemClickListener(item -> {
            if (item.getItemId() == R.id.note_save_menu) save();
            else if (item.getItemId() == R.id.note_delete_menu) {
                deleteDialog.show();
            } else if (item.getItemId() == R.id.add_to_folder) {
                addFolderDialog.show();
            }
            return false;
        });
    }

    private void save() {
        String title = editNoteBinding.titleInp.getEditableText().toString().trim();
        String contentStr = content.getText().toString().trim();



        if (title.isEmpty() && contentStr.isEmpty()) return;

        String dateFormat = DateHelper.getDateTimeString();

        if (noteModel != null) {
            if (title.equals(noteModel.title) && contentStr.equals(noteModel.content)) return;
            noteModel.setTitle(title);
            noteModel.setContent(contentStr);
            noteModel.setUpdated(dateFormat);
            
            notesDao.updateNote(noteModel);
        } else {
            noteModel = new NoteModel(title, contentStr, dateFormat, dateFormat);
            notesDao.insertNote(noteModel);
        }
        snackbar.show();
    }

    void folderDialog() {
        MaterialAlertDialogBuilder builder = new MaterialAlertDialogBuilder(this);
        builder.setTitle(R.string.title_add_to_folder);
        builder.setPositiveButton("Cancel", (dialog, which) -> dialog.dismiss());

        if (noteModel != null) {

            List<Integer> foldersId = folderNoteRefDao.getFoldersInNote(noteModel.noteId);
            List<FolderModel> modelList = folderDao.getFolders();
            String[] names = new String[modelList.size()];
            boolean[] checkedItem = new boolean[modelList.size()];

            for (int j = 0; j < modelList.size(); j++) {
                names[j] = modelList.get(j).name;

                for (int i = 0; i < foldersId.size(); i++) {
                    if (modelList.get(j).folderId == foldersId.get(i)) {
                        checkedItem[i] = true;
                        break;
                    }
                }
            }

            builder
                    .setMultiChoiceItems(names, checkedItem, (dialog, which, isChecked) -> {
                        FolderNoteRef folderNoteRef = new FolderNoteRef(modelList.get(which).folderId, noteModel.noteId);
                        if (isChecked) {
                            folderNoteRefDao.insertRef(folderNoteRef);
                        } else {
                            folderNoteRefDao.deleteRef(folderNoteRef);
                        }
                    });
        } else builder.setMessage(getString(R.string.message_unsaved_note));

        addFolderDialog = builder.create();
    }
}