package com.feveral.seniorhigh.designated;

import android.app.SearchManager;
import android.content.Context;
import android.os.Bundle;
import com.google.android.material.tabs.TabLayout;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.SearchView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;

import com.feveral.seniorhigh.BaseFragment;
import com.feveral.seniorhigh.R;

/**
 * Created by feveral on 2017/8/12.
 */

public class DesignatedFragment extends BaseFragment {

    private RecyclerView recyclerView;
    private DesignatedSchoolAdapter schoolAdapter;
    private DesignatedFirstGroupAdapter firstGroupAdapter;
    private DesignatedSecondAdapter secondGroupAdapter;
    private DesignatedThirdGroupAdapter thirdGroupAdapter;

    private TabLayout tabLayout;
    private ViewPager viewPager;
    private static View fragmentView = null;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater,ViewGroup container, Bundle savedInstanceState) {
        fragmentView = inflater.inflate(R.layout.fragment_designated,container,false);
        setTabLayout();
        return fragmentView;
    }

    public void setToolbarTitle(String title){
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(title);
    }

    private void setTabLayout(){
        tabLayout = fragmentView.findViewById(R.id.tabs_main);
        tabLayout.addTab(tabLayout.newTab().setText("全部學校"));
        tabLayout.addTab(tabLayout.newTab().setText("一類組"));
        tabLayout.addTab(tabLayout.newTab().setText("二類組"));
        tabLayout.addTab(tabLayout.newTab().setText("三類組"));
        viewPager = fragmentView.findViewById(R.id.viewpager_main);
        viewPager.setAdapter(new DesignatedPagerAdapter(DesignatedFragment.this));
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                int position = tab.getPosition();
                viewPager.setCurrentItem(position);
                if(position==0 && schoolAdapter.getStatus().equals("department"))
                    setToolbarTitle(schoolAdapter.getSchool());
                else
                    setToolbarTitle("指考分數");
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    public void setSchoolRecyclerView(){
        recyclerView = fragmentView.findViewById(R.id.recycler_main);
        schoolAdapter = new DesignatedSchoolAdapter(DesignatedFragment.this);
        recyclerView.setAdapter(schoolAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setItemAnimator(getDefaultAnimator());
    }

    public void setFirstRecyclerView(){
        recyclerView = fragmentView.findViewById(R.id.recycler_first_group);
        firstGroupAdapter = new DesignatedFirstGroupAdapter(DesignatedFragment.this);
        recyclerView.setAdapter(firstGroupAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setItemAnimator(getDefaultAnimator());
    }

    public void setSecondRecyclerView(){
        recyclerView = fragmentView.findViewById(R.id.recycler_second_group);
        secondGroupAdapter = new DesignatedSecondAdapter(DesignatedFragment.this);
        recyclerView.setAdapter(secondGroupAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setItemAnimator(getDefaultAnimator());
    }

    public void setThirdRecyclerView(){
        recyclerView = fragmentView.findViewById(R.id.recycler_third_group);
        thirdGroupAdapter = new DesignatedThirdGroupAdapter(DesignatedFragment.this);
        recyclerView.setAdapter(thirdGroupAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setItemAnimator(getDefaultAnimator());
    }

    private DefaultItemAnimator getDefaultAnimator(){
        DefaultItemAnimator defaultItemAnimator = new DefaultItemAnimator();
        defaultItemAnimator.setAddDuration(250);
        defaultItemAnimator.setRemoveDuration(250);
        return defaultItemAnimator;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.option_menu_main, menu);
    }

    public boolean onBackPress(){
        if(schoolAdapter.getStatus().equals("department")){
            schoolAdapter.onBackPress();
            setToolbarTitle("指考分數");
            return true;
        }
        return false;
    }

    @Override
    public int getTitleColorId() {
        return R.color.primary;
    }

    @Override
    public int getTitleStringId() {
        return R.string.designated_grade_text;
    }
}
