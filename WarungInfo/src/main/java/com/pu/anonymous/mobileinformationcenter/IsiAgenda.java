package com.pu.anonymous.mobileinformationcenter;

import android.app.ActionBar;
import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;


/**
 * Created by Anonymous on 18/09/2014.
 */

public class IsiAgenda extends Activity {

    TextView txtTittle, txtTanggal, txtIsi;
    ImageButton btnShare;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.isi_agenda);

        txtTittle = (TextView) findViewById(R.id.agenda_title);
        txtTittle.setText(R.string.judul_agenda);

        txtTanggal = (TextView) findViewById(R.id.agenda_tanggal);
        txtTanggal.setText(R.string.tanggal_agenda);

        txtIsi = (TextView) findViewById(R.id.agenda_isi);
        txtIsi.setText(R.string.isi_agenda);

        btnShare = (ImageButton) findViewById(R.id.agenda_btnShare);
        btnShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(v.getContext(), "Masih dalam tahap penyelesaian", Toast.LENGTH_LONG).show();
            }
        });

        // get action bar
        ActionBar actionBar = getActionBar();

        // Enabling Up / Back navigation
        actionBar.setDisplayHomeAsUpEnabled(true);


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
              default:
                return super.onOptionsItemSelected(item);
        }
    }
}