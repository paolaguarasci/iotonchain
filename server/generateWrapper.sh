#!/bin/bash

basePath="/home/paola/workspace/iotOnChain/contracts/artifacts/contracts";
javaFolder="/home/paola/workspace/iotOnChain/server/src/main/java"
packageOutput="it.unical.IoTOnChain.chain"

contracts=$(ls contracts/contracts);
for c in $contracts
do
  v2=${c::-4}
  echo $c;
  web3j generate truffle --truffle-json=$basePath/$c/$v2.json -o $javaFolder -p $packageOutput
done
