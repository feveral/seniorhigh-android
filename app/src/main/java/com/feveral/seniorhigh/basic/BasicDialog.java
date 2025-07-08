package com.feveral.seniorhigh.basic;

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
import android.widget.Toast;

import com.feveral.seniorhigh.Config;
import com.feveral.seniorhigh.Model.BasicGrade;
import com.feveral.seniorhigh.Model.Favorite;
import com.feveral.seniorhigh.R;
import com.feveral.seniorhigh.utility.TextUtils;

/**
 * Created by feveral on 2017/8/15.
 */

public class BasicDialog {
    private Context context;
    private TextView firstYearYearView;     private TextView secondYearYearView;
    private TextView firstYearFirstOrder;   private TextView secondYearFirstOrder;
    private TextView firstYearFirstGrade;   private TextView secondYearFirstGrade;
    private TextView firstYearSecondOrder;  private TextView secondYearSecondOrder;
    private TextView firstYearSecondGrade;  private TextView secondYearSecondGrade;
    private TextView firstYearThirdOrder;   private TextView secondYearThirdOrder;
    private TextView firstYearThirdGrade;   private TextView secondYearThirdGrade;
    private TextView firstYearForthOrder;   private TextView secondYearForthOrder;
    private TextView firstYearForthGrade;   private TextView secondYearForthGrade;
    private TextView firstYearFifthOrder;   private TextView secondYearFifthOrder;
    private TextView firstYearFifthGrade;   private TextView secondYearFifthGrade;
    private TextView firstYearFirstTitle;   private TextView secondYearFirstTitle;
    private TextView firstYearSecondTitle;  private TextView secondYearSecondTitle;
    private TextView firstYearThirdTitle;   private TextView secondYearThirdTitle;
    private TextView firstYearForthTitle;   private TextView secondYearForthTitle;
    private TextView firstYearFifthTitle;   private TextView secondYearFifthTitle;
    private ImageButton favoriteButton;
    private BasicGrade firstYearGrade;

    private View view;
    private AlertDialog.Builder builder;

    public BasicDialog(Context context){
        this.context = context;

        builder = new AlertDialog.Builder(context , R.style.AlertDialogCustom);
        view = LayoutInflater.from(context).inflate(R.layout.basic_dialog,null);
        firstYearYearView = view.findViewById(R.id.basic_dialog_first_year_year_title);
        firstYearFirstOrder = view.findViewById(R.id.basic_dialog_first_year_first_subject);
        firstYearFirstGrade = (TextView) view.findViewById(R.id.basic_dialog_first_year_first_grade);
        firstYearSecondOrder = (TextView) view.findViewById(R.id.basic_dialog_first_year_second_subject);
        firstYearSecondGrade = (TextView) view.findViewById(R.id.basic_dialog_first_year_second_grade);
        firstYearThirdOrder = (TextView) view.findViewById(R.id.basic_dialog_first_year_third_subject);
        firstYearThirdGrade = (TextView) view.findViewById(R.id.basic_dialog_first_year_third_grade);
        firstYearForthOrder = (TextView) view.findViewById(R.id.basic_dialog_first_year_forth_subject);
        firstYearForthGrade = (TextView) view.findViewById(R.id.basic_dialog_first_year_forth_grade);
        firstYearFifthOrder = (TextView) view.findViewById(R.id.basic_dialog_first_year_fifth_subject);
        firstYearFifthGrade = (TextView) view.findViewById(R.id.basic_dialog_first_year_fifth_grade);
        firstYearFirstTitle = (TextView) view.findViewById(R.id.basic_dialog_first_year_first_title);
        firstYearSecondTitle = (TextView) view.findViewById(R.id.basic_dialog_first_year_second_title);
        firstYearThirdTitle = (TextView) view.findViewById(R.id.basic_dialog_first_year_third_title);
        firstYearForthTitle = (TextView) view.findViewById(R.id.basic_dialog_first_year_forth_title);
        firstYearFifthTitle = (TextView) view.findViewById(R.id.basic_dialog_first_year_fifth_title);

        secondYearYearView = (TextView) view.findViewById(R.id.basic_dialog_second_year_year_title);
        secondYearFirstOrder = (TextView) view.findViewById(R.id.basic_dialog_second_year_first_subject);
        secondYearFirstGrade = (TextView) view.findViewById(R.id.basic_dialog_second_year_first_grade);
        secondYearSecondOrder = (TextView) view.findViewById(R.id.basic_dialog_second_year_second_subject);
        secondYearSecondGrade = (TextView) view.findViewById(R.id.basic_dialog_second_year_second_grade);
        secondYearThirdOrder = (TextView) view.findViewById(R.id.basic_dialog_second_year_third_subject);
        secondYearThirdGrade = (TextView) view.findViewById(R.id.basic_dialog_second_year_third_grade);
        secondYearForthOrder = (TextView) view.findViewById(R.id.basic_dialog_second_year_forth_subject);
        secondYearForthGrade = (TextView) view.findViewById(R.id.basic_dialog_second_year_forth_grade);
        secondYearFifthOrder = (TextView) view.findViewById(R.id.basic_dialog_second_year_fifth_subject);
        secondYearFifthGrade = (TextView) view.findViewById(R.id.basic_dialog_second_year_fifth_grade);
        secondYearFirstTitle = (TextView) view.findViewById(R.id.basic_dialog_second_year_first_title);
        secondYearSecondTitle = (TextView) view.findViewById(R.id.basic_dialog_second_year_second_title);
        secondYearThirdTitle = (TextView) view.findViewById(R.id.basic_dialog_second_year_third_title);
        secondYearForthTitle = (TextView) view.findViewById(R.id.basic_dialog_second_year_forth_title);
        secondYearFifthTitle = (TextView) view.findViewById(R.id.basic_dialog_second_year_fifth_title);
    }

