package com.vitalinfo.dance.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;
import android.view.WindowManager;

import com.vitalinfo.dance.views.MTView;

/**
 * Created by rajesh on 7/4/16.
 */
public class TestActivity extends AppCompatActivity {

  @Override
  public void onCreate(Bundle savedInstanceState) {

    requestWindowFeature(Window.FEATURE_NO_TITLE);
    getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
    super.onCreate(savedInstanceState);
    setContentView(new MTView(this));
  }
}
