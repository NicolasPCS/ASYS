package com.example.asys.ui.qr;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.example.asys.MainActivity;
import com.example.asys.R;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

public class QrScannerActivity extends AppCompatActivity {

    Intent intentAct;
    Bundle extras;
    String tipoAsis, token;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qr_scanner);

        intentAct = getIntent();
        extras = intentAct.getExtras();
        tipoAsis = extras.getString("tipoAsis");
        token = extras.getString("token");

        IntentIntegrator integrator = new IntentIntegrator(QrScannerActivity.this);
        integrator.setDesiredBarcodeFormats(IntentIntegrator.ALL_CODE_TYPES);
        integrator.setPrompt("Lector - CDP");
        integrator.setCameraId(0);
        //integrator.setBeepEnabled(true);
        integrator.setBarcodeImageEnabled(true);
        integrator.initiateScan();
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);

        if (result != null) {
            if (result.getContents() == null) {
                Toast.makeText(this, "Lectora cancelada", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(QrScannerActivity.this, MainActivity.class);

                // Llevar abajo
                intent.putExtra("clave", "111111");
                intent.putExtra("tipoAsis", "Sin asistencia");
                intent.putExtra("token", "No token");

                startActivity(intent);
            } else {
                Toast.makeText(this, "Asistencia registrada correctamente", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(QrScannerActivity.this, MainActivity.class);

                // Llevar abajo
                intent.putExtra("clave", result.getContents());
                intent.putExtra("tipoAsis", tipoAsis);
                intent.putExtra("token", token);

                startActivity(intent);
                //tctResult.setText(result.getContents());
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }
}