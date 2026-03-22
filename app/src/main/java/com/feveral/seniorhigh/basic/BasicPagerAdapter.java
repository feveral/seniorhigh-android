package com.feveral.seniorhigh.basic;

import androidx.viewpager.widget.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.feveral.seniorhigh.R;

public class BasicPagerAdapter extends PagerAdapter {

    private BasicFragment fragment;

    public BasicPagerAdapter(BasicFragment fragment) {
        this.fragment = fragment;
    }

    @Override
    public int getCount() {
        return 3;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        if (position == 0) {
            View view = LayoutInflater.from(fragment.getContext()).inflate(R.layout.basic_school_tab, container, false);
            container.addView(view);
            fragment.setSchoolRecyclerView();
            return view;
        } else if (position == 1) {
            View view = LayoutInflater.from(fragment.getContext()).inflate(R.layout.basic_first_group_tab, container, false);
            container.addView(view);
            fragment.setFirstGroupRecyclerView();
            return view;
        } else {
            View view = LayoutInflater.from(fragment.getContext()).inflate(R.layout.basic_second_group_tab, container, false);
            container.addView(view);
            fragment.setSecondGroupRecyclerView();
            return view;
        }
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }
}
