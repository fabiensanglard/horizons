/*
 * Copyright (C) 2007 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package net.fabiensanglard.sunset;

import static net.fabiensanglard.sunset.MainActivityKt.getDrawableSunset;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.os.BatteryManager;
import android.os.Handler;
import android.service.wallpaper.WallpaperService;
import android.view.SurfaceHolder;

import androidx.annotation.NonNull;

public class SunsetWallpaper extends WallpaperService {

	private void log(@NonNull String msg) {
		if (true) {
			return;
		}
	    System.out.println(msg);
	}

	@Override
	public Engine onCreateEngine() {
		log("onCreateEngine");
		return new BokehEngine();
	}

	class BokehEngine extends Engine {

		private Handler mHandler = new Handler();

		private boolean mVisible;

		@Override
		public void onDestroy() {
			super.onDestroy();
		}

		@Override
		public void onVisibilityChanged(boolean visible) {
			log("onVisibilityChanged visible=" + visible);
			mVisible = visible;
			if (visible) {
				scheduleRedraw();
			}
		}


		@Override
		public void onSurfaceDestroyed(SurfaceHolder holder) {
			log("onSurfaceDestroyed");
			super.onSurfaceDestroyed(holder);
			mVisible = false;
		}


		protected void scheduleRedraw() {
			log("scheduleRedraw");
			if (mVisible) {
				mHandler.postDelayed(() -> drawFrame(), 1 );
			}
		}

		int offsetX;
		int offsetY;
		int height;
		int width;
		int visibleWidth;

		private BroadcastReceiver mBatInfoReceiver = new BroadcastReceiver(){
			@Override
			public void onReceive(Context ctxt, Intent intent) {
				log("Battery event");
//				int level = intent.getIntExtra(BatteryManager.EXTRA_LEVEL, -1);
//				int scale = intent.getIntExtra(BatteryManager.EXTRA_SCALE, -1);
//				float batteryPct = level * 100 / (float)scale;
				scheduleRedraw();
			}
		};

		@Override
		public void onCreate(SurfaceHolder surfaceHolder) {
			log("onCreate");
			super.onCreate(surfaceHolder);

			// By default we don't get touch events, so enable them.
//			setTouchEventsEnabled(true);

			registerReceiver(this.mBatInfoReceiver, new IntentFilter(Intent.ACTION_BATTERY_CHANGED));
		}

		@Override
		public void onSurfaceChanged(SurfaceHolder holder, int format,
				int width, int height) {
			log("onSurfaceChanged");
			this.height = height;
			if (this.isPreview()) {
				this.width = width;
			} else {
				this.width = 2 * width;
			}
			this.visibleWidth = width;

			scheduleRedraw();
			drawFrame();
		}

		@Override
		public void onOffsetsChanged(float xOffset, float yOffset,
				float xOffsetStep, float yOffsetStep, int xPixelOffset,
				int yPixelOffset) {
			log("onOffsetsChanged");
			// store the offsets
			this.offsetX = xPixelOffset;
			this.offsetY = yPixelOffset;

			scheduleRedraw();
			drawFrame();
		}


		private void drawFrame() {
			log("drawFrame");
			SurfaceHolder holder = getSurfaceHolder();

			Canvas c = null;
			try {
				c = holder.lockCanvas();
				if (c != null) {
					draw(c);
				}
			} finally {
				if (c != null)
					holder.unlockCanvasAndPost(c);
			}

		}

		float getBatteryLevel() {
			IntentFilter ifilter = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);
			Intent batteryStatus = 	registerReceiver(null, ifilter);
			int level = batteryStatus.getIntExtra(BatteryManager.EXTRA_LEVEL, -1);
			int scale = batteryStatus.getIntExtra(BatteryManager.EXTRA_SCALE, -1);

			return (level * 100 / (float)scale) / 100;
		}


		void draw(Canvas c) {
			log("draw");
			float batteryLevel = getBatteryLevel();
			int img = getDrawableSunset(batteryLevel);

			Drawable d = getResources().getDrawable(img, null);
			d.setBounds(0, 0, visibleWidth, height);
			d.draw(c);
		}

	}

}
