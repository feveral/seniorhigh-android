package com.feveral.seniorhigh.unify;

import androidx.viewpager.widget.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.feveral.seniorhigh.R;

/**
 * Created by feveral on 2017/8/15.
 */

public class UnifyPagerAdapter extends PagerAdapter {

    private UnifyFragment fragment;

    public UnifyPagerAdapter(UnifyFragment fragment){
        this.fragment = fragment;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {

        if(position==0){
            View view = LayoutInflater.from(fragment.getContext()).inflate(R.layout.unify_school,container,false);
            container.addView(view);
            fragment.setSchoolRecycler();
            return view;
        }
        else{
            View view = LayoutInflater.from(fragment.getContext()).inflate(UnifyGroupMapping.positionToLayoutId(position),container,false);
            container.addView(view);
            fragment.setGroupRecycler(UnifyGroupMapping.positionToRecyclerId(position), UnifyGroupMapping.positionToGroup(position));
            return view;
        }
    }

    @Override
    public int getCount() {
        return 21;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view==object;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }
}
