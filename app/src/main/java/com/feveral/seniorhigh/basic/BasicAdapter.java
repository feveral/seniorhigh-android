package com.feveral.seniorhigh.basic;

import android.content.Context;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.feveral.seniorhigh.Config;
import com.feveral.seniorhigh.Model.BasicGrade;
import com.feveral.seniorhigh.R;
import com.feveral.seniorhigh.database.SearchBasicGrade;
import com.feveral.seniorhigh.utility.TextUtils;

import java.util.ArrayList;

/**
 * Created by feveral on 2017/8/14.
 */

public class BasicAdapter extends RecyclerView.Adapter<BasicAdapter.ViewHolder>{

    private BasicFragment fragment;
    private SearchBasicGrade search;
    private ArrayList<String> schoolList;
    private ArrayList<BasicGrade> departmentList;
    private String status = "school";
    private String school = "";
    private String firstYear = Config.BASIC_FIRST_YEAR;
    private String secondYear = Config.BASIC_SECOND_YEAR;
    private String keyWord = "";

    public BasicAdapter(BasicFragment fragment){
        this.fragment = fragment;
        search = new SearchBasicGrade();
        schoolList = search.findSchoolListFromKeyword(firstYear,keyWord);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        private ViewHolderClick listener;
        private TextView itemTitle;
        private TextView itemRemark;

        public ViewHolder(View itemView) {
            super(itemView);
            itemTitle = (TextView) itemView.findViewById(R.id.item_title_main);
            itemRemark = (TextView) itemView.findViewById(R.id.item_title_remark);
            itemView.setOnClickListener(this);
        }

        public interface ViewHolderClick{
            void clickOnView(View v , int position);
        }

        public void setOnClickListener(ViewHolderClick listener){
            this.listener = listener;
        }

        @Override
        public void onClick(View view) {
            listener.clickOnView(view,getLayoutPosition());
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        View view = LayoutInflater.from(context).inflate(R.layout.item_main,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(BasicAdapter.ViewHolder holder, int position) {
        if(status.equals("school")) {
            holder.itemTitle.setText(schoolList.get(position));
            holder.itemRemark.setText("");
            holder.setOnClickListener(new ViewHolder.ViewHolderClick() {
                @Override
                public void clickOnView(View v, int position) {
                    school = schoolList.get(position);
                    departmentList = search.findDepartmentListFromKeyword(school, firstYear,keyWord);
                    fragment.setToolbarTitle(school);
                    keyWord = "";
                    notifyItemRangeRemoved(0,getItemCount());
                    status = "department";
                    schoolList = search.findSchoolListFromKeyword(firstYear,keyWord);
                    departmentList = search.findDepartmentListFromKeyword(school , firstYear, keyWord);
                    notifyItemRangeInserted(0,getItemCount());
                }
            });
        }
        else if(status.equals("department")) {
            holder.itemTitle.setText(TextUtils.shortStringIfTooLong(departmentList.get(position).getDepartment()));
            holder.itemRemark.setText("");
            holder.setOnClickListener(new ViewHolder.ViewHolderClick() {
                @Override
                public void clickOnView(View v, int position) {
                    BasicDialog gradeDialog = new BasicDialog(fragment.getContext());
                    BasicGrade firstYearGrade = departmentList.get(position);
                    BasicGrade secondYearGrade = search.getExactDepartmentGrade(secondYear,
                            departmentList.get(position).getSchool(),
                            departmentList.get(position).getDepartment());
                    gradeDialog.setFirstGrade(firstYearGrade);
                    gradeDialog.setSecondGrade(secondYearGrade);
                    gradeDialog.show();
                }
            });
        }
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setKeyWord(String keyWord) {
        this.keyWord = keyWord;
    }

    public String getStatus() {
        return status;
    }

    public void onBackPress(){
        notifyChanges();
        setStatus("school");
    }

    public void notifyChanges(){
        notifyItemRangeRemoved(0,getItemCount());
        schoolList = search.findSchoolListFromKeyword(firstYear,keyWord);
        departmentList = search.findDepartmentListFromKeyword(school , firstYear, keyWord);
        notifyItemRangeInserted(0,getItemCount());
    }

    @Override
    public int getItemCount() {
        if(status.equals("school"))
            return schoolList.size();
        else if(status.equals("department"))
            return departmentList.size();
        else
            return 0;
    }
}
