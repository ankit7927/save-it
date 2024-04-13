package com.x64technology.saveit.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.x64technology.saveit.R;
import com.x64technology.saveit.databinding.LayoutFolderBinding;
import com.x64technology.saveit.interfaces.FolderFragment;
import com.x64technology.saveit.models.FolderModel;
import com.x64technology.saveit.utilities.FolderDiffCallback;

import java.util.ArrayList;
import java.util.List;

public class FolderAdapter extends RecyclerView.Adapter<FolderAdapter.FolderViewHolder> {

    List<FolderModel> folderModels = new ArrayList<>();
    Context context;
    FolderFragment folderFragment;

    public FolderAdapter(Context context, FolderFragment folderFragment) {
        this.context = context;
        this.folderFragment = folderFragment;
    }

    @NonNull
    @Override
    public FolderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.layout_folder, parent, false);
        return new FolderViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FolderViewHolder holder, int position) {
        FolderModel folderModel = folderModels.get(position);
        holder.folderBinding.textView.setText(folderModel.name);
        holder.folderBinding.folderImage.setImageDrawable(context.getDrawable(folderModel.color));

        holder.itemView.setOnClickListener(v -> folderFragment.onFolderClick(folderModel));
    }

    @Override
    public int getItemCount() {
        return folderModels.size();
    }

    public void setData(List<FolderModel> newList) {
        DiffUtil.Callback callback = new FolderDiffCallback(folderModels, newList);
        DiffUtil.DiffResult result = DiffUtil.calculateDiff(callback);

        folderModels.clear();
        folderModels.addAll(newList);

        result.dispatchUpdatesTo(this);
    }


    public static class FolderViewHolder extends RecyclerView.ViewHolder {
        LayoutFolderBinding folderBinding;
        public FolderViewHolder(@NonNull View itemView) {
            super(itemView);
            folderBinding = LayoutFolderBinding.bind(itemView);
        }
    }
}
