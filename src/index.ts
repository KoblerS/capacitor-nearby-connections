import { registerPlugin } from '@capacitor/core';

import type { CapacitorNearbyConnectionsPlugin } from './definitions';

const CapacitorNearbyConnections =
  registerPlugin<CapacitorNearbyConnectionsPlugin>(
    'CapacitorNearbyConnections',
    {},
  );

export * from './definitions';
export { CapacitorNearbyConnections };
