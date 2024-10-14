export interface CapacitorNearbyConnectionsPlugin {
  /**
   * Start advertising nearby devices
   * @param options @type {AdvertisementOptions}
   */
  startAdvertising: (options: AdvertisementOptions) => Promise<void>;
  /**
   * Stop advertising nearby devices
   */
  stopAdvertising: () => Promise<void>;
  /**
   * Start discovering nearby devices
   * @param options @type {DiscoveryOptions}
   */
  startDiscovery: (options: DiscoveryOptions) => Promise<void>;
}

export interface AdvertisementOptions {
  serviceId: string;
}

export interface DiscoveryOptions {
  serviceId: string;
}
