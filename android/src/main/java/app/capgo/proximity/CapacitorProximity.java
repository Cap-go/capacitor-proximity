package app.capgo.proximity;

import android.app.Activity;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.view.Window;
import android.view.WindowManager;
import com.getcapacitor.JSObject;
import com.getcapacitor.Logger;
import com.getcapacitor.PluginCall;

public class CapacitorProximity {

    public static final String PLUGIN_VERSION = "8.0.0";
    private static final String TAG = "CapacitorProximity";

    private final Activity activity;
    private final SensorManager sensorManager;
    private final Sensor proximitySensor;

    private SensorEventListener proximitySensorListener;
    private boolean enabled = false;
    private float originalBrightness = WindowManager.LayoutParams.BRIGHTNESS_OVERRIDE_NONE;
    private boolean originalKeepScreenOn = false;

    public CapacitorProximity(Activity activity) {
        this.activity = activity;

        if (activity != null) {
            sensorManager = (SensorManager) activity.getSystemService(Context.SENSOR_SERVICE);
            proximitySensor = sensorManager != null ? sensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY) : null;
        } else {
            sensorManager = null;
            proximitySensor = null;
        }
    }

    public void enable(PluginCall call) {
        if (activity == null) {
            call.reject("Activity not available.");
            return;
        }

        if (proximitySensor == null || sensorManager == null) {
            call.reject("Proximity sensor not available on this device.");
            return;
        }

        if (enabled) {
            call.resolve();
            return;
        }

        Window window = activity.getWindow();
        WindowManager.LayoutParams params = window.getAttributes();
        originalBrightness = params.screenBrightness;
        originalKeepScreenOn = (params.flags & WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON) != 0;

        proximitySensorListener = new SensorEventListener() {
            @Override
            public void onSensorChanged(SensorEvent event) {
                if (event.sensor.getType() != Sensor.TYPE_PROXIMITY) {
                    return;
                }

                float distance = event.values[0];
                boolean isNear = distance < proximitySensor.getMaximumRange();

                if (isNear) {
                    dimScreen();
                } else {
                    restoreScreen();
                }
            }

            @Override
            public void onAccuracyChanged(Sensor sensor, int accuracy) {
                // No-op
            }
        };

        boolean registered = sensorManager.registerListener(proximitySensorListener, proximitySensor, SensorManager.SENSOR_DELAY_NORMAL);

        if (!registered) {
            proximitySensorListener = null;
            call.reject("Failed to register the proximity sensor listener.");
            return;
        }

        enabled = true;
        call.resolve();
    }

    public void disable(PluginCall call) {
        stopMonitoring();
        call.resolve();
    }

    public JSObject getStatus() {
        JSObject result = new JSObject();
        result.put("available", proximitySensor != null);
        result.put("enabled", enabled);
        result.put("platform", "android");
        return result;
    }

    public String getPluginVersion() {
        return PLUGIN_VERSION;
    }

    public void stopMonitoring() {
        restoreScreen();

        if (sensorManager != null && proximitySensorListener != null) {
            sensorManager.unregisterListener(proximitySensorListener);
            proximitySensorListener = null;
        }

        enabled = false;
    }

    private void dimScreen() {
        if (activity == null) {
            return;
        }

        try {
            Window window = activity.getWindow();
            WindowManager.LayoutParams params = window.getAttributes();
            params.flags |= WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON;
            params.screenBrightness = 0.0f;
            window.setAttributes(params);
        } catch (Exception exception) {
            Logger.error(TAG, "Failed to dim the app window.", exception);
        }
    }

    private void restoreScreen() {
        if (activity == null) {
            return;
        }

        try {
            Window window = activity.getWindow();
            WindowManager.LayoutParams params = window.getAttributes();

            params.screenBrightness = originalBrightness;
            if (!originalKeepScreenOn) {
                params.flags &= ~WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON;
            }

            window.setAttributes(params);
        } catch (Exception exception) {
            Logger.error(TAG, "Failed to restore the app window.", exception);
        }
    }
}
