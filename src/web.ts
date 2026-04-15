import { WebPlugin } from '@capacitor/core';

import type { CapacitorProximityPlugin, PluginVersionResult, ProximityStatusResult } from './definitions';

export class CapacitorProximityWeb extends WebPlugin implements CapacitorProximityPlugin {
  async enable(): Promise<void> {
    throw this.unavailable('Proximity monitoring is not available on web.');
  }

  async disable(): Promise<void> {
    throw this.unavailable('Proximity monitoring is not available on web.');
  }

  async getStatus(): Promise<ProximityStatusResult> {
    return {
      available: false,
      enabled: false,
      platform: 'web',
    };
  }

  async getPluginVersion(): Promise<PluginVersionResult> {
    return {
      version: 'web',
    };
  }
}
