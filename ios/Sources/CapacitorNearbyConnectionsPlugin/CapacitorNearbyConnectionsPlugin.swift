import Foundation
import Capacitor
import NearbyConnections

/**
 * Please read the Capacitor iOS Plugin Development Guide
 * here: https://capacitorjs.com/docs/plugins/ios
 */
@objc(CapacitorNearbyConnectionsPlugin)
public class CapacitorNearbyConnectionsPlugin: CAPPlugin, CAPBridgedPlugin {
    public let identifier = "CapacitorNearbyConnectionsPlugin"
    public let jsName = "CapacitorNearbyConnections"
    public let pluginMethods: [CAPPluginMethod] = [
        CAPPluginMethod(name: "echo", returnType: CAPPluginReturnPromise)
    ]
    private let implementation = CapacitorNearbyConnections()

    @objc func echo(_ call: CAPPluginCall) {
        let value = call.getString("value") ?? ""
        call.resolve([
            "value": implementation.echo(value)
        ])
    }
}
