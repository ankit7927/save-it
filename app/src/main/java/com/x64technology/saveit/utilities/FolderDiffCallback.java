package com.x64technology.saveit.utilities;

import androidx.recyclerview.widget.DiffUtil;

import com.x64technology.saveit.models.FolderModel;

import java.util.List;

public class FolderDiffCallback extends DiffUtil.Callback {

    List<FolderModel> oldList, newList;

    public FolderDiffCallback(List<FolderModel> oldList, List<FolderModel> newList) {
        this.oldList = oldList;
        this.newList = newList;
    }

    @Override
    public int getOldListSize() {
        return oldList.size();
    }

    @Override
    public int getNewListSize() {
        return newList.size();
    }

    @Override
    public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
        return oldList.get(oldItemPosition).folderId == newList.get(newItemPosition).folderId;
    }

    @Override
    public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
        FolderModel oldModel = oldList.get(oldItemPosition);
        FolderModel newModel = newList.get(newItemPosition);

        return oldModel.folderId == newModel.folderId && oldModel.name.equals(newModel.name);
    }
}
