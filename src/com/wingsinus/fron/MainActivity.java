package com.wingsinus.fron;

import org.cocos2d.opengl.CCGLSurfaceView;

import com.wingsinus.fron.layer.SceneManager;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

public class MainActivity extends Activity {
	private CCGLSurfaceView mGLSurfaceView;
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
     // set the window status, no tile, full screen and don't sleep
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON,
                WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        mGLSurfaceView = new CCGLSurfaceView(this);
        
        setContentView(mGLSurfaceView);
        FronBitmapMaker.getInstance().setContext(this);
//        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
    }
    
    @Override
    public void onStart() {
        super.onStart();
        SceneManager.getInstance().onStart(mGLSurfaceView);
    }

    @Override
    public void onPause() {
        super.onPause();

        SceneManager.getInstance().onPause();
    }

    @Override
    public void onResume() {
        super.onResume();
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        SceneManager.getInstance().onResume();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        SceneManager.getInstance().onDestroy();
    }
}