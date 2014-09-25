package com.pu.anonymous.mobileinformationcenter;

import android.annotation.TargetApi;
import android.content.res.Configuration;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;

/**
 * Created by Anonymous on 08/09/2014.
 */
public class MainActivity extends BaseActivity {
    public Fragment mContent;
    public TextView mTitle;

    // protected abstract int getLayoutResource();

    @TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH)
    @Override
    public void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my);

        // check if the content frame contains the menu frame
        if (findViewById(R.id.menu_frame) == null) {
            setBehindContentView(R.layout.menu_frame);
            getSlidingMenu().setSlidingEnabled(true);
            getSlidingMenu().setTouchModeAbove(SlidingMenu.TOUCHMODE_MARGIN);
            // show home as up so we can toggle
            getActionBar().setDisplayHomeAsUpEnabled(true);

        } else {
            // add a dummy view
            View v = new View(this);
            setBehindContentView(v);
            getSlidingMenu().setSlidingEnabled(false);
            getSlidingMenu().setTouchModeAbove(SlidingMenu.TOUCHMODE_NONE);
        }

        mTitle = (TextView) LayoutInflater.from(this).inflate(
                R.layout.actionbar_custom_title_center, null);
        android.app.ActionBar.LayoutParams params = new android.app.ActionBar.LayoutParams(
                ActionBar.LayoutParams.MATCH_PARENT,
                ActionBar.LayoutParams.MATCH_PARENT);

        getActionBar().setDisplayShowCustomEnabled(true);
        getActionBar().setCustomView(mTitle, params);

        if (savedInstanceState != null)
            mContent = getSupportFragmentManager().getFragment(
                    savedInstanceState, "mContent");

        if (mContent == null)
            mContent = new FragmentBeritaBaru();

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.content_frame, mContent).commit();

        // set the Behind View Fragment
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.menu_frame, new SideMenuFragment()).commit();
        mTitle.setText("Berita Baru");
//		mTitle.setPadding(-45, 0, 0, 0);

        // customize the SlidingMenu
        SlidingMenu sm = getSlidingMenu();
        sm.setBehindOffsetRes(R.dimen.slidingmenu_offset);
        sm.setShadowWidthRes(R.dimen.shadow_width);
        sm.setShadowDrawable(R.drawable.shadow);
        sm.setBehindScrollScale(0.25f);
        sm.setFadeDegree(0.25f);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        // TODO Auto-generated method stub
        super.onConfigurationChanged(newConfig);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                toggle();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        getSupportFragmentManager().putFragment(outState, "mContent", mContent);
    }

    public void switchContent(final Fragment fragment) {
        mContent = fragment;
        if (mContent instanceof FragmentBeritaBaru) {
            mTitle.setText("Berita Baru");
//			mTitle.setPadding(-45, 0, 0, 0);
        }
        else if (mContent instanceof FragmentProfileHolder) {
            mTitle.setText("Profil Bina Pelaksanaan II");
//			mTitle.setPadding(-25, 0, 0, 0);
        }
        else if (mContent instanceof FragmentGaleri) {
            mTitle.setText("Galeri");
//			mTitle.setPadding(-30, 0, 0, 0);
        }
        else if (mContent instanceof FragmentAgenda) {
            mTitle.setText("Agenda");
//            mTitle.setPadding(-30, 0, 0, 0);
        }


        getSupportFragmentManager().beginTransaction()
                .replace(R.id.content_frame, fragment).commit();
        Handler h = new Handler();
        h.postDelayed(new Runnable() {
            public void run() {
                getSlidingMenu().showContent();
            }
        }, 50);
    }

}