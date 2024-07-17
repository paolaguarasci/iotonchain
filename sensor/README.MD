# Solution end-to-end

- https://portal.azure.com/#home
- https://azure.microsoft.com/it-it/pricing/calculator/
- https://github.com/Azure-Samples/digital-twins-samples/tree/main/AdtSampleApp/SampleClientApp

## Set up an Azure Digital Twins instance

https://learn.microsoft.com/en-us/azure/digital-twins/how-to-set-up-instance-portal

```shell
az login
az account set --subscription "0ba29932-c188-4a88-bf98-b163c7ca1a0f"
az provider register --namespace 'Microsoft.DigitalTwins'
az extension add --upgrade --name azure-iot
az group create --location westus2 --name filiera
az dt create --dt-name dtFiliera --resource-group filiera --location westus2

# l'assegnazione del ruolo va fatta dal portale perche' non riesco a recuperare il mio id utente
az dt role-assignment create --dt-name dtFiliera --assignee "<Azure-AD-user-principal-name-of-user-to-assign>" --role "Azure Digital Twins Data Owner"

```

![alt text](image.png)

```shell
az dt show --dt-name dtFiliera
```

## Learn about the sample building scenario and instantiate the pre-written components

modificare il file SampleClientApp/appsettings.json

```json
{
  "instanceUrl": "https://<your-Azure-Digital-Twins-instance-host-name>"
}
```

con

```json
{
  "instanceUrl": "https://dtFiliera.api.wus2.digitaltwins.azure.net"
}
```

e poi runniamo

```shell
$ dotnet run
$ SetupBuildingScenario

Initializing Building Scenario...
Deleting all twins...

Deleting all twins
Step 1: Find all twins
Step 2: Find and remove relationships for each twin...
Step 3: Delete all twins
Creating 1 floor, 1 room and 1 thermostat...
Uploading CreateModels, ThermostatModel, SpaceModel models
Reading from /home/paola/Downloads/digital-twins-samples-main/AdtSampleApp/SampleClientApp/Models
Submitting models: ThermostatModel.json, SpaceModel.json...
Model(s) created successfully!
Creating SpaceModel and Thermostat...
Preparing...
Submitting...
Twin 'floor1' created successfully!
Preparing...
Submitting...
Twin 'room21' created successfully!
Preparing...
Submitting...
Twin 'thermostat67' created successfully!
Creating edges between the Floor, Room and Thermostat
Submitting...
Relationship floor_to_room_edge of type contains created successfully from floor1 to room21!
Submitting...
Relationship room_to_therm_edge of type contains created successfully from room21 to thermostat67!
```

con questi comandi stiamo istanziando i digital twins!

![alt text](image-1.png)

![alt text](image-2.png)

