package com.shankes.phone;

import java.util.HashMap;
import java.util.Map;

import android.content.Context;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.shankes.util.volley.listener.VolleyListenerInterface;
import com.shankes.util.volley.util.VolleyRequestUtil;

/**
 * 手机号码归属地查询
 * 
 * @author shankesmile
 */
public class Phone {
	// 只显示省份
	private static String httpUrl = "http://apis.baidu.com/apistore/mobilephoneservice/mobilephone?tel=18366136009";
	private static String phone;
	private static String tel;
	// 显示省市
	// "http://apis.baidu.com/apistore/mobilenumber/mobilenumber?phone=18366136009";
	private static Map<String, String> params = null;
	private static Map<String, String> headers = null;

	private static StringBuffer HTTP_ARG;

	private static void init(PhoneOrTel type, String phoneValue) {
		phone = "phone=PHONE";
		tel = "tel=TEL";
		params = null;
		headers = new HashMap<String, String>();
		headers.put("apikey", "9940f7550db411f7dea7acf00a10bb93");
		HTTP_ARG = new StringBuffer();

		switch (type) {
		case PHONE:
			httpUrl = "http://apis.baidu.com/apistore/mobilenumber/mobilenumber";
			phone = phone.replace("PHONE", phoneValue);
			HTTP_ARG.append("?");
			HTTP_ARG.append(phone);
			break;
		case TEL:
			httpUrl = "http://apis.baidu.com/apistore/mobilephoneservice/mobilephone";
			tel = tel.replace("TEL", phoneValue);
			HTTP_ARG.append("?");
			HTTP_ARG.append(tel);
			break;

		default:
			break;
		}
		httpUrl = httpUrl + HTTP_ARG.toString();
	}

	public static void getPhoneInfo(final Context context, PhoneOrTel type, String phoneValue, final TextView textView) {
		init(type, phoneValue);
		VolleyRequestUtil.requestPost(context, httpUrl, "post", params, headers, new VolleyListenerInterface(context,
				VolleyListenerInterface.mSuccessListener, VolleyListenerInterface.mErrorListener) {
			// Volley请求成功时调用的函数
			@Override
			public void onMySuccess(String result) {
				textView.setText(result);
				Toast.makeText(context, result, Toast.LENGTH_LONG).show();
			}

			// Volley请求失败时调用的函数
			@Override
			public void onMyError(VolleyError error) {
				textView.setText(error.toString());
			}
		});
	}

	public enum PhoneOrTel {
		PHONE, // 显示省市,报171不合法
		TEL// 显示省
	}
}