    public void setTitle(String school , String department){
        builder.setTitle(school + "  " + department);
    }

    public BasicDialog setYear(String year){
        String newYear = "-----"  + year + "年度-----";
        firstYearYearView.setText(newYear);
        return this;
    }

    public BasicDialog setFirstGrade(BasicGrade firstYearGrade){
        this.firstYearGrade = firstYearGrade;
        Favorite favorite = new Favorite(Config.TABLE_NAME_BASIC, firstYearGrade.getSchool(), firstYearGrade.getDepartment());
        setFavoriteButton(favorite);
        setFirstYearViewGoneIfGradeNotExist(firstYearGrade);
        builder.setTitle(firstYearGrade.getSchool() + "  " + firstYearGrade.getDepartment());
        firstYearYearView.setText("-----"  + firstYearGrade.getYear() + "年度-----");
        firstYearFirstOrder.setText(firstYearGrade.getFirstOrder());
        firstYearFirstGrade.setText(String.valueOf(firstYearGrade.getFirstGrade()));
        firstYearSecondOrder.setText(firstYearGrade.getSecondOrder());
        firstYearSecondGrade.setText(String.valueOf(firstYearGrade.getSecondGrade()));
        firstYearThirdOrder.setText(firstYearGrade.getThirdOrder());
        firstYearThirdGrade.setText(String.valueOf(firstYearGrade.getThirdGrade()));
        firstYearForthOrder.setText(firstYearGrade.getFourthOrder());
        firstYearForthGrade.setText(String.valueOf(firstYearGrade.getFourthGrade()));
        firstYearFifthOrder.setText(firstYearGrade.getFifthOrder());
        firstYearFifthGrade.setText(String.valueOf(firstYearGrade.getFifthGrade()));
        return this;
    }

    public BasicDialog setSecondGrade(BasicGrade secondYearGrade){
        setSecondYearViewGoneIfGradeNotExist(secondYearGrade);
        secondYearYearView.setText("-----"  + secondYearGrade.getYear() + "年度-----");
        secondYearFirstOrder.setText(secondYearGrade.getFirstOrder());
        secondYearFirstGrade.setText(String.valueOf(secondYearGrade.getFirstGrade()));
        secondYearSecondOrder.setText(secondYearGrade.getSecondOrder());
        secondYearSecondGrade.setText(String.valueOf(secondYearGrade.getSecondGrade()));
        secondYearThirdOrder.setText(secondYearGrade.getThirdOrder());
        secondYearThirdGrade.setText(String.valueOf(secondYearGrade.getThirdGrade()));
        secondYearForthOrder.setText(secondYearGrade.getFourthOrder());
        secondYearForthGrade.setText(String.valueOf(secondYearGrade.getFourthGrade()));
        secondYearFifthOrder.setText(secondYearGrade.getFifthOrder());
        secondYearFifthGrade.setText(String.valueOf(secondYearGrade.getFifthGrade()));
        return this;
    }

