package com.x64technology.saveit.adapters;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.x64technology.saveit.activities.AllNotesFragment;
import com.x64technology.saveit.activities.FoldersFragment;

public class TabAdapter extends FragmentStateAdapter {

    public TabAdapter(@NonNull FragmentManager fragment, Lifecycle lifecycle) {
        super(fragment, lifecycle);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        if (position == 0) return new AllNotesFragment();
        else return new FoldersFragment();
    }

    @Override
    public int getItemCount() {
        return 2;
    }
}
