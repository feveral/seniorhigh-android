package com.feveral.seniorhigh.unify;

import android.content.Context;
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

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class UnifySearchAdapter extends RecyclerView.Adapter<UnifySearchAdapter.ViewHolder>{

    private ArrayList<UnifyGrade> _gradeList;
    private SearchUnifyGrade _search;
    private String _firstYear = Config.UNIFY_FIRST_YEAR;
    private String _secondYear = Config.UNIFY_SECOND_YEAR;
    private String _keyword = "";
    private Context _context;

    public UnifySearchAdapter(Context context){
        this._context = context;
        this._search = new SearchUnifyGrade();
        this._gradeList = new ArrayList<>();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        private UnifySearchAdapter.ViewHolder.ViewHolderClick listener;
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

        public void setOnClickListener(UnifySearchAdapter.ViewHolder.ViewHolderClick listener){
            this.listener = listener;
        }

        @Override
        public void onClick(View view) {
            listener.clickOnView(view,getLayoutPosition());
        }
    }

    @NonNull
    @Override
    public UnifySearchAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        View view = LayoutInflater.from(context).inflate(R.layout.item_group,parent,false);
        return new UnifySearchAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UnifySearchAdapter.ViewHolder holder, int position) {
        holder.itemTitle.setText(_gradeList.get(position).getSchool());
        holder.itemSubtitle.setText(TextUtils.shortStringIfTooLong(_gradeList.get(position).getDepartment()));
        holder.itemRemark.setText(String.valueOf(_gradeList.get(position).getGrade()));
        holder.setOnClickListener(new UnifySearchAdapter.ViewHolder.ViewHolderClick() {
            @Override
            public void clickOnView(View view, int position) {
                UnifyGrade grade = _gradeList.get(position);
                ArrayList<UnifyGrade> firstYearGradeList = UnifyGrade.find(_firstYear, grade.getSchool(), grade.getDepartment());
                ArrayList<UnifyGrade> secondYearGradeList = UnifyGrade.find(_secondYear, grade.getSchool(), grade.getDepartment());
                UnifyDialog gradeDialog = new UnifyDialog(_context);
                gradeDialog.setFirstYearGrade(firstYearGradeList);
                gradeDialog.setSecondYearGrade(secondYearGradeList);
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
        _gradeList = UnifyGrade.findByKeyWord(_firstYear, _keyword);
        notifyItemRangeInserted(0, getItemCount());
    }

}
