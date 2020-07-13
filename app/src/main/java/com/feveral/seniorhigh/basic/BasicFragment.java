package com.feveral.seniorhigh.basic;

import android.app.SearchManager;
import android.content.Context;
import android.os.Bundle;

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
 * Created by feveral on 2017/8/12.
 */

public class BasicFragment extends BaseFragment {

    private RecyclerView recyclerView;
    private BasicAdapter adapter;
    private static View fragmentView = null;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        fragmentView = inflater.inflate(R.layout.fragment_basic,container,false);
        setRecyclerView();
        return fragmentView;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.option_menu_main, menu);
    }

    private void setRecyclerView(){
        recyclerView = fragmentView.findViewById(R.id.recycler_basic);
        adapter = new BasicAdapter(BasicFragment.this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setItemAnimator(MyAnimation.getDefaultAnimator());
    }


    public void setToolbarTitle(String title){
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(title);
    }

    public boolean onBackPress(){
        if( adapter .getStatus().equals("department")){
            adapter.onBackPress();
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
