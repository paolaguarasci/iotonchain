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

const ACCOUNT_PVT_KEY = process.env.ACCOUNT_PVT_KEY as string;

const config = {
  solidity: {
    version: '0.8.24',
    settings: {
      optimizer: {
         enabled: true,
         runs: 1000
      }
    }
  },
  networks: {
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
    currency: 'EUR',
    darkMode: true,
    L1: 'polygon',
    currencyDisplayPrecision: 4,
    includeIntrinsicGas: true,
    coinmarketcap: COINMARKETCAP_API_KEY
  }
};

export default config;
