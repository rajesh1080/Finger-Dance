package com.vitalinfo.dance.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.InputDevice;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.vitalinfo.dance.R;
import com.vitalinfo.dance.utility.Constants;
import com.vitalinfo.dance.utility.Utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

  private List<Integer> tileIDList;
  private TextView selectedTile;
  private boolean isGameOverPopupShowing = false;
  private static int[] colorDrawableResArr = new int[] {R.drawable.green_button, R.drawable.indigo_blue_button, R.drawable.orange_button, R.drawable.red_button, R.drawable.grey_button, R.drawable.brown_button};
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    Intent intent = getIntent();
    if(intent == null) {
      finish();
      return;
    }
    setContentView(R.layout.activity_main);

    Bundle bundle = intent.getExtras();
    if(bundle == null) {
      finish();
      return;
    }
    Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
    setSupportActionBar(toolbar);
    LinearLayout ll = (LinearLayout) findViewById(R.id.tileParent);
    int n = bundle.getInt(Constants.BOARD_SIZE_KEY, 3);
    int cellWidth = (int) (Utils.getScreenWidth(getApplicationContext()) / n);
    tileIDList = new ArrayList<Integer>();
    LinearLayout childLL;
    int id;
    for(int i = 0; i < n; i++) {
      childLL = new LinearLayout(getApplicationContext());
      childLL.setOrientation(LinearLayout.HORIZONTAL);
      childLL.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
      childLL.setPadding(4, 0, 4, 0);
      for(int j = 0; j < n; j++) {
        id = i * 10 + j;
        childLL.addView(getTileView(id, cellWidth));
        tileIDList.add(id);
      }
      ll.addView(childLL);
    }

    selectRandomTile();

  }

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    // Inflate the menu; this adds items to the action bar if it is present.
    getMenuInflater().inflate(R.menu.menu_main, menu);
    return true;
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    // Handle action bar item clicks here. The action bar will
    // automatically handle clicks on the Home/Up button, so long
    // as you specify a parent activity in AndroidManifest.xml.
    int id = item.getItemId();

    //noinspection SimplifiableIfStatement
    if (id == R.id.action_settings) {
      return true;
    }

    return super.onOptionsItemSelected(item);
  }

  private void selectRandomTile() {
    if(tileIDList == null) return;
    int len = tileIDList.size();
    if(len == 0) return;
    Random r = new Random();
    int i = r.nextInt(len);
    int id = tileIDList.get(i);
    tileIDList.remove(i);
    selectedTile = (TextView) findViewById(id);
    selectedTile.setSelected(true);
  }

  private TextView getTileView(final int id, int cellWidth) {
    TextView tile = new TextView(getApplicationContext());
    LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(cellWidth, cellWidth, 1);
    params.setMargins(4, 4, 4, 4);
    tile.setLayoutParams(params);
    int res = colorDrawableResArr[(new Random()).nextInt(colorDrawableResArr.length)];
    tile.setBackgroundResource(res);
    //tile.setBackgroundColor(Color.BLACK);
    tile.setId(id);
    //tile.setPadding(20, 20, 20, 20);


    return tile;
  }

  @Override
  public boolean onTouchEvent(MotionEvent event){
    //this.gestureDetectorCompat.onTouchEvent(event);
    // Be sure to call the superclass implementation

    Log.e(Constants.TAG, "hi range = " + InputDevice. getDevice(event.getDeviceId()).getMotionRanges().get(0).getMax());
    int action = event.getAction();
    switch(action & MotionEvent.ACTION_MASK) {
      case MotionEvent.ACTION_DOWN:
        int count = event.getPointerCount(); // Number of 'fingers' in this time
        //Log.e(Constants.TAG, " ACTION_DOWN Down " + count + " x= " + event.getX() + " y = " + event.getY());
        if(isSelectedTileTapped(event.getX(), event.getY()) ) {
          selectRandomTile();
        } else {
          //Log.e(Constants.TAG, " select wrong");
          gameOverPopUp();
        }

        break;
      case MotionEvent.ACTION_POINTER_DOWN:
        // multitouch!! - touch down
        count = event.getPointerCount(); // Number of 'fingers' in this time
        count = count - 1;
        //Log.e(Constants.TAG, " pointer Down " + count + " x= " + event.getX(event.getPointerId(count)) + " y = " + event.getY(event.getPointerId(count)));
        if(isSelectedTileTapped(event.getX(event.getPointerId(count)), event.getY(event.getPointerId(count)))) {
          selectRandomTile();
        } else {
          //Log.e(Constants.TAG, " select wrong");
          gameOverPopUp();
        }

        break;
      case MotionEvent.ACTION_UP :
        //Log.e(Constants.TAG, " select ACTION_UP");
        gameOverPopUp();
        break;
      case MotionEvent.ACTION_POINTER_UP :
        //Log.e(Constants.TAG, " select ACTION_POINTER_UP");
        gameOverPopUp();
        break;
    }

    return super.onTouchEvent(event);
  }

  private boolean isSelectedTileTapped(float x, float y) {
    if(selectedTile == null) return false;
    //Log.e(Constants.TAG, " left = " + getRelativeLeft(selectedTile) + " Right = " + getRelativeRight(selectedTile));
    //Log.e(Constants.TAG, " width = " + selectedTile.getWidth() + " x = " + x);

    if(x > getRelativeLeft(selectedTile) && x < getRelativeRight(selectedTile) && y > getRelativeTop(selectedTile) && y < getRelativeBottom(selectedTile)) {
      return true;
    }

    return false;
  }

  private int getRelativeLeft(View myView) {
    if (myView.getParent() == myView.getRootView())
      return myView.getLeft();
    else
      return myView.getLeft() + getRelativeLeft((View) myView.getParent());
  }
  private int getRelativeRight(View myView) {
    if (myView.getParent() == myView.getRootView())
      return myView.getRight();
    else
      return myView.getRight() + getRelativeLeft((View) myView.getParent());
  }
  private int getRelativeTop(View myView) {
    if (myView.getParent() == myView.getRootView())
      return myView.getTop();
    else
      return myView.getTop() + getRelativeTop((View) myView.getParent());
  }

  private int getRelativeBottom(View myView) {
    if (myView.getParent() == myView.getRootView())
      return myView.getBottom();
    else
      return myView.getBottom() + getRelativeTop((View) myView.getParent());
  }

  private void gameOverPopUp() {
    if(isGameOverPopupShowing == true) return;
    final AlertDialog.Builder builder = new AlertDialog.Builder(this);
    builder.setTitle(R.string.gameOverTitle);
    builder.setMessage(R.string.restartGameText);
    builder.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
      @Override
      public void onClick(DialogInterface dialog, int which) {
        dialog.cancel();
        LinearLayout ll = (LinearLayout) findViewById(R.id.tileParent);
        tileIDList = new ArrayList<Integer>();
        LinearLayout childLL;
        View view;
        for (int i = 0; i < ll.getChildCount(); i++) {
          childLL = (LinearLayout) ll.getChildAt(i);
          for (int j = 0; j < childLL.getChildCount(); j++) {
            view = childLL.getChildAt(j);
            if (view.isSelected()) {
              view.setSelected(false);
            }
            tileIDList.add(view.getId());
          }
        }
        selectRandomTile();
        isGameOverPopupShowing = false;
      }
    });
    builder.setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
      @Override
      public void onClick(DialogInterface dialog, int which) {
        dialog.cancel();
        isGameOverPopupShowing = false;
        finish();
      }
    });
    builder.create().show();
    isGameOverPopupShowing = true;
  }

}