```shell
$ Query

Response:
{
  "$dtId": "room21",
  "$etag": "W/\u002257bd2147-bb81-45ac-9f51-a2921deec175\u0022",
  "DisplayName": "Room 21",
  "Location": "Puget Sound",
  "Temperature": 0,
  "ComfortIndex": 0,
  "$metadata": {
    "$model": "dtmi:contosocom:DigitalTwins:Space;1",
    "DisplayName": {
      "lastUpdateTime": "2024-07-16T20:10:14.330962\u002B00:00",
      "sourceTime": null
    },
    "Location": {
      "lastUpdateTime": "2024-07-16T20:10:14.330962\u002B00:00",
      "sourceTime": null
    },
    "Temperature": {
      "lastUpdateTime": "2024-07-16T20:10:14.330962\u002B00:00",
      "sourceTime": null
    },
    "ComfortIndex": {
      "lastUpdateTime": "2024-07-16T20:10:14.330962\u002B00:00",
      "sourceTime": null
    },
    "$lastUpdateTime": "2024-07-16T20:10:14.330962\u002B00:00"
  }
}
Response:
{
  "$dtId": "floor1",
  "$etag": "W/\u0022377588f5-9771-43f7-bdfa-c2ca9cade61f\u0022",
  "DisplayName": "Floor 1",
  "Location": "Puget Sound",
  "Temperature": 0,
  "ComfortIndex": 0,
  "$metadata": {
    "$model": "dtmi:contosocom:DigitalTwins:Space;1",
    "DisplayName": {
      "lastUpdateTime": "2024-07-16T20:10:13.8899752\u002B00:00",
      "sourceTime": null
    },
    "Location": {
      "lastUpdateTime": "2024-07-16T20:10:13.8899752\u002B00:00",
      "sourceTime": null
    },
    "Temperature": {
      "lastUpdateTime": "2024-07-16T20:10:13.8899752\u002B00:00",
      "sourceTime": null
    },
    "ComfortIndex": {
      "lastUpdateTime": "2024-07-16T20:10:13.8899752\u002B00:00",
      "sourceTime": null
    },
    "$lastUpdateTime": "2024-07-16T20:10:13.8899752\u002B00:00"
  }
}
Response:
{
  "$dtId": "thermostat67",
  "$etag": "W/\u002221df980a-e392-4232-9695-2edba68059a2\u0022",
  "DisplayName": "Thermostat 67",
  "Location": "Puget Sound",
  "FirmwareVersion": "1.3.9",
  "Temperature": 0,
  "ComfortIndex": 0,
  "$metadata": {
    "$model": "dtmi:contosocom:DigitalTwins:Thermostat;1",
    "DisplayName": {
      "lastUpdateTime": "2024-07-16T20:10:14.6751745\u002B00:00",
      "sourceTime": null
    },
    "Location": {
      "lastUpdateTime": "2024-07-16T20:10:14.6751745\u002B00:00",
      "sourceTime": null
    },
    "FirmwareVersion": {
      "lastUpdateTime": "2024-07-16T20:10:14.6751745\u002B00:00",
      "sourceTime": null
    },
    "Temperature": {
      "lastUpdateTime": "2024-07-16T20:10:14.6751745\u002B00:00",
      "sourceTime": null
    },
    "ComfortIndex": {
      "lastUpdateTime": "2024-07-16T20:10:14.6751745\u002B00:00",
      "sourceTime": null
    },
    "$lastUpdateTime": "2024-07-16T20:10:14.6751745\u002B00:00"
  }
}
End Query

```

## Use an Azure Functions app to route simulated telemetry from an IoT Hub device into digital twin properties

Ora pubblichiamo!

```shell

# Create an Azure storage account by running the following command:
az storage account create --name storageaccountcc6pi --location westus2 --resource-group filiera --sku Standard_LRS

# Create an Azure function app by running the following command:
az functionapp create --name myFunctionDTfiliera --storage-account storageaccountcc6pi --functions-version 4 --consumption-plan-location westus2 --runtime dotnet-isolated --resource-group filiera

#### Attenzione ../SimpleFunctionApp
$ dotnet publish -c Release -o publish
$ cd publish && zip -r ../publish.zip ./*.*
$ realpath publish.zip
$ az functionapp deployment source config-zip --resource-group filiera --name myFunctionDTfiliera --src /home/paola/Downloads/digital-twins-samples-main/AdtSampleApp/SampleFunctionsApp/publish.zip

# Se questi due comandi falliscono ricontrolla il deploy e la costruzione dello zip, deve essere ricorsivo e senza la cartella publish
$ az functionapp function show --resource-group filiera --name myFunctionDTfiliera --function-name ProcessDTRoutedData
$ az functionapp function show --resource-group filiera --name myFunctionDTfiliera --function-name ProcessHubToDTEvents

az functionapp identity assign --resource-group filiera --name myFunctionDTfiliera

{
  "principalId": "15a98ab3-3e5f-4377-82b9-e0188879464c",
  "tenantId": "7519d0cd-2106-47d9-adcb-320023abff57",
  "type": "SystemAssigned",
  "userAssignedIdentities": null
}

$ az dt role-assignment create --resource-group filiera --dt-name dtFiliera --assignee 15a98ab3-3e5f-4377-82b9-e0188879464c --role "Azure Digital Twins Data Owner"

{
  "condition": null,
  "conditionVersion": null,
  "createdBy": null,
  "createdOn": "2024-07-16T20:38:09.717100+00:00",
  "delegatedManagedIdentityResourceId": null,
  "description": null,
  "id": "/subscriptions/0ba29932-c188-4a88-bf98-b163c7ca1a0f/resourceGroups/filiera/providers/Microsoft.DigitalTwins/digitalTwinsInstances/dtFiliera/providers/Microsoft.Authorization/roleAssignments/749e9b56-5505-490e-a502-b713b785b365",
  "name": "749e9b56-5505-490e-a502-b713b785b365",
  "principalId": "15a98ab3-3e5f-4377-82b9-e0188879464c",
  "principalType": "ServicePrincipal",
  "resourceGroup": "filiera",
  "roleDefinitionId": "/subscriptions/0ba29932-c188-4a88-bf98-b163c7ca1a0f/providers/Microsoft.Authorization/roleDefinitions/bcd981a7-7f74-457b-83e1-cceb9e632ffe",
  "scope": "/subscriptions/0ba29932-c188-4a88-bf98-b163c7ca1a0f/resourceGroups/filiera/providers/Microsoft.DigitalTwins/digitalTwinsInstances/dtFiliera",
  "type": "Microsoft.Authorization/roleAssignments",
  "updatedBy": "0d1e8c69-60ae-4a9e-a4dd-926aeca3fd08",
  "updatedOn": "2024-07-16T20:38:09.966102+00:00"
}

az functionapp config appsettings set --resource-group filiera --name myFunctionDTfiliera --settings "ADT_SERVICE_URL=https://dtFiliera.api.wus2.digitaltwins.azure.net"
```

