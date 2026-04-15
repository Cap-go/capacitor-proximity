import Foundation
import UIKit

@objc public class CapacitorProximity: NSObject {
    public static let pluginVersion = "8.0.0"

    @objc public func enable() -> Bool {
        UIDevice.current.isProximityMonitoringEnabled = true
        return UIDevice.current.isProximityMonitoringEnabled
    }

    @objc public func disable() {
        UIDevice.current.isProximityMonitoringEnabled = false
    }

    @objc public func isAvailable() -> Bool {
        let original = UIDevice.current.isProximityMonitoringEnabled
        UIDevice.current.isProximityMonitoringEnabled = true
        let available = UIDevice.current.isProximityMonitoringEnabled
        UIDevice.current.isProximityMonitoringEnabled = original
        return available
    }

    @objc public func isEnabled() -> Bool {
        UIDevice.current.isProximityMonitoringEnabled
    }

    @objc public func getPluginVersion() -> String {
        Self.pluginVersion
    }
}
