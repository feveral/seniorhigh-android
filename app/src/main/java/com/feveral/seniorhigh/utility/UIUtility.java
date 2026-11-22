package com.feveral.seniorhigh.utility;

import android.content.res.Resources;
import android.util.TypedValue;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.view.ViewGroup;
import android.view.Window;

import com.feveral.seniorhigh.R;

import androidx.core.view.WindowCompat;
import androidx.core.view.WindowInsetsControllerCompat;

import androidx.appcompat.widget.Toolbar;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class UIUtility {

    public static int getActionBarSize(Resources.Theme theme, Resources resources) {
        TypedValue tv = new TypedValue();
        if (theme.resolveAttribute(android.R.attr.actionBarSize, tv, true)) {
            return TypedValue.complexToDimensionPixelSize(tv.data, resources.getDisplayMetrics());
        }
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 56, resources.getDisplayMetrics());
    }

    public static void adjustToolbarWindowInsets(Toolbar toolbar, Resources.Theme theme, Resources resources) {
        ViewCompat.setOnApplyWindowInsetsListener(toolbar, (v, insets) -> {
            int statusBarHeight = insets.getInsets(WindowInsetsCompat.Type.statusBars()).top;
            v.setPadding(v.getPaddingLeft(), statusBarHeight, v.getPaddingRight(), v.getPaddingBottom());

            ViewGroup.LayoutParams params = v.getLayoutParams();
            params.height = statusBarHeight + getActionBarSize(theme, resources);
            v.setLayoutParams(params);
            return insets;
        });
    }

    /** Dark status/navigation bar icons on the light app background. */
    public static void applyLightSystemBars(Window window) {
        WindowInsetsControllerCompat controller =
                WindowCompat.getInsetsController(window, window.getDecorView());
        controller.setAppearanceLightStatusBars(true);
        controller.setAppearanceLightNavigationBars(true);
    }

    /** Keep bottom content (e.g. the banner ad) clear of the gesture/navigation bar. */
    public static void adjustBottomWindowInsets(View view) {
        final int basePadding = view.getPaddingBottom();
        ViewCompat.setOnApplyWindowInsetsListener(view, (v, insets) -> {
            int bottom = insets.getInsets(WindowInsetsCompat.Type.navigationBars()).bottom;
            v.setPadding(v.getPaddingLeft(), v.getPaddingTop(), v.getPaddingRight(), basePadding + bottom);
            return insets;
        });
    }

    /** Two-line dialog header: school as a small label, department as the bold title. */
    public static View buildDialogTitle(Context context, String school, String department) {
        View title = LayoutInflater.from(context).inflate(R.layout.dialog_title, null);
        ((TextView) title.findViewById(R.id.dialog_title_school)).setText(school);
        ((TextView) title.findViewById(R.id.dialog_title_department)).setText(department);
        return title;
    }

    /** Hide rows tagged "score_row" whose children have all been hidden, so no empty gaps remain. */
    public static void collapseEmptyRows(View view) {
        if (!(view instanceof ViewGroup)) {
            return;
        }
        ViewGroup group = (ViewGroup) view;
        if ("score_row".equals(group.getTag())) {
            boolean anyVisible = false;
            for (int i = 0; i < group.getChildCount(); i++) {
                if (group.getChildAt(i).getVisibility() != View.GONE) {
                    anyVisible = true;
                    break;
                }
            }
            group.setVisibility(anyVisible ? View.VISIBLE : View.GONE);
            return;
        }
        for (int i = 0; i < group.getChildCount(); i++) {
            collapseEmptyRows(group.getChildAt(i));
        }
    }
}