```shell
# Creazione dell'hub iot
az iot hub create --name iotFiliera --resource-group filiera --sku S1

az eventgrid event-subscription create --name iotFilieraSub --event-delivery-schema eventgridschema --source-resource-id /subscriptions/0ba29932-c188-4a88-bf98-b163c7ca1a0f/resourceGroups/filiera/providers/Microsoft.Devices/IotHubs/iotFiliera --included-event-types Microsoft.Devices.DeviceTelemetry --endpoint-type azurefunction --endpoint /subscriptions/0ba29932-c188-4a88-bf98-b163c7ca1a0f/resourceGroups/filiera/providers/Microsoft.Web/sites/myFunctionDTfiliera/functions/ProcessHubToDTEvents

az iot hub device-identity create --device-id thermostat67 --hub-name iotFiliera --resource-group filiera
{
  "authentication": {
    "symmetricKey": {
      "primaryKey": "DBjM+s6CF2loiW/h61NpRXNNuTtnmzPtTAIoTKXWX9Y=",
      "secondaryKey": "U0/pyAxsVqRqIMs/Zo2YJ+8a4IzRTdBSKAIoTPWha/c="
    },
    "type": "sas",
    "x509Thumbprint": {
      "primaryThumbprint": null,
      "secondaryThumbprint": null
    }
  },
  "capabilities": {
    "iotEdge": false
  },
  "cloudToDeviceMessageCount": 0,
  "connectionState": "Disconnected",
  "connectionStateUpdatedTime": "0001-01-01T00:00:00",
  "deviceId": "thermostat67",
  "deviceScope": null,
  "etag": "OTg0OTQ2MDAw",
  "generationId": "638567614255751683",
  "lastActivityTime": "0001-01-01T00:00:00",
  "parentScopes": null,
  "status": "enabled",
  "statusReason": null,
  "statusUpdatedTime": "0001-01-01T00:00:00"
}

az iot hub connection-string show --hub-name iotFiliera
{
  "connectionString": "HostName=iotFiliera.azure-devices.net;SharedAccessKeyName=iothubowner;SharedAccessKey=izyoD3dUi7wm94nqh9aZBFYfbURsMMes8AIoTH1HtyI="
}

az iot hub device-identity connection-string show --device-id thermostat67 --hub-name iotFiliera
{
  "connectionString": "HostName=iotFiliera.azure-devices.net;DeviceId=thermostat67;SharedAccessKey=DBjM+s6CF2loiW/h61NpRXNNuTtnmzPtTAIoTKXWX9Y="
}

# in DeviceSimulator
# modificare queste righe
# private const string iotHubConnectionString = "HostName=iotFiliera.azure-devices.net;SharedAccessKeyName=iothubowner;SharedAccessKey=izyoD3dUi7wm94nqh9aZBFYfbURsMMes8AIoTH1HtyI=";
# private const string deviceConnectionString = "HostName=iotFiliera.azure-devices.net;DeviceId=thermostat67;SharedAccessKey=DBjM+s6CF2loiW/h61NpRXNNuTtnmzPtTAIoTKXWX9Y=";

```

## Propagate changes through the twin graph by processing digital twin notifications with Azure Functions, endpoints, and routes

runnure il simulatore e il client in due finestre diverse
con `dotnet run` per entrambi. Al client passare `ObserveProperties thermostat67 Temperature`
e poi alla fine

![alt text](image-3.png)

![alt text](image-4.png)

![alt text](image-5.png)
