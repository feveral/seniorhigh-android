package com.feveral.seniorhigh.widget;

import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.feveral.seniorhigh.BaseFragment;
import com.feveral.seniorhigh.Config;
import com.feveral.seniorhigh.R;

import androidx.core.content.ContextCompat;

public class WidgetSettingFragment extends BaseFragment {

    private View fragmentView;
    private Button designatedButton;
    private Button basicButton;
    private Button unifyButton;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        fragmentView = inflater.inflate(R.layout.fragment_widget_setting,container,false);
        setButton();
        return fragmentView;
    }

    public void updateWidget() {
        int[] ids = AppWidgetManager.getInstance(getActivity().getApplication()).getAppWidgetIds(new ComponentName(getActivity().getApplication(), CountDownWidget.class));
        CountDownWidget countDownWidget = new CountDownWidget();
        countDownWidget.onUpdate(getContext(), AppWidgetManager.getInstance(getActivity()),ids);
    }

    private void setButtonWhite(Button button) {
        button.setTextColor(ContextCompat.getColor(getContext(),  R.color.black));
        button.setBackgroundResource(R.drawable.button_shape_white);
    }

    private void setButtonBlack(Button button) {
        button.setTextColor(ContextCompat.getColor(getContext(),  R.color.white));
        button.setBackgroundResource(R.drawable.button_shape_black);
    }

    private void refreshButtonStatus() {
        WidgetSettingManager widgetSettingManager = new WidgetSettingManager(getContext());
        String widgetMode = widgetSettingManager.getMode();
        designatedButton = fragmentView.findViewById(R.id.widget_choose_button_designated);
        basicButton = fragmentView.findViewById(R.id.widget_choose_button_basic);
        unifyButton = fragmentView.findViewById(R.id.widget_choose_button_unify);
        setButtonWhite(designatedButton);
        setButtonWhite(basicButton);
        setButtonWhite(unifyButton);
        if (widgetMode.equals(Config.TABLE_NAME_DESIGNATED)) {
            setButtonBlack(designatedButton);
        } else if (widgetMode.equals(Config.TABLE_NAME_BASIC)) {
            setButtonBlack(basicButton);
        } else if (widgetMode.equals(Config.TABLE_NAME_UNIFY)) {
            setButtonBlack(unifyButton);
        }
    }

    private void setButton() {
        refreshButtonStatus();
        final WidgetSettingManager widgetSettingManager = new WidgetSettingManager(getContext());
        designatedButton = fragmentView.findViewById(R.id.widget_choose_button_designated);
        basicButton = fragmentView.findViewById(R.id.widget_choose_button_basic);
        unifyButton = fragmentView.findViewById(R.id.widget_choose_button_unify);
        designatedButton.setText(new CountDownManager(Config.TABLE_NAME_DESIGNATED).getRemainText());
        basicButton.setText(new CountDownManager(Config.TABLE_NAME_BASIC).getRemainText());
        unifyButton.setText(new CountDownManager(Config.TABLE_NAME_UNIFY).getRemainText());
        designatedButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                widgetSettingManager.changeMode(Config.TABLE_NAME_DESIGNATED);
                refreshButtonStatus();
                updateWidget();
            }
        });
        basicButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                widgetSettingManager.changeMode(Config.TABLE_NAME_BASIC);
                refreshButtonStatus();
                updateWidget();
            }
        });
        unifyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                widgetSettingManager.changeMode(Config.TABLE_NAME_UNIFY);
                refreshButtonStatus();
                updateWidget();
            }
        });
    }

    @Override
    public int getTitleColorId() {
        return R.color.primary;
    }

    @Override
    public int getTitleStringId() {
        return R.string.count_down_text;
    }
}
