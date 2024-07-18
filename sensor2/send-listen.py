import logging
import threading
import time
import random
import os
import time
from dotenv import load_dotenv
from azure.identity import DefaultAzureCredential
from azure.digitaltwins.core import DigitalTwinsClient
load_dotenv() 


"""
FIXME:
Non si logga, devo capire come riuscire ad usare le variabili d'ambiente con i thread
"""

def listen(arg):
    while not arg["stop"]:
        get_twin = arg['service_client'].get_digital_twin(arg['digital_twin_id'])
        logging.debug("listen %s", get_twin[arg['propertye']])
        time.sleep(random.randint(1,5))

def send(arg):
    while not arg["stop"]:
        new_value = dict();
        new_value["path"] = '/Temperature'
        new_value["op"] = "replace"
        new_value["value"] = random.randint(10,20)
        arg['service_client'].update_digital_twin(arg['digital_twin_id'], [new_value])
        logging.debug("send %s", new_value["value"])
        time.sleep(random.randint(1,5))

def main():
    logging.basicConfig(
        level=logging.DEBUG,
        format="%(relativeCreated)6d %(threadName)s %(message)s"
    )

    url = os.getenv("DTURL")
    credential = DefaultAzureCredential()
    service_client = DigitalTwinsClient(url, credential)
    digital_twin_id = 'thermostat67'
    propertye = 'Temperature'

    info = {"stop": False, "credential": credential, "service_client": service_client, "digital_twin_id": digital_twin_id, "propertye": propertye}
    thread = threading.Thread(target=listen, args=(info,))
    thread_two = threading.Thread(target=send, args=(info,))
    thread.start()
    thread_two.start()

    while True:
        try:
            logging.debug("Checking in from main thread")
            time.sleep(0.75)
        except KeyboardInterrupt:
            info["stop"] = True
            logging.debug('Stopping')
            break
    thread.join()
    thread_two.join()

if __name__ == "__main__":
    main()
