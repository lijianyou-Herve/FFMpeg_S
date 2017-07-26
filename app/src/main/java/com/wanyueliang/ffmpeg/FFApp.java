package com.wanyueliang.ffmpeg;

import android.app.Application;

public class FFApp extends Application {

    static {
        System.loadLibrary("ffmpeg");
        System.loadLibrary("ffmpeginvoke");
    }

}