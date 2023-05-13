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
package com.sample.livewallpaper.bokehrainbow;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.os.BatteryManager;
import android.os.Bundle;
import android.view.SurfaceHolder;

import net.fabiensanglard.sunset.R;

public class BokehRainbowWallpaper extends AnimationWallpaper {

	@Override
	public Engine onCreateEngine() {
		return new BokehEngine();
	}

	class BokehEngine extends AnimationEngine {

		int[] stages = {
				R.drawable.screen0,
				R.drawable.screen1,
				R.drawable.screen2,
				R.drawable.screen3,
				R.drawable.screen4,
				R.drawable.screen5,
				R.drawable.screen6,
				R.drawable.screen7,
				R.drawable.screen8,
				R.drawable.screen9,
				R.drawable.screen10,
				R.drawable.screen11,
				R.drawable.screen12,
				R.drawable.screen13,
				R.drawable.screen14,
				R.drawable.screen15,
				R.drawable.screen16,
				R.drawable.screen17,
				R.drawable.screen18,
				R.drawable.screen19,
				R.drawable.screen20,
				R.drawable.screen21,
				R.drawable.screen22,
				R.drawable.screen23,
				R.drawable.screen24,
				R.drawable.screen25,
				R.drawable.screen26,
				R.drawable.screen27,
				R.drawable.screen28,
				R.drawable.screen29,
				R.drawable.screen30,
				R.drawable.screen31,
				R.drawable.screen32,
				R.drawable.screen33,
				R.drawable.screen34,
				R.drawable.screen35,
				R.drawable.screen36,
				R.drawable.screen37,
				R.drawable.screen38,
				R.drawable.screen39,
				R.drawable.screen40,
				R.drawable.screen41,
				R.drawable.screen42,
				R.drawable.screen43,
				R.drawable.screen44,
				R.drawable.screen45,
				R.drawable.screen46,
				R.drawable.screen47,
				R.drawable.screen48,
				R.drawable.screen49,
				R.drawable.screen50,
				R.drawable.screen51,
				R.drawable.screen52,
				R.drawable.screen53,
				R.drawable.screen54,
				R.drawable.screen55,
				R.drawable.screen56,
				R.drawable.screen57,
				R.drawable.screen58,
				R.drawable.screen59,
				R.drawable.screen60,
				R.drawable.screen61,
				R.drawable.screen62,
				R.drawable.screen63,
				R.drawable.screen64,
				R.drawable.screen65,
				R.drawable.screen66,
				R.drawable.screen67,
				R.drawable.screen68,
				R.drawable.screen69,
				R.drawable.screen70,
				R.drawable.screen71,
				R.drawable.screen72,
				R.drawable.screen73,
				R.drawable.screen74,
				R.drawable.screen75,
				R.drawable.screen76,
				R.drawable.screen77,
				R.drawable.screen78,
				R.drawable.screen79,
				R.drawable.screen80,
				R.drawable.screen81,
				R.drawable.screen82,
				R.drawable.screen83,
				R.drawable.screen84,
				R.drawable.screen85,
				R.drawable.screen86,
				R.drawable.screen87,
				R.drawable.screen88,
				R.drawable.screen89,
				R.drawable.screen90,
				R.drawable.screen91,
				R.drawable.screen92,
				R.drawable.screen93,
				R.drawable.screen94,
				R.drawable.screen95,
				R.drawable.screen96,
				R.drawable.screen97,
				R.drawable.screen98,
				R.drawable.screen99,
				R.drawable.screen100,
		};

		int getDrawableSunset(float f) {
			int index = (int)(f * stages.length);
			if (index >= stages.length)
				index = stages.length - 1 ;
			return stages[index];
		}


		int offsetX;
		int offsetY;
		int height;
		int width;
		int visibleWidth;

//		Set<BokehRainbowCircle> circles = new HashSet<BokehRainbowCircle>();

		int iterationCount = 0;

//		private BroadcastReceiver mBatInfoReceiver = new BroadcastReceiver(){
//			@Override
//			public void onReceive(Context ctxt, Intent intent) {
//				int level = intent.getIntExtra(BatteryManager.EXTRA_LEVEL, -1);
//				int scale = intent.getIntExtra(BatteryManager.EXTRA_SCALE, -1);
//				float batteryPct = level * 100 / (float)scale;
//				drawFrame(batteryPct/10);
//			}
//		};

		@Override
		public void onCreate(SurfaceHolder surfaceHolder) {
			super.onCreate(surfaceHolder);

			// By default we don't get touch events, so enable them.
//			setTouchEventsEnabled(true);

//			registerReceiver(this.mBatInfoReceiver, new IntentFilter(Intent.ACTION_BATTERY_CHANGED));
		}

		@Override
		public void onSurfaceChanged(SurfaceHolder holder, int format,
				int width, int height) {

			this.height = height;
			if (this.isPreview()) {
				this.width = width;
			} else {
				this.width = 2 * width;
			}
			this.visibleWidth = width;

			super.onSurfaceChanged(holder, format, width, height);
		}

		@Override
		public void onOffsetsChanged(float xOffset, float yOffset,
				float xOffsetStep, float yOffsetStep, int xPixelOffset,
				int yPixelOffset) {
			// store the offsets
			this.offsetX = xPixelOffset;
			this.offsetY = yPixelOffset;

			super.onOffsetsChanged(xOffset, yOffset, xOffsetStep, yOffsetStep,
					xPixelOffset, yPixelOffset);
		}

		@Override
		public Bundle onCommand(String action, int x, int y, int z,
				Bundle extras, boolean resultRequested) {
			return super.onCommand(action, x, y, z, extras, resultRequested);
		}

		@Override
		protected void drawFrame() {
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

//	    float batteryLevel = 0f;

		float getBatteryLevel() {
			IntentFilter ifilter = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);
			Intent batteryStatus = 	registerReceiver(null, ifilter);
			int level = batteryStatus.getIntExtra(BatteryManager.EXTRA_LEVEL, -1);
			int scale = batteryStatus.getIntExtra(BatteryManager.EXTRA_SCALE, -1);

			return (level * 100 / (float)scale) / 100;
		}


		void draw(Canvas c) {

			float batteryLevel = getBatteryLevel();
			int img = getDrawableSunset(batteryLevel);

			Drawable d = getResources().getDrawable(img, null);
			d.setBounds(0, 0, visibleWidth, height);
			d.draw(c);

//			Drawable stars = getResources().getDrawable(R.drawable.stars2, null);
//			stars.setBounds(0, 0, visibleWidth, height);
//			stars.setAlpha((int) (120 * (1 - batteryLevel) ));
//			stars.draw(c);
		}

		@Override
		protected void iteration() {
			super.iteration();
		}

	}

}
