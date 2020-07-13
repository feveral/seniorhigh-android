package com.feveral.seniorhigh.basic;

import android.annotation.TargetApi;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import com.feveral.seniorhigh.R;
import com.google.android.material.textfield.TextInputEditText;

import static com.feveral.seniorhigh.animation.MyAnimation.getDefaultAnimator;

public class BasicSearchActivity extends AppCompatActivity {

    RecyclerView _recyclerView;
    BasicSearchAdapter _searchListAdapter;
    TextInputEditText _searchTextField;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_basic_search);
        setToolBar();
        setStatusBarColor();
        setSearchTextField();
        setSchoolRecyclerView();
        loadAdvertisement();
    }

    private void loadAdvertisement(){
        MobileAds.initialize(this, this.getResources().getString(R.string.ad_application_id)); //AdMob 應用程式編號
        AdView adView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().addTestDevice("DF3F1017104F0244987606DBD16D7A1D").build();
        adView.loadAd(adRequest);
    }

    private void setToolBar(){
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle(R.string.basic_grade_text);
        toolbar.setBackgroundColor(ContextCompat.getColor(getBaseContext(), R.color.primary));
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
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
        _searchTextField = findViewById(R.id.basic_search_input);
        _searchTextField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override
            public void afterTextChanged(Editable s) {}
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                _searchListAdapter.setKeyWord(s.toString());
                TextView designatedInputPrompt = findViewById(R.id.basic_search_input_prompt);
                if (_searchListAdapter.getItemCount() == 0) {
                    designatedInputPrompt.setVisibility(View.VISIBLE);
                } else {
                    designatedInputPrompt.setVisibility(View.GONE);
                }
            }
        });
    }

    public void setSchoolRecyclerView() {
        _recyclerView = findViewById(R.id.basic_search_recycler);
        _searchListAdapter = new BasicSearchAdapter(this);
        _recyclerView.setAdapter(_searchListAdapter);
        _recyclerView.setLayoutManager(new LinearLayoutManager(this));
        _recyclerView.setItemAnimator(getDefaultAnimator());
    }
}