    private void setFirstYearViewGoneIfGradeNotExist(BasicGrade basicGrade){
        if(basicGrade.getFirstOrder().equals("") || basicGrade.getFirstGrade()== 0){
            firstYearFirstOrder.setVisibility(View.GONE);
            firstYearFirstGrade.setVisibility(View.GONE);
            firstYearFirstTitle.setVisibility(View.GONE);
        }
        if(basicGrade.getSecondOrder().equals("") || basicGrade.getSecondGrade()== 0){
            firstYearSecondOrder.setVisibility(View.GONE);
            firstYearSecondGrade.setVisibility(View.GONE);
            firstYearSecondTitle.setVisibility(View.GONE);
        }
        if(basicGrade.getThirdOrder().equals("") || basicGrade.getThirdGrade()== 0){
            firstYearThirdOrder.setVisibility(View.GONE);
            firstYearThirdGrade.setVisibility(View.GONE);
            firstYearThirdTitle.setVisibility(View.GONE);
        }
        if(basicGrade.getFourthOrder().equals("") || basicGrade.getFourthGrade()== 0){
            firstYearForthOrder.setVisibility(View.GONE);
            firstYearForthGrade.setVisibility(View.GONE);
            firstYearForthTitle.setVisibility(View.GONE);
        }
        if(basicGrade.getFifthOrder().equals("") || basicGrade.getFifthGrade()== 0){
            firstYearFifthOrder.setVisibility(View.GONE);
            firstYearFifthGrade.setVisibility(View.GONE);
            firstYearFifthTitle.setVisibility(View.GONE);
        }
    }

    private void setSecondYearViewGoneIfGradeNotExist(BasicGrade basicGrade){
        if(basicGrade.getSchool().equals(""))
            secondYearYearView.setVisibility(View.GONE);
        if(basicGrade.getFirstOrder().equals("") || basicGrade.getFirstGrade()== 0){
            secondYearFirstOrder.setVisibility(View.GONE);
            secondYearFirstGrade.setVisibility(View.GONE);
            secondYearFirstTitle.setVisibility(View.GONE);
        }
        if(basicGrade.getSecondOrder().equals("") || basicGrade.getSecondGrade()== 0){
            secondYearSecondOrder.setVisibility(View.GONE);
            secondYearSecondGrade.setVisibility(View.GONE);
            secondYearSecondTitle.setVisibility(View.GONE);
        }
        if(basicGrade.getThirdOrder().equals("") || basicGrade.getThirdGrade()== 0){
            secondYearThirdOrder.setVisibility(View.GONE);
            secondYearThirdGrade.setVisibility(View.GONE);
            secondYearThirdTitle.setVisibility(View.GONE);
        }
        if(basicGrade.getFourthOrder().equals("") || basicGrade.getFourthGrade()== 0){
            secondYearForthOrder.setVisibility(View.GONE);
            secondYearForthGrade.setVisibility(View.GONE);
            secondYearForthTitle.setVisibility(View.GONE);
        }
        if(basicGrade.getFifthOrder().equals("") || basicGrade.getFifthGrade()== 0){
            secondYearFifthOrder.setVisibility(View.GONE);
            secondYearFifthGrade.setVisibility(View.GONE);
            secondYearFifthTitle.setVisibility(View.GONE);
        }
    }

    private void setFavoriteButton(Favorite favorite) {
        favoriteButton = view.findViewById(R.id.favorite_button);
        setFavoriteButtonDrawable(favorite);
        favoriteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Favorite favorite = new Favorite(Config.TABLE_NAME_BASIC, firstYearGrade.getSchool(), firstYearGrade.getDepartment());
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

    public BasicDialog show(){
        builder.setView(view);
        AlertDialog dialog = builder.create();
        Window view = dialog.getWindow();
        view.setBackgroundDrawableResource(R.drawable.dialog_background);
        dialog.show();
        return this;
    }
}
