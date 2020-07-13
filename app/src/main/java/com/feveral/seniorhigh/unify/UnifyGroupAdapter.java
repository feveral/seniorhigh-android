package com.feveral.seniorhigh.unify;

import android.content.Context;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.feveral.seniorhigh.Config;
import com.feveral.seniorhigh.Model.UnifyGrade;
import com.feveral.seniorhigh.R;
import com.feveral.seniorhigh.database.SearchUnifyGrade;
import com.feveral.seniorhigh.utility.TextUtils;

import java.util.ArrayList;

/**
 * Created by feveral on 2017/8/17.
 */

public class UnifyGroupAdapter extends RecyclerView.Adapter<UnifyGroupAdapter.ViewHolder>{

    private UnifyFragment fragment;
    private String firstYear = Config.UNIFY_FIRST_YEAR;
    private String secondYear = Config.UNIFY_SECOND_YEAR;
    private String keyWord = "";
    private String school = "";
    private String group = "";
    private ArrayList<UnifyGrade> departmentList;
    private SearchUnifyGrade search;

    public UnifyGroupAdapter(UnifyFragment fragment, String group){
        this.fragment = fragment;
        this.group = group;
        this.search = new SearchUnifyGrade();
        this.departmentList = search.findDepartmentGroupListFromKeyword(firstYear, group,keyWord);
        UnifyGrade.sortHighToLow(departmentList);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        private ViewHolderClick listener;
        private TextView itemTitle;
        private TextView itemSubTitle;
        private TextView itemRemark;

        public ViewHolder(View itemView) {
            super(itemView);
            itemTitle = (TextView) itemView.findViewById(R.id.item_title_main);
            itemSubTitle = (TextView) itemView.findViewById(R.id.item_subtitle_main);
            itemRemark = (TextView)itemView.findViewById(R.id.item_title_remark);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            listener.clickOnView(view , getLayoutPosition());
        }

        public interface ViewHolderClick{
            void clickOnView(View view, int position);
        }

        public void setOnClickListener(ViewHolderClick listener){
            this.listener = listener;
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        View view = LayoutInflater.from(context).inflate(R.layout.item_group,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.itemTitle.setText(departmentList.get(position).getSchool());
        holder.itemRemark.setText(String.valueOf(departmentList.get(position).getGrade()));
        holder.itemSubTitle.setText(TextUtils.shortStringIfTooLong(departmentList.get(position).getDepartment()));
        holder.setOnClickListener(new UnifyGroupAdapter.ViewHolder.ViewHolderClick() {
            @Override
            public void clickOnView(View view, int position) {
                UnifyGrade grade = departmentList.get(position);
                ArrayList<UnifyGrade> firstYearGradeList = UnifyGrade.find(firstYear, grade.getSchool(), grade.getDepartment());
                ArrayList<UnifyGrade> secondYearGradeList = UnifyGrade.find(secondYear, grade.getSchool(), grade.getDepartment());
                UnifyDialog gradeDialog = new UnifyDialog(fragment.getContext());
                gradeDialog.setFirstYearGrade(firstYearGradeList);
                gradeDialog.setSecondYearGrade(secondYearGradeList);
                gradeDialog.show();
            }
        });
    }

    public void notifyChanges(){
        notifyItemRangeRemoved(0,getItemCount());
        departmentList = search.findDepartmentGroupListFromKeyword(firstYear ,group, keyWord);
        notifyItemRangeInserted(0,getItemCount());
    }

    public String getKeyWord() {
        return keyWord;
    }


    public void setKeyWord(String keyWord) {
        this.keyWord = keyWord;
    }

    @Override
    public int getItemCount() {
        return departmentList.size();
    }
}
