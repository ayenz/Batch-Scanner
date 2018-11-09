package com.ayenz.stillmen.tokpedxjnebatchscanner;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.Result;

import java.util.ArrayList;
import java.util.List;

import me.dm7.barcodescanner.zxing.ZXingScannerView;

public class ScannerActivity extends AppCompatActivity implements ZXingScannerView.ResultHandler {
    private ZXingScannerView mScannerView;
    private LinearLayout qrCameraLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.scanner_fragment);

        qrCameraLayout = (LinearLayout) findViewById(R.id.llayput);
        mScannerView = new ZXingScannerView(this);
        qrCameraLayout.addView(mScannerView);

        List<BarcodeFormat> formats = new ArrayList<>();
        formats.add(BarcodeFormat.QR_CODE);
        formats.add(BarcodeFormat.AZTEC);
        formats.add(BarcodeFormat.CODABAR);
        formats.add(BarcodeFormat.CODE_39);
        formats.add(BarcodeFormat.CODE_93);
        formats.add(BarcodeFormat.CODE_128);
        formats.add(BarcodeFormat.DATA_MATRIX);
        formats.add(BarcodeFormat.EAN_8);
        formats.add(BarcodeFormat.EAN_13);
        formats.add(BarcodeFormat.ITF);
        formats.add(BarcodeFormat.MAXICODE);
        formats.add(BarcodeFormat.PDF_417);
        formats.add(BarcodeFormat.QR_CODE);
        formats.add(BarcodeFormat.RSS_14);
        formats.add(BarcodeFormat.RSS_EXPANDED);
        formats.add(BarcodeFormat.UPC_A);
        formats.add(BarcodeFormat.UPC_E);
        formats.add(BarcodeFormat.UPC_EAN_EXTENSION);

        mScannerView.setFormats(formats);
    }

    @Override
    public void onResume() {
        super.onResume();
        mScannerView.setResultHandler(this); // Register ourselves as a handler for scan results.
        mScannerView.startCamera();          // Start camera on resume
    }

    @Override
    public void onPause() {
        super.onPause();
        mScannerView.stopCamera();
    }

    @Override
    public void handleResult(final Result result) {
        Log.e("handler", result.getText()); // Prints scan results
        Log.e("handler", result.getBarcodeFormat().toString()); // Prints the scan format (qrcode)
        //mScannerView.removeAllViews(); //<- here remove all the views, it will make an Activity having no View
        //mScannerView.stopCamera(); //<- then stop the camera

        final String barcode = result.getText(); //barcode result

        Intent intent = new Intent();
        intent.putExtra("BARCODE", barcode);
        setResult(RESULT_OK, intent);
        finish();
    }

    @Override
    public void onBackPressed() {
        final String barcode = "";
        Intent intent = new Intent();
        intent.putExtra("BARCODE", barcode);
        setResult(RESULT_OK, intent);
        finish();
    }
}
