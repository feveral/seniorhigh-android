package com.feveral.seniorhigh.unify;

import android.app.SearchManager;
import android.content.Context;
import android.os.Bundle;
import com.google.android.material.tabs.TabLayout;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;
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
import com.feveral.seniorhigh.animation.MyAnimation;

/**
 * Created by feveral on 2017/8/15.
 */

public class UnifyFragment extends BaseFragment {

    private View fragmentView;
//    private SearchView searchView;
    private UnifyPagerAdapter pagerAdapter;
    private TabLayout tabLayout;
    private UnifySchoolAdapter schoolAdapter;
    private ViewPager viewPager;
    private UnifyGroupAdapter groupAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        fragmentView = inflater.inflate(R.layout.fragment_unify,container,false);
        pagerAdapter = new UnifyPagerAdapter(UnifyFragment.this);
        setTabLayout();
        return fragmentView;
    }
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.option_menu_main, menu);
    }

    public void setToolbarTitle(String title){
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(title);
    }

    public void setSchoolRecycler(){
        RecyclerView recyclerView = fragmentView.findViewById(R.id.recycler_unify_school);
        schoolAdapter = new UnifySchoolAdapter(UnifyFragment.this);
        recyclerView.setAdapter(schoolAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setItemAnimator(MyAnimation.getDefaultAnimator());
    }

    public void setGroupRecycler(int recyclerGroupId , String group){
        RecyclerView recyclerView = fragmentView.findViewById(recyclerGroupId);
        groupAdapter = new UnifyGroupAdapter(UnifyFragment.this,group);
        recyclerView.setAdapter(groupAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setItemAnimator(MyAnimation.getDefaultAnimator());
        groupAdapter.notifyChanges();
    }

    private void setTabLayout(){
        tabLayout = (TabLayout) fragmentView.findViewById(R.id.unify_tablayout);
        tabLayout.addTab(tabLayout.newTab().setText("全部學校"));
        tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
        for(int position = 1 ; position < 21 ; position++){
            tabLayout.addTab(tabLayout.newTab().setText(UnifyGroupMapping.positionToGroup(position)));
        }
        viewPager = (ViewPager) fragmentView.findViewById(R.id.unify_viewpager);
        viewPager.setAdapter(pagerAdapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                int position = tab.getPosition();
                viewPager.setCurrentItem(position);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    public boolean onBackPress(){
        if(schoolAdapter.getStatus().equals("department")){
            schoolAdapter.onBackPress();
            setToolbarTitle("統測分數");
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
        return R.string.unify_grade_text;
    }
}
