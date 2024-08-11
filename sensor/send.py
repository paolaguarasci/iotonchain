import os
import time
from dotenv import load_dotenv
from azure.identity import DefaultAzureCredential
from azure.digitaltwins.core import DigitalTwinsClient
load_dotenv()
url = os.getenv("DTURL")
credential = DefaultAzureCredential()
service_client = DigitalTwinsClient(url, credential)
digitalTwinId = 'thermostat67'
digitalTwinId = 'truck_barillaspa_5'
propertye = 'Temperature'
newValue = dict();
newValue["path"] =  '/' + propertye
newValue["op"] = "replace"

while True:
  newValue["value"] = 123
  service_client.update_digital_twin(digitalTwinId, [newValue])
  print(f'Invio lettura: {newValue["value"]}')
  time.sleep(1)



