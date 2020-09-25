package com.feveral.seniorhigh;

import android.annotation.TargetApi;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import androidx.annotation.NonNull;

import com.feveral.seniorhigh.basic.BasicSearchActivity;
import com.feveral.seniorhigh.database.GradeDatabase;
import com.feveral.seniorhigh.database.UserDatabase;
import com.feveral.seniorhigh.unify.UnifySearchActivity;
import com.feveral.seniorhigh.widget.WidgetSettingFragment;
import com.google.android.material.navigation.NavigationView;
import androidx.fragment.app.FragmentTransaction;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.appcompat.widget.Toolbar;

import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;

import com.feveral.seniorhigh.basic.BasicFragment;
import com.feveral.seniorhigh.designated.DesignatedFragment;
import com.feveral.seniorhigh.designated.DesignatedSearchActivity;
import com.feveral.seniorhigh.favorite.FavoriteFragment;
import com.feveral.seniorhigh.response.ResponseFragment;
import com.feveral.seniorhigh.unify.UnifyFragment;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

public class MainActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private DrawerLayout drawerLayout;
    private NavigationView sideBar;

    private BaseFragment currentFragment;
    private DesignatedFragment designatedFragment = new DesignatedFragment();
    private BasicFragment basicFragment = new BasicFragment();
    private UnifyFragment unifyFragment = new UnifyFragment();
    private ResponseFragment responseFragment = new ResponseFragment();
    private FavoriteFragment favoriteFragment= new FavoriteFragment();
    private WidgetSettingFragment widgetSettingFragment = new WidgetSettingFragment();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        GradeDatabase.initialDatabase(this);
        UserDatabase.initialDatabase(this);
        setToolbar();
        setupSidePanel();
        setToggle();
        switchFragment("DesignatedFragment");
        loadAdvertisement();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.search && currentFragment instanceof DesignatedFragment) {
            Intent intent = new Intent(this, DesignatedSearchActivity.class);
            startActivity(intent);
        } else if (item.getItemId() == R.id.search && currentFragment instanceof BasicFragment) {
            Intent intent = new Intent(this, BasicSearchActivity.class);
            startActivity(intent);
        } else if (item.getItemId() == R.id.search && currentFragment instanceof UnifyFragment) {
            Intent intent = new Intent(this, UnifySearchActivity.class);
            startActivity(intent);
        }
        return true;
    }

    private void loadAdvertisement(){
        AdManager adManager = new AdManager(this);
//        adManager.loadInterstitialAd();
//        adManager.showDelayedInterstitialAd();
        AdView adView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().addTestDevice("DF3F1017104F0244987606DBD16D7A1D").build();
        adView.loadAd(adRequest);
//        adManager.loadNativeAd();
    }

    private void setupSidePanel(){
        sideBar = findViewById(R.id.sidebar);
        sideBar.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()){
                    case R.id.sidebar_item_designated:
                        switchFragment("DesignatedFragment");
                        break;
                    case R.id.sidebar_item_basic:
                        switchFragment("BasicFragment");
                        break;
                    case R.id.sidebar_item_unify:
                        switchFragment("UnifyFragment");
                        break;
                    case R.id.sidebar_item_response:
                        switchFragment("ResponseFragment");
                        break;
                    case R.id.sidebar_item_favorite:
                        switchFragment("FavoriteFragment");
                        break;
                    case R.id.sidebar_item_widget:
                        switchFragment("WidgetSettingFragment");
                        break;
                    default:
                        return false;
                }
                return true;
            }
        });
        sideBar.getMenu().getItem(0).setChecked(true);
    }

    private void switchFragment(String fragmentName){
        switch (fragmentName){
            case "DesignatedFragment":
                sideBar.setItemIconTintList(getResources().getColorStateList(R.color.sidebar_designated_color));
                sideBar.setItemTextColor(getResources().getColorStateList(R.color.sidebar_designated_color));
                changeFragment(designatedFragment);
                break;
            case "BasicFragment":
                sideBar.setItemIconTintList(getResources().getColorStateList(R.color.sidebar_basic_color));
                sideBar.setItemTextColor(getResources().getColorStateList(R.color.sidebar_basic_color));
                changeFragment(basicFragment);
                break;
            case "UnifyFragment":
                sideBar.setItemIconTintList(getResources().getColorStateList(R.color.sidebar_unify_color));
                sideBar.setItemTextColor(getResources().getColorStateList(R.color.sidebar_unify_color));
                changeFragment(unifyFragment);
                break;
            case "ResponseFragment":
                sideBar.setItemIconTintList(getResources().getColorStateList(R.color.sidebar_response_color));
                sideBar.setItemTextColor(getResources().getColorStateList(R.color.sidebar_response_color));
                changeFragment(responseFragment);
                break;
            case "FavoriteFragment":
                sideBar.setItemIconTintList(getResources().getColorStateList(R.color.sidebar_favorite_color));
                sideBar.setItemTextColor(getResources().getColorStateList(R.color.sidebar_favorite_color));
                changeFragment(favoriteFragment);
                break;
            case "WidgetSettingFragment":
                sideBar.setItemIconTintList(getResources().getColorStateList(R.color.sidebar_widget_color));
                sideBar.setItemTextColor(getResources().getColorStateList(R.color.sidebar_widget_color));
                changeFragment(widgetSettingFragment);
                break;
        }
    }

    private void changeFragment(BaseFragment to){
        FragmentTransaction transaction = getSupportFragmentManager()
                .beginTransaction();
        to.setAnimationListener(fragmentAnimationListener);
        if (currentFragment != null) {
            if (currentFragment.equals(to)) {
                return;
            }
            if (!to.isAdded()) {
                transaction.hide(currentFragment)
                        .add(R.id.fragment_container, to).commit();
            } else {
                transaction.hide(currentFragment).show(to).commit();
            }
        } else {
            transaction.add(R.id.fragment_container, to).commit();
        }
        currentFragment = to;
        setFragmentTitleAndColor();
    }

    @Override
    public void onBackPressed() {
        if(currentFragment instanceof DesignatedFragment) {
            if (!designatedFragment.onBackPress()) {
                super.onBackPressed();
            }
        } else if (currentFragment instanceof  BasicFragment){
            if (!basicFragment.onBackPress()) {
                super.onBackPressed();
            }
        } else if (currentFragment instanceof  UnifyFragment){
            if (!unifyFragment.onBackPress()) {
                super.onBackPressed();
            }
        } else {
            super.onBackPressed();
        }
    }

    private void setToolbar(){
        toolbar = findViewById(R.id.toolbar_main);
        toolbar.setTitle("指考成績");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
    }

    private void setToggle(){
        drawerLayout = findViewById(R.id.drawer_main);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this , drawerLayout , toolbar , R.string.open , R.string.close) {
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
            }
        };
        toggle.syncState();
        drawerLayout.setDrawerListener(toggle);
    }

    private void setFragmentTitleAndColor(){
        toolbar.setTitle(currentFragment.getTitleStringId());
        toolbar.setBackgroundColor(ContextCompat.getColor(getBaseContext(),currentFragment.getTitleColorId()));
        setStatusBarColor(ContextCompat.getColor(getBaseContext(),currentFragment.getTitleColorId()));
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private void setStatusBarColor(int color) {
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

    private Animation.AnimationListener fragmentAnimationListener = new Animation.AnimationListener() {
        @Override
        public void onAnimationStart(Animation animation) {}
        @Override
        public void onAnimationRepeat(Animation animation) {}
        @Override
        public void onAnimationEnd(Animation animation) {
            drawerLayout.closeDrawer(GravityCompat.START);
        }
    };
}
