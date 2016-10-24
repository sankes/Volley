package com.shankes.util.volley.util;

import java.util.Map;

import android.content.Context;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.toolbox.StringRequest;
import com.shankes.util.volley.app.VolleyApplication;
import com.shankes.util.volley.encode.MyStringRequest;
import com.shankes.util.volley.listener.VolleyListenerInterface;

/**
 * ʹ��ʱע��:
 * 
 * 1.���volley��jar��volley-lib-1.0.19.jar
 * 
 * 2.AndroidManifest.xml���������Ȩ��
 * 
 * 3.AndroidManifest.xml��<application>�������
 * android:name="com.shankes.util.volley.app.VolleyApplication"
 * 
 * 
 * https://mvnrepository.com/artifact/com.mcxiaoke.volley/library compile group:
 * 
 * 'com.mcxiaoke.volley', name: 'library', version: '1.0.19'
 * 
 * @author shankesmile
 */
public class VolleyRequestUtil {

	public static StringRequest stringRequest;

	public static Context context;

	/**
	 * ��ȡGET��������
	 * 
	 * @param context
	 *            ��ǰ������
	 * @param url
	 *            �����url��ַ
	 * @param tag
	 *            ��ǰ����ı�ǩ
	 * @param headers
	 *            Header
	 * @param volleyListenerInterface
	 *            VolleyListenerInterface�ӿ�
	 */
	public static void requestGet(Context context, String url, String tag, final Map<String, String> headers,
			VolleyListenerInterface volleyListenerInterface) {
		// �����������е�tag�������
		VolleyApplication.getRequestQueue().cancelAll(tag);
		// ������ǰ�����󣬻�ȡ�ַ�������,����ʹ��MyStringRequest�������ֵ��������
		stringRequest = new MyStringRequest(Request.Method.GET, url, volleyListenerInterface.successListener(),
				volleyListenerInterface.errorListener()) {
			@Override
			public Map<String, String> getHeaders() throws AuthFailureError {
				if (headers != null) {
					return headers;
				} else {
					return super.getHeaders();
				}
			}
		};
		// Ϊ��ǰ������ӱ��
		stringRequest.setTag(tag);
		// ����ǰ������ӵ����������
		VolleyApplication.getRequestQueue().add(stringRequest);
		// ������ǰ�������
		// VolleyApplication.getRequestQueue().start();
	}

	/**
	 * ��ȡPOST��������
	 * 
	 * @param context
	 *            ��ǰ������
	 * @param url
	 *            �����url��ַ
	 * @param tag
	 *            ��ǰ����ı�ǩ
	 * @param params
	 *            POST��������
	 * @param volleyListenerInterface
	 *            VolleyListenerInterface�ӿ�
	 */
	public static void requestPost(Context context, String url, String tag, final Map<String, String> params,
			final Map<String, String> headers, VolleyListenerInterface volleyListenerInterface) {
		// �����������е�tag�������
		VolleyApplication.getRequestQueue().cancelAll(tag);
		// ������ǰ��POST���󣬲�����������д��Map��,����ʹ��MyStringRequest�������ֵ��������
		stringRequest = new MyStringRequest(Request.Method.POST, url, volleyListenerInterface.successListener(),
				volleyListenerInterface.errorListener()) {
			@Override
			protected Map<String, String> getParams() throws AuthFailureError {
				return params;
			}

			@Override
			public Map<String, String> getHeaders() throws AuthFailureError {
				if (headers != null) {
					return headers;
				} else {
					return super.getHeaders();
				}
			}
		};
		// Ϊ��ǰ������ӱ��
		stringRequest.setTag(tag);
		// ����ǰ������ӵ����������
		VolleyApplication.getRequestQueue().add(stringRequest);
		// ������ǰ�������
		// VolleyApplication.getRequestQueue().start();
	}
}
