package com.pu.anonymous.mobileinformationcenter;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
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
public class SignUp extends Activity {

    ProgressDialog pDialog;
    ImageButton btnSignup;
    Button btnSignHere;
    EditText etUsername, etEmail, etPassword, etConfirmPassword;
    String username, email, password, confirm_pass;
    int result;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sign_up);

        etUsername = (EditText) findViewById(R.id.su_username);
        etEmail = (EditText) findViewById(R.id.su_email);
        etPassword = (EditText) findViewById(R.id.su_password);
        etConfirmPassword = (EditText) findViewById(R.id.su_confirm_password);

        btnSignHere = (Button) findViewById(R.id.btn_sign_here);
        btnSignHere.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SignUp.this, Login.class));
            }
        });

        btnSignup = (ImageButton) findViewById(R.id.btn_sg_signup);
        btnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (isNetworkAvailable()) {
//                    task = "register";
                    username = etUsername.getText().toString();
                    email = etEmail.getText().toString();
                    password = etPassword.getText().toString();
                    confirm_pass = etConfirmPassword.getText()
                            .toString();
                    if (username.equals("") || email.equals("") || password.equals("") || confirm_pass.equals(""))

                        Toast.makeText(getApplicationContext(), "Kolom Harus diisi Semua",
                                Toast.LENGTH_LONG).show();

                    else {
                        if (!password.equals(confirm_pass))

                            Toast.makeText(getApplicationContext(), "Password Tidak Cocok",
                                    Toast.LENGTH_LONG).show();
                        else {

                            new RegisterTask().execute();

                        }
                    }
                } else

                    Toast.makeText(getApplicationContext(), "Tidak ada koneksi tersedia",
                            Toast.LENGTH_LONG).show();
            }
        });

    }


    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager
                .getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    private class RegisterTask extends AsyncTask<String, String, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // Showing progress dialog
            pDialog = new ProgressDialog(SignUp.this);
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

            params.add(new BasicNameValuePair("method", "register"));
            params.add(new BasicNameValuePair("username", etUsername.getText().toString()));
            params.add(new BasicNameValuePair("email", etEmail.getText().toString()));
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
            super.onPostExecute(result);
            // Dismiss the progress dialog
            if (pDialog.isShowing())
                pDialog.dismiss();

            if (result.equals("1")) {

                startActivity(new Intent(SignUp.this, MainActivity.class));

            } else if (result.equals("0")) {

                Toast.makeText(getApplicationContext(), "Username dan Password Salah", Toast.LENGTH_LONG)
                        .show();

            }

        }

    }

}
