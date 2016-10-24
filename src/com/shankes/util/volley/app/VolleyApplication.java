package com.shankes.util.volley.app;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

import android.app.Application;

/**
 * ��Ҫ������AndroidManifest.xml�ļ����޸�Application��name����Ӧ����������Ȩ�ޣ�
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

		// ����Volley��Http�������
		volleyQueue = Volley.newRequestQueue(getApplicationContext());
	}

	// ����Volley��HTTP������нӿ�
	public static RequestQueue getRequestQueue() {
		return volleyQueue;
	}
}
