import 'dotenv/config';
import { DefaultAzureCredential } from '@azure/identity';
import { DigitalTwinsClient } from '@azure/digital-twins-core';
const delay = (ms) => new Promise((resolve) => setTimeout(resolve, ms));

async function receiver() {
  const url = process.env.DTURL;

  const credential = new DefaultAzureCredential();
  const serviceClient = new DigitalTwinsClient(url, credential);

  const digitalTwinId = process.argv[2] ?? 'thermostat67';
  const propertye = process.argv[3] ?? 'Temperature';

  console.log(`Inizio l'ascolto sul sensore ${digitalTwinId}`);

  while (true) {
    const twin = await serviceClient.getDigitalTwin(digitalTwinId);
    console.log(`${propertye} = ${twin.body[propertye]}`);
    await delay(5000);
  }
}

receiver();
