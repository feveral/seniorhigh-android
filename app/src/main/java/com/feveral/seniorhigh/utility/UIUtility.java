package com.feveral.seniorhigh.utility;

import android.content.res.Resources;
import android.util.TypedValue;
import android.view.ViewGroup;

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
}
