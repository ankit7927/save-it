package com.x64technology.saveit.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;

import com.x64technology.saveit.R;
import com.x64technology.saveit.adapters.FolderAdapter;
import com.x64technology.saveit.database.AppDatabase;
import com.x64technology.saveit.database.FolderDao;
import com.x64technology.saveit.databinding.FragmentFoldersBinding;
import com.x64technology.saveit.interfaces.FolderFragment;
import com.x64technology.saveit.models.FolderModel;

public class FoldersFragment extends Fragment implements FolderFragment {
    FragmentFoldersBinding foldersBinding;
    FolderDao folderDao;
    FolderAdapter folderAdapter;
    Intent intent;
    public FoldersFragment() {}


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        foldersBinding = FragmentFoldersBinding.bind(inflater.inflate(R.layout.fragment_folders, container, false));

        folderDao = AppDatabase.getInstance(getActivity()).folderDao();

        folderAdapter = new FolderAdapter(getActivity(), this);

        foldersBinding.folderRecycler.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        foldersBinding.folderRecycler.setAdapter(folderAdapter);

        setCallbacks();

        return foldersBinding.getRoot();
    }

    private void setCallbacks() {
        folderDao.getAllFolders().observe(getActivity(), folderModels -> {
            if (folderModels.isEmpty()) {
                foldersBinding.folderRecycler.setVisibility(View.GONE);
                foldersBinding.noFolderText.setVisibility(View.VISIBLE);
            } else {
                foldersBinding.folderRecycler.setVisibility(View.VISIBLE);
                foldersBinding.noFolderText.setVisibility(View.GONE);
                folderAdapter.setData(folderModels);
            }
        });
    }

    @Override
    public void onFolderClick(FolderModel folderModel) {
        intent = new Intent(getActivity(), FolderNotesActivity.class);
        intent.putExtra(getString(R.string.folder_model), folderModel);
        startActivity(intent);
    }
}