package com.feveral.seniorhigh.designated;

import android.content.Context;
import androidx.recyclerview.widget.RecyclerView;
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
 * Created by feveral on 2017/8/7.
 */

public class DesignatedThirdGroupAdapter extends RecyclerView.Adapter<DesignatedThirdGroupAdapter.ViewHolder> {

    private DesignatedFragment fragment;
    private SearchDesignatedGrade search;
    private ArrayList<DesignatedGrade> gradeList;
    private String keyWord = "";
    private final String firstYear = Config.DESIGNATED_FIRST_YEAR;
    private final String secondYear = Config.DESIGNATED_SECOND_YEAR;

    public DesignatedThirdGroupAdapter(DesignatedFragment fragment){
        this.fragment = fragment;
        search = new SearchDesignatedGrade();
        gradeList = search.getThirdGroupList(firstYear);
        DesignatedGrade.sortHighToLow(gradeList);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        private ViewHolderClick listener;
        private TextView itemTitle;
        private TextView itemRemark;
        private TextView itemSubtitle;

        public ViewHolder(View itemView) {
            super(itemView);
            itemTitle = itemView.findViewById(R.id.item_title_main);
            itemRemark = itemView.findViewById(R.id.item_title_remark);
            itemSubtitle = itemView.findViewById(R.id.item_subtitle_main);
            itemView.setOnClickListener(this);
        }

        public interface ViewHolderClick{
            void clickOnView(View view, int position);
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
        View view = LayoutInflater.from(context).inflate(R.layout.item_group,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(DesignatedThirdGroupAdapter.ViewHolder holder, int position) {
        holder.itemTitle.setText(gradeList.get(position).getSchool());
        holder.itemSubtitle.setText(TextUtils.shortStringIfTooLong(gradeList.get(position).getDepartment()));
        holder.itemRemark.setText(gradeList.get(position).getBalanceString());
        holder.setOnClickListener(new ViewHolder.ViewHolderClick() {
            @Override
            public void clickOnView(View view, int position) {
                DesignatedDialog gradeDialog = new DesignatedDialog(fragment.getContext());
                DesignatedGrade firstYearGrade = gradeList.get(position);
                DesignatedGrade secondYearGrade = search.getExactDepartmentGrade(secondYear,
                        gradeList.get(position).getSchool(),
                        gradeList.get(position).getDepartment());
                gradeDialog.setFirstYearGrade(firstYearGrade);
                gradeDialog.setSecondYearGrade(secondYearGrade);
                gradeDialog.show();
            }
        });
    }

    public void setKeyWord(String keyWord) {
        this.keyWord = keyWord;
    }

    public String getKeyWord() {
        return keyWord;
    }

    @Override
    public int getItemCount() {
        return gradeList.size();
    }
}
