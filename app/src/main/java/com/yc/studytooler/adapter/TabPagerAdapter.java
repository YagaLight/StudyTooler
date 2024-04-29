package com.yc.studytooler.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.yc.studytooler.fragment.PageOneFragment;
import com.yc.studytooler.fragment.PageTwoFragment;

/**
 * @ClassName TabPagerAdapter
 * @Descripttion TODO
 * @Author chaoyue
 * @Date 2024/4/2 3:02
 * @VERSION 1.0
 */
public class TabPagerAdapter extends FragmentStateAdapter {


    public TabPagerAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position) {
            case 0:
                return new PageOneFragment();  // 返回第一个Fragment
            case 1:
                return new PageTwoFragment();  // 返回第二个Fragment
            default:
                throw new IllegalStateException("Unexpected position: " + position);
        }
    }

    @Override
    public int getItemCount() {
        return 3;
    }
}
