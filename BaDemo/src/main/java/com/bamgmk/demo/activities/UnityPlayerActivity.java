package com.bamgmk.demo.activities;

import com.bamgmk.demo.activities.MainActivity;
import com.unity3d.player.*;
import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.PixelFormat;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.Window;

import java.util.ArrayList;

public class UnityPlayerActivity extends Activity
{
	protected UnityPlayer mUnityPlayer; // don't change the name of this variable; referenced from native code
	public String charData;
	public String vicData;
    private ArrayList<Integer> enemyTypes;


	public void test (){
		Intent intent = new Intent(this, MainActivity.class);
		startActivity(intent);
	}

	public void victory(){
		Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra("enemyTypes",enemyTypes);
		intent.putExtra("vicData",vicData);
		startActivity(intent);
	}

	// Setup activity layout
	@Override protected void onCreate (Bundle savedInstanceState)
	{
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		charData = getIntent().getExtras().getString("charData");
		vicData = getIntent().getExtras().getString("vicData");
        enemyTypes = getIntent().getExtras().getIntegerArrayList("enemyTypes");
		Log.d("test","Chardata: "+charData);
        Log.d("test","Vicdata: "+vicData);

		getWindow().setFormat(PixelFormat.RGBX_8888); // <--- This makes xperia play happy

		mUnityPlayer = new UnityPlayer(this);
		setContentView(mUnityPlayer);
		mUnityPlayer.requestFocus();
	}

	// Quit Unity
	@Override protected void onDestroy ()
	{
		mUnityPlayer.quit();
		super.onDestroy();
	}

	// Pause Unity
	@Override protected void onPause()
	{
		super.onPause();
		mUnityPlayer.quit();
		super.onDestroy();
	}

	// Resume Unity
	@Override protected void onResume()
	{
		super.onResume();
		mUnityPlayer.resume();
	}

	// This ensures the layout will be correct.
	@Override public void onConfigurationChanged(Configuration newConfig)
	{
		super.onConfigurationChanged(newConfig);
		mUnityPlayer.configurationChanged(newConfig);
	}

	// Notify Unity of the focus change.
	@Override public void onWindowFocusChanged(boolean hasFocus)
	{
		super.onWindowFocusChanged(hasFocus);
		mUnityPlayer.windowFocusChanged(hasFocus);
	}

	// For some reason the multiple keyevent type is not supported by the ndk.
	// Force event injection by overriding dispatchKeyEvent().
	@Override public boolean dispatchKeyEvent(KeyEvent event)
	{
		if (event.getAction() == KeyEvent.ACTION_MULTIPLE)
			return mUnityPlayer.injectEvent(event);
		return super.dispatchKeyEvent(event);
	}

	@Override
	public void onBackPressed() {
	}

	// Pass any events not handled by (unfocused) views straight to UnityPlayer
	@Override public boolean onKeyUp(int keyCode, KeyEvent event)     { return mUnityPlayer.injectEvent(event); }
	@Override public boolean onKeyDown(int keyCode, KeyEvent event)   { return mUnityPlayer.injectEvent(event); }
	@Override public boolean onTouchEvent(MotionEvent event)          { return mUnityPlayer.injectEvent(event); }
	/*API12*/ public boolean onGenericMotionEvent(MotionEvent event)  { return mUnityPlayer.injectEvent(event); }
}
