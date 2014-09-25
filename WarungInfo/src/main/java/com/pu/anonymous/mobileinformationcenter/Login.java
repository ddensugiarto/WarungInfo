package com.pu.anonymous.mobileinformationcenter;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Anonymous on 08/09/2014.
 */

public class Login extends Activity {

    ProgressDialog pDialog;
    ImageButton btnLogin, btnSignUp;
    Button btnTapHere;
    EditText etUsername, etPassword;
    String username, password;
    public static String session, usernamepref,
            passwordpref;
    int result, hint;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        LoadPreferencesSession();

        btnTapHere = (Button) findViewById(R.id.btn_tap_here);
        btnTapHere.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Login.this, ForgotPassword.class));
            }
        });

        etUsername = (EditText) findViewById(R.id.lg_email);
        etPassword = (EditText) findViewById(R.id.lg_password);

        btnLogin = (ImageButton) findViewById(R.id.btn_lg_login);
        btnLogin.setOnClickListener(new View.OnClickListener() {

        @Override
        public void onClick(View v) {
            if (isNetworkAvailable()) {

                LoadPreferencesSession();
                username = etUsername.getText().toString();
                password = etPassword.getText().toString();

                   if (username.equals("") || password.equals(""))
                       Toast.makeText(getApplicationContext(), "Masukan Username dan Password", Toast.LENGTH_LONG).show();

                   else {
                       new LoginTask().execute();
                       LoadPreferencesSession();
                   }
            } else
                Toast.makeText(getApplicationContext(), "Koneksi Tidak Tersedia", Toast.LENGTH_LONG)
                                                        .show();
            }
        }

        );

        btnSignUp = (ImageButton) findViewById(R.id.btn_lg_signup);
        btnSignUp.setOnClickListener(new View.OnClickListener() {

             @Override
             public void onClick(View v) {
                 startActivity(new Intent(Login.this, SignUp.class));

             }
        });

    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager
                .getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    public void onBackPressed() {

    }

    private class LoginTask extends AsyncTask<String, String, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // Showing progress dialog
            pDialog = new ProgressDialog(Login.this);
            pDialog.setMessage("Mohon Tunggu...");
            pDialog.setCancelable(true);
            pDialog.show();

        }

        @Override
        protected String doInBackground(String... arg0) {
            // Creating service handler class instance
            ServiceHandler sh = new ServiceHandler();
            // Building Parameters
            List<NameValuePair> params = new ArrayList<NameValuePair>();

            params.add(new BasicNameValuePair("method", "login"));
            params.add(new BasicNameValuePair("username", etUsername.getText().toString()));
            params.add(new BasicNameValuePair("password", etPassword.getText().toString()));

            // Making a request to url and getting response
            String jsonStr = sh.makeServiceCall(getResources().getString(R.string.url), ServiceHandler.GET, params);

            Log.d("Response: ", "> " + jsonStr);

            if (jsonStr != null) {
                try {
                    JSONObject jsonObj = new JSONObject(jsonStr);

                    // Getting JSON Array node
                    result = jsonObj.getInt("result");

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            } else {
                Log.e("ServiceHandler", "Couldn't get any data from the url");
            }

            return Integer.toString(result);
        }

        @Override
        protected void onPostExecute(String result) {
            LoadPreferencesSession();
            super.onPostExecute(result);
            // Dismiss the progress dialog
            if (pDialog.isShowing())
                pDialog.dismiss();

            if (result.equals("1")) {
                SavePreferences("session", "login");
                startActivity(new Intent(Login.this, MainActivity.class));

            } else if (result.equals("0")) {

                Toast.makeText(getApplicationContext(), "Username dan Password Salah", Toast.LENGTH_LONG)
                        .show();

            }
        }

    }

    private void SavePreferences(String key, String value) {
        SharedPreferences sharedPreferences = PreferenceManager
                .getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(key, value);
        editor.commit();
    }

    private void LoadPreferencesSession() {
        SharedPreferences sharedPreferences = PreferenceManager
                .getDefaultSharedPreferences(this);
        session = sharedPreferences.getString("session", "");
        usernamepref = sharedPreferences.getString("username", "");
        passwordpref = sharedPreferences.getString("password", "");

    }

    public boolean session() {
        LoadPreferencesSession();
        System.out.println("session :" + session);
        if (session.equals("login")) {
            startActivity(new Intent(Login.this, MainActivity.class));
            return true;
        } else
            setContentView(R.layout.login);
            return false;
    }
}