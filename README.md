# @capgo/capacitor-proximity
 <a href="https://capgo.app/"><img src='https://raw.githubusercontent.com/Cap-go/capgo/main/assets/capgo_banner.png' alt='Capgo - Instant updates for capacitor'/></a>

<div align="center">
  <h2><a href="https://capgo.app/?ref=plugin_proximity"> ➡️ Get Instant updates for your App with Capgo</a></h2>
  <h2><a href="https://capgo.app/consulting/?ref=plugin_proximity"> Missing a feature? We'll build the plugin for you 💪</a></h2>
</div>

Capacitor plugin for enabling proximity monitoring in mobile apps.

## Why this plugin

This plugin wraps the native proximity APIs used by iOS and Android so a Capacitor app can:

- Enable proximity monitoring when a screen should react to the user's face or hand.
- Disable it cleanly when the flow ends.
- Check whether the current device supports the sensor.
- Read the plugin version from native code.

On iOS, the plugin forwards directly to `UIDevice.isProximityMonitoringEnabled`.
On Android, it listens to the device proximity sensor and dims the active app window while the sensor is covered.

## Platform support

| Platform | Support |
|----------|---------|
| iOS      | ✅ Uses native proximity monitoring |
| Android  | ✅ Uses `Sensor.TYPE_PROXIMITY` and dims the current app window |
| Web      | ❌ Not available |

## Compatibility

| Plugin version | Capacitor compatibility | Maintained |
| -------------- | ----------------------- | ---------- |
| v8.*.*         | v8.*.*                  | ✅         |
| v7.*.*         | v7.*.*                  | On demand  |
| v6.*.*         | v6.*.*                  | ❌         |
| v5.*.*         | v5.*.*                  | ❌         |

> The plugin major version follows the Capacitor major version.

## Install

```bash
bun add @capgo/capacitor-proximity
bunx cap sync
```

## Usage

```ts
import { CapacitorProximity } from '@capgo/capacitor-proximity';

const status = await CapacitorProximity.getStatus();

if (status.available) {
  await CapacitorProximity.enable();

  // ...run your near-ear or face-down flow...

  await CapacitorProximity.disable();
}
```

## Notes

- Test on a real device. Simulators and most desktop browsers do not expose a proximity sensor.
- On Android, the plugin only affects the current app window. It does not control the full system display outside your app.
- On iOS, Apple controls the exact screen-off behavior once proximity monitoring is enabled.

## Example app

The `example-app/` folder contains a small Vite demo for checking status, enabling the plugin, disabling it, and reading the native version.

## API

<docgen-index>

* [`enable()`](#enable)
* [`disable()`](#disable)
* [`getStatus()`](#getstatus)
* [`getPluginVersion()`](#getpluginversion)
* [Interfaces](#interfaces)

</docgen-index>

<docgen-api>
<!--Update the source file JSDoc comments and rerun docgen to update the docs below-->

Capacitor plugin for enabling proximity monitoring in mobile apps.

### enable()

```typescript
enable() => Promise<void>
```

Enable proximity monitoring.

On iOS this enables `UIDevice.isProximityMonitoringEnabled`.
On Android this starts listening to `TYPE_PROXIMITY` and dims the current
app window while the sensor is covered.

**Since:** 0.0.1

--------------------


### disable()

```typescript
disable() => Promise<void>
```

Disable proximity monitoring.

This restores the default app window behavior and stops sensor monitoring.

**Since:** 0.0.1

--------------------


### getStatus()

```typescript
getStatus() => Promise<ProximityStatusResult>
```

Get the current sensor availability and plugin enabled state.

**Returns:** <code>Promise&lt;<a href="#proximitystatusresult">ProximityStatusResult</a>&gt;</code>

**Since:** 0.0.1

--------------------


### getPluginVersion()

```typescript
getPluginVersion() => Promise<PluginVersionResult>
```

Get the current native plugin version.

**Returns:** <code>Promise&lt;<a href="#pluginversionresult">PluginVersionResult</a>&gt;</code>

**Since:** 0.0.1

--------------------


### Interfaces


#### ProximityStatusResult

Result returned by `getStatus()`.

| Prop            | Type                                     | Description                                                      | Since |
| --------------- | ---------------------------------------- | ---------------------------------------------------------------- | ----- |
| **`available`** | <code>boolean</code>                     | Whether the current device exposes a usable proximity sensor.    | 0.0.1 |
| **`enabled`**   | <code>boolean</code>                     | Whether proximity monitoring is currently enabled by the plugin. | 0.0.1 |
| **`platform`**  | <code>'ios' \| 'android' \| 'web'</code> | Platform label returned by the native or web implementation.     | 0.0.1 |


#### PluginVersionResult

Result returned when requesting the plugin version.

| Prop          | Type                | Description                   | Since |
| ------------- | ------------------- | ----------------------------- | ----- |
| **`version`** | <code>string</code> | Native plugin version string. | 0.0.1 |

</docgen-api>
