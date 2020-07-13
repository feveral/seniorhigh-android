package com.feveral.seniorhigh.unify;

import android.content.Context;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
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
 * Created by feveral on 2017/8/16.
 */

public class UnifySchoolAdapter extends RecyclerView.Adapter<UnifySchoolAdapter.ViewHolder> {

    private UnifyFragment fragment;
    private String firstYear = Config.UNIFY_FIRST_YEAR;
    private String secondYear = Config.UNIFY_SECOND_YEAR;
    private String keyWord = "";
    private String status = "school";
    private String school = "";
    private ArrayList<String> schoolList;
    private ArrayList<String> departmentList;
    private SearchUnifyGrade search;

    public UnifySchoolAdapter(UnifyFragment fragment){
        this.fragment = fragment;
        this.search = new SearchUnifyGrade();
        this.schoolList = search.findSchoolListFromKeyword(firstYear, keyWord);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        private ViewHolderClick listener;
        private TextView itemTitle;
        private TextView itemRemark;

        public ViewHolder(View itemView) {
            super(itemView);
            itemTitle = itemView.findViewById(R.id.item_title_main);
            itemRemark = itemView.findViewById(R.id.item_title_remark);
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
        View view = LayoutInflater.from(context).inflate(R.layout.item_main,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        if(status.equals("school")) {
            holder.itemTitle.setText(schoolList.get(position));
            holder.itemRemark.setText("");
            holder.setOnClickListener(new ViewHolder.ViewHolderClick() {
                @Override
                public void clickOnView(View view, int position) {
                    school = schoolList.get(position);
                    departmentList = UnifyGrade.findDepartments(firstYear, school);
                    fragment.setToolbarTitle(school);
                    keyWord = "";
                    notifyItemRangeRemoved(0,getItemCount());
                    status = "department";
                    schoolList = search.findSchoolListFromKeyword(firstYear, keyWord);
                    departmentList = UnifyGrade.findDepartments(firstYear, school);
                    notifyItemRangeInserted(0,getItemCount());
                }
            });
        }
        else if(status.equals("department")){
            holder.itemTitle.setText(TextUtils.shortStringIfTooLong(departmentList.get(position)));
            holder.setOnClickListener(new ViewHolder.ViewHolderClick() {
                @Override
                public void clickOnView(View view, int position) {
                    String department = departmentList.get(position);
                    ArrayList<UnifyGrade> firstYearGradeList = UnifyGrade.find(firstYear, school, department);
                    ArrayList<UnifyGrade> secondYearGradeList = UnifyGrade.find(secondYear, school, department);
                    UnifyDialog gradeDialog = new UnifyDialog(fragment.getContext());
                    gradeDialog.setFirstYearGrade(firstYearGradeList);
                    gradeDialog.setSecondYearGrade(secondYearGradeList);
                    gradeDialog.show();
                }
            });
        }
    }

    public void notifyChanges(){
        notifyItemRangeRemoved(0,getItemCount());
        schoolList = search.findSchoolListFromKeyword(firstYear, keyWord);
        departmentList = UnifyGrade.findDepartments(firstYear, school);
        notifyItemRangeInserted(0,getItemCount());
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

    public String getKeyWord() {
        return keyWord;
    }

    public void setKeyWord(String keyWord) {
        this.keyWord = keyWord;
    }

    public void onBackPress(){
        notifyChanges();
        setStatus("school");
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
