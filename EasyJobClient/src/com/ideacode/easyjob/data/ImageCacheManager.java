package com.ideacode.easyjob.data;

import android.app.ActivityManager;
import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.TransitionDrawable;
import android.widget.ImageView;

import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.ideacode.easyjob.EasyJobApplication;
import com.ideacode.easyjob.api.RequestManager;

/**
 * ͼƬ���ػ�����
 * @author ly-suhai
 *
 */
public class ImageCacheManager {
	// ȡ�����ڴ���ֵ��1/8��ΪͼƬ����
	private static final int MEM_CACHE_SIZE = 1024 * 1024 * ((ActivityManager) EasyJobApplication.getContext()
			.getSystemService(Context.ACTIVITY_SERVICE)).getMemoryClass() / 8;

	private static ImageLoader mImageLoader = new ImageLoader(RequestManager.mRequestQueue, new BitmapLruCache(
			MEM_CACHE_SIZE));

	private ImageCacheManager() {

	}

	public static ImageLoader.ImageContainer loadImage(String requestUrl,
			ImageLoader.ImageListener imageListener) {
		return loadImage(requestUrl, imageListener, 0, 0);
	}

	public static ImageLoader.ImageContainer loadImage(String requestUrl,
			ImageLoader.ImageListener imageListener, int maxWidth, int maxHeight) {
		return mImageLoader.get(requestUrl, imageListener, maxWidth, maxHeight);
	}

	public static ImageLoader.ImageListener getImageListener(final ImageView view,
			final Drawable defaultImageDrawable, final Drawable errorImageDrawable) {
		return new ImageLoader.ImageListener() {
			@Override
			public void onErrorResponse(VolleyError error) {
				if (errorImageDrawable != null) {
					view.setImageDrawable(errorImageDrawable);
				}
			}

			@Override
			public void onResponse(ImageLoader.ImageContainer response, boolean isImmediate) {
				if (response.getBitmap() != null) {
					if (!isImmediate && defaultImageDrawable != null) {
						TransitionDrawable transitionDrawable = new TransitionDrawable(
								new Drawable[]{
										defaultImageDrawable,
										new BitmapDrawable(EasyJobApplication.getContext().getResources(),
												response.getBitmap())
								}
								);
						transitionDrawable.setCrossFadeEnabled(true);
						view.setImageDrawable(transitionDrawable);
						transitionDrawable.startTransition(100);
					} else {
						view.setImageBitmap(response.getBitmap());
					}
				} else if (defaultImageDrawable != null) {
					view.setImageDrawable(defaultImageDrawable);
				}
			}
		};
	}
}
