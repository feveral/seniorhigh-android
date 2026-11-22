package com.feveral.seniorhigh.ui;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewParent;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatTextView;

import com.feveral.seniorhigh.R;

/**
 * Score pill used in list rows.
 *
 * Adapters set plain strings: either a single score ("53.10") or the 學測 form
 * "subjects\nscore" ("國英數B社\n52"). This view always shows only the score so every
 * chip in a list has the same shape, and routes the subject part to an optional
 * caption view (attr captionView). Empty text hides the chip.
 */
public class ScoreChipView extends AppCompatTextView {

    private int captionViewId = View.NO_ID; // assigned after super(); 0 during inflation
    private String captionPrefix = "";
    private TextView captionView;

    public ScoreChipView(Context context) {
        this(context, null);
    }

    public ScoreChipView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, android.R.attr.textViewStyle);
    }

    public ScoreChipView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.ScoreChipView);
        captionViewId = a.getResourceId(R.styleable.ScoreChipView_captionView, View.NO_ID);
        String prefix = a.getString(R.styleable.ScoreChipView_captionPrefix);
        captionPrefix = prefix == null ? "" : prefix;
        a.recycle();
    }

    @Override
    public void setText(CharSequence text, BufferType type) {
        String raw = text == null ? "" : text.toString().trim();
        String score = raw;
        String subjects = "";
        int newline = raw.lastIndexOf('\n');
        if (newline >= 0) {
            subjects = raw.substring(0, newline).trim();
            score = raw.substring(newline + 1).trim();
        }
        super.setText(score, type);
        setVisibility(score.isEmpty() ? View.GONE : View.VISIBLE);
        updateCaption(subjects);
    }

    private void updateCaption(String subjects) {
        TextView caption = findCaption();
        if (caption == null) {
            return;
        }
        if (subjects.isEmpty()) {
            caption.setVisibility(View.GONE);
        } else {
            caption.setText(captionPrefix + subjects);
            caption.setVisibility(View.VISIBLE);
        }
    }

    @Nullable
    private TextView findCaption() {
        if (captionViewId == View.NO_ID || captionViewId == 0) {
            return null;
        }
        if (captionView != null) {
            return captionView;
        }
        // Search upward so the caption is found within this row only.
        ViewParent parent = getParent();
        while (parent instanceof View) {
            View found = ((View) parent).findViewById(captionViewId);
            if (found instanceof TextView) {
                captionView = (TextView) found;
                return captionView;
            }
            parent = parent.getParent();
        }
        return null;
    }
}
