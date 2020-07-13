package com.feveral.seniorhigh.basic;

import android.content.Context;
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

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class BasicSearchAdapter extends RecyclerView.Adapter<BasicSearchAdapter.ViewHolder> {

    private ArrayList<BasicGrade> _gradeList;
    private SearchBasicGrade _search;
    private String _firstYear = Config.BASIC_FIRST_YEAR;
    private String _secondYear = Config.BASIC_SECOND_YEAR;
    private String _keyword = "";
    private Context _context;

    public BasicSearchAdapter(Context context){
        this._context = context;
        this._search = new SearchBasicGrade();
        this._gradeList = new ArrayList<>();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        private BasicSearchAdapter.ViewHolder.ViewHolderClick listener;
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

        public void setOnClickListener(BasicSearchAdapter.ViewHolder.ViewHolderClick listener){
            this.listener = listener;
        }

        @Override
        public void onClick(View view) {
            listener.clickOnView(view,getLayoutPosition());
        }
    }

    @NonNull
    @Override
    public BasicSearchAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {        Context context = parent.getContext();
        View view = LayoutInflater.from(context).inflate(R.layout.item_group,parent,false);
        return new BasicSearchAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BasicSearchAdapter.ViewHolder holder, int position) {
        holder.itemTitle.setText(_gradeList.get(position).getSchool());
        holder.itemSubtitle.setText(TextUtils.shortStringIfTooLong(_gradeList.get(position).getDepartment()));
        holder.setOnClickListener(new BasicSearchAdapter.ViewHolder.ViewHolderClick() {
            @Override
            public void clickOnView(View view, int position) {
                BasicDialog gradeDialog = new BasicDialog(_context);
                BasicGrade firstYearGrade = _gradeList.get(position);
                BasicGrade secondYearGrade = _search.getExactDepartmentGrade(_secondYear,
                        _gradeList.get(position).getSchool(),
                        _gradeList.get(position).getDepartment());
                gradeDialog.setFirstGrade(firstYearGrade);
                gradeDialog.setSecondGrade(secondYearGrade);
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
        _gradeList = BasicGrade.findByKeyWord(_firstYear, _keyword);
        notifyItemRangeInserted(0, getItemCount());
    }

}
