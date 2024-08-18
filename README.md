# horizons

This is the source code for a live wallpaper. It uses a battery listener to display an image matching the battery percentage as wallpaper.

# Assets

The original intend was to mimic Pixel horizon wallpaper.  The assets are not provided. To generate them, you will need to extract them from your own Pixel (or emulator). Use this script to extract the background and put them in the `res` folder.

```
adb shell settings put global stay_on_while_plugged_in 3

for BATTERY_LEVEL in {1..100}
do
    adb shell dumpsys battery set level $BATTERY_LEVEL
    adb shell settings put system screen_brightness 200
    adb exec-out screencap -p > screen$BATTERY_LEVEL.png
done      

adb shell dumpsys battery reset   
adb shell settings put global stay_on_while_plugged_in 0
```

If you need to clean up the screencaps, use the provided Photoshop script `Batch Actions.atn`.
