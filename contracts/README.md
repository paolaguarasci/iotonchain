# Sample Hardhat Project

```shell
#lancia la rete locale
npx hardhat node

#avvia i test
npx hardhat test

#deploy sulla localhost (per campiare rete usare --network RETE_CONFIGURATA)
npx hardhat ignition deploy ./ignition/modules/Lock.ts

# Check delle variabili di ambiete che e' necessario configurare
# sono conservate in /home/USER/.config/hardhat-nodejs/vars.json
npx hardhat vars setup # setup guidato
npx hardhat vars list  # lista semplice
```

# Creazione e compilazione del contratto

Il riferimento è [OpenZeppelin](https://docs.openzeppelin.com/learn/developing-smart-contracts)

Avvio di una rete di test Ethereum like in locale

```shell
npx hardhat node
```

In un'altra shell

```shell
# Compila i .sol nella cartella contracts
npx hardhat compile

# Deploy il contratto sulla rete di test
# Lo script può essere cosi semplice oppure più complesso
# Fai riferimento a https://hardhat.org/ignition/docs/getting-started#overview
npx hardhat run --network polygonAmoy scripts/box.deploy.js

# Apre una shell interattiva tipo REPL sulla rete
npx hardhat console --network polygonAmoy
```

# Utilizzo del contratto compilato con Web3j (su spring boot)

```shell

# Costruzione del wrapper
#     Va usato il tool a linea di comando che si installa con
#     curl -L get.web3j.io | sh && source ~/.web3j/source.sh
web3j generate truffle --truffle-json=./contracts/contracts/Box.sol/Box.json -o ./server/src/main/java -p it.unical.IoTOnChain.chain
```


npm install -g npm@10.8.2
@nomicfoundation/hardhat-toolbox
