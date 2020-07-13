package com.feveral.seniorhigh.designated;

import androidx.viewpager.widget.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.feveral.seniorhigh.R;

/**
 * Created by feveral on 2017/8/8.
 */

public class DesignatedPagerAdapter extends PagerAdapter {

    private DesignatedFragment fragment;

    public DesignatedPagerAdapter(DesignatedFragment fragment){
        this.fragment = fragment;
    }

    @Override
    public int getCount() {
        return 4;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view==object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        if(position==0) {
            View view = LayoutInflater.from(fragment.getContext()).inflate(R.layout.designed_school_tab, container, false);
            container.addView(view);
            fragment.setSchoolRecyclerView();
            return view;
        }
        else if(position==1){
            View view = LayoutInflater.from(fragment.getContext()).inflate(R.layout.designed_first_group_tab, container, false);
            container.addView(view);
            fragment.setFirstRecyclerView();
            return view;
        }
        else if(position==2) {
            View view = LayoutInflater.from(fragment.getContext()).inflate(R.layout.designed_second_group_tab, container, false);
            container.addView(view);
            fragment.setSecondRecyclerView();
            return view;
        }
        else if(position==3) {
            View view = LayoutInflater.from(fragment.getContext()).inflate(R.layout.designed_third_group_tab, container, false);
            container.addView(view);
            fragment.setThirdRecyclerView();
            return view;
        }
        return null;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }
}