package com.feveral.seniorhigh.unify;

import android.content.Context;

import androidx.appcompat.app.AlertDialog;
import androidx.core.content.ContextCompat;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.feveral.seniorhigh.Config;
import com.feveral.seniorhigh.Model.Favorite;
import com.feveral.seniorhigh.Model.UnifyGrade;
import com.feveral.seniorhigh.R;

import java.lang.reflect.Array;
import java.util.ArrayList;

/**
 * Created by feveral on 2017/8/16.
 */

public class UnifyDialog {

    private Context context;
    private ImageButton favoriteButton;
    private UnifyGrade firstYearGrade = new UnifyGrade();
    private UnifyGrade secondYearGrade = new UnifyGrade();

    private View view;
    private AlertDialog.Builder builder;

    public UnifyDialog(Context context){
        this.context = context;
        builder = new AlertDialog.Builder(context, R.style.AlertDialogCustom);
        view = LayoutInflater.from(context).inflate(R.layout.unify_dialog,null);
    }

    private void setTitle(String school , String department){
        builder.setTitle(school + "  " + department);
    }

    private void setTextViewText(int resId, String text) {
        TextView t = view.findViewById(resId);
        t.setText(text);
        t.setVisibility(View.VISIBLE);
    }

    public UnifyDialog setFirstYearGrade(ArrayList<UnifyGrade> gradeList) {
        this.firstYearGrade = gradeList.get(0);
        setFavoriteButton();
        setTitle(this.firstYearGrade.getSchool(), this.firstYearGrade.getDepartment());
        setTextViewText(R.id.unify_dialog_year_first_year, "-----" + this.firstYearGrade.getYear() + "年度-----");
        Log.d("setFirstYearGrade", String.valueOf(gradeList.get(0).getGrade()));
        if (gradeList.size() >= 1) {
            setTextViewText(R.id.grade1_first_year, Double.toString(gradeList.get(0).getGrade()));
            setTextViewText(R.id.group1_first_year, gradeList.get(0).getDepartmentGroup());
            LinearLayout layout = view.findViewById(R.id.grade_group_layout1_first_year);
            layout.setVisibility(View.VISIBLE);
        }
        if (gradeList.size() >= 2) {
            setTextViewText(R.id.grade2_first_year, Double.toString(gradeList.get(1).getGrade()));
            setTextViewText(R.id.group2_first_year, gradeList.get(1).getDepartmentGroup());
            LinearLayout layout = view.findViewById(R.id.grade_group_layout2_first_year);
            layout.setVisibility(View.VISIBLE);
        }
        if (gradeList.size() >= 3) {
            setTextViewText(R.id.grade3_first_year, Double.toString(gradeList.get(2).getGrade()));
            setTextViewText(R.id.group3_first_year, gradeList.get(2).getDepartmentGroup());
            LinearLayout layout = view.findViewById(R.id.grade_group_layout3_first_year);
            layout.setVisibility(View.VISIBLE);
        }
        if (gradeList.size() >= 4) {
            setTextViewText(R.id.grade4_first_year, Double.toString(gradeList.get(3).getGrade()));
            setTextViewText(R.id.group4_first_year, gradeList.get(3).getDepartmentGroup());
            LinearLayout layout = view.findViewById(R.id.grade_group_layout4_first_year);
            layout.setVisibility(View.VISIBLE);
        }
        if (gradeList.size() >= 5) {
            setTextViewText(R.id.grade5_first_year, Double.toString(gradeList.get(4).getGrade()));
            setTextViewText(R.id.group5_first_year, gradeList.get(4).getDepartmentGroup());
            LinearLayout layout = view.findViewById(R.id.grade_group_layout5_first_year);
            layout.setVisibility(View.VISIBLE);
        }
        return this;
    }

