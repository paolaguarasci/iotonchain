import 'dotenv/config';
import { DefaultAzureCredential } from '@azure/identity';
import { DigitalTwinsClient } from '@azure/digital-twins-core';
// const delay = (ms) => new Promise((resolve) => setTimeout(resolve, ms));

async function manage() {
  const url = process.env.DTURL;
  const credential = new DefaultAzureCredential();
  const serviceClient = new DigitalTwinsClient(url, credential);
  const models = await serviceClient.listModels();
  for await (const model of models) {
    console.log(`Model ID: ${model.id}`);
  }

  const query = 'SELECT * FROM digitaltwins';
  const queryResult = serviceClient.queryTwins(query);
  for await (const item of queryResult) {
    console.log(`DigitalTwin: ${JSON.stringify(item)}`); 
    const digitalTwinId = itme.$dtId;
    const componentPath = 'Component1';
    const component = await serviceClient.getComponent(
      digitalTwinId,
      componentPath
    );
    console.log(`Component: ${component}`);
  }
}

await manage();
