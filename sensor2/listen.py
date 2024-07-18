import os
from dotenv import load_dotenv
from azure.identity import DefaultAzureCredential
from azure.digitaltwins.core import DigitalTwinsClient
load_dotenv() 
url = os.getenv("DTURL")
credential = DefaultAzureCredential()
service_client = DigitalTwinsClient(url, credential)
digitalTwinId = 'thermostat67'
propertye = 'Temperature'
get_twin = service_client.get_digital_twin(digitalTwinId)
print(f'{propertye}: {get_twin[propertye]}')
