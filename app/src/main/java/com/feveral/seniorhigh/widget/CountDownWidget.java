package com.feveral.seniorhigh.widget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.RemoteViews;

import com.feveral.seniorhigh.BrandActivity;
import com.feveral.seniorhigh.Config;
import com.feveral.seniorhigh.R;

/**
 * Implementation of App Widget functionality.
 */
public class CountDownWidget extends AppWidgetProvider {

    static void setWidgetText(Context context, RemoteViews views) {
        WidgetSettingManager widgetSettingManager = new WidgetSettingManager(context);
        String widgetMode = widgetSettingManager.getMode();
        CountDownManager countDownManager = new CountDownManager(widgetMode);
        String remainText = countDownManager.getRemainText();
        views.setTextViewText(R.id.examine_prompt, remainText.substring(0,4));
        views.setTextViewText(R.id.remain_days, remainText.substring(4));
        Intent intent = new Intent(context, BrandActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, 0);
        views.setOnClickPendingIntent(R.id.widget_layout, pendingIntent);
    }

    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId) {
        // Construct the RemoteViews object
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.count_down_widget);
        setWidgetText(context, views);
        // Instruct the widget manager to update the widget
        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);
        }
    }

    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }
}

