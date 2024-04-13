package com.x64technology.saveit.utilities;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.appcompat.app.AlertDialog;

import com.google.android.material.snackbar.Snackbar;
import com.x64technology.saveit.R;
import com.x64technology.saveit.database.AppDatabase;
import com.x64technology.saveit.database.FolderDao;
import com.x64technology.saveit.database.FolderNoteRefDao;
import com.x64technology.saveit.databinding.LayoutFolderAlertBinding;
import com.x64technology.saveit.models.FolderModel;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;

public class CustomAlertDialog {
    public static AlertDialog getFolderDialog(Context context, ViewGroup viewGroup, FolderModel folderModel) {

        FolderDao folderDao = AppDatabase.getInstance(context).folderDao();

        View view = LayoutInflater.from(context).inflate(R.layout.layout_folder_alert, viewGroup, false);
        LayoutFolderAlertBinding alertBinding = LayoutFolderAlertBinding.bind(view);

        if (folderModel != null) {
            alertBinding.folderNameInp.getEditText().setText(folderModel.name);
            alertBinding.folderColorRadio.clearCheck();
        }

        MaterialAlertDialogBuilder alertDialogBuilder = new MaterialAlertDialogBuilder(context)
                .setTitle("New Folder")
                .setView(alertBinding.getRoot())
                .setPositiveButton("Done", (dialog, which) -> {
                    String name = alertBinding.folderNameInp.getEditText().getText().toString();
                    if (name.equals("")) return;

                    int folderColorSuffix = 0;
                    int checkedRadioButtonId = alertBinding.folderColorRadio.getCheckedRadioButtonId();

                    if (checkedRadioButtonId == R.id.folder_blue)
                        folderColorSuffix = R.drawable.folder_blue;
                    else if (checkedRadioButtonId == R.id.folder_cyan)
                        folderColorSuffix = R.drawable.folder_cyan;
                    else if (checkedRadioButtonId == R.id.folder_indigo)
                        folderColorSuffix = R.drawable.folder_indigo;
                    else if (checkedRadioButtonId == R.id.folder_purple)
                        folderColorSuffix = R.drawable.folder_purple;
                    else if (checkedRadioButtonId == R.id.folder_teal)
                        folderColorSuffix = R.drawable.folder_teal;


                    if (folderModel != null) {
                        folderModel.setName(name);
                        folderModel.setColor(folderColorSuffix);
                        folderDao.updateFolder(folderModel);
                    } else folderDao.insertFolder(new FolderModel(name, folderColorSuffix));

                }).setNegativeButton("Cancel", (dialog, which) -> dialog.dismiss());

        return alertDialogBuilder.create();
    }

    public static AlertDialog removeNotesDialog(Context context, FolderModel folderModel) {
        FolderNoteRefDao folderNoteRefDao = AppDatabase.getInstance(context).folderNoteRefDao();

        MaterialAlertDialogBuilder alertDialogBuilder = new MaterialAlertDialogBuilder(context)
                .setTitle(context.getString(R.string.title_delete_note))
                .setMessage(context.getString(R.string.message_remove_notes))
                .setPositiveButton("Yes", (dialog, which) -> {
                    folderNoteRefDao.removeNotesFromFolder(folderModel.folderId);
                    ((Activity)(context)).finish();
                })
                .setNegativeButton("No", (dialog, which) -> dialog.dismiss());

        return alertDialogBuilder.create();
    }

    public static AlertDialog deleteFolderDialog(Context context, FolderModel folderModel) {
        FolderNoteRefDao folderNoteRefDao = AppDatabase.getInstance(context).folderNoteRefDao();
        FolderDao folderDao = AppDatabase.getInstance(context).folderDao();

        MaterialAlertDialogBuilder alertDialogBuilder = new MaterialAlertDialogBuilder(context)
                .setTitle(context.getString(R.string.title_delete_note))
                .setMessage(context.getString(R.string.message_delete_folder))
                .setPositiveButton("Yes", (dialog, which) -> {
                    folderDao.deleteFolder(folderModel);
                    folderNoteRefDao.removeNotesFromFolder(folderModel.folderId);
                    ((Activity)(context)).finish();
                }).setNegativeButton("No", (dialog, which) -> dialog.dismiss());

        return alertDialogBuilder.create();
    }
}
