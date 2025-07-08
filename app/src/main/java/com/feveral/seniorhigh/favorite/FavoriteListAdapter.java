package com.feveral.seniorhigh.favorite;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import com.feveral.seniorhigh.Config;
import com.feveral.seniorhigh.Model.BasicGrade;
import com.feveral.seniorhigh.Model.DesignatedGrade;
import com.feveral.seniorhigh.Model.Favorite;
import com.feveral.seniorhigh.Model.UnifyGrade;
import com.feveral.seniorhigh.R;
import com.feveral.seniorhigh.basic.BasicDialog;
import com.feveral.seniorhigh.designated.DesignatedDialog;
import com.feveral.seniorhigh.unify.UnifyDialog;

import java.util.ArrayList;

/**
 * Created by feveral on 2018/8/19.
 */

public class FavoriteListAdapter extends BaseExpandableListAdapter {

    private String[] groupList = {"指考", "學測", "統測"};
    private ArrayList<Favorite> _designatedFavoriteList;
    private ArrayList<Favorite> _basicFavoriteList;
    private ArrayList<Favorite> _unifyFavoriteList;

    private FavoriteFragment fragment;

    public FavoriteListAdapter(FavoriteFragment fragment){
        this.fragment = fragment;
        this.refreshFavoriteData();
    }

    public void refreshFavoriteData() {
        this._designatedFavoriteList = Favorite.findByExamine(Config.TABLE_NAME_DESIGNATED);
        this._basicFavoriteList = Favorite.findByExamine(Config.TABLE_NAME_BASIC);
        this._unifyFavoriteList = Favorite.findByExamine(Config.TABLE_NAME_UNIFY);
    }

    @Override
    public int getGroupCount() {
        return groupList.length;
    }

    @Override
    public int getChildrenCount(int i) {
        if (i == 0) {
            return _designatedFavoriteList.size();
        } else if (i == 1) {
            return _basicFavoriteList.size();
        } else if (i == 2) {
            return _unifyFavoriteList.size();
        }
        return 0;
    }

    @Override
    public Object getGroup(int i) {
        return groupList[i];
    }

    @Override
    public Object getChild(int i, int i1) {
        if (i == 0) {
            return _designatedFavoriteList.get(i1);
        } else if (i == 1) {
            return _basicFavoriteList.get(i1);
        } else if (i == 2) {
            return _unifyFavoriteList.get(i1);
        }
        return null;
    }

    @Override
    public long getGroupId(int i) {
        return i;
    }

    @Override
    public long getChildId(int i, int i1) {
        return i1;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View view, ViewGroup viewGroup) {
        GroupViewHolder groupViewHolder;
        Context context = viewGroup.getContext();
        if (view == null) {
            view = LayoutInflater.from(context).inflate(R.layout.favorite_list_group, viewGroup, false);
            groupViewHolder = new GroupViewHolder();
            groupViewHolder.title = view.findViewById(R.id.favorite_group_name);
            view.setTag(groupViewHolder);
        } else {
            groupViewHolder = (GroupViewHolder) view.getTag();
        }
        groupViewHolder.title.setText(groupList[groupPosition]);
        refreshFavoriteData();
        return view;
    }

    @Override
    public View getChildView(int groupPosition, final int childPosition, boolean isLastChild, View view, ViewGroup viewGroup) {
        ChildViewHolder childViewHolder;
        final Context context = viewGroup.getContext();
        if (view == null) {
            view = LayoutInflater.from(context).inflate(R.layout.item_group, viewGroup, false);
            childViewHolder = new ChildViewHolder();
            childViewHolder.school = view.findViewById(R.id.item_title_main);
            childViewHolder.department = view.findViewById(R.id.item_subtitle_main);
            childViewHolder.grade = view.findViewById(R.id.item_title_remark);
            view.setTag(childViewHolder);
        } else {
            childViewHolder = (ChildViewHolder) view.getTag();
        }

        if (groupPosition == 0) {
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    DesignatedGrade firstYearGrade = DesignatedGrade.find(Config.DESIGNATED_FIRST_YEAR,
                            _designatedFavoriteList.get(childPosition).getSchool(),
                            _designatedFavoriteList.get(childPosition).getDepartment());
                    DesignatedGrade secondYearGrade = DesignatedGrade.find(Config.DESIGNATED_SECOND_YEAR,
                            _designatedFavoriteList.get(childPosition).getSchool(),
                            _designatedFavoriteList.get(childPosition).getDepartment());
                    DesignatedDialog dialog = new DesignatedDialog(context);
                    dialog.setFirstYearGrade(firstYearGrade);
                    dialog.setSecondYearGrade(secondYearGrade);
                    dialog.show();
                }
            });
            childViewHolder.school.setText(_designatedFavoriteList.get(childPosition).getSchool());
            childViewHolder.department.setText(_designatedFavoriteList.get(childPosition).getDepartment());

        } else if (groupPosition == 1) {
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    BasicGrade firstYearGrade = BasicGrade.find(Config.BASIC_FIRST_YEAR,
                            _basicFavoriteList.get(childPosition).getSchool(),
                            _basicFavoriteList.get(childPosition).getDepartment());
                    BasicGrade secondYearGrade = BasicGrade.find(Config.BASIC_SECOND_YEAR,
                            _basicFavoriteList.get(childPosition).getSchool(),
                            _basicFavoriteList.get(childPosition).getDepartment());
                    BasicDialog dialog = new BasicDialog(context);
                    dialog.setFirstGrade(firstYearGrade);
                    dialog.setSecondGrade(secondYearGrade);
                    dialog.show();
                }
            });
            childViewHolder.school.setText(_basicFavoriteList.get(childPosition).getSchool());
            childViewHolder.department.setText(_basicFavoriteList.get(childPosition).getDepartment());
        } else if (groupPosition == 2) {
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ArrayList<UnifyGrade> firstYearGradeList = UnifyGrade.find(Config.UNIFY_FIRST_YEAR,
                            _unifyFavoriteList.get(childPosition).getSchool(),
                            _unifyFavoriteList.get(childPosition).getDepartment());
                    ArrayList<UnifyGrade> secondYearGradeList = UnifyGrade.find(Config.UNIFY_SECOND_YEAR,
                            _unifyFavoriteList.get(childPosition).getSchool(),
                            _unifyFavoriteList.get(childPosition).getDepartment());
                    UnifyDialog dialog = new UnifyDialog(context);
                    dialog.setFirstYearGrade(firstYearGradeList);
                    dialog.setSecondYearGrade(secondYearGradeList);
                    dialog.show();
                }
            });
            childViewHolder.school.setText(_unifyFavoriteList.get(childPosition).getSchool());
            childViewHolder.department.setText(_unifyFavoriteList.get(childPosition).getDepartment());
        }
        return view;
    }

    @Override
    public boolean isChildSelectable(int i, int i1) {
        return true;
    }

    static class GroupViewHolder {
        TextView title;
    }
    static class ChildViewHolder {
        TextView school;
        TextView department;
        TextView grade;
    }
}
