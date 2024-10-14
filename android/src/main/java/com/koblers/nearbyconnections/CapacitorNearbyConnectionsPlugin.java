package com.koblers.nearbyconnections;

import com.getcapacitor.JSObject;
import com.getcapacitor.Plugin;
import com.getcapacitor.PluginCall;
import com.getcapacitor.PluginMethod;
import com.getcapacitor.annotation.CapacitorPlugin;

@CapacitorPlugin(name = "CapacitorNearbyConnections")
public class CapacitorNearbyConnectionsPlugin extends Plugin {

    private CapacitorNearbyConnections implementation = new CapacitorNearbyConnections();

    @PluginMethod(returnType = PluginMethod.RETURN_NONE)
    public void startAdvertisement(PluginCall call) {
        let serviceId = call.getString("serviceId");

        AdvertisingOptions advertisingOptions = new AdvertisingOptions.Builder().setStrategy(STRATEGY).build();
        Nearby.getConnectionsClient(context)
                .startAdvertising(
                        getLocalUserName(), serviceId, connectionLifecycleCallback, advertisingOptions)
                .addOnSuccessListener(
                        (Void unused) -> {
                            call.resolve();
                        })
                .addOnFailureListener(
                        (Exception e) -> {
                            call.reject(e.getMessage());
                        });
    }

    @PluginMethod(returnType = PluginMethod.RETURN_NONE)
    public void stopAdvertisement(PluginCall call) {
        Nearby.getConnectionsClient(context)
                .stopAdvertising()
                .addOnSuccessListener(
                        (Void unused) -> {
                            call.resolve();
                        })
                .addOnFailureListener(
                        (Exception e) -> {
                            call.reject(e.getMessage());
                        });
    }

    @PluginMethod(returnType = PluginMethod.RETURN_NONE)
    public void startDiscovery(PluginCall call) {
        let serviceId = call.getString("serviceId");

        DiscoveryOptions discoveryOptions = new DiscoveryOptions.Builder().setStrategy(STRATEGY).build();
        Nearby.getConnectionsClient(context)
                .startDiscovery(serviceId, endpointDiscoveryCallback, discoveryOptions)
                .addOnSuccessListener(
                        (Void unused) -> {
                            call.resolve();
                        })
                .addOnFailureListener(
                        (Exception e) -> {
                            call.reject(e.getMessage());
                        });
    }

    private final ConnectionLifecycleCallback connectionLifecycleCallback = new ConnectionLifecycleCallback() {
        @Override
        public void onConnectionInitiated(String endpointId, ConnectionInfo connectionInfo) {
            JSObject ret = new JSObject();
            ret.put("endpointId", endpointId);
            ret.put("authenticationToken", connectionInfo.getAuthenticationToken());
            ret.put("isIncomingConnection", connectionInfo.isIncomingConnection());
            ret.put("endpointName", connectionInfo.getEndpointName());
            notifyListeners("onConnectionInitiated", ret);

            // Automatically accept the connection on both sides.
            Nearby.getConnectionsClient(context).acceptConnection(endpointId, payloadCallback);
        }

        @Override
        public void onConnectionResult(String endpointId, ConnectionResolution result) {
            JSObject ret = new JSObject();
            ret.put("endpointId", endpointId);
            ret.put("status", result.getStatus().getStatusCode());
            notifyListeners("onConnectionResult", ret);
        }

        @Override
        public void onDisconnected(String endpointId) {
            JSObject ret = new JSObject();
            ret.put("endpointId", endpointId);
            notifyListeners("onDisconnected", ret);
        }
    };

    private final EndpointDiscoveryCallback endpointDiscoveryCallback = new EndpointDiscoveryCallback() {
        @Override
        public void onEndpointFound(String endpointId, DiscoveredEndpointInfo info) {
            JSObject ret = new JSObject();
            ret.put("endpointId", endpointId);
            ret.put("serviceId", info.getServiceId());
            ret.put("endpointName", info.getEndpointName());
            notifyListeners("onEndpointFound", ret);
        }

        @Override
        public void onEndpointLost(String endpointId) {
            JSObject ret = new JSObject();
            ret.put("endpointId", endpointId);
            notifyListeners("onEndpointLost", ret);
        }
    };
}
