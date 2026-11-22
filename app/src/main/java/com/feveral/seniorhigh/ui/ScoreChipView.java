package com.feveral.seniorhigh.ui;

import android.content.Context;
import android.graphics.Typeface;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.text.style.StyleSpan;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.core.content.ContextCompat;

import com.feveral.seniorhigh.R;

/**
 * Score pill used in list rows.
 *
 * Adapters set plain strings: either a single score ("53.10") or the 學測 form
 * "subjects\nscore" ("國英數B社\n52"). The 學測 form is rendered as a small subject
 * line above a bold score, both centered, so every chip in a list keeps the same
 * width and the score sits in the same place. Empty text hides the chip.
 */
public class ScoreChipView extends AppCompatTextView {

    private static final float SUBJECT_SCALE = 0.75f;

    public ScoreChipView(Context context) {
        super(context);
    }

    public ScoreChipView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public ScoreChipView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public void setText(CharSequence text, BufferType type) {
        String raw = text == null ? "" : text.toString().trim();
        setVisibility(raw.isEmpty() ? View.GONE : View.VISIBLE);

        int newline = raw.lastIndexOf('\n');
        if (newline < 0) {
            SpannableStringBuilder single = new SpannableStringBuilder(raw);
            single.setSpan(new StyleSpan(Typeface.BOLD), 0, single.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            super.setText(single, BufferType.SPANNABLE);
            return;
        }
        String subjects = raw.substring(0, newline).trim();
        String score = raw.substring(newline + 1).trim();

        SpannableStringBuilder sb = new SpannableStringBuilder();
        sb.append(subjects);
        int subjectEnd = sb.length();
        sb.setSpan(new RelativeSizeSpan(SUBJECT_SCALE), 0, subjectEnd, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        // getContext() is safe here; this branch never runs during TextView construction.
        sb.setSpan(new ForegroundColorSpan(ContextCompat.getColor(getContext(), R.color.on_amber_container_soft)),
                0, subjectEnd, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        sb.append('\n');
        int scoreStart = sb.length();
        sb.append(score);
        sb.setSpan(new StyleSpan(Typeface.BOLD), scoreStart, sb.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        super.setText(sb, BufferType.SPANNABLE);
    }
}
