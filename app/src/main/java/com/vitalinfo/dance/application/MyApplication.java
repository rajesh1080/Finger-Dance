package com.vitalinfo.dance.application;

import android.app.Application;
import android.util.Log;

import com.crashlytics.android.Crashlytics;
import com.google.android.gms.analytics.GoogleAnalytics;
import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;
import com.vitalinfo.dance.utility.Constants;

import io.fabric.sdk.android.Fabric;

/**
 * Created by rajesh on 2/1/16.
 */
public class MyApplication extends Application {

  private Tracker mTracker;
  @Override
  public void onCreate() {
    super.onCreate();

    // Example: single kit
    // TwitterAuthConfig authConfig =  new TwitterAuthConfig("consumerKey", "consumerSecret");
    // Fabric.with(this, new TwitterCore(authConfig));

    // Example: multiple kits
    // Fabric.with(this, new TwitterCore(authConfig), new TweetUi());
    Fabric.with(this, new Crashlytics());

  }



  /**
   * Gets the default {@link Tracker} for this {@link Application}.
   * @return tracker
   */
  synchronized public Tracker getDefaultTracker() {
    if (mTracker == null) {
      GoogleAnalytics analytics = GoogleAnalytics.getInstance(this);
      // To enable debug logging use: adb shell setprop log.tag.GAv4 DEBUG
      //mTracker = analytics.newTracker(R.xml.global_tracker);
      mTracker = analytics.newTracker("UA-72010389-4 ");
    }
    return mTracker;
  }

  public void sendScreenNameToGoogleAnalytics(String screenName) {
    //Log.e(Constants.TAG, "sendScreenNameToGoogleAnalytics ");
    getDefaultTracker();
    if(mTracker != null) {
      mTracker.setScreenName(screenName);
      mTracker.send(new HitBuilders.ScreenViewBuilder().build());
      Log.e(Constants.TAG, "sendScreenNameToGoogleAnalytics " + screenName);
    }
  }

  public void sendActionToGoogleAnalytics(String category, String action) {
    getDefaultTracker();
    if(mTracker != null) {
      mTracker.send(new HitBuilders.EventBuilder()
        .setCategory(category)
        .setAction(action)
        .build());
      Log.e(Constants.TAG, "sendActionToGoogleAnalytics " + action);
    }
  }

}
