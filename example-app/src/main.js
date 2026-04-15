import './style.css';
import { CapacitorProximity } from '@capgo/capacitor-proximity';

const output = document.getElementById('plugin-output');
const statusPill = document.getElementById('status-pill');
const refreshStatusButton = document.getElementById('refresh-status');
const enableButton = document.getElementById('enable-proximity');
const disableButton = document.getElementById('disable-proximity');
const versionButton = document.getElementById('plugin-version');

const setStatus = (value) => {
  statusPill.textContent = value;
};

const setOutput = (value) => {
  output.textContent = typeof value === 'string' ? value : JSON.stringify(value, null, 2);
};

const withAction = async (label, action) => {
  try {
    setStatus(label);
    const result = await action();
    setStatus('Done');
    if (result !== undefined) {
      setOutput(result);
    }
  } catch (error) {
    setStatus('Failed');
    setOutput(`Error: ${error?.message ?? error}`);
  }
};

refreshStatusButton.addEventListener('click', async () => {
  await withAction('Reading status', async () => await CapacitorProximity.getStatus());
});

enableButton.addEventListener('click', async () => {
  await withAction('Enabling', async () => {
    await CapacitorProximity.enable();
    return {
      action: 'enable',
      status: await CapacitorProximity.getStatus(),
    };
  });
});

disableButton.addEventListener('click', async () => {
  await withAction('Disabling', async () => {
    await CapacitorProximity.disable();
    return {
      action: 'disable',
      status: await CapacitorProximity.getStatus(),
    };
  });
});

versionButton.addEventListener('click', async () => {
  await withAction('Reading version', async () => await CapacitorProximity.getPluginVersion());
});

setOutput({
  hint: 'Use a physical device, then refresh the status before enabling proximity monitoring.',
});
