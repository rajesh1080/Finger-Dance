package com.vitalinfo.dance.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
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
    setContentView(R.layout.instruction_layout);
    //Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
    //setSupportActionBar(toolbar);

    if (Build.VERSION.SDK_INT >= 7) {
      PackageManager pm = getPackageManager();
      boolean hasMultitouch = pm.hasSystemFeature(PackageManager.FEATURE_TOUCHSCREEN_MULTITOUCH);
      if (hasMultitouch) {
        StringBuilder sb = new StringBuilder();
        hasMultitouch = pm.hasSystemFeature(PackageManager.FEATURE_TOUCHSCREEN_MULTITOUCH_JAZZHAND);
        if (hasMultitouch) {
          // set multitouch event listeners
         // Log.e(Constants.TAG, "has multiple touch 21");
          sb.append(getString(R.string.moreThanFiveTouchAllowed));
          sb.append("\n");
        } else {
          //hasMultitouch = pm.hasSystemFeature(PackageManager.FEATURE_TOUCHSCREEN_MULTITOUCH_DISTINCT);
          sb.append(getString(R.string.moreThanThreeTouchAllowed));
          sb.append("\n");
        }
        sb.append(getString(R.string.instructionText));
        TextView instructionText = (TextView) findViewById(R.id.instruction);
        instructionText.setText(sb.toString());
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