    public UnifyDialog setSecondYearGrade(ArrayList<UnifyGrade> gradeList) {
        if (gradeList.size() == 0) {
            return this;
        }
        this.secondYearGrade = gradeList.get(0);
        setFavoriteButton();
        setTextViewText(R.id.unify_dialog_year_second_year, "-----" + this.secondYearGrade.getYear() + "年度-----");
        LinearLayout secondYearLayout = view.findViewById(R.id.second_year_layout);
        secondYearLayout.setVisibility(View.VISIBLE);
        if (gradeList.size() >= 1) {
            setTextViewText(R.id.grade1_second_year, Double.toString(gradeList.get(0).getGrade()));
            setTextViewText(R.id.group1_second_year, gradeList.get(0).getDepartmentGroup());
            LinearLayout layout = view.findViewById(R.id.grade_group_layout1_second_year);
            layout.setVisibility(View.VISIBLE);
        }
        if (gradeList.size() >= 2) {
            setTextViewText(R.id.grade2_second_year, Double.toString(gradeList.get(1).getGrade()));
            setTextViewText(R.id.group2_second_year, gradeList.get(1).getDepartmentGroup());
            LinearLayout layout = view.findViewById(R.id.grade_group_layout2_second_year);
            layout.setVisibility(View.VISIBLE);
        }
        if (gradeList.size() >= 3) {
            setTextViewText(R.id.grade3_second_year, Double.toString(gradeList.get(2).getGrade()));
            setTextViewText(R.id.group3_second_year, gradeList.get(2).getDepartmentGroup());
            LinearLayout layout = view.findViewById(R.id.grade_group_layout3_second_year);
            layout.setVisibility(View.VISIBLE);
        }
        if (gradeList.size() >= 4) {
            setTextViewText(R.id.grade4_second_year, Double.toString(gradeList.get(3).getGrade()));
            setTextViewText(R.id.group4_second_year, gradeList.get(3).getDepartmentGroup());
            LinearLayout layout = view.findViewById(R.id.grade_group_layout4_second_year);
            layout.setVisibility(View.VISIBLE);
        }
        if (gradeList.size() >= 5) {
            setTextViewText(R.id.grade5_second_year, Double.toString(gradeList.get(4).getGrade()));
            setTextViewText(R.id.group5_second_year, gradeList.get(4).getDepartmentGroup());
            LinearLayout layout = view.findViewById(R.id.grade_group_layout5_second_year);
            layout.setVisibility(View.VISIBLE);
        }
        return this;
    }

//    public UnifyDialog setGrade(UnifyGrade grade) {
//        this.firstYearGrade = grade;
//        setFavoriteButton();
//        setTitle(grade.getSchool(), grade.getDepartment());
//        firstYearYearView.setText("-----" + grade.getYear() + "年度-----");
//        firstYearGradeView.setText(Double.toString(grade.getGrade()));
//        firstYearGroupView.setText(grade.getDepartmentGroup());
//        return this;
//    }

    public UnifyDialog show(){
        builder.setView(view);
        AlertDialog dialog = builder.create();
        Window view = dialog.getWindow();
        view.setBackgroundDrawableResource(R.drawable.dialog_background);
        dialog.show();
        return this;
    }

    private void setFavoriteButton() {
        Favorite favorite = new Favorite(Config.TABLE_NAME_UNIFY, firstYearGrade.getSchool(), firstYearGrade.getDepartment());
        favoriteButton = view.findViewById(R.id.favorite_button);
        setFavoriteButtonDrawable(favorite);
        favoriteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Favorite favorite = new Favorite(Config.TABLE_NAME_UNIFY, firstYearGrade.getSchool(), firstYearGrade.getDepartment());
                Favorite.saveOrDelete(favorite);
                setFavoriteButtonDrawable(favorite);
            }
        });
    }

    private void setFavoriteButtonDrawable(Favorite favorite) {
        Drawable drawable;
        if (Favorite.isFavoriteExist(favorite)) {
            drawable = ContextCompat.getDrawable(context, R.drawable.ic_favorite);
        } else {
            drawable = ContextCompat.getDrawable(context, R.drawable.ic_favorite_border);
        }
        final int sdk = android.os.Build.VERSION.SDK_INT;
        if(sdk < android.os.Build.VERSION_CODES.JELLY_BEAN) {
            favoriteButton.setBackgroundDrawable(drawable);
        } else {
            favoriteButton.setBackground(drawable);
        }
    }
}
