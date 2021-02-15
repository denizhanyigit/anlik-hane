package com.dyapp.anindaev;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class AcilisActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_acilis);
        InternetKontrol();
        if (InternetKontrol()) {
            Thread gecis;
            gecis = new Thread() {
                @Override
                public void run() {
                    try {

                        synchronized (this) {
                            wait(1000);
                        }
                    } catch (InterruptedException ex) {

                    } finally {
                        Intent intent = new Intent(AcilisActivity.this, GirisActivity.class);
                        startActivity(intent);
                        finish();

                    }

                }
            };
            gecis.start();
        } else {

                   ilanlarimSeceneklerDialog();
        }

    }

    public void ilanlarimSeceneklerDialog() {
        LayoutInflater inflater = getLayoutInflater();
        View view = inflater.inflate(R.layout.internet_yok, null);
        TextView tamam;

        tamam = (TextView) view.findViewById(R.id.tamam);

        AlertDialog.Builder alert = new AlertDialog.Builder(AcilisActivity.this);
        alert.setView(view);
        alert.setCancelable(false);
        final AlertDialog dialog = alert.create();
        dialog.show();
        //  dialog.getWindow().setLayout(650, 700);
        tamam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.cancel();
                finish();
                System.exit(0);

            }
        });

    }


    public boolean InternetKontrol() {
        ConnectivityManager manager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        if (manager.getActiveNetworkInfo() != null
                && manager.getActiveNetworkInfo().isAvailable()
                && manager.getActiveNetworkInfo().isConnected()) {
            return true;
        } else {
            return false;
        }
    }
}