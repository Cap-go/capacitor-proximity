/**
 * Result returned by `getStatus()`.
 *
 * @since 0.0.1
 */
export interface ProximityStatusResult {
  /**
   * Whether the current device exposes a usable proximity sensor.
   *
   * @since 0.0.1
   */
  available: boolean;

  /**
   * Whether proximity monitoring is currently enabled by the plugin.
   *
   * @since 0.0.1
   */
  enabled: boolean;

  /**
   * Platform label returned by the native or web implementation.
   *
   * @since 0.0.1
   */
  platform: 'ios' | 'android' | 'web';
}

/**
 * Result returned when requesting the plugin version.
 *
 * @since 0.0.1
 */
export interface PluginVersionResult {
  /**
   * Native plugin version string.
   *
   * @since 0.0.1
   */
  version: string;
}

/**
 * Capacitor plugin for enabling proximity monitoring in mobile apps.
 *
 * @since 0.0.1
 */
export interface CapacitorProximityPlugin {
  /**
   * Enable proximity monitoring.
   *
   * On iOS this enables `UIDevice.isProximityMonitoringEnabled`.
   * On Android this starts listening to `TYPE_PROXIMITY` and dims the current
   * app window while the sensor is covered.
   *
   * @returns Promise that resolves when proximity monitoring is active
   * @since 0.0.1
   * @example
   * ```typescript
   * await CapacitorProximity.enable();
   * ```
   */
  enable(): Promise<void>;

  /**
   * Disable proximity monitoring.
   *
   * This restores the default app window behavior and stops sensor monitoring.
   *
   * @returns Promise that resolves when proximity monitoring is disabled
   * @since 0.0.1
   * @example
   * ```typescript
   * await CapacitorProximity.disable();
   * ```
   */
  disable(): Promise<void>;

  /**
   * Get the current sensor availability and plugin enabled state.
   *
   * @returns Promise resolving to the current plugin status
   * @since 0.0.1
   * @example
   * ```typescript
   * const status = await CapacitorProximity.getStatus();
   * ```
   */
  getStatus(): Promise<ProximityStatusResult>;

  /**
   * Get the current native plugin version.
   *
   * @returns Promise resolving to the plugin version
   * @since 0.0.1
   * @example
   * ```typescript
   * const { version } = await CapacitorProximity.getPluginVersion();
   * ```
   */
  getPluginVersion(): Promise<PluginVersionResult>;
}
