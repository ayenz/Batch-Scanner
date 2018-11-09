package com.ayenz.stillmen.tokpedxjnebatchscanner;

import android.Manifest;
import android.app.Activity;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity{
    private static final int ZXING_CAMERA_PERMISSION = 1;
    private EditText editable;
    private Class<?> mClss;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editable= (EditText) findViewById(R.id.editText5);

        Button scan = (Button) findViewById(R.id.button);
        Button copy = (Button) findViewById(R.id.copy);

        scan.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                if (ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.CAMERA)
                        != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(MainActivity.this,
                            new String[]{Manifest.permission.CAMERA}, ZXING_CAMERA_PERMISSION);
                } else {
                    Intent myIntent = new Intent(view.getContext(), ScannerActivity.class);
                    startActivityForResult(myIntent, 0);
                }
            }

        });

        copy.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                String teks=editable.getText().toString();
                ClipData clip = ClipData.newPlainText("Copied", teks);
                clipboard.setPrimaryClip(clip);
                Toast.makeText(getApplicationContext(),"Copied!",Toast.LENGTH_LONG).show();
            }

        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        String bar=data.getStringExtra("BARCODE");
        Log.e("Data", bar);
        if (bar.isEmpty()){
            String teks=editable.getText().toString();
            editable.setText(teks);
            editable.setSelection(editable.getText().length());
        }
        else{
            String teks=editable.getText().toString()+"\n"+bar;
            editable.setText(teks);
            editable.setSelection(editable.getText().length());
        }
    }

    public void onClickWhatsApp(View view) {

        PackageManager pm=getPackageManager();
        try {

            Intent waIntent = new Intent(Intent.ACTION_SEND);
            waIntent.setType("text/plain");
            String text = editable.getText().toString();

            PackageInfo info=pm.getPackageInfo("com.whatsapp", PackageManager.GET_META_DATA);
            //Check if package exists or not. If not then code
            //in catch block will be called
            waIntent.setPackage("com.whatsapp");

            waIntent.putExtra(Intent.EXTRA_TEXT, text);
            startActivity(Intent.createChooser(waIntent, "Share with"));

        } catch (PackageManager.NameNotFoundException e) {
            Toast.makeText(this, "WhatsApp not Installed", Toast.LENGTH_SHORT)
                    .show();
        }

    }

    public void clear(View v){
        editable.setText("");
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,  String permissions[], int[] grantResults) {
        switch (requestCode) {
            case ZXING_CAMERA_PERMISSION:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Intent myIntent = new Intent(this, ScannerActivity.class);
                    startActivityForResult(myIntent, 0);
                } else {
                    Toast.makeText(this, "Please grant camera permission to use the QR Scanner", Toast.LENGTH_SHORT).show();
                }
                return;
        }
    }
}
