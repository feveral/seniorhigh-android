package com.feveral.seniorhigh.designated;

import android.content.Context;
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

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class DesignatedSearchAdapter extends RecyclerView.Adapter<DesignatedSearchAdapter.ViewHolder> {

    private ArrayList<DesignatedGrade> _gradeList;
    private SearchDesignatedGrade _search;
    private String _firstYear = Config.DESIGNATED_FIRST_YEAR;
    private String _secondYear = Config.DESIGNATED_SECOND_YEAR;
    private String _keyword = "";
    private Context _context;

    public DesignatedSearchAdapter(Context context){
        this._context = context;
        this._search = new SearchDesignatedGrade();
        this._gradeList = new ArrayList<>();
        DesignatedGrade.sortHighToLow(this._gradeList);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        private DesignatedSearchAdapter.ViewHolder.ViewHolderClick listener;
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

        public void setOnClickListener(DesignatedSearchAdapter.ViewHolder.ViewHolderClick listener){
            this.listener = listener;
        }

        @Override
        public void onClick(View view) {
            listener.clickOnView(view,getLayoutPosition());
        }
    }

    @NonNull
    @Override
    public DesignatedSearchAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {        Context context = parent.getContext();
        View view = LayoutInflater.from(context).inflate(R.layout.item_group,parent,false);
        return new DesignatedSearchAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DesignatedSearchAdapter.ViewHolder holder, int position) {
        holder.itemTitle.setText(_gradeList.get(position).getSchool());
        holder.itemSubtitle.setText(TextUtils.shortStringIfTooLong(_gradeList.get(position).getDepartment()));
        holder.itemRemark.setText(_gradeList.get(position).getBalanceString());
        holder.setOnClickListener(new ViewHolder.ViewHolderClick() {
            @Override
            public void clickOnView(View view, int position) {
                DesignatedDialog gradeDialog = new DesignatedDialog(_context);
                DesignatedGrade firstYearGrade = _gradeList.get(position);
                DesignatedGrade secondYearGrade = _search.getExactDepartmentGrade(_secondYear,
                        _gradeList.get(position).getSchool(),
                        _gradeList.get(position).getDepartment());
                gradeDialog.setFirstYearGrade(firstYearGrade);
                gradeDialog.setSecondYearGrade(secondYearGrade);
                gradeDialog.show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return _gradeList.size();
    }

    public void setKeyWord(String keyWord){
        _keyword = keyWord;
        notifyItemRangeRemoved(0, getItemCount());
        _gradeList = DesignatedGrade.findByKeyWord(_firstYear, _keyword);
        DesignatedGrade.sortHighToLow(_gradeList);
        notifyItemRangeInserted(0, getItemCount());
    }
}
