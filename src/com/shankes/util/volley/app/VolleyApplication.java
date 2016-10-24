package com.shankes.util.volley.app;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

import android.app.Application;

/**
 * 不要忘记在AndroidManifest.xml文件中修改Application的name和相应的网络请求权限：
 * 
 * <uses-permission android:name="android.permission.INTERNET" />
 * 
 * <application android:name=".VolleyApplication"
 * 
 * android:allowBackup="true"
 * 
 * android:icon="@mipmap/ic_launcher"
 * 
 * android:label="@string/app_name"
 * 
 * android:supportsRtl="true"
 * 
 * android:theme="@style/AppTheme" > </application>
 * 
 * @author shankesmile
 * 
 */
public class VolleyApplication extends Application {

	public static RequestQueue volleyQueue;

	@Override
	public void onCreate() {
		super.onCreate();

		// 建立Volley的Http请求队列
		volleyQueue = Volley.newRequestQueue(getApplicationContext());
	}

	// 开放Volley的HTTP请求队列接口
	public static RequestQueue getRequestQueue() {
		return volleyQueue;
	}
}
