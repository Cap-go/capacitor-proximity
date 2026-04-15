import Capacitor
import Foundation

@objc(CapacitorProximityPlugin)
public class CapacitorProximityPlugin: CAPPlugin, CAPBridgedPlugin {
    public let identifier = "CapacitorProximityPlugin"
    public let jsName = "CapacitorProximity"
    public let pluginMethods: [CAPPluginMethod] = [
        CAPPluginMethod(name: "enable", returnType: CAPPluginReturnPromise),
        CAPPluginMethod(name: "disable", returnType: CAPPluginReturnPromise),
        CAPPluginMethod(name: "getStatus", returnType: CAPPluginReturnPromise),
        CAPPluginMethod(name: "getPluginVersion", returnType: CAPPluginReturnPromise)
    ]

    private let implementation = CapacitorProximity()

    deinit {
        implementation.disable()
    }

    @objc func enable(_ call: CAPPluginCall) {
        DispatchQueue.main.async {
            if self.implementation.enable() {
                call.resolve()
                return
            }

            call.reject("Proximity sensor not available on this device.")
        }
    }

    @objc func disable(_ call: CAPPluginCall) {
        DispatchQueue.main.async {
            self.implementation.disable()
            call.resolve()
        }
    }

    @objc func getStatus(_ call: CAPPluginCall) {
        DispatchQueue.main.async {
            call.resolve([
                "available": self.implementation.isAvailable(),
                "enabled": self.implementation.isEnabled(),
                "platform": "ios"
            ])
        }
    }

    @objc func getPluginVersion(_ call: CAPPluginCall) {
        call.resolve([
            "version": implementation.getPluginVersion()
        ])
    }
}
