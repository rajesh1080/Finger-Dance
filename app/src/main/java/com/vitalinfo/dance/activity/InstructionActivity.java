package com.vitalinfo.dance.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import com.vitalinfo.dance.R;
import com.vitalinfo.dance.utility.Constants;

/**
 * Created by rajesh on 7/4/16.
 */
public class InstructionActivity extends AppCompatActivity {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    //Fabric.with(this, new Crashlytics());
    setContentView(R.layout.instruction_layout);
    //Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
    //setSupportActionBar(toolbar);

    if (Build.VERSION.SDK_INT >= 7) {
      PackageManager pm = getPackageManager();
      boolean hasMultitouch = pm.hasSystemFeature(PackageManager.FEATURE_TOUCHSCREEN_MULTITOUCH);
      if (hasMultitouch) {
        StringBuilder sb = new StringBuilder();
        sb.append("<b>");
        sb.append(getString(R.string.instruction));
        sb.append("</b><ul>");
        hasMultitouch = pm.hasSystemFeature(PackageManager.FEATURE_TOUCHSCREEN_MULTITOUCH_JAZZHAND);
        if (hasMultitouch) {
          // set multitouch event listeners
         // Log.e(Constants.TAG, "has multiple touch 21");
          sb.append("<li>");
          sb.append(getString(R.string.moreThanFiveTouchAllowed));
          sb.append("</li>");
        } else {
          //hasMultitouch = pm.hasSystemFeature(PackageManager.FEATURE_TOUCHSCREEN_MULTITOUCH_DISTINCT);
          sb.append("<li>");
          sb.append(getString(R.string.moreThanThreeTouchAllowed));
          sb.append("</li>");
        }
        sb.append("<li>");
        sb.append(getString(R.string.instructionTextLine1));
        sb.append("</li>");
        sb.append("<li>");
        sb.append(getString(R.string.instructionTextLine2));
        sb.append("</li>");
        sb.append("<li>");
        sb.append(getString(R.string.instructionTextLine3));
        sb.append("</li>");
        sb.append("<li>");
        sb.append(getString(R.string.instructionTextLine4));
        sb.append("</li>");
        sb.append("<ul><li>");
        sb.append(getString(R.string.instructionTextLine5));
        sb.append("</li>");
        sb.append("<li>");
        sb.append(getString(R.string.instructionTextLine6));
        sb.append("</li></ul>");
        sb.append("</ul>");
        WebView tv = (WebView) findViewById(R.id.instruction);

        tv.getSettings().setJavaScriptEnabled(true);
        //tv.getSettings().setPl.setPluginsEnabled(true);
        tv.getSettings().setSupportZoom(false);
        tv.getSettings().setAllowFileAccess(true);
        WebSettings webSettings = tv.getSettings();
        tv.getSettings().setAllowFileAccess(true);
        webSettings.setTextSize(WebSettings.TextSize.NORMAL);

        tv.loadDataWithBaseURL(null, sb.toString(), "text/html", "UTF-8", null);
      } else {
        notSupportedPopup();
      }
    } else {
      notSupportedPopup();
    }

    Button startGame = (Button) findViewById(R.id.startGame);
    startGame.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        Spinner boardSize = (Spinner) findViewById(R.id.boardSize);
        int selectedItemPosition = boardSize.getSelectedItemPosition();
        if (selectedItemPosition == 0) {
          AlertDialog.Builder builder = new AlertDialog.Builder(InstructionActivity.this);
          builder.setMessage(R.string.chooseBoardSize);
          builder.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
              dialog.cancel();
            }
          });
          builder.create().show();
        } else {
          Intent intent = new Intent(getApplicationContext(), MainActivity.class);
          Bundle bundle = new Bundle();
          bundle.putInt(Constants.BOARD_SIZE_KEY, selectedItemPosition + 1);
          intent.putExtras(bundle);
          startActivity(intent);
          finish();
        }
      }
    });

    TextView testPointers = (TextView) findViewById(R.id.testPointers);
    testPointers.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        Intent intent = new Intent(getApplicationContext(), TestActivity.class);
        startActivity(intent);
      }
    });

    /* trying to get exact no of touch pointer allowed simultaneously
    ViewConfiguration vc = ViewConfiguration.get(getApplicationContext());
    Log.e(Constants.TAG, "Hi getScaledDoubleTapSlop = " + vc.getScaledDoubleTapSlop());
    Log.e(Constants.TAG, "Hi getScaledEdgeSlop = " + vc.getScaledEdgeSlop());
    Log.e(Constants.TAG, "Hi getScaledTouchSlop = " + vc.getScaledTouchSlop());
    */
  }

  private void notSupportedPopup() {
    AlertDialog.Builder builder = new AlertDialog.Builder(InstructionActivity.this);
    builder.setTitle(R.string.multiTouchNotSupportedTitle);
    builder.setMessage(R.string.multiTouchNotSupportedDesc);
    builder.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
      @Override
      public void onClick(DialogInterface dialog, int which) {
        dialog.cancel();
        finish();
      }
    });
    builder.setCancelable(false);
    builder.create().show();
  }
}


