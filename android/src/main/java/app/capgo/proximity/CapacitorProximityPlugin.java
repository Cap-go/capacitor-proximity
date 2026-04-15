package app.capgo.proximity;

import com.getcapacitor.JSObject;
import com.getcapacitor.Plugin;
import com.getcapacitor.PluginCall;
import com.getcapacitor.PluginMethod;
import com.getcapacitor.annotation.CapacitorPlugin;

@CapacitorPlugin(name = "CapacitorProximity")
public class CapacitorProximityPlugin extends Plugin {

    private CapacitorProximity implementation;

    @Override
    public void load() {
        super.load();
        implementation = new CapacitorProximity(getActivity());
    }

    @Override
    protected void handleOnDestroy() {
        if (implementation != null) {
            implementation.stopMonitoring();
        }
        super.handleOnDestroy();
    }

    @PluginMethod
    public void enable(PluginCall call) {
        bridge.executeOnMainThread(() -> implementation.enable(call));
    }

    @PluginMethod
    public void disable(PluginCall call) {
        bridge.executeOnMainThread(() -> implementation.disable(call));
    }

    @PluginMethod
    public void getStatus(PluginCall call) {
        call.resolve(implementation.getStatus());
    }

    @PluginMethod
    public void getPluginVersion(PluginCall call) {
        JSObject result = new JSObject();
        result.put("version", implementation.getPluginVersion());
        call.resolve(result);
    }
}
