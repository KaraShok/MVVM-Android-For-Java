package com.karashok.library.common.databinding.view_pager;


import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.karashok.library.common.base.BaseFragment;

import java.util.List;

/**
 * @author KaraShokZ(zhangyaozhong)
 * @name ViewPagerFragmentStateAdapter
 * DESCRIPTION ViewPager的FragmentStatePagerAdapter
 * @date 2018/06/16/下午6:18
 */
public class ViewPagerFragmentStateAdapter extends FragmentStatePagerAdapter {

    private List<BaseFragment> mFragments;
    private List<String> mTites;

    public ViewPagerFragmentStateAdapter(FragmentManager fm) {
        super(fm);
    }

    public void setFragments(List<BaseFragment> fragments) {
        this.mFragments = fragments;
        notifyDataSetChanged();
    }

    public void setTites(List<String> mTites) {
        this.mTites = mTites;
    }

    @Override
    public Fragment getItem(int position) {
        return mFragments.get(position);
    }

    @Override
    public int getCount() {
        return mFragments != null ? mFragments.size() : 0;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mTites.get(position);
    }
}
