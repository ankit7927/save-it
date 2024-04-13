package com.x64technology.saveit.utilities;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.appcompat.app.AlertDialog;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.x64technology.saveit.R;
import com.x64technology.saveit.database.AppDatabase;
import com.x64technology.saveit.database.FolderDao;
import com.x64technology.saveit.database.FolderNoteRefDao;
import com.x64technology.saveit.databinding.LayoutFolderAlertBinding;
import com.x64technology.saveit.models.FolderModel;

public class CustomAlertDialog {
    public static AlertDialog getFolderDialog(Context context, ViewGroup viewGroup, FolderModel folderModel) {

        FolderDao folderDao = AppDatabase.getInstance(context).folderDao();

        View view = LayoutInflater.from(context).inflate(R.layout.layout_folder_alert, viewGroup, false);
        LayoutFolderAlertBinding alertBinding = LayoutFolderAlertBinding.bind(view);

        if (folderModel != null) {
            alertBinding.folderNameInp.getEditText().setText(folderModel.name);
        }

        MaterialAlertDialogBuilder alertDialogBuilder = new MaterialAlertDialogBuilder(context)
                .setTitle("New Folder")
                .setView(alertBinding.getRoot())
                .setPositiveButton("Done", (dialog, which) -> {
                    String name = alertBinding.folderNameInp.getEditText().getText().toString();
                    if (name.isEmpty()) return;

                    if (folderModel != null) {
                        folderModel.setName(name);

                        folderDao.updateFolder(folderModel);
                    } else folderDao.insertFolder(new FolderModel(name));

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
