package com.dreamer.liketaptapdetailpage;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.content.ContextCompat;
import android.support.v4.graphics.ColorUtils;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;

import com.dreamer.liketaptapdetailpage.fragment.ContentFragment;
import com.gyf.barlibrary.ImmersionBar;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();

    private AppBarLayout mAppBarLayout;
    private Toolbar mToolbar;
    private TabLayout mTablayout;
    private ViewPager mViewPager;

    private String[] tabs = {"介绍", "详情", "评论"};
    private List<Fragment> fragments;

    private int distance;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initViews();
        initData();
        setListener();
    }

    private void initViews() {
        mAppBarLayout = findViewById(R.id.appbarlayout);
        mToolbar = findViewById(R.id.toolbar);
        mTablayout = findViewById(R.id.tablayout);
        mViewPager = findViewById(R.id.viewpager);

        // 用了一个适配状态栏库
        ImmersionBar.setTitleBar(this, mToolbar);

    }

    private void initData() {

        // 这个100是我在布局写死的，也就是那个介绍信息的布局高度，状态栏的高度 + 介绍信息的高度
        distance = dp2px(100) + getResources().getDimensionPixelSize(R.dimen.status_bar_height);

        fragments = new ArrayList<>();
        fragments.add(ContentFragment.newInstance(getResources().getColor(android.R.color.holo_orange_light)));
        fragments.add(ContentFragment.newInstance(getResources().getColor(android.R.color.holo_purple)));
        fragments.add(ContentFragment.newInstance(getResources().getColor(android.R.color.holo_red_dark)));

        mViewPager.setOffscreenPageLimit(3);
        mViewPager.setAdapter(new TabAdapter(getSupportFragmentManager()));
        mTablayout.setupWithViewPager(mViewPager);
    }

    private int dp2px(int dp) {
        return (int) (getResources().getDisplayMetrics().density * dp + 0.5f);
    }

    private void setListener() {
        mAppBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {

                int dy = Math.abs(verticalOffset);
                if (dy >= distance) {
                    mToolbar.setBackgroundColor(ColorUtils.blendARGB(Color.TRANSPARENT,
                            ContextCompat.getColor(MainActivity.this, android.R.color.holo_blue_dark), 1));
                    Log.i(TAG, "y=" + verticalOffset + ", ratio=1" + ", distance=" + distance);
                } else {
                    float ratio = dy / (float) distance;
                    mToolbar.setBackgroundColor(ColorUtils.blendARGB(Color.TRANSPARENT,
                            ContextCompat.getColor(MainActivity.this, android.R.color.holo_blue_dark), ratio));
                    Log.i(TAG, "y=" + verticalOffset + ", ratio=" + ratio + ", distance=" + distance);
                }
            }
        });
    }

    // adapter
    class TabAdapter extends FragmentPagerAdapter {
        public TabAdapter(FragmentManager fm) {
            super(fm);
        }

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
            return tabs[position];
        }
    }

}
