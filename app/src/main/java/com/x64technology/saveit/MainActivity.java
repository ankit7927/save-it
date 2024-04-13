package com.x64technology.saveit;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;
import androidx.viewpager2.widget.ViewPager2;

import com.x64technology.saveit.databinding.ActivityMainBinding;
import com.x64technology.saveit.activities.EditNoteActivity;
import com.x64technology.saveit.activities.FolderNotesActivity;
import com.x64technology.saveit.adapters.FolderAdapter;
import com.x64technology.saveit.adapters.NotesAdapter;
import com.x64technology.saveit.adapters.TabAdapter;
import com.x64technology.saveit.database.AppDatabase;
import com.x64technology.saveit.database.FolderDao;
import com.x64technology.saveit.database.NotesDao;
import com.x64technology.saveit.interfaces.FolderFragment;
import com.x64technology.saveit.interfaces.NoteFragment;
import com.x64technology.saveit.models.FolderModel;
import com.x64technology.saveit.models.NoteModel;
import com.x64technology.saveit.utilities.CustomAlertDialog;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements NoteFragment, FolderFragment {

    ActivityMainBinding mainBinding;
    TabAdapter tabAdapter;
    AlertDialog folderDialog;
    FolderDao folderDao;
    NotesDao notesDao;
    NotesAdapter notesAdapter;
    FolderAdapter folderAdapter;

    int currentTab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mainBinding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(mainBinding.getRoot());

        initVars();

        setCallbacks();
    }

    private void initVars() {
        tabAdapter = new TabAdapter(getSupportFragmentManager(), getLifecycle());
        mainBinding.viewpager2.setAdapter(tabAdapter);

        folderDialog = CustomAlertDialog.getFolderDialog(this, mainBinding.getRoot(), null);

        folderDao = AppDatabase.getInstance(this).folderDao();
        notesDao = AppDatabase.getInstance(this).notesDao();

        notesAdapter = new NotesAdapter(this, this);
        folderAdapter = new FolderAdapter(this, this);
    }


    private void setCallbacks() {
        mainBinding.tabbar.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                mainBinding.viewpager2.setCurrentItem(tab.getPosition());
                currentTab = tab.getPosition();

                if (tab.getPosition() == 0) {
                    mainBinding.searchRecyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
                    mainBinding.searchRecyclerView.setAdapter(notesAdapter);
                    mainBinding.searchView.setHint("Search Note");
                } else {
                    mainBinding.searchRecyclerView.setLayoutManager(new GridLayoutManager(MainActivity.this,2));
                    mainBinding.searchRecyclerView.setAdapter(folderAdapter);
                    mainBinding.searchView.setHint("Search Folder");
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        mainBinding.viewpager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                mainBinding.tabbar.selectTab(mainBinding.tabbar.getTabAt(position));
            }
        });

        mainBinding.newFab.setOnClickListener(v -> {
            if (currentTab == 0) startActivity(new Intent(MainActivity.this, EditNoteActivity.class));
            else if (currentTab == 1) folderDialog.show();
        });

        mainBinding.materialToolbar.setOnMenuItemClickListener(item -> {
            if (item.getItemId() == R.id.search_note_menu) {
                mainBinding.searchView.show();
            }
            return false;
        });

        mainBinding.searchView.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() != 0) {
                    if (currentTab == 0) {
                        System.out.println(s);
                        List<NoteModel> noteModelList = notesDao.searchNote(s.toString());
                        notesAdapter.setData(noteModelList);
                    } else {
                        List<FolderModel> folderModelList = folderDao.searchFolder(s.toString());
                        folderAdapter.setData(folderModelList);
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }


    @Override
    public void onNoteClick(NoteModel noteModel) {
        Intent intent = new Intent(this, EditNoteActivity.class);
        intent.putExtra("note", noteModel);
        startActivity(intent);
    }

    @Override
    public void onFolderClick(FolderModel folderModel) {
        Intent intent = new Intent(this, FolderNotesActivity.class);
        intent.putExtra("folder_model", folderModel);
        startActivity(intent);
    }
}