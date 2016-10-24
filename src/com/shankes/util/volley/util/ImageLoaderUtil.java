package com.shankes.util.volley.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.widget.ImageView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.NetworkImageView;
import com.shankes.util.volley.app.VolleyApplication;
import com.shankes.util.volley.cache.MyBitmapCache;
import com.shankes.volley.R;

public class ImageLoaderUtil {

	/*
	 * ͨ��ImageRequest����ʾ����ͼƬ
	 */
	public static void setImageRequest(Context context, String url, final ImageView imageView) {
		ImageRequest imageRequest = new ImageRequest(url,
				new Response.Listener<Bitmap>() {
					@Override
					public void onResponse(Bitmap bitmap) {
						imageView.setImageBitmap(bitmap);
					}
				}, 0, 0, Bitmap.Config.RGB_565, new Response.ErrorListener() {
					@Override
					public void onErrorResponse(VolleyError volleyError) {
						imageView.setBackgroundResource(R.drawable.ic_launcher);
					}
				});
		VolleyApplication.getRequestQueue().add(imageRequest);
	}

	/*
	 * ͨ��ImageLoader����ʾ����ͼƬ
	 */
	public static void setImageLoader(Context context, String url, ImageView imageView,
			int defaultImageResId, int errorImageResId) {
		ImageLoader loader = new ImageLoader(
VolleyApplication.getRequestQueue(),
				MyBitmapCache.getMyImageCache(context));
		ImageLoader.ImageListener imageListener = ImageLoader.getImageListener(
				imageView, defaultImageResId, errorImageResId);
		loader.get(url, imageListener);
	}

	/*
	 * ͨ��Volley��NetWorkImageView����ʾ����ͼƬ
	 */
	public static void setNetWorkImageView(Context context, String url,
			NetworkImageView netWorkImageView, int defaultImageResId,
			int errorImageResId) {
		ImageLoader loader = new ImageLoader(
				VolleyApplication.getRequestQueue(), MyBitmapCache.getMyImageCache(context));

		netWorkImageView.setDefaultImageResId(defaultImageResId);
		netWorkImageView.setErrorImageResId(errorImageResId);
		netWorkImageView.setImageUrl(url, loader);
	}
}
