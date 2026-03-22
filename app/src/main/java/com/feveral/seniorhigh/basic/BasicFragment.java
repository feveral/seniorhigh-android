package com.feveral.seniorhigh.basic;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.tabs.TabLayout;
import androidx.viewpager.widget.ViewPager;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;

import com.feveral.seniorhigh.BaseFragment;
import com.feveral.seniorhigh.R;
import com.feveral.seniorhigh.animation.MyAnimation;

/**
 * Created by feveral on 2017/8/12.
 */

public class BasicFragment extends BaseFragment {

    private BasicAdapter schoolAdapter;
    private BasicFirstGroupAdapter firstGroupAdapter;
    private BasicSecondGroupAdapter secondGroupAdapter;

    private TabLayout tabLayout;
    private ViewPager viewPager;
    private static View fragmentView = null;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        fragmentView = inflater.inflate(R.layout.fragment_basic, container, false);
        setTabLayout();
        return fragmentView;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.option_menu_main, menu);
    }

    private void setTabLayout() {
        tabLayout = fragmentView.findViewById(R.id.tabs_main);
        tabLayout.addTab(tabLayout.newTab().setText("全部學校"));
        tabLayout.addTab(tabLayout.newTab().setText("一類組"));
        tabLayout.addTab(tabLayout.newTab().setText("二/三類組"));
        viewPager = fragmentView.findViewById(R.id.viewpager_main);
        viewPager.setAdapter(new BasicPagerAdapter(BasicFragment.this));
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                int position = tab.getPosition();
                viewPager.setCurrentItem(position);
                if (position == 0 && schoolAdapter != null && schoolAdapter.getStatus().equals("department"))
                    setToolbarTitle(schoolAdapter.getSchool());
                else
                    setToolbarTitle("學測分數");
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {}

            @Override
            public void onTabReselected(TabLayout.Tab tab) {}
        });
    }

    public void setSchoolRecyclerView() {
        RecyclerView recyclerView = fragmentView.findViewById(R.id.recycler_basic);
        schoolAdapter = new BasicAdapter(BasicFragment.this);
        recyclerView.setAdapter(schoolAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setItemAnimator(MyAnimation.getDefaultAnimator());
    }

    public void setFirstGroupRecyclerView() {
        RecyclerView recyclerView = fragmentView.findViewById(R.id.recycler_basic_first_group);
        firstGroupAdapter = new BasicFirstGroupAdapter(BasicFragment.this);
        recyclerView.setAdapter(firstGroupAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setItemAnimator(MyAnimation.getDefaultAnimator());
    }

    public void setSecondGroupRecyclerView() {
        RecyclerView recyclerView = fragmentView.findViewById(R.id.recycler_basic_second_group);
        secondGroupAdapter = new BasicSecondGroupAdapter(BasicFragment.this);
        recyclerView.setAdapter(secondGroupAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setItemAnimator(MyAnimation.getDefaultAnimator());
    }

    public void setToolbarTitle(String title) {
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(title);
    }

    public boolean onBackPress() {
        if (schoolAdapter != null && schoolAdapter.getStatus().equals("department")) {
            schoolAdapter.onBackPress();
            setToolbarTitle("學測分數");
            return true;
        }
        return false;
    }

    @Override
    public int getTitleStringId() {
        return R.string.basic_grade_text;
    }

    @Override
    public int getTitleColorId() {
        return R.color.primary;
    }
}
