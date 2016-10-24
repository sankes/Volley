package com.shankes.volley;

import java.util.HashMap;
import java.util.Map;

import com.android.volley.VolleyError;
import com.android.volley.toolbox.NetworkImageView;
import com.shankes.phone.Phone;
import com.shankes.phone.Phone.PhoneOrTel;
import com.shankes.util.volley.customview.CircleNetworkImageView;
import com.shankes.util.volley.listener.VolleyListenerInterface;
import com.shankes.util.volley.util.ImageLoaderUtil;
import com.shankes.util.volley.util.VolleyRequestUtil;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		TextView volleyPost = (TextView) findViewById(R.id.volley_post);
		String phoneValue = "18366136009";
		phoneValue = "17186456771";

		// ��ʾʡ��,��171���Ϸ�
		// Phone.getPhoneInfo(this, PhoneOrTel.PHONE, phoneValue, volleyGet);
		// ��ʾʡ
		Phone.getPhoneInfo(this, PhoneOrTel.TEL, phoneValue, volleyPost);
		testVolleyGet();
		// testVolleyPost();
		testImageRequest();
		testImageLoader();
		testCircleNetWorkImageView();
		testNetWorkImageView();
	}

	/**
	 * 1 .��GET��ʽ����������Դ
	 */
	private void testVolleyGet() {
		// �����ʴ�api: http://www.jisuapi.com/api/iqa/
		String httpUrl = "http://api.jisuapi.com/iqa/query?appkey=c36f5e0c04a75df0&question=Ц��";
		VolleyRequestUtil.requestGet(this, httpUrl, "get", null, new VolleyListenerInterface(this,
				VolleyListenerInterface.mSuccessListener, VolleyListenerInterface.mErrorListener) {
			// Volley����ɹ�ʱ���õĺ���
			@Override
			public void onMySuccess(String result) {
				Toast.makeText(MainActivity.this, "1.��GET��ʽ����������Դ�����" + result, Toast.LENGTH_LONG).show();
				TextView volleyGet = (TextView) findViewById(R.id.volley_get);
				volleyGet.setText(result);
			}

			// Volley����ʧ��ʱ���õĺ���
			@Override
			public void onMyError(VolleyError error) {
				Toast.makeText(MainActivity.this, "1.��GET��ʽ����������Դ�����" + error, Toast.LENGTH_LONG).show();
				TextView volleyGet = (TextView) findViewById(R.id.volley_get);
				volleyGet.setText(error.toString());
			}
		});
	}

	/**
	 * 2.��POST��ʽ����������Դ
	 */
	private void testVolleyPost() {
		// �ٶ�timezone
		// String httpUrl =
		// "http://api.map.baidu.com/timezone/v1?ak=7sqA81OWkmxg20kWEGb5idInSj71pCG1&timestamp=1473595403&location=40.055,116.308";
		// Map<String, String> params = null;
		// Map<String, String> headers = null;

		// ���ؽ��������Ԥ��,Ӧ���ǽӿڲ�֧��post
		// String httpUrl = "http://api.map.baidu.com/timezone/v1";
		// Map<String, String> params = new HashMap<String, String>();
		// params.put("ak", "7sqA81OWkmxg20kWEGb5idInSj71pCG1");
		// params.put("timestamp", "1473595403");
		// params.put("location", "40.055,116.308");
		// Map<String, String> headers = null;

		// String httpUrl =
		// "http://apis.baidu.com/apistore/mobilenumber/mobilenumber?phone=18366136009";
		String httpUrl = "http://apis.baidu.com/apistore/mobilephoneservice/mobilephone?tel=18366136009";
		Map<String, String> params = null;
		Map<String, String> headers = new HashMap<String, String>();
		headers.put("apikey", "9940f7550db411f7dea7acf00a10bb93");

		// �ֻ���������ز�ѯapi:http://apistore.baidu.com/apiworks/servicedetail/117.html
		// String httpUrl =
		// "http://apis.baidu.com/apistore/mobilenumber/mobilenumber";
		// Map<String, String> params = new HashMap<String, String>();
		// params.put("phone", "18366136009");
		// Map<String, String> headers = new HashMap<String, String>();
		// headers.put("apikey", "9940f7550db411f7dea7acf00a10bb93");

		VolleyRequestUtil.requestPost(this, httpUrl, "post", params, headers, new VolleyListenerInterface(this,
				VolleyListenerInterface.mSuccessListener, VolleyListenerInterface.mErrorListener) {
			// Volley����ɹ�ʱ���õĺ���
			@Override
			public void onMySuccess(String result) {
				Toast.makeText(MainActivity.this, "2.��POST��ʽ����������Դ" + result, Toast.LENGTH_LONG).show();
				TextView volleyPost = (TextView) findViewById(R.id.volley_post);
				volleyPost.setText(result);
			}

			// Volley����ʧ��ʱ���õĺ���
			@Override
			public void onMyError(VolleyError error) {
				Toast.makeText(MainActivity.this, "2.��POST��ʽ����������Դ" + error, Toast.LENGTH_LONG).show();
				TextView volleyPost = (TextView) findViewById(R.id.volley_post);
				volleyPost.setText(error.toString());
			}
		});

	}

	/**
	 * 3.ͨ��ImageRequest����ʾ����ͼƬ
	 */
	private void testImageRequest() {
		// �����ֱ�Ϊ������ͼƬ�ĵ�ַ��ͼƬ������ImageView
		ImageView imgView = (ImageView) findViewById(R.id.imageRequestIV);
		String ImageRequestUrl = "https://www.baidu.com/img/bd_logo1.png";
		ImageLoaderUtil.setImageRequest(this, ImageRequestUrl, imgView);
	}

	/**
	 * 4.ͨ��ImageLoader����ʾ����ͼƬ
	 */
	private void testImageLoader() {
		// �����ֱ�Ϊ������ͼƬ�ĵ�ַ��ͼƬ������ImageView��Ĭ����ʾ��ͼƬResourceID������ʧ��ʱ��ʾ��ͼƬ��ResourceID
		ImageView imgView = (ImageView) findViewById(R.id.imageLoaderIV);
		String imageLoaderUrl = "http://img.mukewang.com/570367ca00019ee206000338-240-135.jpg";
		ImageLoaderUtil.setImageLoader(this, imageLoaderUrl, imgView, R.drawable.ic_launcher, R.drawable.ic_launcher);
	}

	/**
	 * 5.ͨ��Volley��NetWorkImageView����ʾ����ͼƬ
	 */
	private void testCircleNetWorkImageView() {
		// �����ֱ�Ϊ������ͼƬ�ĵ�ַ��ͼƬ������NetworkImageView��Ĭ����ʾ��ͼƬResourceID������ʧ��ʱ��ʾ��ͼƬ��ResourceID
		CircleNetworkImageView netWorkImageView = (CircleNetworkImageView) findViewById(R.id.CircleNetworkImageView);
		String netWorkImageViewUrl = "https://ss0.bdstatic.com/k4oZeXSm1A5BphGlnYG/skin/207.jpg?2";
		ImageLoaderUtil.setNetWorkImageView(this, netWorkImageViewUrl, netWorkImageView, R.drawable.ic_launcher,
				R.drawable.ic_launcher);
	}

	/**
	 * 6.ͨ��Volley��NetWorkImageView����ʾ����ͼƬ
	 */

	private void testNetWorkImageView() { //
		// �����ֱ�Ϊ������ͼƬ�ĵ�ַ��ͼƬ������NetworkImageView
		// ��Ĭ����ʾ��ͼƬResourceID������ʧ��ʱ��ʾ��ͼƬ��ResourceID
		NetworkImageView netWorkImageView = (NetworkImageView) findViewById(R.id.netWorkImageView);
		String netWorkImageViewUrl = "https://ss0.bdstatic.com/k4oZeXSm1A5BphGlnYG/skin/207.jpg?2";
		ImageLoaderUtil.setNetWorkImageView(this, netWorkImageViewUrl, netWorkImageView, R.drawable.ic_launcher,
				R.drawable.ic_launcher);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
