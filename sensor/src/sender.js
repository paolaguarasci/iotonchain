import 'dotenv/config';
import { DefaultAzureCredential } from '@azure/identity';
import { DigitalTwinsClient } from '@azure/digital-twins-core';
const delay = (ms) => new Promise((resolve) => setTimeout(resolve, ms));

// https://stackoverflow.com/questions/25582882/javascript-math-random-normal-distribution-gaussian-bell-curve
function randn_bm(min, max, skew) {
  let u = 0,
    v = 0;
  while (u === 0) u = Math.random(); //Converting [0,1) to (0,1)
  while (v === 0) v = Math.random();
  let num = Math.sqrt(-2.0 * Math.log(u)) * Math.cos(2.0 * Math.PI * v);

  num = num / 10.0 + 0.5; // Translate to 0 -> 1
  if (num > 1 || num < 0)
    num = randn_bm(min, max, skew); // resample between 0 and 1 if out of range
  else {
    num = Math.pow(num, skew); // Skew
    num *= max - min; // Stretch to fill range
    num += min; // offset to min
  }
  return num;
}

async function sender() {
  const url = process.env.DTURL;

  const credential = new DefaultAzureCredential();
  const serviceClient = new DigitalTwinsClient(url, credential);

  const digitalTwinId = process.argv[2] ?? 'thermostat67';
  const propertye = process.argv[3] ?? 'Temperature';

  console.log(`Inizio l'invio sul sensore ${digitalTwinId}`);

  while (true) {
    const data = [
      {
        op: 'replace',
        path: '/' + propertye,
        value: randn_bm(-19, -17, 1)
      }
    ];
    await serviceClient.updateDigitalTwin(digitalTwinId, data);
    console.log(`Send: ${data[0].value}`);
    await delay(5000);
  }
}

await sender();
