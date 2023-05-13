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

import android.os.Handler;
import android.service.wallpaper.WallpaperService;
import android.view.SurfaceHolder;

public abstract class AnimationWallpaper extends WallpaperService {

	protected abstract class AnimationEngine extends Engine {
		private Handler mHandler = new Handler();

		private Runnable mIteration = () -> {
			iteration();
			drawFrame();
		};

		private boolean mVisible;

		@Override
		public void onDestroy() {
			super.onDestroy();
			// stop the animation
			mHandler.removeCallbacks(mIteration);
		}

		@Override
		public void onVisibilityChanged(boolean visible) {
			mVisible = visible;
			if (visible) {
				iteration();
				drawFrame();
			} else {
				// stop the animation
				mHandler.removeCallbacks(mIteration);
			}
		}

		@Override
		public void onSurfaceChanged(SurfaceHolder holder, int format,
				int width, int height) {
			iteration();
			drawFrame();
		}

		@Override
		public void onSurfaceDestroyed(SurfaceHolder holder) {
			super.onSurfaceDestroyed(holder);
			mVisible = false;
			// stop the animation
			mHandler.removeCallbacks(mIteration);
		}

		@Override
		public void onOffsetsChanged(float xOffset, float yOffset,
				float xOffsetStep, float yOffsetStep, int xPixelOffset,
				int yPixelOffset) {
			iteration();
			drawFrame();
		}

		protected abstract void drawFrame();

		protected void iteration() {
			// Reschedule the next redraw in 40ms
			mHandler.removeCallbacks(mIteration);
			if (mVisible) {
				mHandler.postDelayed(mIteration, 1000 );
			}
		}
	}
}
