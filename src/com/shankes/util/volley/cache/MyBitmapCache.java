package com.shankes.util.volley.cache;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.lang.ref.SoftReference;
import java.util.HashMap;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.support.v4.util.LruCache;

import com.android.volley.toolbox.ImageLoader.ImageCache;

public class MyBitmapCache implements ImageCache {
	/*
	 * �ڴ滺��:��ͼƬ�ݴ����ڴ���(�������õ�)��Ŀ�ģ�Ϊ����������������ȣ�ͬʱ��ʡ�û�������
	 * 
	 * ���ػ���:��ͼƬ�־û�(���ñ������ⲿ�Ĵ洢������)��Ŀ�ģ��ڳ���û�������ʱ����Ȼ�����ݿ���ʾ
	 * 
	 * 
	 * ʹ��Volley������ʵ��ͼƬ��3�����棺
	 * 
	 * L1��LruCache (�ڴ滺��)
	 * 
	 * Androidϵͳ��ÿһ��Ӧ�ó�����һ���̶���С���ڴ����(16M��32M)
	 * 
	 * ��Щ�洢�ռ���һ������ר�����ڴ��ͼƬ�ģ������򲻶ϵ����ڴ��м���ͼƬ�Ĳ�����
	 * 
	 * �ﵽϵͳΪ���ͼƬ������ڴ�ķ�ֵ֮�󣬳���ͻᱨOOM���󣬱�ϵͳǿ���˳���
	 * 
	 * ��Android3.0֮ǰ�������ж�ͼƬ���ڴ滺�涼���ɿ��������п��ƣ����ֲ�����ʽ�Ƚ����׵���OOM
	 * 
	 * ��Ϊʵ�ʿ���������Google��Android3.0����LruCache����ʵ���ڴ�Ĺ���
	 * 
	 * LruCache��һ�����ڴ��л������ݵ����������������ص㣺1������ָ������������
	 * 
	 * 2������Lru(�������ʹ��)�㷨ʵ�ֶ������е����ݵĹ���
	 * 
	 * L2��SoftReference
	 * (�ڴ滺��)(�����ã���LruCache�е���������ʱ�����һЩ���ݱ��߳������߳��������������������һ�����û��Լ��������߳����ģ���ʱ����������
	 * ��Ĳ���Ҫ��
	 * ������ŵ�softReference�У��ڶ�����һ�������зŲ����ˣ������߳�һЩ���ݣ���Щ�����п��ܻ���������Ҫ�ģ���Ҫ�������ڶ��������л�������
	 * )
	 * 
	 * L3���ⲿ�洢���� (���ػ���)
	 */
	@SuppressWarnings("rawtypes")
	private LruCache bitmapLruCache;// ����ǿ���õ�������һ�����棩
	private HashMap<String, SoftReference<Bitmap>> allEvictedHashMap;// �����������������������ã�
	private static MyBitmapCache myCache;// Ϊ�˲�����������汾��Ҫʹ�õ���ģʽ
	private static Context context;

	public static MyBitmapCache getMyImageCache(Context context) {
		if (myCache == null) {
			myCache = new MyBitmapCache(context);
		}
		return myCache;
	}

	private MyBitmapCache(Context context) {
		this.context = context;
		// ���������䵽�����ڴ�
		long totalMemory = Runtime.getRuntime().maxMemory();
		// ʵ����һ��������
		allEvictedHashMap = new HashMap<String, SoftReference<Bitmap>>();
		bitmapLruCache = new LruCache<String, Bitmap>((int) (totalMemory / 8)) {
			// �����ٷ��ṩ�Ļ�����䷽���У������ǰ����������㣬���ּ��㷽ʽ�������ڴ������oom��,���ǿ���ͨ��sizeof�������ļ��㷽�����óɼ���ͼƬ�Ĵ�С
			@Override
			protected int sizeOf(String key, Bitmap value) {
				// TODO Auto-generated method stub
				// ����ÿ��ͼƬ�Ĵ�С
				int rowByte = value.getRowBytes();// ��ȡͼƬÿһ�е��ֽ���
				int height = value.getHeight();// ͼƬ�ĸ߶�
				int perBitmapSize = rowByte * height;// ����ͼƬ��������

				return perBitmapSize;
			}

			// ��ʾָ���������б��Ƴ���ͼƬ���ͷŷ�ʽ
			// ����һ����ʾ�����Ƿ�����Ϊһ�����治������߳��� ��������ͼƬ��Ӧ��keyֵ�����������ɵ����ݣ�������:�µ�����
			@Override
			protected void entryRemoved(boolean evicted, String key, Bitmap oldValue, Bitmap newValue) {
				// TODO Auto-generated method stub
				super.entryRemoved(evicted, key, oldValue, newValue);
				// �������������������߳���ͼƬ����(Volley3�������еĵ�2������L2)
				if (evicted) {// true���oldValue����������
					SoftReference<Bitmap> bitmapReference = new SoftReference<Bitmap>(oldValue);
					allEvictedHashMap.put(key, bitmapReference);
				}
			}
		};
	}

