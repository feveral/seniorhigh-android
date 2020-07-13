package com.feveral.seniorhigh.favorite;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;

import com.feveral.seniorhigh.BaseFragment;
import com.feveral.seniorhigh.R;

/**
 * Created by feveral on 2018/8/18.
 */

public class FavoriteFragment extends BaseFragment{

    private View fragment;
    private ExpandableListView expandableListView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        fragment = inflater.inflate(R.layout.fragment_favorite, container,false);
        expandableListView = fragment.findViewById(R.id.favorite_list);
        expandableListView.setAdapter(new FavoriteListAdapter(this));
        return fragment;
    }

    @Override
    public int getTitleColorId() {
        return R.color.primary;
    }

    @Override
    public int getTitleStringId() {
        return R.string.favorite_text;
    }
}
