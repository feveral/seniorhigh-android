package com.feveral.seniorhigh.designated;
import android.content.Context;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.feveral.seniorhigh.Config;
import com.feveral.seniorhigh.Model.DesignatedGrade;
import com.feveral.seniorhigh.R;
import com.feveral.seniorhigh.database.SearchDesignatedGrade;
import com.feveral.seniorhigh.utility.TextUtils;

import java.util.ArrayList;

/**
 * Created by feveral on 2017/7/24.
 */

public class DesignatedSchoolAdapter extends RecyclerView.Adapter<DesignatedSchoolAdapter.ViewHolder> {

    private SearchDesignatedGrade search;
    private DesignatedFragment fragment;
    private String status = "school";
    private String school = "";
    private String keyWordSchool = "";
    private String keyWordDepartment = "";
    private final String firstYear = Config.DESIGNATED_FIRST_YEAR;
    private final String secondYear = Config.DESIGNATED_SECOND_YEAR;
    private ArrayList<String> schoolList;
    private ArrayList<DesignatedGrade> departmentList;

    public DesignatedSchoolAdapter(DesignatedFragment fragment){
        this.fragment = fragment;
        search = new SearchDesignatedGrade();
        schoolList = search.findSchoolListFromKeyword(firstYear,keyWordSchool);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        private ViewHolderClick mListener;
        private TextView itemTitle;
        private TextView itemRemark;

        public ViewHolder(View itemView) {
            super(itemView);
            itemTitle = itemView.findViewById(R.id.item_title_main);
            itemRemark = itemView.findViewById(R.id.item_title_remark);
            itemView.setOnClickListener(this);
        }

        public interface ViewHolderClick{
            void clickOnView(View v , int position);
        }

        public void setOnClickListener(ViewHolderClick listener){
            this.mListener = listener;
        }

        @Override
        public void onClick(View view) {
            mListener.clickOnView(view,getLayoutPosition());
        }
    }

    @Override
    public DesignatedSchoolAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        View view = LayoutInflater.from(context).inflate(R.layout.item_main,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final DesignatedSchoolAdapter.ViewHolder holder, int position) {
        if(status.equals("school")){
            holder.itemTitle.setText(schoolList.get(position));
            holder.itemRemark.setText("");
            holder.setOnClickListener(new ViewHolder.ViewHolderClick() {
                @Override
                public void clickOnView(View v, int position) {
                    school = schoolList.get(position);
                    departmentList = search.findDepartmentListFromKeyword(school,firstYear,keyWordDepartment);
                    fragment.setToolbarTitle(school);
                    keyWordDepartment = "";
                    notifyItemRangeRemoved(0,getItemCount());
                    status = "department";
                    schoolList = search.findSchoolListFromKeyword(firstYear,keyWordSchool);
                    departmentList = search.findDepartmentListFromKeyword(school ,firstYear, keyWordDepartment);
                    notifyItemRangeInserted(0,getItemCount());
                }
            });
        }

        else if(status.equals("department")){
            holder.itemTitle.setText(TextUtils.shortStringIfTooLong(departmentList.get(position).getDepartment()));
            holder.itemRemark.setText(departmentList.get(position).getBalanceString());
            holder.setOnClickListener(new ViewHolder.ViewHolderClick() {
                @Override
                public void clickOnView(View v, int position) {
                    DesignatedDialog gradeDialog = new DesignatedDialog(fragment.getContext());
                    DesignatedGrade firstYearGrade = departmentList.get(position);
                    DesignatedGrade secondYearGrade = search.getExactDepartmentGrade(secondYear,
                            departmentList.get(position).getSchool(),
                            departmentList.get(position).getDepartment());
                    gradeDialog.setFirstYearGrade(firstYearGrade);
                    gradeDialog.setSecondYearGrade(secondYearGrade);
                    gradeDialog.show();
                }
            });
        }
    }

    public void notifyChanges(){
        notifyItemRangeRemoved(0,getItemCount());
        schoolList = search.findSchoolListFromKeyword(firstYear,keyWordSchool);
        departmentList = search.findDepartmentListFromKeyword(school ,firstYear, keyWordDepartment);
        notifyItemRangeInserted(0,getItemCount());
    }

    public String getSchool() {
        return school;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setKeyWordSchool(String keyWordSchool){
        this.keyWordSchool = keyWordSchool;
    }

    public void setKeyWordDepartment(String keyWordDepartment){
        this.keyWordDepartment = keyWordDepartment;
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