	/**
	 * ��ʹ��volley�������ͼƬ��ʱ�򲢲������Ͻ����صģ�������ȥ�鿴���������Ƿ��У�����н�������Ӧ�������û�н���Ӧ��������
	 */
	@Override
	public Bitmap getBitmap(String url) {
		// ȡ�Ѿ�����ͼƬ
		Bitmap bitmap1 = (Bitmap) bitmapLruCache.get(url);
		if (bitmap1 != null) {
			return bitmap1;
		}
		// ��ȡ���������ͼƬ
		SoftReference<Bitmap> aBitmapReference = allEvictedHashMap.get(url);
		if (aBitmapReference != null) {
			Bitmap bitmap2 = aBitmapReference.get();
			if (bitmap2 != null) {
				// ���������
				bitmapLruCache.put(url, bitmap2);// �����ݴ�����ӵ�һ��������
				return bitmap2;
			}
		}

		// ȡ�����������е�ͼƬ(ȡ�ñ���ͼƬ)
		File cacheFile = getCacheFile();
		readImage(url, cacheFile);

		return null;
	}

	/**
	 * ��ȡ���ػ���ͼƬ
	 * 
	 * @param url
	 * @param cacheFile
	 */
	@SuppressWarnings("unchecked")
	private Bitmap readImage(String url, File cacheFile) {
		String[] str = url.split("/");
		try {
			FileInputStream fis = new FileInputStream(cacheFile.getAbsolutePath() + File.separator
					+ str[str.length - 1]);
			ByteArrayOutputStream bos = new ByteArrayOutputStream();
			byte[] tmp = new byte[1024];
			int len = 0;
			while ((len = fis.read(tmp)) != -1) {
				bos.write(tmp, 0, len);
			}
			byte[] imgData = bos.toByteArray();
			Bitmap bitmap3 = BitmapFactory.decodeByteArray(imgData, 0, imgData.length);
			bitmapLruCache.put(url, bitmap3);// �Ѵ��ڱ��ص�ͼƬ���浽�ڴ���
			return bitmap3;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}

	}

	private File getCacheFile() {
		File cacheFile;
		if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
			// ��SD���ϴ��������ļ��� sd��·��/android/data/����/cache
			File sdCache = context.getExternalCacheDir();
			cacheFile = sdCache;
		} else {
			// �ֻ��Դ��Ĵ洢�ռ��д��������ļ��� data/data/����/cache
			File internalCache = context.getCacheDir();
			cacheFile = internalCache;
		}
		return cacheFile;
	}

	// ����ͼƬ
	@SuppressWarnings("unchecked")
	@Override
	public void putBitmap(String url, Bitmap bitmap) {
		// TODO Auto-generated method stub
		// ʵ�ֵ�һ������
		bitmapLruCache.put(url, bitmap);

		// ʵ�ֵ���������
		File cacheFile = getCacheFile();
		saveImage(url, bitmap, cacheFile);

	}

	// ��ͼƬд�뱾��
	private void saveImage(String url, Bitmap bitmap, File cacheFile) {
		// TODO Auto-generated method stub
		String[] str = url.split("/");
		try {
			FileOutputStream fos = new FileOutputStream(cacheFile.getAbsolutePath() + File.separator
					+ str[str.length - 1]);
			bitmap.compress(CompressFormat.JPEG, 100, fos);// ��ͼƬд�뵽����
															// ����һ��ͼƬ�ĸ�ʽ,�����������������,
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
