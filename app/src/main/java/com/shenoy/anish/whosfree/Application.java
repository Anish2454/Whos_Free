package com.shenoy.anish.whosfree;

import com.facebook.appevents.AppEventsLogger;

/**
 * Created by owner on 7/29/17.
 */

public class Application extends android.app.Application {

    @Override
    public void onCreate() {
        super.onCreate();
        AppEventsLogger.activateApp(this);
    }
}
