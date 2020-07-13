package com.feveral.seniorhigh.designated;

import android.content.Context;
import androidx.appcompat.app.AlertDialog;
import androidx.core.content.ContextCompat;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.ImageButton;
import android.widget.TextView;

import com.feveral.seniorhigh.Config;
import com.feveral.seniorhigh.Model.DesignatedGrade;
import com.feveral.seniorhigh.Model.Favorite;
import com.feveral.seniorhigh.R;

/**
 * Created by feveral on 2017/7/27.
 */

public class DesignatedDialog {
    private Context context;
    private TextView firstYearView;
    private TextView firstWeightView;
    private TextView firstBalanceView;
    private TextView firstPeopleView;

    private TextView secondYearView;
    private TextView secondWeightView;
    private TextView secondBalanceView;
    private TextView secondPeopleView;
    private ImageButton favoriteButton;

    private DesignatedGrade firstYearGrade;
    private DesignatedGrade secondYearGrade;

    private View view;
    private AlertDialog.Builder builder;

    public DesignatedDialog(Context context){
        this.context = context;
        builder = new AlertDialog.Builder(context , R.style.AlertDialogCustom);
        view = LayoutInflater.from(context).inflate(R.layout.designated_dialog,null);
        firstYearView = (TextView) view.findViewById(R.id.dialog_first_year);
        firstWeightView = (TextView) view.findViewById(R.id.dialog_first_weight);
        firstBalanceView = (TextView) view.findViewById(R.id.dialog_first_balance);
        firstPeopleView = (TextView) view.findViewById(R.id.dialog_first_people);
        secondYearView = (TextView) view.findViewById(R.id.dialog_second_year);
        secondWeightView = (TextView) view.findViewById(R.id.dialog_second_weight);
        secondBalanceView = (TextView) view.findViewById(R.id.dialog_second_balance);
        secondPeopleView = (TextView) view.findViewById(R.id.dialog_second_people);
    }

    public void setTitle(String school , String department){
        builder.setTitle(school + "  " + department);
    }

    public DesignatedDialog setFirstYearGrade(DesignatedGrade grade) {
        this.firstYearGrade = grade;
        setTitle(grade.getSchool(), grade.getDepartment());
        setFavoriteButton();
        firstYearView.setText(grade.getYearString());
        firstWeightView.setText(grade.getWeightString());
        firstBalanceView.setText(grade.getBalanceString());
        firstPeopleView.setText(grade.getPeopleString());
        return this;
    }

    public DesignatedDialog setSecondYearGrade(DesignatedGrade grade) {
        this.secondYearGrade = grade;
        secondYearView.setText(grade.getYearString());
        secondWeightView.setText(grade.getWeightString());
        secondBalanceView.setText(grade.getBalanceString());
        secondPeopleView.setText(grade.getPeopleString());
        return this;
    }

    public DesignatedDialog show(){
        if(isGradeEmpty()){
            setSecondGone();
        }
        builder.setView(view);
        AlertDialog dialog = builder.create();
        Window view = dialog.getWindow();
        view.setBackgroundDrawableResource(R.drawable.dialog_background);
        dialog.show();
        return this;
    }

    private boolean isGradeEmpty(){
        return secondWeightView.getText().equals("");
    }

    private void setSecondGone(){
        TextView secondBalancePromptView = view.findViewById(R.id.dialog_second_balance_prompt);
        TextView secondPeoplePromptView = view.findViewById(R.id.dialog_second_people_prompt);
        secondBalancePromptView.setVisibility(View.GONE);
        secondPeoplePromptView.setVisibility(View.GONE);
        secondYearView.setVisibility(View.GONE);
        secondWeightView.setVisibility(View.GONE);
        secondBalanceView.setVisibility(View.GONE);
        secondPeopleView.setVisibility(View.GONE);
    }

    private void setFavoriteButton() {
        Favorite favorite = new Favorite(Config.TABLE_NAME_DESIGNATED, firstYearGrade.getSchool(), firstYearGrade.getDepartment());
        favoriteButton = view.findViewById(R.id.favorite_button);
        setFavoriteButtonDrawable(favorite);
        favoriteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Favorite favorite = new Favorite(Config.TABLE_NAME_DESIGNATED, firstYearGrade.getSchool(), firstYearGrade.getDepartment());
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