package com.jay.core.mvvm;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.List;

/**
 * create by hj on 2020/9/2
 **/
public class BaseFragmentViewPager extends FragmentPagerAdapter {

    private List<Fragment> fragments;
    private List<String> title;

    public BaseFragmentViewPager(@NonNull FragmentManager fm,
                                 List<Fragment> mFragment) {
        this(fm, mFragment, null);
    }

    public BaseFragmentViewPager(@NonNull FragmentManager fm,
                                 List<Fragment> mFragment, List<String> mTitle) {
        super(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        this.fragments = mFragment;
        this.title = mTitle;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        if (title != null) {
            return title.get(position);
        }
        return super.getPageTitle(position);
    }
}
