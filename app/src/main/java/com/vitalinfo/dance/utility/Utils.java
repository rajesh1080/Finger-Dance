package com.vitalinfo.dance.utility;

import android.content.Context;
import android.graphics.Point;
import android.view.Display;
import android.view.WindowManager;

/**
 * Created by rajesh on 7/4/16.
 */
public class Utils {

  public static int getScreenHeight(Context ctx) {
    WindowManager wm = (WindowManager)ctx.getSystemService(Context.WINDOW_SERVICE);
    Display display = wm.getDefaultDisplay();
    final Point point = new Point();
    try {
      display.getSize(point);
    } catch (NoSuchMethodError ignore) { // Older device
      point.x = display.getWidth();
      point.y = display.getHeight();
    }
    return point.y;
  }

  public static int getScreenWidth(Context ctx) {
    WindowManager wm = (WindowManager)ctx.getSystemService(Context.WINDOW_SERVICE);
    Display display = wm.getDefaultDisplay();
    final Point point = new Point();
    try {
      display.getSize(point);
    } catch (NoSuchMethodError ignore) { // Older device
      point.x = display.getWidth();
      point.y = display.getHeight();
    }
    return point.x;
  }

}
