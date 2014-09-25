package com.pu.anonymous.mobileinformationcenter;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.PixelFormat;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Window;


public class SplashScreen extends Activity {

    public static String session, usernamepref,
            passwordpref;

    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        Window window = getWindow();
        window.setFormat(PixelFormat.RGBA_8888);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_screen);

    Thread Timer = new Thread() {
        public void run() {
            try {
                sleep(3500);
                session();
                finish();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {

            }

        }
    };

    Timer.start();
}

    public void onBackPressed() {

    }
    private void LoadPreferencesSession() {
        SharedPreferences sharedPreferences = PreferenceManager
                .getDefaultSharedPreferences(this);
        session = sharedPreferences.getString("session", "");
        usernamepref = sharedPreferences.getString("username", "");
        passwordpref = sharedPreferences.getString("password", "");

    }

    public void session() {
        LoadPreferencesSession();
        System.out.println("session :" + session);
        if (session.equals("login")) {
            startActivity(new Intent(SplashScreen.this, MainActivity.class));

        } else {
            startActivity(new Intent(SplashScreen.this, Login.class));
        } finish();
    }
}
