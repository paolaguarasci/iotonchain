import '@nomicfoundation/hardhat-toolbox';
import '@nomicfoundation/hardhat-verify';
import '@nomicfoundation/hardhat-ethers';
import '@openzeppelin/hardhat-upgrades';
import { task } from 'hardhat/config';

require('dotenv').config();

const ETHERSCAN_API_KEY = process.env.ETHERSCAN_API_KEY as string;
const POLYSCAN_API_KEY = process.env.POLYSCAN_API_KEY as string;
const INFURA_API_KEY = process.env.INFURA_API_KEY as string;
const COINMARKETCAP_API_KEY = process.env.COINMARKETCAP_API_KEY as string;

// La chiave privata dell'account di metamask
const ACCOUNT_PVT_KEY = process.env.ACCOUNT_PVT_KEY as string;

task('accounts', 'Prints the list of accounts', async (taskArgs, hre) => {
  const accounts = await hre.ethers.getSigners();
  for (const account of accounts) {
    console.log(`${account.address}`);
  }
});

const config = {
  solidity: {
    version: '0.8.24',
    settings: {
      // evmVersion: 'paris',
      // optimizer: {
      //   enabled: true,
      //   runs: 1000
      // }
    }
  },
  defender: {
//    apiKey: process.env.DEFENDER_KEY as string,
//    apiSecret: process.env.DEFENDER_SECRET as string,
//    useDefenderDeploy: true
  },
  networks: {
    sepolia: {
      url: `https://sepolia.infura.io/v3/${INFURA_API_KEY}`,
      accounts: [ACCOUNT_PVT_KEY]
    },
    holesky: {
      url: `https://holesky.infura.io/v3/${INFURA_API_KEY}`,
      accounts: [ACCOUNT_PVT_KEY]
    },
    polygonAmoy: {
      url: `https://polygon-amoy-bor-rpc.publicnode.com`,
      accounts: [ACCOUNT_PVT_KEY]
    }
  },
  etherscan: {
    apiKey: {
      mainnet: ETHERSCAN_API_KEY,
      holesky: ETHERSCAN_API_KEY,
      polygonAmoy: POLYSCAN_API_KEY
    }
  },
  sourcify: {
    enabled: false
  },
  gasReporter: {
    enabled: true,
    // enabled: false,
    currency: 'EUR',
    darkMode: true,
    // L1: "ethereum",
    L1: 'polygon',
    // L1: 'avalanche',
    // L1: "binance",
    // L1: "fantom",
    // L1: "moonbeam",
    // L1: "moonriver",
    // L1: "gnosis",
    currencyDisplayPrecision: 4,
    includeIntrinsicGas: true,
    coinmarketcap: COINMARKETCAP_API_KEY
  }
};

export default config;
