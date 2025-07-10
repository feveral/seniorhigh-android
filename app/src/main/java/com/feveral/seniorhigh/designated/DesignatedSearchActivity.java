package com.feveral.seniorhigh.designated;

import android.annotation.TargetApi;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.feveral.seniorhigh.R;
import com.feveral.seniorhigh.utility.AdUtility;
import com.google.android.material.textfield.TextInputEditText;

import static com.feveral.seniorhigh.animation.MyAnimation.getDefaultAnimator;

public class DesignatedSearchActivity extends AppCompatActivity {

    RecyclerView _recyclerView;
    DesignatedSearchAdapter _searchListAdapter;
    TextInputEditText _searchTextField;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_designated_search);
        setToolBar();
        setStatusBarColor();
        setSchoolRecyclerView();
        setSearchTextField();
        AdUtility.loadAd(findViewById(R.id.adView));
    }

    public void setToolBar() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setBackgroundColor(ContextCompat.getColor(getBaseContext(), R.color.primary));
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        toolbar.setNavigationOnClickListener(v -> onBackPressed());
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private void setStatusBarColor() {
        int color = ContextCompat.getColor(getBaseContext(), R.color.primary);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            if (color == Color.BLACK
                    && window.getNavigationBarColor() == Color.BLACK) {
                window.clearFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            } else {
                window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            }
            window.setStatusBarColor(color);
        }
    }

    public void setSearchTextField() {
        _searchTextField = findViewById(R.id.designated_search_input);
        _searchTextField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override
            public void afterTextChanged(Editable s) {}
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                _searchListAdapter.setKeyWord(s.toString());
                TextView designatedInputPrompt = findViewById(R.id.designated_search_input_prompt);
                if (_searchListAdapter.getItemCount() == 0) {
                    designatedInputPrompt.setVisibility(View.VISIBLE);
                } else {
                    designatedInputPrompt.setVisibility(View.GONE);
                }
            }
        });
    }

    public void setSchoolRecyclerView() {
        _recyclerView = findViewById(R.id.designated_search_recycler);
        _searchListAdapter = new DesignatedSearchAdapter(this);
        _recyclerView.setAdapter(_searchListAdapter);
        _recyclerView.setLayoutManager(new LinearLayoutManager(this));
        _recyclerView.setItemAnimator(getDefaultAnimator());
    }
}
