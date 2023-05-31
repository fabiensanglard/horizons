# horizons

This is the source code for a live wallpaper replacing the discontinued Horizon
for Pixel 6 and Pixel 7 as featured in the article on [fabien.sanglard.net](https://fabiensanglard.net/sunset).

# Assets

The assets are not provided for obvious legal reasons. To generate them, you will need your Pixel 5. Use
this script to extract the background and put them in the `res` folder.

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